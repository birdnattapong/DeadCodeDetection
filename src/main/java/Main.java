import DeadClassInterface.DeadClassInterfaceDetector;
import DeadMethod.DeadMethodDetector;

public class Main {
    public static void main(String[] args){
        /* path */
        String input = "/Users/Peeradon/Documents/OpenSourceProject/Corpus/QualitasCorpus-20130901r/Systems/xmojo/xmojo-5.0.0/src/com";
        String report = "/Users/Peeradon/Desktop";

        /* call DeadCodeDetector.jar */
        DeadClassInterfaceDetector deadClassInterfaceDetector = new DeadClassInterfaceDetector();
        deadClassInterfaceDetector.setInputSource(input);

        /* operation system */
        deadClassInterfaceDetector.run();
        deadClassInterfaceDetector.createReport(report);
    }
}

