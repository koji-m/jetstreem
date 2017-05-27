package pw.koj.jetstreem.cli;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import java.lang.invoke.MethodHandle;
import java.net.*;
import org.ho.yaml.Yaml;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import pw.koj.jetstreem.parser.*;
import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

import static java.lang.invoke.MethodHandles.publicLookup;
import static java.lang.invoke.MethodType.methodType;

public class Main {
    public static final String SOURCE_ENCODING = "UTF-8";
    private static JCommander jc;
    private static GlobalArgs global;
    private static ClassLoader defaultLoader;

    public static void main(String[] args) throws Throwable {
        global = new GlobalArgs();
        jc = new JCommander(global);
        jc.setProgramName("jetstreem");

        jc.parse(args);

        execute();
    }

    private static void execute() throws Throwable {
        MainParam mp = new MainParam(global.mainParams);

        if (global.version) {
            System.out.println("jetstreem 0.0.1");
        }
        else if (global.help) {
            jc.usage();
        }
        else if (global.diagnose != null) {
            if (mp.notSpecified()) {
                usageWithError("no input file specified");
                return;
            }

            if (global.diagnose.equals("ir")) {
                NamespaceNode ast = parseFile(new File(mp.file()));
                printAst(ast);
                Object ir = generateIr(ast);
                printIr(ir);
            }
            else if (global.diagnose.equals("ast")) {
                NamespaceNode ast = parseFile(new File(mp.file()));
                printAst(ast);
            }
            else {
                usageWithError("option: diagnose syntax error");
            }
            return;
        }
        else if (global.classPath != null) {
            runClass(global.classPath, mp.classArgs());
            //run(klass, global.arguments.toArray(new String[global.arguments.size()]));
        }
        else if (mp.strmFileSpecified()) {
            NamespaceNode ast = parseFile(new File(mp.file()));
            Object ir = generateIr(ast);
            String mainClass = generateClassFiles(ir);
            runClass(mainClass, mp.fileArgs());
        }
        else {
            usageWithError("invalid syntax");
        }
    }

    private static void usageWithError(String msg) {
        System.out.println(msg);
        jc.usage();
    }

    private static void printAst(NamespaceNode ast) {
        String res = Yaml.dump(ast).replaceAll("!pw.koj.jetstreem.parser.", "");
        System.out.println("--------------------AST begin--------------------");
        System.out.println(res);
        System.out.println("--------------------AST end--------------------");
    }

    private static void printIr(Object ir) {
        String res = Yaml.dump(ir)
            .replaceAll("!pw.koj.jetstreem.compiler.ir.", "")
            .replaceAll("!pw.koj.jetstreem.compiler.", "");
        System.out.println("--------------------IR begin--------------------");
        System.out.println(res);
        System.out.println("--------------------IR end--------------------");
    }

    public static String simpleNameOf(String fileName) throws Exception {
        String regex = "(.*)\\.strm$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(fileName);
        if (m.find()) {
            return m.group(1);
        }
        else {
            throw new Exception("file name error: " + fileName);
        }
    }

    public static NamespaceNode parseFile(File file) throws Exception {
        return newFileParser(file).program(simpleNameOf(file.getName()));
    }

    public static Parser newFileParser(File file) throws Exception {
          BufferedReader rdr =
              new BufferedReader(
                  new InputStreamReader(new FileInputStream(file),
                                        SOURCE_ENCODING));
          return new Parser(rdr, file.getName());
    }

    public static Object generateIr(NamespaceNode ast) throws Exception {
        AstToIrVisitor v = new AstToIrVisitor();
        return v.transform(ast);
    }

    public static String generateClassFiles(Object ir) throws Exception {
        BytecodeGenerator g = new BytecodeGenerator(null);
        g.generate((Namespace)ir, new LinkedList<>());
        return g.writeClassFiles();
    }

    public static void runClass(String className, String[] args) throws Throwable {
        Class<?> klass = Class.forName(className, true, initClassLoader());
        run(klass, args);
    }

    private static void run(Class<?> klass, String[] args) throws Throwable {
        MethodHandle main;
        main = publicLookup().findStatic(klass, "main", methodType(void.class, String[].class));
        main.invoke(args);
    }

    private static StrmClassLoader initClassLoader() throws MalformedURLException {
        URLClassLoader parent = parentClassLoader();
        StrmClassLoader cl = new StrmClassLoader(parent);
        Thread.currentThread().setContextClassLoader(cl);
        return cl;
    }

    private static URLClassLoader parentClassLoader() throws MalformedURLException {
        String[] classPaths = System.getenv("CLASSPATH").split(":");
        int len = classPaths.length + 1;
        URL[] urls = new URL[len];

        for (int i = 1; i < len; i++) {
            urls[i] = new File(classPaths[i-1]).toURI().toURL();
        }

        urls[0] = new File(".").toURI().toURL();

        return new URLClassLoader(urls);
    }

    static class GlobalArgs {
        @Parameter(names = {"--version", "-v"}, description = "prints version")
        boolean version;

        @Parameter(names = {"--help", "-h"}, description = "prints help")
        boolean help;

        @Parameter(names = {"--diagnose", "-d"}, description = "\"ast\" prints AST, \"ir\" prints IR")
        String diagnose;

        @Parameter(names = {"--classpath", "-c"}, description = "run class file")
        String classPath;

        @Parameter(description = "file [args...]")
        List<String> mainParams = new ArrayList<>();
    }

    static class MainParam {
        private List<String> params;

        public MainParam(List<String> params) {
            this.params = params;
        }

        public boolean notSpecified() {
            return params.isEmpty();
        }

        public boolean strmFileSpecified() {
            try {
                String filename = new File(params.get(0)).getName();
                return Pattern.compile("(.*)\\.strm$").matcher(filename).find();
            }
            catch (Exception e) {
                return false;
            }
        }

        public String file() {
            return params.get(0);
        }

        public String[] classArgs() {
            return params.toArray(new String[params.size()]);
        }

        public String[] fileArgs() {
            return params.subList(1, params.size()).toArray(new String[params.size() - 1]);
        }
    }
}

