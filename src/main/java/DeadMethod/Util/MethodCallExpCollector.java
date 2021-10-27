package DeadMethod.Util;

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MethodCallExpCollector extends VoidVisitorAdapter<Void> {

    private List<String> methodCallExp = new ArrayList<>();

    @Override
    public void visit(MethodCallExpr n, Void arg) {
        super.visit(n, arg);
//        System.out.println("[Line "+n.getBegin().get().line+"] "+n.resolve().getQualifiedSignature());
        methodCallExp.add(n.resolve().getQualifiedSignature());
    }

    public List<String> getMethodCallExp() {
        return methodCallExp;
    }
}
