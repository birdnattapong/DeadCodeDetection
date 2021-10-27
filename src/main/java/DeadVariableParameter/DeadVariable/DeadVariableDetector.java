package DeadVariableParameter.DeadVariable;

import DeadVariableParameter.Token.ClassToken;
import DeadVariableParameter.Token.ObjectVariableToken;
import DeadVariableParameter.TokenGenerator.ClassTokenGenerator;
import Files_Reader.ASTParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeadVariableDetector {
    List<ClassToken> classTokenList;
    String inputSource;
    float AstTime;

    public DeadVariableDetector() { }

    //method to detect alive/dead static field -> set to FileToken
    private void detectDeadStaticField() {
        for (int i = 0; i<this.classTokenList.size(); i++) {
            DeadVariableDetectorOperator deadVariableDetectorOperator = new DeadVariableDetectorOperator();
            List<ObjectVariableToken> aliveStaticField = new ArrayList<>();

            if (!this.classTokenList.get(i).getStaticField().isEmpty()) {
                //step 1 : check with itself
                deadVariableDetectorOperator.checkRegexForStaticField(this.classTokenList.get(i), aliveStaticField, this.classTokenList.get(i));

                //step 2 : check with child classes that extend
                if (this.classTokenList.get(i).getStaticField().size() > 0) {
                    if (!this.classTokenList.get(i).getChildClassToDetect().isEmpty()) {
                        for (int j = 0; j<this.classTokenList.get(i).getChildClassToDetect().size(); j++) {
                            //case detect with child class use -> checkRegexForField method (send param 'childCase') -> because we use only variableName to detect (not Class.variableName)
                            deadVariableDetectorOperator.checkRegexForField(this.classTokenList.get(i), aliveStaticField, this.classTokenList.get(i).getChildClassToDetect().get(j), "childCase");
                        }
                    }
                }

                //step 3 : check with child classes that import
                if (this.classTokenList.get(i).getStaticField().size() > 0) {
                    if (!this.classTokenList.get(i).getClassThatImportToDetect().isEmpty()) {
                        for (int j = 0; j<this.classTokenList.get(i).getClassThatImportToDetect().size(); j++) {
                            deadVariableDetectorOperator.checkRegexForStaticField(this.classTokenList.get(i), aliveStaticField, this.classTokenList.get(i).getClassThatImportToDetect().get(j));
                        }
                    }
                }

                //step 4 : check with classes in same package
                if (this.classTokenList.get(i).getStaticField().size() > 0) {
                    if (!this.classTokenList.get(i).getClassInSamePackageToDetect().isEmpty()) {
                        for (int j = 0; j < this.classTokenList.get(i).getClassInSamePackageToDetect().size(); j++) {
                            deadVariableDetectorOperator.checkRegexForStaticField(this.classTokenList.get(i), aliveStaticField, this.classTokenList.get(i).getClassInSamePackageToDetect().get(j));
                        }
                    }
                }
            }

            //set aliveStaticField to FileToken
            if (aliveStaticField.size() > 0) {
                this.classTokenList.get(i).setAliveStaticField(aliveStaticField);
            }

            //set deadStaticField to FileToken
            if (this.classTokenList.get(i).getStaticField().size() > 0) {
                this.classTokenList.get(i).setDeadStaticField(this.classTokenList.get(i).getStaticField());
            }
        }
    }

    //method to detect alive/dead field -> set to FileToken
    private void detectDeadField() {
        for (int i = 0; i<this.classTokenList.size(); i++) {
            DeadVariableDetectorOperator deadVariableDetectorOperator = new DeadVariableDetectorOperator();
            List<ObjectVariableToken> aliveField = new ArrayList<>();

            if (!this.classTokenList.get(i).getField().isEmpty()) {
                //step 1 : check with itself
                deadVariableDetectorOperator.checkRegexForField(this.classTokenList.get(i), aliveField, this.classTokenList.get(i), "general");

                //step 2 : check with child classes that extend
                if (this.classTokenList.get(i).getField().size() > 0) {
                    if (!this.classTokenList.get(i).getChildClassToDetect().isEmpty()) {
                        for (int j = 0; j<this.classTokenList.get(i).getChildClassToDetect().size(); j++) {
                            deadVariableDetectorOperator.checkRegexForField(this.classTokenList.get(i), aliveField, this.classTokenList.get(i).getChildClassToDetect().get(j), "general");
                        }
                    }
                }

                //step 3 : check with child classes that import
                if (this.classTokenList.get(i).getField().size() > 0) {
                    if (!this.classTokenList.get(i).getClassThatImportToDetect().isEmpty()) {
                        for (int j = 0; j<this.classTokenList.get(i).getClassThatImportToDetect().size(); j++) {
                            deadVariableDetectorOperator.checkRegexForField(this.classTokenList.get(i), aliveField, this.classTokenList.get(i).getClassThatImportToDetect().get(j), "general");
                        }
                    }
                }

                //step 4 : check with classes in same package
                if (this.classTokenList.get(i).getField().size() > 0) {
                    if (!this.classTokenList.get(i).getClassInSamePackageToDetect().isEmpty()) {
                        for (int j = 0; j<this.classTokenList.get(i).getClassInSamePackageToDetect().size(); j++) {
                            deadVariableDetectorOperator.checkRegexForField(this.classTokenList.get(i), aliveField, this.classTokenList.get(i).getClassInSamePackageToDetect().get(j), "general");
                        }
                    }
                }
            }

            //set aliveField to FileToken
            if (aliveField.size() > 0) {
                this.classTokenList.get(i).setAliveField(aliveField);
            }

            //set deadField to FileToken
            if (this.classTokenList.get(i).getField().size() > 0) {
                this.classTokenList.get(i).setDeadField(this.classTokenList.get(i).getField());
            }
        }
    }

    //method to detect alive/dead variable -> set to FileToken
    private void detectDeadVariable() {
        for (int i = 0; i<this.classTokenList.size(); i++) {
            DeadVariableDetectorOperator deadVariableDetectorOperator = new DeadVariableDetectorOperator();
            List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();

            //check within constructor / method
            if (!this.classTokenList.get(i).getMethodTokenList().isEmpty() || !this.classTokenList.get(i).getConstructorTokenList().isEmpty()) {
                deadVariableDetectorOperator.checkRegexForVariable(this.classTokenList.get(i), aliveObjectVariableToken, this.classTokenList.get(i));
            }

            //set DeadVariable to FileToken
            if (aliveObjectVariableToken.size() > 0) {
                this.classTokenList.get(i).setAliveVariable(aliveObjectVariableToken);
            }

            //add deadVariable to FileToken
            if (!this.classTokenList.get(i).getMethodTokenList().isEmpty()){
                for (int j = 0; j<this.classTokenList.get(i).getMethodTokenList().size(); j++)  {
                    if (this.classTokenList.get(i).getMethodTokenList().get(j).getVariable().size() > 0) {
                        this.classTokenList.get(i).addDeadVariable(this.classTokenList.get(i).getMethodTokenList().get(j).getVariable());
                    }
                }
            }
        }
    }

    //to set all dead variable to filetoken
    private void setAllDeadVariable() {
        for (int i = 0; i<this.classTokenList.size(); i++) {
            List<ObjectVariableToken> allDeadObjectVariableToken = new ArrayList<>();

            if (!this.classTokenList.get(i).getDeadStaticField().isEmpty()) {
                allDeadObjectVariableToken.addAll(this.classTokenList.get(i).getDeadStaticField());
            }
            if (!this.classTokenList.get(i).getDeadField().isEmpty()) {
                allDeadObjectVariableToken.addAll(this.classTokenList.get(i).getDeadField());
            }
            if (!this.classTokenList.get(i).getDeadVariable().isEmpty()) {
                allDeadObjectVariableToken.addAll(this.classTokenList.get(i).getDeadVariable());
            }

            this.classTokenList.get(i).setAllDeadVariable(allDeadObjectVariableToken);
        }
    }

    //method to set inputSourcePath
    public void setInputSource(String sourcePath) {
        this.inputSource = sourcePath;
    }

    //method to operate ast parser and detection process
    public void run() {
        System.out.println();
        System.out.println("========= Dead Variable Detection =========");

        long start = System.currentTimeMillis();

        System.out.println("Parsing file to AST ...");
        ASTParser astParser = new ASTParser(this.inputSource);

        System.out.println("Setting Tokens ...");
        ClassTokenGenerator classTokenGenerator = new ClassTokenGenerator(astParser.cu);
        this.classTokenList = classTokenGenerator.getFileTokenList();

        System.out.println("Detecting Dead Variable ...");
        DeadVariableDetector deadVariableDetector = new DeadVariableDetector();
        detectDeadStaticField();
        detectDeadField();
        detectDeadVariable();
        setAllDeadVariable();

        long end = System.currentTimeMillis();
        this.AstTime = (end - start)/1000F;
    }

    //method to create report specific file name
    public void createReport (String outputReportPath) throws IOException {
        String fullFileName = "DeadVariable";
        Output output = new Output();
        output.createFile(outputReportPath, fullFileName);
        output.sendInfo(this.classTokenList);
        output.write(this.AstTime);
    }
}