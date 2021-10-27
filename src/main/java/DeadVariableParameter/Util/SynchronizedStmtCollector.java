package DeadVariableParameter.Util;

import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedStmtCollector extends VoidVisitorAdapter<Void> {
    List<String> synchronizedStmt = new ArrayList<>();

    @Override
    public void visit(SynchronizedStmt vd, Void arg) {
        super.visit(vd, arg);

        this.synchronizedStmt.add(vd.getExpression().toString());
    }

    public List<String> getSynchronizedStmt() {
        return synchronizedStmt;
    }
}
