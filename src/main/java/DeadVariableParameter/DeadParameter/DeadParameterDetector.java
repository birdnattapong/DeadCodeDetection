package DeadVariableParameter.DeadParameter;

import DeadVariableParameter.Token.ClassToken;
import DeadVariableParameter.Token.ObjectParameterToken;
import DeadVariableParameter.TokenGenerator.ClassTokenGenerator;
import Files_Reader.ASTParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeadParameterDetector {
    List<ClassToken> classTokenList;
    String inputSource;
    float AstTime;

    public DeadParameterDetector() { }

    private void detectDeadParameter() {
        for (int i = 0; i< classTokenList.size(); i++) {
            DeadParameterDetectorOperator deadParameterDetectorOperator = new DeadParameterDetectorOperator();
            List<ObjectParameterToken> aliveParameter = new ArrayList<>();

            if (this.classTokenList.get(i).getModifier() != null) {
                if (this.classTokenList.get(i).getModifier().equals("default")) { // check only in default class
                    if (this.classTokenList.get(i).getConstructorTokenList().size() > 0 || this.classTokenList.get(i).getMethodTokenList().size() > 0) {
                        deadParameterDetectorOperator.checkRegexForParameter(this.classTokenList.get(i), aliveParameter, this.classTokenList.get(i));
                    }

                    //set AllAliveParameter to FileToken
                    if (aliveParameter.size() > 0) {
                        this.classTokenList.get(i).setAllAliveParameter(aliveParameter);
                    }

                    //set AllDeadParameter to FileToken
                    for (int j = 0; j<this.classTokenList.get(i).getConstructorTokenList().size(); j++) {
                        if (this.classTokenList.get(i).getConstructorTokenList().get(j).getParameterToken().size() > 0) {
                            this.classTokenList.get(i).addDeadParameter(this.classTokenList.get(i).getConstructorTokenList().get(j).getParameterToken());
                        }
                    }
                    for (int j = 0; j<this.classTokenList.get(i).getMethodTokenList().size(); j++) {
                        if (this.classTokenList.get(i).getMethodTokenList().get(j).getParameterToken().size() > 0) {
                            this.classTokenList.get(i).addDeadParameter(this.classTokenList.get(i).getMethodTokenList().get(j).getParameterToken());
                        }
                    }
                }
            }

        }
    }

    //method to set inputSourcePath
    public void setInputSource(String sourcePath) {
        this.inputSource = sourcePath;
    }

    //method to operate ast parser and detection process
    public void run() {
        System.out.println();
        System.out.println("========= Dead Parameter Detection =========");

        long start = System.currentTimeMillis();

        System.out.println("Parsing file to AST ...");
        ASTParser astParser = new ASTParser(this.inputSource);

        System.out.println("Setting Tokens ...");
        ClassTokenGenerator classTokenGenerator = new ClassTokenGenerator(astParser.cu);
        this.classTokenList = classTokenGenerator.getFileTokenList();

        System.out.println("Detecting Dead Parameter ...");
        DeadParameterDetector deadParameterDetector = new DeadParameterDetector();
        detectDeadParameter();

        long end = System.currentTimeMillis();
        this.AstTime = (end - start)/1000F;
    }

    //method to create report specific file name
    public void createReport (String outputReportPath) throws IOException {
        String fullFileName = "DeadParameter";
        DeadVariableParameter.DeadParameter.Output output = new Output();
        output.createFile(outputReportPath, fullFileName);
        output.sendInfo(this.classTokenList);
        output.write(this.AstTime);
    }
}
