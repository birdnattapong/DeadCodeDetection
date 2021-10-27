package DeadVariableParameter.DeadParameter;

import DeadVariableParameter.Token.ObjectParameterToken;
import DeadVariableParameter.Token.ClassToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadParameterDetectorOperator {

    //method to operate algorithm for checking regex for static field
    public void checkRegexForParameter(ClassToken currentClassToken, List<ObjectParameterToken> aliveObjectParameter, ClassToken classTokenToDetect) {

        //delete main method (no check)
        for (int i = 0; i< classTokenToDetect.getMethodTokenList().size(); i++) {
            if (classTokenToDetect.getMethodTokenList().get(i).getMethodName().equals("main")) {
                classTokenToDetect.getMethodTokenList().remove(classTokenToDetect.getMethodTokenList().get(i));
            }
        }

        //setting stringToCheck to parameter in constructor
        if (currentClassToken.getConstructorTokenList().size() > 0) { // if file has constructor
            for (int i = 0; i< currentClassToken.getConstructorTokenList().size(); i++) {
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) { // if constructor has parameter
                    for (int j = 0; j< currentClassToken.getConstructorTokenList().get(i).getParameterToken().size(); j++) {
                        currentClassToken.getConstructorTokenList().get(i).getParameterToken().get(j).setStringToCheck(
                                currentClassToken.getConstructorTokenList().get(i).getParameterToken().get(j).getParameterName());  // set stringToCheck to parameter
                    }
                }
            }
        }

        //checking parameter usage for constructor
        for (int i = 0; i< currentClassToken.getConstructorTokenList().size(); i++) {
            if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getMethodCalls()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getAssignExpr()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getObjectCreationExpr()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getIfStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getForStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getForeachStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getReturnStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getWhileStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getDoStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getSwitchStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getVariableDeclarator()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getArrayAccess()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getConstructorTokenList().get(i).getExplicitConstructorStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getSwitchEntryStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getSynchronizedStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getConstructorTokenList().get(i).getParameterToken(), classTokenToDetect.getThrowStmt()));
                }
            }
        }

        //setting stringToCheck to parameter in method
        if (currentClassToken.getMethodTokenList().size() > 0) { // if file has method
            for (int i = 0; i< currentClassToken.getMethodTokenList().size(); i++) {
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) { // if method has parameter
                    for (int j = 0; j< currentClassToken.getMethodTokenList().get(i).getParameterToken().size(); j++) {
                        currentClassToken.getMethodTokenList().get(i).getParameterToken().get(j).setStringToCheck(
                                currentClassToken.getMethodTokenList().get(i).getParameterToken().get(j).getParameterName()); // set stringToCheck to parameter
                    }
                }
            }
        }

        //checking parameter usage for method
        for (int i = 0; i< currentClassToken.getMethodTokenList().size(); i++) {
            if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getMethodCalls()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getAssignExpr()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getObjectCreationExpr()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getIfStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getForStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getForeachStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getReturnStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getWhileStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getDoStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getSwitchStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getMethodTokenList().get(i).getVariableDeclarator()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getArrayAccess()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getSwitchEntryStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getSynchronizedStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getParameterToken().size() > 0) {
                    aliveObjectParameter.addAll(checkParameterUsage(currentClassToken.getMethodTokenList().get(i).getParameterToken(), classTokenToDetect.getThrowStmt()));
                }
            }
        }
    }

    //method to check regex for parameter -> done
    private List<ObjectParameterToken> checkParameterUsage(List<ObjectParameterToken> objectParameterToken, List<String> stmt) {
        List<ObjectParameterToken> aliveObjectParameterToken = new ArrayList<>();
        List<ObjectParameterToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectParameterToken.size(); i++) {
            for (int j = 0; j < stmt.size(); j++) {
                String statement = stmt.get(j);
                String stringToCheck = objectParameterToken.get(i).getStringToCheck();

                if (checkRegex("", statement, stringToCheck)) {
                    aliveObjectParameterToken.add(objectParameterToken.get(i));
                    toRemove.add(objectParameterToken.get(i));
                    break;
                }
            }
        }

        objectParameterToken.removeAll(toRemove);
        return aliveObjectParameterToken;
    }

    //method to check regex for string and statement
    private boolean checkRegex(String callType, String statement, String stringTocheck) {
//        System.out.println(callType);
//        System.out.println(statement);
//        System.out.println(stringTocheck);

        Pattern pattern1 = Pattern.compile("\\b" + stringTocheck + "\\b");
        Pattern pattern2 = Pattern.compile("(?<!\\\\)\"");
        Matcher m1 = pattern1.matcher(statement);
        Matcher m2;
        while(m1.find()){                               // if any <toCapture> found (first condition fulfilled)
            int start = m1.start();
            int end = m1.end();
            m2 = pattern2.matcher(statement);
            int count = 0;
            while(m2.find() && m2.start() < start){     // count number of valid double quotes "
                count++;
            }
            if(count % 2 == 0) {                        // if number of valid quotes is even
                char[] tcar = new char[statement.length()];
                Arrays.fill(tcar, '-');
                for (int i=start; i<end; i++) {
                    tcar[i] = '^';
                }
//                System.out.println("======= result =======");
//                System.out.println(statement);
//                System.out.println(new String(tcar));
//                System.out.println();
                return true;
            }
        }
        if (!m1.find()) {
//            System.out.println("====== not found ======");
//            System.out.println();
        }
        return false;
    }
}
