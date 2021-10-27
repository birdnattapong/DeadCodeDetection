package DeadVariableParameter.Util;

import DeadVariableParameter.Token.ObjectVariableToken;
import DeadVariableParameter.Token.MethodToken;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.*;

public class FieldNameCollector extends VoidVisitorAdapter<Void> {
    private List<ObjectVariableToken> objectVariableTokenList = new ArrayList<>();
    private List<MethodToken> methodTokenList;
    private boolean accept;

    public FieldNameCollector(List<MethodToken> methodTokenList) {
        this.methodTokenList = methodTokenList;
    }

    @Override
    public void visit(VariableDeclarator vd, Void arg) {
        super.visit(vd, arg);

        ObjectVariableToken objectVariableToken = new ObjectVariableToken();

        if (this.methodTokenList.size() > 0) {
            for (int j = 0; j < this.methodTokenList.size(); j++) {
                if (vd.getRange().get().begin.line >= this.methodTokenList.get(j).getBeginLine() && vd.getRange().get().begin.line <= this.methodTokenList.get(j).getEndLine()) {
                    this.accept = false;
                    break;
                } else if (vd.getRange().get().begin.line <= this.methodTokenList.get(j).getBeginLine() || vd.getRange().get().begin.line >= this.methodTokenList.get(j).getEndLine()) {
                    this.accept = true;
                }
            }
        } else if (this.methodTokenList.size() == 0) {
            objectVariableToken.setVariableName(vd.getNameAsString());
            objectVariableToken.setBeginLine(vd.getRange().get().begin.line);
            objectVariableToken.setModifier("common");
            this.objectVariableTokenList.add(objectVariableToken);
        }

        if (this.accept == true) {
            objectVariableToken.setVariableName(vd.getNameAsString());
            objectVariableToken.setBeginLine(vd.getRange().get().begin.line);
            objectVariableToken.setModifier("common");
            this.objectVariableTokenList.add(objectVariableToken);
        }
    }

    public List<ObjectVariableToken> getVariableList() {
        return objectVariableTokenList;
    }
}
