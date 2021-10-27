package DeadVariableParameter.Util;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

// VisitorAdapter for class name collecting

public class ModifierCollector extends VoidVisitorAdapter<Void> {
    String modifier;

    // use Class/Interface Declaration object
    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        super.visit(n, arg);

        if (n.isAbstract()) {
            modifier = "abstract";
        }else if (n.isInterface()) {
            modifier = "interface";
        }else {
            modifier = "default";
        }
    }

    public String getModifier() {
        return modifier;
    }
}
