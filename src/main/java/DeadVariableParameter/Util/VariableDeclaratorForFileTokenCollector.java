package DeadVariableParameter.Util;

import DeadVariableParameter.Token.MethodToken;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclaratorForFileTokenCollector extends VoidVisitorAdapter<Void> {
    private List<String> variableDeclarator = new ArrayList<>();
    private List<MethodToken> methodTokenList;
    private boolean accept;

    public VariableDeclaratorForFileTokenCollector(List<MethodToken> methodTokenList) {
        this.methodTokenList = methodTokenList;
    }

    @Override
    public void visit(VariableDeclarator vd, Void arg) {
        super.visit(vd, arg);
//        System.out.println("Check for : " + vd.getParentNodeForChildren().toString() + " at line : " + vd.getRange().get().begin.line);
        if (this.methodTokenList.size() > 0) {
            for (int i=0; i<this.methodTokenList.size(); i++) {
                if (vd.getRange().get().begin.line >= this.methodTokenList.get(i).getBeginLine() && vd.getRange().get().begin.line <= this.methodTokenList.get(i).getEndLine()) {
                    this.accept = false;
//                System.out.println("String is in method line between " + this.methodTokenList.get(i).getBeginLine() + " - " + this.methodTokenList.get(i).getEndLine());
                    break;
                }
                else if (vd.getRange().get().begin.line <= this.methodTokenList.get(i).getBeginLine() || vd.getRange().get().begin.line >= this.methodTokenList.get(i).getEndLine()){
                    this.accept = true;
//                System.out.println("String is not in method line between " + this.methodTokenList.get(i).getBeginLine() + " - " + this.methodTokenList.get(i).getEndLine());
                }
            }
        }
        else if (this.methodTokenList.size() == 0) {
            if (vd.getParentNodeForChildren().toString().contains("=")) {
                this.variableDeclarator.add(returnAfterEqualSign(vd.getParentNodeForChildren().toString()));
            }
        }
//        System.out.println("accept = " + this.accept);
//        System.out.println();
        if (this.accept == true) {
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