package DeadClassInterface.Util;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class ImportCollector extends VoidVisitorAdapter<Void> {

    private List<String> importStm = new ArrayList<>();

    @Override
    public void visit(ImportDeclaration n, Void arg) {
        super.visit(n, arg);
        this.importStm.add(n.getNameAsString());
    }

    public List<String> getImportStm() {
        return importStm;
    }
}
