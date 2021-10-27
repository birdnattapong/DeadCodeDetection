package DeadVariableParameter.Util;

import DeadVariableParameter.Token.ObjectParameterToken;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class MethodDeclarationCollector extends VoidVisitorAdapter<Void> {
    private List<String> methodName = new ArrayList<>();
    private List<Integer> beginLine = new ArrayList<>();
    private List<Integer> endLine = new ArrayList<>();
    private List<String> returnType = new ArrayList<>();
    private List<ObjectParameterToken> parameters = new ArrayList<>();

    @Override
    public void visit(MethodDeclaration md, Void arg) {
        super.visit(md, arg);
        this.methodName.add(md.getNameAsString());
        this.beginLine.add(md.getTokenRange().get().getBegin().getRange().get().begin.line);
        this.endLine.add(md.getTokenRange().get().getEnd().getRange().get().end.line);
        this.returnType.add(md.getTypeAsString());

        for (int i=0; i<md.getParameters().size(); i++) {
            ObjectParameterToken objectParameterToken = new ObjectParameterToken();
            objectParameterToken.setParameterName(md.getParameters().get(i).getName().getIdentifier());
            objectParameterToken.setBeginLine(md.getParameters().get(i).getRange().get().begin.line);
            this.parameters.add(objectParameterToken);
        }
    }

    public List<String> getMethodName() {
        return methodName;
    }

    public List<Integer> getBeginLine() {
        return beginLine;
    }

    public List<Integer> getEndLine() {
        return endLine;
    }

    public List<String> getReturnType() {
        return returnType;
    }

    public List<ObjectParameterToken> getParameters() {
        return parameters;
    }
}
