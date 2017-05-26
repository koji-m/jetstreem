package pw.koj.jetstreem.cli;

import java.util.*;
import java.util.regex.*;
import java.io.*;
import org.ho.yaml.Yaml;
import pw.koj.jetstreem.parser.*;
import pw.koj.jetstreem.compiler.*;
import pw.koj.jetstreem.compiler.ir.*;

public class Main {
    public static final String SOURCE_ENCODING = "UTF-8";

    public static void main(String[] args) {
        NamespaceNode ast;
        String arg = args[0];

        try {
            ast = parseFile(new File(arg));
            String res = Yaml.dump(ast);
            res = res.replaceAll("!pw.koj.jetstreem.parser.", "");
            System.out.println("--------------------AST begin--------------------");
            System.out.println(res);
            System.out.println("--------------------AST end--------------------");

            AstToIrVisitor v = new AstToIrVisitor();
            Object ir = v.transform(ast);
            res = Yaml.dump(ir);
            res = res.replaceAll("!pw.koj.jetstreem.compiler.ir.", "");
            res = res.replaceAll("!pw.koj.jetstreem.compiler.", "");
            System.out.println("--------------------IR begin--------------------");
            System.out.println(res);
            System.out.println("--------------------IR end--------------------");

            BytecodeGenerator g = new BytecodeGenerator(null);
            g.generate((Namespace)ir, new LinkedList<>());
            g.writeClassFiles();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
}

