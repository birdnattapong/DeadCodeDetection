package DeadMethod.Util;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodVisitor extends VoidVisitorAdapter<Void> {
    private List<String> methodName = new ArrayList<>();
    private List<Integer> beginLine = new ArrayList<>();
    private List<Integer> endLine = new ArrayList<>();
    private List<String> className = new ArrayList<>();

    // Key = name, Value = Signature
    private Map<String,String> signatureByName = new HashMap<>();

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        super.visit(n, arg);
        // get all non-override method
        if(!n.getAnnotations().toString().contains("@Override")){
            this.methodName.add(n.getNameAsString());
            this.beginLine.add(n.getTokenRange().get().getBegin().getRange().get().begin.line);
            this.endLine.add(n.getTokenRange().get().getEnd().getRange().get().end.line);
            this.signatureByName.put(n.getNameAsString(),n.resolve().getQualifiedSignature());
            this.className.add(n.resolve().getClassName());
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

    public String getSignatureByName(String methodName){
        return signatureByName.get(methodName);
    }

    public List<String> getClassName() {
        return className;
    }
}
