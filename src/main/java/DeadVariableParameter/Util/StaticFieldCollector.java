package DeadVariableParameter.Util;

import DeadVariableParameter.Token.ObjectVariableToken;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class StaticFieldCollector extends VoidVisitorAdapter<Void> {
    private List<ObjectVariableToken> objectVariableTokenList = new ArrayList<>();

    @Override
    public void visit(ClassOrInterfaceDeclaration s, Void arg) {
        super.visit(s, arg);
        for (FieldDeclaration ff : s.getFields()) {
            if (ff.getModifiers().contains(Modifier.staticModifier())) {
                ObjectVariableToken objectVariableToken = new ObjectVariableToken();
                objectVariableToken.setVariableName(ff.getVariables().stream().iterator().next().getNameAsString());
                objectVariableToken.setParent(s.getName().toString());
                objectVariableToken.setModifier("static");
                objectVariableToken.setBeginLine(ff.getVariables().stream().iterator().next().getRange().get().begin.line);
                this.objectVariableTokenList.add(objectVariableToken);
            }
        }
    }

    public List<ObjectVariableToken> getVariableList() {
        return objectVariableTokenList;
    }
}
