package DeadVariableParameter.Util;

import DeadVariableParameter.Token.ObjectVariableToken;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class VariableNameCollector extends VoidVisitorAdapter<Void> {
    private List<ObjectVariableToken> objectVariableTokenList = new ArrayList<>();
    private Integer beginLine;
    private Integer endLine;

    public VariableNameCollector(Integer beginLine, Integer endLine) {
        this.beginLine = beginLine;
        this.endLine = endLine;
    }

    @Override
    public void visit(VariableDeclarator vd, Void arg) {
        super.visit(vd, arg);
        if (vd.getRange().get().begin.line >= beginLine && vd.getRange().get().begin.line <= endLine) {
            ObjectVariableToken objectVariableToken = new ObjectVariableToken();
            objectVariableToken.setVariableName(vd.getNameAsString());
            objectVariableToken.setBeginLine(vd.getRange().get().begin.line);
            objectVariableToken.setModifier("local");
            this.objectVariableTokenList.add(objectVariableToken);
        }
    }

    public List<ObjectVariableToken> getVariableList() {
        return objectVariableTokenList;
    }
}