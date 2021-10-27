package DeadVariableParameter.Util;

import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class ThrowStmtCollector extends VoidVisitorAdapter<Void> {
    List<String> throwStmt = new ArrayList<>();

    @Override
    public void visit(ThrowStmt vd, Void arg ) {
        super.visit(vd, arg);
        this.throwStmt.add(vd.getExpression().toString());
    }

    public List<String> getThrowStmt() {
        return throwStmt;
    }
}
