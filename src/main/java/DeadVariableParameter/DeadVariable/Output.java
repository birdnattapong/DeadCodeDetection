package DeadVariableParameter.DeadVariable;

import DeadVariableParameter.Token.ClassToken;

import java.io.*;
import java.util.*;


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
        bufferedWriter.write("Dead Variable with AST. Total elapse time: " + AstTime + " seconds.\n");

        Integer count = 0;

        for (int i = 0; i<this.classTokenList.size(); i++) {
            if (this.classTokenList.get(i).getAllDeadVariable().size() > 0) {
                count = count + this.classTokenList.get(i).getAllDeadVariable().size();
                try {
                    for (int j = 0; j<this.classTokenList.get(i).getAllDeadVariable().size(); j++) {
                        String tmp = "Variable ; " + this.classTokenList.get(i).getAllDeadVariable().get(j).getVariableName() + " has 0 references"
                                + " ; " + this.classTokenList.get(i).getPackageName() + "." + this.classTokenList.get(i).getClassName()
                                + " (line:" + this.classTokenList.get(i).getAllDeadVariable().get(j).getBeginLine() + ") ; dead variable\n";
                        bufferedWriter.write(tmp);
                    }
                }catch (IOException e){
                    System.out.print("Print Error!");
                    e.printStackTrace();
                }
            }
        }

        bufferedWriter.write("Total Dead Variable Count : " + count);
        this.bufferedWriter.close();

        System.out.println("Report Created.");
        System.out.println("Detecting Completed.");
        System.out.println("Total time : " + AstTime);
    }
}
