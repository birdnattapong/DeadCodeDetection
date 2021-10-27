package DeadVariableParameter.Util;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclaratorCollector extends VoidVisitorAdapter<Void> {
    private List<String> variableDeclarator = new ArrayList<>();
    private Integer beginLine;
    private Integer endLine;

    public VariableDeclaratorCollector(Integer beginLine, Integer endLine) {
        this.beginLine = beginLine;
        this.endLine = endLine;
    }

    @Override
    public void visit(VariableDeclarator vd, Void arg) {
        super.visit(vd, arg);
        if (vd.getRange().get().begin.line >= beginLine && vd.getRange().get().begin.line <= endLine) {
            if (vd.getParentNodeForChildren().toString().contains("=")) {
                this.variableDeclarator.add(returnAfterEqualSign(vd.getParentNodeForChildren().toString()));
            }
        }
    }

    public List<String> getVariableDeclarator() {
        return variableDeclarator;
    }

    private String returnAfterEqualSign(String variableDeclarator) {
        String newString = variableDeclarator.substring(variableDeclarator.indexOf("=")+1);
        return newString.trim();
    }
}