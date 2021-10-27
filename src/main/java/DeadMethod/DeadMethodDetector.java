package DeadMethod;

import DeadMethod.Util.MethodVisitor;
import Files_Reader.File_Reader;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JarTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeadMethodDetector {
    // Timer
    private long start;
    private long end;
    private float time;

    // Fields for using in symbol solving
    private final List<CompilationUnit> searchCu = new ArrayList<>();
    private String source;
    private final List<String> jarPaths = new ArrayList<>();
    private final List<TypeSolver> jarLibs = new ArrayList<>();
    private JavaSymbolSolver javaSymbolSolver;
    private final List<MethodToken> methodTokens = new ArrayList<>();
    private List<String> methodInUsed = new ArrayList<>();

    private List<String> errorLog = new ArrayList<>();

    private List<String> allSearchPaths = new ArrayList<>();

    private List<CompilationUnit> sourceCu = new ArrayList<>();
    private List<String> allSourcePaths = new ArrayList<>();

    private String inputSearchPath;
    private String inputSourcePath;


    // Dead method has to separate compilation units, because of the SymbolSolving method.
    public void run(){

        start = System.currentTimeMillis();

        System.out.println("Setting Paths ...");
        setSources(inputSourcePath);
        setAllSourcePaths(inputSourcePath);
        setAllSearchPaths(inputSearchPath);

        System.out.println("Setting Symbol Solver ...");
        setSymbolSolver();

        System.out.println("Parsing AST ...");
        parseAST();

        System.out.println("Setting Method Token ...");
        setMethodToken();

        System.out.println("Detecting Dead Method ...");
        detectDeadMethod();

        end = System.currentTimeMillis();
        time = (end - start)/1000F;
    }

    // Method for using .jar library
    public void setJarLibs(String jarPath){
        System.out.println("Setting Jar Library Path ...");
        if(!(jarPath == null) || !jarPath.equals("") || !jarPath.equals(" ")){
            try {
                jarPaths.addAll(new File_Reader().readJarPath(jarPath));
                for(String path : jarPaths){
                    jarLibs.add(new JarTypeSolver(path));
                }
            }catch (IOException e){
                System.out.println("Error in setJarLibs in DeadMethod");
                e.printStackTrace();
            }
        }
    }

    // Set Symbol solver
    private void setSymbolSolver(){
        ReflectionTypeSolver reflectionTypeSolver = new ReflectionTypeSolver();
        TypeSolver javaParserTypeSolver = new JavaParserTypeSolver(new File(source));

        CombinedTypeSolver typeSolver = new CombinedTypeSolver();
        typeSolver.add(reflectionTypeSolver);
        typeSolver.add(javaParserTypeSolver);

        if(!jarLibs.isEmpty()){
            for(TypeSolver jar : jarLibs){
                typeSolver.add(jar);
            }
        }
        javaSymbolSolver = new JavaSymbolSolver(typeSolver);
        StaticJavaParser.getConfiguration().setSymbolResolver(javaSymbolSolver);
    }

    // This method will parsing search file to Cu.
    private void parseAST() {

        for (String tmpPath : allSearchPaths) {
            try {
                CompilationUnit cu = StaticJavaParser.parse(new File(tmpPath));
                searchCu.add(cu);
            } catch (FileNotFoundException e) {
                errorLog.add("FileNotFoundException in DeadMethodDetector.parseAST: " + e.getMessage());
            }
        }

        for (String tmp : allSourcePaths) {
            try {
                System.out.println("Searching method call in : " + tmp);
                CompilationUnit cu = StaticJavaParser.parse(new File(tmp));
                cu.findAll(MethodCallExpr.class).forEach(mce ->
                        methodInUsed.add(mce.resolve().getQualifiedSignature())
                );
            } catch (FileNotFoundException e) {
                errorLog.add("FileNotFound at parseAST in: " + tmp+"\t"+ e.getMessage());
            } catch (UnsolvedSymbolException e) {
                errorLog.add("UnsolvedSymbolException at parseAST in: " + tmp+"\t"+e.getMessage());
            } catch (StackOverflowError e){
                errorLog.add("StackOverFlowException at parseAST in: "+ tmp+"\t"+e.getMessage());
            }catch (RuntimeException e){
                errorLog.add("RuntimeException at parseAST in: "+ tmp+"\t"+e.getMessage());
            }catch (Exception e){
                errorLog.add("Exception at parseAST in: "+ tmp+"\t"+e.getMessage());
            }
        }
    }

    // Set method tokens from compilation units.
    // This method will get all method define in each file.
    private void setMethodToken() {
        for(CompilationUnit cuTmp : searchCu){
            try {

                // Visit method declaration in AST.
                MethodVisitor methodVisitor = new MethodVisitor();
                methodVisitor.visit(cuTmp, null);

                // get method names and lines
                List<String> methodNames = methodVisitor.getMethodName();
                List<Integer> lines = methodVisitor.getBeginLine();
                List<String> className = methodVisitor.getClassName();

                for (int i = 0; i < methodNames.size(); i++) {
                    MethodToken methodToken = new MethodToken();
                    if (cuTmp.getStorage().isPresent()) {
                        // Get file name.
                        methodToken.setFile(cuTmp.getStorage().get().getFileName());
                        // Get package name.
                        methodToken.setPackageName(cuTmp.getPackageDeclaration().get().getNameAsString());
                        // Get path.
                        methodToken.setPath(cuTmp.getStorage().get().getPath().toString());
                    }
                    methodToken.setName(methodNames.get(i));
                    methodToken.setQualifiedSignature(methodVisitor
                            .getSignatureByName(methodNames.get(i)));
                    methodToken.setLine(lines.get(i));
                    methodToken.setClassName(className.get(i));

                    methodTokens.add(methodToken);
                }
            } catch (UnsolvedSymbolException e) {
                errorLog.add("Error create method Token : UnsolvedSymbol Exception" +
                        " in: " + cuTmp.getStorage().get().getPath() + " " + e.getMessage());
            } catch (Exception e) {
                errorLog.add("Error create method Token in: " + cuTmp.getStorage().get().getPath() +
                        " " + e.getMessage());
            }
        }
    }

    private void detectDeadMethod(){



        // Changing Method token dead status.
        for (MethodToken methodToken : methodTokens){
            if(methodInUsed.contains(methodToken.getQualifiedSignature())){
                methodToken.setDead(false);
            }if(methodToken.getName().equals("main")){
                methodToken.setDead(false);
            }
        }
    }

    public void createReport(String reportLocation){
        System.out.println("Create Result Report ...");
        FileWriter f;
        BufferedWriter bw;
        try {
            f = new FileWriter(reportLocation+"/DeadMethod.txt");
            bw = new BufferedWriter(f);
            if(time >= 60){
                float timeInMinutes = time/60;
                bw.write("Total time: "+timeInMinutes+" minutes. \n");
            }else{
                bw.write("Total time: "+time+" seconds.\n");
            }
            for (MethodToken methodToken : methodTokens) {
                if(methodToken.getDead() == true){
                    String tmp = "Method "+methodToken.getName()+
                            " "+methodToken.getPackageName()+"."+
                            methodToken.getClassName()+"."+methodToken.getName()+
                            "("+methodToken.getFile()+":"+methodToken.getLine()+")"+
                            "\n";
                    bw.write(tmp);
                }
            }
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("Report Created.");
        System.out.println("Detecting Completed.");
        System.out.println("Total time: "+time+" seconds");
    }

    public void setSources(String source) {
        this.source = source;
    }

    private void setAllSearchPaths(String path){
        File_Reader fileReader = new File_Reader();
        this.allSearchPaths.addAll(fileReader.readPath(path));
    }

    private void setAllSourcePaths(String path){
        File_Reader fileReader = new File_Reader();
        this.allSourcePaths.addAll(fileReader.readPath(path));
    }

    // Method for create error report
    public void createErrorLog(String location){
        if(!errorLog.isEmpty()){
            FileWriter f;
            BufferedWriter bw;
            try {
                f = new FileWriter(location + "/ErrorLog.txt");
                bw = new BufferedWriter(f);
                for (String s : errorLog) {
                    bw.write(s+"\n");
                }
                bw.close();
            }catch (FileNotFoundException e){
                System.out.println("FileNotFoundException in createReport:ErrorLog");
            } catch (IOException e) {
                System.out.println("IOException in createReport:ErrorLog");
            }
        }
    }

    public List<MethodToken> getMethodTokens() {
        return methodTokens;
    }

    public List<String> getMethodInUsed() {
        return methodInUsed;
    }

    public List<String> getAllSearchPaths() {
        return allSearchPaths;
    }

    public List<CompilationUnit> getSearchCu() {
        return searchCu;
    }

    public void setInputSearchPath(String inputSearchPath) {
        this.inputSearchPath = inputSearchPath;
    }

    public void setInputSourcePath(String inputSourcePath) {
        this.inputSourcePath = inputSourcePath;
    }
}
