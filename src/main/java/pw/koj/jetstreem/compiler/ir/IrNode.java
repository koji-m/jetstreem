package pw.koj.jetstreem.compiler.ir;

import java.util.*;
import pw.koj.jetstreem.compiler.*;

public interface IrNode {
    public void accept(BytecodeGenerator visitor, RuntimeContext<RuntimeScope> ctx) throws Exception;
}

