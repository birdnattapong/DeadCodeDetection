package DeadClassInterface.Util;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

// VisitorAdapter for class name collecting
public class InterfaceNameCollector extends VoidVisitorAdapter<List<String>> {

    private List<Integer> declarationLine = new ArrayList<>();
    @Override
    public void visit(ClassOrInterfaceDeclaration n, List<String> arg) {
        super.visit(n, arg);
        // Check if a interface
        if(n.isClassOrInterfaceDeclaration()&& n.isInterface()){
            arg.add(n.getNameAsString());
            declarationLine.add(n.getRange().get().begin.line);
        }
    }

    public List<Integer> getDeclarationLine() {
        return declarationLine;
    }
}
