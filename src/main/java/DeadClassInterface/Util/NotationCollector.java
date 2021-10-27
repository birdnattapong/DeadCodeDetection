package DeadClassInterface.Util;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class NotationCollector extends VoidVisitorAdapter<List<String>> {
    @Override
    public void visit(ClassOrInterfaceDeclaration n, List<String> arg) {
        super.visit(n, arg);
        NodeList notaions = n.getAnnotations();
        for (Object node : notaions){
            arg.add(node.toString());
        }
    }
}
