package DeadVariableParameter.DeadParameter;

import DeadVariableParameter.Token.ClassToken;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class Output {
    private String fileLocation;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private List<ClassToken> classTokenList;

    public void createFile(String outputReportPath, String fileName) {
        this.fileLocation = outputReportPath + "/" + fileName + ".txt";

        try {
            this.fileWriter = new FileWriter(fileLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendInfo(List<ClassToken> classTokenList) {
        this.classTokenList = classTokenList;
    }

    public void write(float AstTime) throws IOException {
        System.out.println("Create Report ...");

        this.bufferedWriter = new BufferedWriter(this.fileWriter);
        bufferedWriter.write("Dead Parameter with AST. Total elapse time: " + AstTime + " seconds.\n");

        Integer count = 0;

        for (int i = 0; i<this.classTokenList.size(); i++) {
            if (this.classTokenList.get(i).getAllDeadParameter().size() > 0) {
                count = count + this.classTokenList.get(i).getAllDeadParameter().size();
                try {
                    for (int j = 0; j<this.classTokenList.get(i).getAllDeadParameter().size(); j++) {
                        String tmp = "Parameter ; " + this.classTokenList.get(i).getAllDeadParameter().get(j).getParameterName() + " has 0 references"
                                + " ; " + this.classTokenList.get(i).getPackageName() + "." + this.classTokenList.get(i).getClassName()
                                + " (line:" + this.classTokenList.get(i).getAllDeadParameter().get(j).getBeginLine() + ")\n";
                        bufferedWriter.write(tmp);
                    }
                }catch (IOException e){
                    System.out.print("Print Error!");
                    e.printStackTrace();
                }
            }
        }

        bufferedWriter.write("Total Dead Parameter Count : " + count);
        this.bufferedWriter.close();

        System.out.println("Report Created.");
        System.out.println("Detecting Completed.");
        System.out.println("Total time : " + AstTime);
    }
}
