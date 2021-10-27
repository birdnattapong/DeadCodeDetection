package DeadVariableParameter.Util;

import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class SwitchEntryStmtCollector extends VoidVisitorAdapter<Void> {
    List<String> switchEntryStmt = new ArrayList<>();

    @Override
    public void visit(SwitchEntry vd, Void arg) {
        super.visit(vd, arg);

        if (!vd.getLabels().isEmpty()) {
            this.switchEntryStmt.add(vd.getLabels().stream().iterator().next().toString());
        }
    }

    public List<String> getSwitchEntryStmt() {
        return switchEntryStmt;
    }
}
