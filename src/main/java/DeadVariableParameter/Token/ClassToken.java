package DeadVariableParameter.Token;

import java.util.*;

public class ClassToken {
    private String className;
    private String location;
    private String packageName;

    private String modifier; // 1. default class / 2. interface / 3. abstract class

    private List<String> extendsClassStmt = new ArrayList<>();
    private List<ClassToken> extendsClass = new ArrayList<>();
    private List<String> importClassStmt = new ArrayList<>();
    private List<ClassToken> importClass = new ArrayList<>();
    private List<ClassToken> ClassInSamePackageToDetect = new ArrayList<>();

    private List<ClassToken> childClassToDetect = new ArrayList<>();
    private List<ClassToken> classThatImportToDetect = new ArrayList<>();

    private List<ObjectVariableToken> staticField = new ArrayList<>();
    private List<ObjectVariableToken> field = new ArrayList<>();

    private List<ConstructorToken> constructorTokenList = new ArrayList<>();
    private List<MethodToken> methodTokenList = new ArrayList<>();

    private List<String> methodCalls = new ArrayList<>();
    private List<String> assignExpr = new ArrayList<>();
    private List<String> objectCreationExpr = new ArrayList<>();
    private List<String> IfStmt = new ArrayList<>();
    private List<String> ForStmt = new ArrayList<>();
    private List<String> ForeachStmt = new ArrayList<>();
    private List<String> ReturnStmt = new ArrayList<>();
    private List<String> WhileStmt = new ArrayList<>();
    private List<String> DoStmt = new ArrayList<>();
    private List<String> SwitchStmt = new ArrayList<>();
    private List<String> VariableDeclarator = new ArrayList<>();
    private List<String> ArrayAccess = new ArrayList<>();
    private List<String> SwitchEntryStmt = new ArrayList<>();
    private List<String> SynchronizedStmt = new ArrayList<>();
    private List<String> ThrowStmt = new ArrayList<>();

    private List<ObjectVariableToken> AliveStaticField = new ArrayList<>();
    private List<ObjectVariableToken> DeadStaticField = new ArrayList<>();
    private List<ObjectVariableToken> AliveField = new ArrayList<>();
    private List<ObjectVariableToken> DeadField = new ArrayList<>();
    private List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
    private List<ObjectVariableToken> deadObjectVariableToken = new ArrayList<>();
    private List<ObjectVariableToken> allAliveObjectVariableToken = new ArrayList<>();
    private List<ObjectVariableToken> allDeadObjectVariableToken = new ArrayList<>();

    private List<ObjectParameterToken> AllAliveParameter = new ArrayList<>();
    private List<ObjectParameterToken> AllDeadParameter = new ArrayList<>();

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }



    public List<String> getExtendsClassStmt() {
        return extendsClassStmt;
    }

    public void setExtendsClassStmt(List<String> extendsClassStmt) {
        this.extendsClassStmt = extendsClassStmt;
    }

    public List<String> getImportClassStmt() {
        return importClassStmt;
    }

    public void setImportClassStmt(List<String> importClassStmt) {
        this.importClassStmt = importClassStmt;
    }



    public List<ClassToken> getExtendsClass() {
        return extendsClass;
    }

    public void setExtendsClass(List<ClassToken> extendsClass) {
        this.extendsClass = extendsClass;
    }

    public List<ClassToken> getImportClass() {
        return importClass;
    }

    public void setImportClass(List<ClassToken> importClass) {
        this.importClass = importClass;
    }

    public List<ClassToken> getClassInSamePackageToDetect() {
        return ClassInSamePackageToDetect;
    }

    public void setClassInSamePackageToDetect(List<ClassToken> classInSamePackageToDetect) {
        ClassInSamePackageToDetect = classInSamePackageToDetect;
    }



    public List<ClassToken> getChildClassToDetect() {
        return childClassToDetect;
    }

    public void addChildClassToDetect(ClassToken childClassToDetect) {
        this.childClassToDetect.add(childClassToDetect);
    }

    public List<ClassToken> getClassThatImportToDetect() {
        return classThatImportToDetect;
    }

    public void addClassThatImportToDetect(ClassToken classThatImportToDetect) {
        this.classThatImportToDetect.add(classThatImportToDetect);
    }



    public List<ObjectVariableToken> getStaticField() {
        return staticField;
    }

    public List<ObjectVariableToken> getField() {
        return field;
    }

    public void setStaticField(List<ObjectVariableToken> staticFieldList) {
        this.staticField = staticFieldList;
    }

    public void setField(List<ObjectVariableToken> field) {
        this.field = field;
    }



    public List<ConstructorToken> getConstructorTokenList() {
        return constructorTokenList;
    }

    public void setConstructorTokenList(List<ConstructorToken> constructorTokenList) {
        this.constructorTokenList = constructorTokenList;
    }

    public List<MethodToken> getMethodTokenList() {
        return methodTokenList;
    }

    public void setMethodTokenList(List<MethodToken> methodTokenList) {
        this.methodTokenList = methodTokenList;
    }



    public List<String> getMethodCalls() {
        return methodCalls;
    }

    public void setMethodCalls(List<String> methodCalls) {
        this.methodCalls = methodCalls;
    }

    public List<String> getAssignExpr() {
        return assignExpr;
    }

    public void setAssignExpr(List<String> assignExpr) {
        this.assignExpr = assignExpr;
    }

    public List<String> getObjectCreationExpr() {
        return objectCreationExpr;
    }

    public void setObjectCreationExpr(List<String> objectCreationExpr) {
        this.objectCreationExpr = objectCreationExpr;
    }

    public List<String> getIfStmt() {
        return IfStmt;
    }

    public void setIfStmt(List<String> ifStmt) {
        IfStmt = ifStmt;
    }

    public List<String> getForStmt() {
        return ForStmt;
    }

    public void setForStmt(List<String> forStmt) {
        ForStmt = forStmt;
    }

    public List<String> getForeachStmt() {
        return ForeachStmt;
    }

    public void setForeachStmt(List<String> foreachStmt) {
        ForeachStmt = foreachStmt;
    }

    public List<String> getReturnStmt() {
        return ReturnStmt;
    }

    public void setReturnStmt(List<String> returnStmt) {
        ReturnStmt = returnStmt;
    }

    public List<String> getWhileStmt() {
        return WhileStmt;
    }

    public void setWhileStmt(List<String> whileStmt) {
        WhileStmt = whileStmt;
    }

    public List<String> getDoStmt() {
        return DoStmt;
    }

    public void setDoStmt(List<String> doStmt) {
        DoStmt = doStmt;
    }

    public List<String> getSwitchStmt() {
        return SwitchStmt;
    }

    public void setSwitchStmt(List<String> switchStmt) {
        SwitchStmt = switchStmt;
    }

    public List<String> getVariableDeclarator() {
        return VariableDeclarator;
    }

    public void setVariableDeclarator(List<String> variableDeclarator) {
        VariableDeclarator = variableDeclarator;
    }

    public List<String> getArrayAccess() {
        return ArrayAccess;
    }

    public void setArrayAccess(List<String> arrayAccess) {
        ArrayAccess = arrayAccess;
    }

    public List<String> getSwitchEntryStmt() {
        return SwitchEntryStmt;
    }

    public void setSwitchEntryStmt(List<String> switchEntryStmt) {
        SwitchEntryStmt = switchEntryStmt;
    }

    public List<String> getSynchronizedStmt() {
        return SynchronizedStmt;
    }

    public void setSynchronizedStmt(List<String> synchronizedStmt) {
        SynchronizedStmt = synchronizedStmt;
    }

    public List<String> getThrowStmt() {
        return ThrowStmt;
    }

    public void setThrowStmt(List<String> throwStmt) {
        ThrowStmt = throwStmt;
    }



    public List<ObjectVariableToken> getDeadStaticField() {
        return DeadStaticField;
    }

    public List<ObjectVariableToken> getDeadField() {
        return DeadField;
    }

    public List<ObjectVariableToken> getDeadVariable() {
        return deadObjectVariableToken;
    }

    public void setDeadStaticField(List<ObjectVariableToken> deadStaticField) {
        DeadStaticField = deadStaticField;
    }

    public void setDeadField(List<ObjectVariableToken> deadField) {
        DeadField = deadField;
    }

    public void addDeadVariable(List<ObjectVariableToken> deadObjectVariableToken) {
        this.deadObjectVariableToken.addAll(deadObjectVariableToken);
    }



    public List<ObjectVariableToken> getAliveStaticField() {
        return AliveStaticField;
    }

    public List<ObjectVariableToken> getAliveField() {
        return AliveField;
    }

    public List<ObjectVariableToken> getAliveVariable() {
        return aliveObjectVariableToken;
    }

    public void setAliveStaticField(List<ObjectVariableToken> aliveStaticField) {
        AliveStaticField = aliveStaticField;
    }

    public void setAliveField(List<ObjectVariableToken> aliveField) {
        AliveField = aliveField;
    }

    public void setAliveVariable(List<ObjectVariableToken> aliveObjectVariableToken) {
        this.aliveObjectVariableToken = aliveObjectVariableToken;
    }



    public List<ObjectVariableToken> getAllAliveVariable() {
        return allAliveObjectVariableToken;
    }

    public List<ObjectVariableToken> getAllDeadVariable() {
        return allDeadObjectVariableToken;
    }

    public void setAllAliveVariable(List<ObjectVariableToken> allAliveObjectVariableToken) {
        this.allAliveObjectVariableToken = allAliveObjectVariableToken;
    }

    public void setAllDeadVariable(List<ObjectVariableToken> allDeadObjectVariableToken) {
        this.allDeadObjectVariableToken = allDeadObjectVariableToken;
    }



    public List<ObjectParameterToken> getAllAliveParameter() {
        return AllAliveParameter;
    }

    public void setAllAliveParameter(List<ObjectParameterToken> allAliveParameter) {
        AllAliveParameter = allAliveParameter;
    }

    public List<ObjectParameterToken> getAllDeadParameter() {
        return AllDeadParameter;
    }

    public void addDeadParameter(List<ObjectParameterToken> deadParameter) {
        AllDeadParameter.addAll(deadParameter);
    }
}
