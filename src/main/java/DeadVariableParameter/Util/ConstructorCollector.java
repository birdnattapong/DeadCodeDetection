package DeadVariableParameter.Util;

import DeadVariableParameter.Token.ObjectParameterToken;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConstructorCollector extends VoidVisitorAdapter<Void> {
    private List<String> constructorName = new ArrayList<>();
    private List<Integer> beginLine = new ArrayList<>();
    private List<Integer> endLine = new ArrayList<>();
    private List<ObjectParameterToken> parameters = new ArrayList<>();

    @Override
    public void visit(ConstructorDeclaration cd, Void arg) {
        super.visit(cd, arg);

        this.constructorName.add(cd.getNameAsString());
        this.beginLine.add(cd.getTokenRange().get().getBegin().getRange().get().begin.line);
        this.endLine.add(cd.getTokenRange().get().getEnd().getRange().get().end.line);

        for (int i=0; i<cd.getParameters().size(); i++) {
            ObjectParameterToken objectParameterToken = new ObjectParameterToken();
            objectParameterToken.setParameterName(cd.getParameters().get(i).getName().getIdentifier());
            objectParameterToken.setBeginLine(cd.getParameters().get(i).getRange().get().begin.line);
            this.parameters.add(objectParameterToken);
        }
    }

    public List<String> getConstructorName() {
        return constructorName;
    }

    public List<Integer> getBeginLine() {
        return beginLine;
    }

    public List<Integer> getEndLine() {
        return endLine;
    }

    public List<ObjectParameterToken> getParameters() {
        return parameters;
    }
}
