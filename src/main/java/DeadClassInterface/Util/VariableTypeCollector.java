package DeadClassInterface.Util;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.List;

public class VariableTypeCollector extends VoidVisitorAdapter<List<String>> {

    @Override
    public void visit(VariableDeclarator n, List<String> arg) {
        super.visit(n, arg);
        arg.add(n.getTypeAsString());
    }
}
