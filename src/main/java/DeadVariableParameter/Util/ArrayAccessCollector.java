package DeadVariableParameter.Util;

import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class ArrayAccessCollector extends VoidVisitorAdapter<Void> {
    public List<String> arrayAccessIndex = new ArrayList<>();

    @Override
    public void visit(ArrayAccessExpr arrayAccessExpr, Void arg) {
        super.visit(arrayAccessExpr, arg);
        this.arrayAccessIndex.add(arrayAccessExpr.getIndex().toString());
    }

    public List<String> getArrayAccessIndex() {
        return arrayAccessIndex;
    }
}
