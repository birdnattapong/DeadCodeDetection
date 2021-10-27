package DeadVariableParameter.TokenGenerator;

import DeadVariableParameter.Token.ObjectParameterToken;
import DeadVariableParameter.Token.ClassToken;
import DeadVariableParameter.Token.ConstructorToken;
import DeadVariableParameter.Token.MethodToken;
import DeadVariableParameter.Token.ObjectVariableToken;
import DeadVariableParameter.Util.*;
import com.github.javaparser.ast.CompilationUnit;

import java.util.*;

public class ClassTokenGenerator {
    List<CompilationUnit> cu;
    List<ClassToken> classTokenList = new ArrayList<>();
    List<String> packageNameList = new ArrayList<>();

    public ClassTokenGenerator(List<CompilationUnit> cu) {
        this.cu = new ArrayList<>(cu);

        for (int i=0; i<this.cu.size(); i++) {
            //if packageName is not in packageNameList -> result -> all package name
            if (!this.cu.get(i).getPackageDeclaration().isEmpty()) {
                if (!this.packageNameList.contains(this.cu.get(i).getPackageDeclaration().get().getNameAsString())) {
                    this.packageNameList.add(this.cu.get(i).getPackageDeclaration().get().getNameAsString());
                }
            }
            else if (this.cu.get(i).getPackageDeclaration().isEmpty()) {
                if (!this.packageNameList.contains("default")) {
                    this.packageNameList.add("default");
                }
            }
        }

        //loop to construct Token
        for (int i=0; i<this.cu.size(); i++) {
            //First step : construct Constructor and Method Token
            List<ConstructorToken> constructorTokenTemp = new ArrayList<>();
            ConstructorCollector constructorCollector = new ConstructorCollector();
            setConstructorTokenTemp(this.cu.get(i), constructorTokenTemp, constructorCollector);

            List<MethodToken> methodTokenTemp = new ArrayList<>();
            MethodDeclarationCollector methodDeclarationCollector = new MethodDeclarationCollector();
            setMethodTokenTemp(this.cu.get(i), methodTokenTemp, methodDeclarationCollector);

            //Second step : construct File Token -> put Method Token to File Token
            ClassToken classToken = new ClassToken();
            classToken.setClassName(replaceExtention(this.cu.get(i).getStorage().get().getFileName()));
            classToken.setLocation(this.cu.get(i).getStorage().get().getPath().toString());

            if (this.cu.get(i).getPackageDeclaration().isEmpty()) {
                classToken.setPackageName("default");
            }
            else if (!this.cu.get(i).getPackageDeclaration().isEmpty()) {
                classToken.setPackageName(this.cu.get(i).getPackageDeclaration().get().getNameAsString());
            }

            classToken.setModifier(getModifier(this.cu.get(i)));
            classToken.setExtendsClassStmt(getExtendsClassStmt(this.cu.get(i)));
            classToken.setImportClassStmt(getImportClassStmt(this.cu.get(i)));

            classToken.setConstructorTokenList(constructorTokenTemp);
            classToken.setMethodTokenList(methodTokenTemp);

            classToken.setStaticField(getStaticFieldForFileTokenTemp(this.cu.get(i)));
            classToken.setField(getFieldNameForFileTokenTemp(this.cu.get(i), methodTokenTemp, classToken.getStaticField()));
            classToken.setMethodCalls(getMethodCallForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setAssignExpr(getAssignExprForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setObjectCreationExpr(getObjectCreationExprForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setIfStmt(getIfStmtForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setForStmt(getForStmtForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setForeachStmt(getForeachStmtForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setReturnStmt(getReturnStmtForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setWhileStmt(getWhileStmtForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setDoStmt(getDoStmtForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setSwitchStmt(getSwitchStmtForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setVariableDeclarator(getVariableDeclaratorForFileTokenTemp(this.cu.get(i), methodTokenTemp));
            classToken.setArrayAccess(getArrayAccessForFileTokenTemp(this.cu.get(i)));
            classToken.setSwitchEntryStmt(getCaseStmtForFileTokenTemp(this.cu.get(i)));
            classToken.setSynchronizedStmt(getSynchronizedStmtForFileTokenTemp(this.cu.get(i)));
            classToken.setThrowStmt(getThrowStmtForFileTokenTemp(this.cu.get(i)));

            this.classTokenList.add(classToken);
        }

        //to set extendsClass
        for (int i = 0; i<this.classTokenList.size(); i++) {
            if (!this.classTokenList.get(i).getExtendsClassStmt().isEmpty()) {
                this.classTokenList.get(i).setExtendsClass(getExtendsClass(this.classTokenList.get(i)));
            }
        }

        //to set ImportClass
        for (int i = 0; i<this.classTokenList.size(); i++) {
            if (!this.classTokenList.get(i).getImportClassStmt().isEmpty()) {
                this.classTokenList.get(i).setImportClass(getImportClass(this.classTokenList.get(i)));
            }
        }

        //to add childClassThatExtendsToDetect to parent
        for (int i = 0; i<this.classTokenList.size(); i++) {
            if (!this.classTokenList.get(i).getExtendsClass().isEmpty()) {
                for (int j = 0; j<this.classTokenList.get(i).getExtendsClass().size(); j++) {
                    this.classTokenList.get(i).getExtendsClass().get(j).addChildClassToDetect(this.classTokenList.get(i));
                }
            }
        }

        //to add classThatImportToDetect to parent
        for (int i = 0; i<this.classTokenList.size(); i++) {
            if (!this.classTokenList.get(i).getImportClass().isEmpty()) {
                for (int j = 0; j<this.classTokenList.get(i).getImportClass().size(); j++) {
                    this.classTokenList.get(i).getImportClass().get(j).addClassThatImportToDetect(this.classTokenList.get(i));
                }
            }
        }

        //to set ClassInSamePackageToDetect
        for (int i = 0; i<this.classTokenList.size(); i++) {
            String packageName = this.classTokenList.get(i).getPackageName();
            List<ClassToken> AllFileInPackage = new ArrayList<>();

            for (int j = 0; j<this.classTokenList.size(); j++) {
                if (this.classTokenList.get(j).getPackageName().equals(packageName)) {
                    if (!this.classTokenList.get(j).getClassName().equals(this.classTokenList.get(i).getClassName())) {
                        AllFileInPackage.add(this.classTokenList.get(j));
                    }
                }
            }

            this.classTokenList.get(i).setClassInSamePackageToDetect(getFileInSamePackageToDetect(this.classTokenList.get(i), AllFileInPackage));
        }
    }

    //method to set ConstructorToken
    private void setConstructorTokenTemp(CompilationUnit cu, List<ConstructorToken> constructorToken, ConstructorCollector constructorCollector) {
        constructorCollector.visit(cu, null);
        List<String> constructorName = constructorCollector.getConstructorName();
        List<Integer> beginLine = constructorCollector.getBeginLine();
        List<Integer> endLine = constructorCollector.getEndLine();
        List<ObjectParameterToken> objectParameterTokenList = constructorCollector.getParameters();

        for (int i=0; i<constructorName.size(); i++) {
            ConstructorToken constructorTokenTemp = new ConstructorToken();
            constructorTokenTemp.setConstructorName(constructorName.get(i));
            constructorTokenTemp.setBeginLine(beginLine.get(i));
            constructorTokenTemp.setEndLine(endLine.get(i));

            List<ObjectParameterToken> objectParameterTokensTemp = new ArrayList<>();

            for (int j=0; j<objectParameterTokenList.size(); j++) {
                if (objectParameterTokenList.get(j).getBeginLine() >= beginLine.get(i) && objectParameterTokenList.get(j).getBeginLine() <= endLine.get(i)) {
                    objectParameterTokensTemp.add(objectParameterTokenList.get(j));
                }
            }

            constructorTokenTemp.setParameterToken(objectParameterTokensTemp);

            VariableNameCollector variableNameCollector = new VariableNameCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setVariable(getVariableForMethodTokenTemp(cu, variableNameCollector));

            MethodCallCollector methodCallCollector = new MethodCallCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setMethodCalls(getMethodCallForMethodTokenTemp(cu, methodCallCollector));

            AssignExprCollector assignExprCollector = new AssignExprCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setAssignExpr(getAssignExprForMethodTokenTemp(cu, assignExprCollector));

            ObjectCreationExprCollector objectCreationExprCollector = new ObjectCreationExprCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setObjectCreationExpr(getObjectCreationExprForMethodTokenTemp(cu, objectCreationExprCollector));

            IfStmtCollector ifStmtCollector = new IfStmtCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setIfStmt(getIfStmtForMethodTokenTemp(cu, ifStmtCollector));

            ForStmtCollector forStmtCollector = new ForStmtCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setForStmt(getForStmtForMethodTokenTemp(cu, forStmtCollector));

            ForeachStmtCollector foreachStmtCollector = new ForeachStmtCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setForeachStmt(getForeachStmtForMethodTokenTemp(cu, foreachStmtCollector));

            ReturnStmtCollector returnStmtCollector = new ReturnStmtCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setReturnStmt(getReturnStmtForMethodTokenTemp(cu, returnStmtCollector));

            WhileStmtCollector whileStmtCollector = new WhileStmtCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setWhileStmt(getWhileStmtForMethodTokenTemp(cu, whileStmtCollector));

            DoStmtCollector doStmtCollector = new DoStmtCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setDoStmt(getDoStmtForMethodTokenTemp(cu, doStmtCollector));

            SwitchStmtCollector switchStmtCollector = new SwitchStmtCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setSwitchStmt(getSwitchStmtForMethodTokenTemp(cu, switchStmtCollector));

            VariableDeclaratorCollector variableDeclaratorCollector = new VariableDeclaratorCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setVariableDeclarator(getVariableDeclaratorForMethodTokenTemp(cu, variableDeclaratorCollector));

            ExplicitConstructorStmtCollector explicitConstructorStmtCollector = new ExplicitConstructorStmtCollector(beginLine.get(i), endLine.get(i));
            constructorTokenTemp.setExplicitConstructorStmt(getExplicitConstructorStmtForMethodTokenTemp(cu, explicitConstructorStmtCollector));

            constructorToken.add(constructorTokenTemp);
        }
    }

    //method to set MethodTokenTemp
    private void setMethodTokenTemp(CompilationUnit cu, List<MethodToken> methodToken, MethodDeclarationCollector methodDeclarationCollector) {
        methodDeclarationCollector.visit(cu, null);
        List<String> methodName = methodDeclarationCollector.getMethodName();
        List<Integer> beginLine = methodDeclarationCollector.getBeginLine();
        List<Integer> endLine = methodDeclarationCollector.getEndLine();
        List<String> returnType = methodDeclarationCollector.getReturnType();
        List<ObjectParameterToken> objectParameterTokenList = methodDeclarationCollector.getParameters();

        for (int i=0; i<methodName.size(); i++) {
            MethodToken methodTokenTemp = new MethodToken();
            methodTokenTemp.setMethodName(methodName.get(i));
            methodTokenTemp.setBeginLine(beginLine.get(i));
            methodTokenTemp.setEndLine(endLine.get(i));
            methodTokenTemp.setReturntype(returnType.get(i));

            List<ObjectParameterToken> objectParameterTokensTemp = new ArrayList<>();

            for (int j=0; j<objectParameterTokenList.size(); j++) {
                if (objectParameterTokenList.get(j).getBeginLine() >= beginLine.get(i) && objectParameterTokenList.get(j).getBeginLine() <= endLine.get(i)) {
                    objectParameterTokensTemp.add(objectParameterTokenList.get(j));
                }
            }

            methodTokenTemp.setParameterToken(objectParameterTokensTemp);

            VariableNameCollector variableNameCollector = new VariableNameCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setVariable(getVariableForMethodTokenTemp(cu, variableNameCollector));

            MethodCallCollector methodCallCollector = new MethodCallCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setMethodCalls(getMethodCallForMethodTokenTemp(cu, methodCallCollector));

            AssignExprCollector assignExprCollector = new AssignExprCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setAssignExpr(getAssignExprForMethodTokenTemp(cu, assignExprCollector));

            ObjectCreationExprCollector objectCreationExprCollector = new ObjectCreationExprCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setObjectCreationExpr(getObjectCreationExprForMethodTokenTemp(cu, objectCreationExprCollector));

            IfStmtCollector ifStmtCollector = new IfStmtCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setIfStmt(getIfStmtForMethodTokenTemp(cu, ifStmtCollector));

            ForStmtCollector forStmtCollector = new ForStmtCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setForStmt(getForStmtForMethodTokenTemp(cu, forStmtCollector));

            ForeachStmtCollector foreachStmtCollector = new ForeachStmtCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setForeachStmt(getForeachStmtForMethodTokenTemp(cu, foreachStmtCollector));

            ReturnStmtCollector returnStmtCollector = new ReturnStmtCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setReturnStmt(getReturnStmtForMethodTokenTemp(cu, returnStmtCollector));

            WhileStmtCollector whileStmtCollector = new WhileStmtCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setWhileStmt(getWhileStmtForMethodTokenTemp(cu, whileStmtCollector));

            DoStmtCollector doStmtCollector = new DoStmtCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setDoStmt(getDoStmtForMethodTokenTemp(cu, doStmtCollector));

            SwitchStmtCollector switchStmtCollector = new SwitchStmtCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setSwitchStmt(getSwitchStmtForMethodTokenTemp(cu, switchStmtCollector));

            VariableDeclaratorCollector variableDeclaratorCollector = new VariableDeclaratorCollector(beginLine.get(i), endLine.get(i));
            methodTokenTemp.setVariableDeclarator(getVariableDeclaratorForMethodTokenTemp(cu, variableDeclaratorCollector));

            methodToken.add(methodTokenTemp);
        }
    }

    //method to get Variable within method (range begin-end)
    private List<ObjectVariableToken> getVariableForMethodTokenTemp(CompilationUnit cu, VariableNameCollector variableNameCollector) {
        variableNameCollector.visit(cu, null);
        List<ObjectVariableToken> objectVariableTokenList = variableNameCollector.getVariableList();
        return objectVariableTokenList;
    }

    //method to get MethodCall within method (range begin-end)
    private List<String> getMethodCallForMethodTokenTemp(CompilationUnit cu, MethodCallCollector methodCallCollector) {
        methodCallCollector.visit(cu, new ArrayList<>());
        List<String> methodcall = methodCallCollector.getMethodCall();
        return methodcall;
    }

    //method to get AssignExpr within method (range begin-end)
    private List<String> getAssignExprForMethodTokenTemp(CompilationUnit cu, AssignExprCollector assignExprCollector) {
        assignExprCollector.visit(cu, new ArrayList<>());
        List<String> assignExpr = assignExprCollector.getAssignExpr();
        return assignExpr;
    }

    //method to get ObjectCreationExpr within method (range begin-end)
    private List<String> getObjectCreationExprForMethodTokenTemp(CompilationUnit cu, ObjectCreationExprCollector objectCreationExprCollector) {
        objectCreationExprCollector.visit(cu, null);
        List<String> objectCreationExpr = objectCreationExprCollector.getObjectCreationExpr();
        return objectCreationExpr;
    }

    //method to get IfStmt within method (range begin-end)
    private List<String> getIfStmtForMethodTokenTemp(CompilationUnit cu, IfStmtCollector ifStmtCollector) {
        ifStmtCollector.visit(cu, new ArrayList<>());
        List<String> ifStmt = ifStmtCollector.getIfStmt();
        return ifStmt;
    }

    //method to get ForStmt within method (range begin-end)
    private List<String> getForStmtForMethodTokenTemp(CompilationUnit cu, ForStmtCollector forStmtCollector) {
        forStmtCollector.visit(cu, new ArrayList<>());
        List<String> forStmt = forStmtCollector.getForStmt();
        return forStmt;
    }

    //method to get ForeachStmt within method (range begin-end)
    private List<String> getForeachStmtForMethodTokenTemp(CompilationUnit cu, ForeachStmtCollector foreachStmtCollector) {
        foreachStmtCollector.visit(cu, new ArrayList<>());
        List<String> foreachStmt = foreachStmtCollector.getForeachStmt();
        return foreachStmt;
    }

    //method to get ReturnStmt within method (range begin-end)
    private List<String> getReturnStmtForMethodTokenTemp(CompilationUnit cu, ReturnStmtCollector returnStmtCollector) {
        returnStmtCollector.visit(cu, new ArrayList<>());
        List<String> returnStmt = returnStmtCollector.getReturnStmt();
        return returnStmt;
    }

    //method to get WhileStmt within method (range begin-end)
    private List<String> getWhileStmtForMethodTokenTemp(CompilationUnit cu, WhileStmtCollector whileStmtCollector) {
        whileStmtCollector.visit(cu, null);
        List<String> whileStmt = whileStmtCollector.getWhileStmt();
        return whileStmt;
    }

    //method to get DoStmt within method (range begin-end)
    private List<String> getDoStmtForMethodTokenTemp(CompilationUnit cu, DoStmtCollector doStmtCollector) {
        doStmtCollector.visit(cu, null);
        List<String> doStmt = doStmtCollector.getDoStmt();
        return doStmt;
    }

    //method to get SwitchStmt within method (range begin-end)
    private List<String> getSwitchStmtForMethodTokenTemp(CompilationUnit cu, SwitchStmtCollector switchStmtCollector) {
        switchStmtCollector.visit(cu, new ArrayList<>());
        List<String> switchStmt = switchStmtCollector.getSwitchStmt();
        return switchStmt;
    }

    //method to get VariableDeclarator within method (range begin-end)
    private List<String> getVariableDeclaratorForMethodTokenTemp(CompilationUnit cu, VariableDeclaratorCollector variableDeclaratorCollector) {
        variableDeclaratorCollector.visit(cu, null);
        List<String> variableDeclarator = variableDeclaratorCollector.getVariableDeclarator();
        return variableDeclarator;
    }

    //method to get explicitConstructorStmt
    private List<String> getExplicitConstructorStmtForMethodTokenTemp(CompilationUnit cu, ExplicitConstructorStmtCollector explicitConstructorStmtCollector) {
        explicitConstructorStmtCollector.visit(cu, null);
        List<String> explicitConstructorStmt = explicitConstructorStmtCollector.getExplicitConstructorInvocationStmt();
        return explicitConstructorStmt;
    }

    //method to replace fileNameExtention
    private String replaceExtention(String fileName) {
        return fileName.replaceAll("[.].*", "");
    }

    //method to get modifier of class (default, interface, abstract) ?
    private String getModifier(CompilationUnit cu) {
        ModifierCollector modifierCollector = new ModifierCollector();
        modifierCollector.visit(cu, null);
        return modifierCollector.getModifier();
    }

    //method to get parent class
    private List<String> getExtendsClassStmt(CompilationUnit cu) {
        List<String> extendsClass = new ArrayList<>();
        ClassExtensionCollector classExtensionCollector = new ClassExtensionCollector();
        classExtensionCollector.visit(cu, extendsClass);
        return extendsClass;
    }

    //method to get extends file token (not duplicate with import file token)
    private List<ClassToken> getExtendsClass(ClassToken classToken) {
        List<ClassToken> extendsClass = new ArrayList<>();

        for (int i = 0; i< classToken.getExtendsClassStmt().size(); i++) {
            for (int j = 0; j<this.classTokenList.size(); j++) {
                if (classToken.getExtendsClassStmt().get(i).equals(this.classTokenList.get(j).getClassName())) {
                    if (!extendsClass.contains(this.classTokenList.get(j))) {
                        extendsClass.add(this.classTokenList.get(j));
                    }
                }
            }
        }

        return extendsClass;
    }

    //method to get import class
    private List<String> getImportClassStmt(CompilationUnit cu) {
        ImportCollector importCollector = new ImportCollector();
        importCollector.visit(cu, null);
        List<String> fullImportStmt = importCollector.getFullImportStmt();
        List<String> importStmt = new ArrayList<>();

        for (int i=0; i<fullImportStmt.size(); i++) {
            StringTokenizer tokenizer = new StringTokenizer(fullImportStmt.get(i), ".");
            List<String> tokens = new ArrayList<>();

            while (tokenizer.hasMoreTokens()) {
                tokens.add(tokenizer.nextToken());
            }

            String packageNameToken = ""; // import package (not include class)
            String lastToken = ""; // import class

            for (int j=0; j<tokens.size(); j++) {
                if (j != tokens.size()-1 && j != tokens.size()-2) {
                    packageNameToken = packageNameToken + tokens.get(j) + ".";
                }
                else if (j != tokens.size()-1 && j == tokens.size()-2) {
                    packageNameToken = packageNameToken + tokens.get(j);
                }
                else if (j == tokens.size()-1) {
                    lastToken = tokens.get(j).replaceAll("\\s", "");
                }
            }

            for (int j=0; j<packageNameList.size(); j++) {
                if (packageNameToken.equals(packageNameList.get(j))) {
                    importStmt.add(packageNameToken + "." + lastToken);
                }
            }
        }

        return importStmt;
    }

    //method to get import file token
    private List<ClassToken> getImportClass(ClassToken classToken) {
        List<ClassToken> importClass = new ArrayList<>();
        List<String> fullImportStmt = classToken.getImportClassStmt();

        for (int i=0; i<fullImportStmt.size(); i++) {
            StringTokenizer tokenizer = new StringTokenizer(fullImportStmt.get(i), ".");
            List<String> tokens = new ArrayList<>();

            while (tokenizer.hasMoreTokens()) {
                tokens.add(tokenizer.nextToken());
            }

            String packageNameToken = ""; // import package (not include class)
            String lastToken = ""; // import class

            for (int j=0; j<tokens.size(); j++) {
                if (j != tokens.size()-1 && j != tokens.size()-2) {
                    packageNameToken = packageNameToken + tokens.get(j) + ".";
                }
                else if (j != tokens.size()-1 && j == tokens.size()-2) {
                    packageNameToken = packageNameToken + tokens.get(j);
                }
                else if (j == tokens.size()-1) {
                    lastToken = tokens.get(j).replaceAll("\\s", "");
                }
            }

            if (lastToken.equals("*")) {
                for (int j = 0; j<this.classTokenList.size(); j++) {
                    if (packageNameToken.equals(this.classTokenList.get(j).getPackageName())) {
                        if (!classToken.getExtendsClass().isEmpty()) {
                            for (int k = 0; k< classToken.getExtendsClass().size(); k++) {
                                if (!classToken.getExtendsClass().get(k).equals(this.classTokenList.get(j))) {
                                    if (!importClass.contains(this.classTokenList.get(j))) {
                                        importClass.add(this.classTokenList.get(j));
                                    }
                                }
                            }
                        }
                        else if (classToken.getExtendsClass().isEmpty()) {
                            if (!importClass.contains(this.classTokenList.get(j))) {
                                importClass.add(this.classTokenList.get(j));
                            }
                        }
                    }
                }
            }
            else if (!lastToken.equals("*")) {
                for (int j = 0; j<this.classTokenList.size(); j++) {
                    if (lastToken.equals(this.classTokenList.get(j).getClassName()) && packageNameToken.equals(this.classTokenList.get(j).getPackageName())) {
                        if (!classToken.getExtendsClass().isEmpty()) {
                            for (int k = 0; k< classToken.getExtendsClass().size(); k++) {
                                if (!classToken.getExtendsClass().get(k).equals(this.classTokenList.get(j))) {
                                    if (!importClass.contains(this.classTokenList.get(j))) {
                                        importClass.add(this.classTokenList.get(j));
                                    }
                                }
                            }
                        }
                        else if (classToken.getExtendsClass().isEmpty()) {
                            if (!importClass.contains(this.classTokenList.get(j))) {
                                importClass.add(this.classTokenList.get(j));
                            }
                        }
                    }
                }
            }
        }
        return importClass;
    }

    //method to get fileInSamePackageToDetect (not include extendsClass in same package)
    private List<ClassToken> getFileInSamePackageToDetect(ClassToken classToken, List<ClassToken> AllFileInPackage) {
        List<ClassToken> AllFileInPackageToDetect = new ArrayList<>();
        //check extendsClass not in AllFileInPackage
        if (!classToken.getChildClassToDetect().isEmpty()) {
            for (int i = 0; i< classToken.getChildClassToDetect().size(); i++) {
                for (int j=0; j<AllFileInPackage.size(); j++) {
                    if (AllFileInPackage.contains(classToken.getChildClassToDetect().get(i))) {
                        AllFileInPackage.remove(classToken.getChildClassToDetect().get(i));
                    }
                }
            }
        }

        AllFileInPackageToDetect.addAll(AllFileInPackage);
        return AllFileInPackageToDetect;
    }

    //method to get static field (not in method)
    private List<ObjectVariableToken> getStaticFieldForFileTokenTemp(CompilationUnit cu) {
        StaticFieldCollector staticFieldCollector = new StaticFieldCollector();
        staticFieldCollector.visit(cu, null);
        List<ObjectVariableToken> objectVariableToken = staticFieldCollector.getVariableList();

        return objectVariableToken;
    }

    //method to get Field name (not include Variable in method) (not include static field)
    private List<ObjectVariableToken> getFieldNameForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp, List<ObjectVariableToken> staticField) {
        FieldNameCollector fieldNameCollector = new FieldNameCollector(methodTokenTemp);
        fieldNameCollector.visit(cu, null);
        List<ObjectVariableToken> commonFieldName = fieldNameCollector.getVariableList();

        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (ObjectVariableToken FieldName : commonFieldName) {
            for (ObjectVariableToken staticFieldName : staticField) {
                if (FieldName.getVariableName().equals(staticFieldName.getVariableName())) {
                    if (FieldName.getBeginLine().equals(staticFieldName.getBeginLine()) && staticFieldName.getModifier().equals("static")) {
                        toRemove.add(FieldName);
                        break;
                    }
                }
            }
        }

        commonFieldName.removeAll(toRemove);

        return commonFieldName;
    }

    //method to get MethodCall (not include in method)
    private List<String> getMethodCallForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        MethodCallForFileTokenCollector methodCallForFileTokenCollector = new MethodCallForFileTokenCollector(methodTokenTemp);
        methodCallForFileTokenCollector.visit(cu, null);
        List<String> methodCall = methodCallForFileTokenCollector.getMethodCall();
        return methodCall;
    }

    //method to get AssignExpr (not include in method)
    private List<String> getAssignExprForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        AssignExprForFileTokenCollector assignExprForFileTokenCollector = new AssignExprForFileTokenCollector(methodTokenTemp);
        assignExprForFileTokenCollector.visit(cu, null);
        List<String> assignExpr = assignExprForFileTokenCollector.getAssignExpr();
        return assignExpr;
    }

    //method to get ObjectCreationExpr (not include in method)
    private List<String> getObjectCreationExprForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        ObjectCreationExprForFileTokenCollector objectCreationExprForFileTokenCollector = new ObjectCreationExprForFileTokenCollector(methodTokenTemp);
        objectCreationExprForFileTokenCollector.visit(cu, null);
        List<String> objectCreationExpr = objectCreationExprForFileTokenCollector.getObjectCreationExpr();
        return objectCreationExpr;
    }

    //method to get IfStmt (not include in method)
    private List<String> getIfStmtForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        IfStmtForFileTokenCollector ifStmtForFileTokenCollector = new IfStmtForFileTokenCollector(methodTokenTemp);
        ifStmtForFileTokenCollector.visit(cu, null);
        List<String> ifStmt = ifStmtForFileTokenCollector.getIfStmt();
        return ifStmt;
    }

    //method to get ForStmt (not include in method)
    private List<String> getForStmtForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        ForStmtForFileTokenCollector forStmtForFileTokenCollector = new ForStmtForFileTokenCollector(methodTokenTemp);
        forStmtForFileTokenCollector.visit(cu, null);
        List<String> forStmt = forStmtForFileTokenCollector.getForStmt();
        return forStmt;
    }

    //method to get ForeachStmt (not include in method)
    private List<String> getForeachStmtForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        ForeachStmtForFileTokenCollector foreachStmtForFileTokenCollector = new ForeachStmtForFileTokenCollector(methodTokenTemp);
        foreachStmtForFileTokenCollector.visit(cu, null);
        List<String> foreachStmt = foreachStmtForFileTokenCollector.getForeachStmt();
        return foreachStmt;
    }

    //method to get ReturnStmt (not include in method)
    private List<String> getReturnStmtForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        ReturnStmtForFileTokenCollector returnStmtForFileTokenCollector = new ReturnStmtForFileTokenCollector(methodTokenTemp);
        returnStmtForFileTokenCollector.visit(cu, null);
        List<String> returnStmt = returnStmtForFileTokenCollector.getReturnStmt();
        return returnStmt;
    }

    //method to get WhileStmt (not include in method)
    private List<String> getWhileStmtForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        WhileStmtForFileTokenCollector whileStmtForFileTokenCollector = new WhileStmtForFileTokenCollector(methodTokenTemp);
        whileStmtForFileTokenCollector.visit(cu, null);
        List<String> whileStmt = whileStmtForFileTokenCollector.getWhileStmt();
        return whileStmt;
    }

    //method to get DoStmt (not include in method)
    private List<String> getDoStmtForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        DoStmtForFileTokenCollector doStmtForFileTokenCollector = new DoStmtForFileTokenCollector(methodTokenTemp);
        doStmtForFileTokenCollector.visit(cu, null);
        List<String> doStmt = doStmtForFileTokenCollector.getDoStmt();
        return doStmt;
    }

    //method to get SwitchStmt (not include in method)
    private List<String> getSwitchStmtForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        SwitchStmtForFileTokenCollector switchStmtForFileTokenCollector = new SwitchStmtForFileTokenCollector(methodTokenTemp);
        switchStmtForFileTokenCollector.visit(cu, null);
        List<String> switchStmt = switchStmtForFileTokenCollector.getSwitchStmt();
        return switchStmt;
    }

    //method to get VariableDeclarator (not include in method)
    private List<String> getVariableDeclaratorForFileTokenTemp(CompilationUnit cu, List<MethodToken> methodTokenTemp) {
        VariableDeclaratorForFileTokenCollector variableDeclaratorForFileTokenCollector = new VariableDeclaratorForFileTokenCollector(methodTokenTemp);
        variableDeclaratorForFileTokenCollector.visit(cu, null);
        List<String> variableDeclarator = variableDeclaratorForFileTokenCollector.getVariableDeclarator();
        return variableDeclarator;
    }

    //method to get ArrayAccess
    private List<String> getArrayAccessForFileTokenTemp(CompilationUnit cu) {
        ArrayAccessCollector arrayAccessCollector = new ArrayAccessCollector();
        arrayAccessCollector.visit(cu, null);
        List<String> arrayAccess = arrayAccessCollector.getArrayAccessIndex();
        return arrayAccess;
    }

    //method to get Case Stmt
    private List<String> getCaseStmtForFileTokenTemp(CompilationUnit cu) {
        SwitchEntryStmtCollector switchEntryStmtCollector = new SwitchEntryStmtCollector();
        switchEntryStmtCollector.visit(cu, null);
        List<String> switchEntryStmt = switchEntryStmtCollector.getSwitchEntryStmt();
        return switchEntryStmt;
    }

    //method to get synchronizedStmt
    private List<String> getSynchronizedStmtForFileTokenTemp(CompilationUnit cu) {
        SynchronizedStmtCollector synchronizedStmtCollector = new SynchronizedStmtCollector();
        synchronizedStmtCollector.visit(cu, null);
        List<String> synchronizedStmt = synchronizedStmtCollector.getSynchronizedStmt();
        return synchronizedStmt;
    }

    //method to get ThrowStmt
    private List<String> getThrowStmtForFileTokenTemp(CompilationUnit cu) {
        ThrowStmtCollector throwStmtCollector = new ThrowStmtCollector();
        throwStmtCollector.visit(cu, null);
        List<String> throwStmt = throwStmtCollector.getThrowStmt();
        return throwStmt;
    }

    public List<ClassToken> getFileTokenList() {
        return classTokenList;
    }
}
