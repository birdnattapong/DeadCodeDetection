package DeadVariableParameter.Util;

import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExplicitConstructorStmtCollector extends VoidVisitorAdapter<Void> {
    private List<String> ExplicitConstructorInvocationStmt = new ArrayList<>();
    private Integer beginLine;
    private Integer endLine;

    public ExplicitConstructorStmtCollector(Integer beginLine, Integer endLine) {
        this.beginLine = beginLine;
        this.endLine = endLine;
    }

    @Override
    public void visit(ExplicitConstructorInvocationStmt se, Void arg) {
        super.visit(se, arg);

        if (se.getRange().get().begin.line >= beginLine && se.getRange().get().begin.line <= endLine) {
            this.ExplicitConstructorInvocationStmt.add(se.getParentNodeForChildren().toString());
        }
    }

    public List<String> getExplicitConstructorInvocationStmt() {
        return ExplicitConstructorInvocationStmt;
    }
}
