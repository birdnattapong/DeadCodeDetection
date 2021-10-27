package DeadVariableParameter.DeadVariable;

import DeadVariableParameter.Token.ClassToken;
import DeadVariableParameter.Token.ObjectVariableToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeadVariableDetectorOperator {

    //method to operate algorithm for checking regex for static field
    public void checkRegexForStaticField(ClassToken currentClassToken, List<ObjectVariableToken> aliveStaticField, ClassToken classTokenToDetect) {
        //if location of fileToCheck equals to location of fileToDetect -> send (fieldName) to check
        //else if not equals -> send (className.fieldName) to check
        for (int i = 0; i< currentClassToken.getStaticField().size(); i++) {
            if (!currentClassToken.getLocation().equals(classTokenToDetect.getLocation())) {
                String stringToCheck = currentClassToken.getStaticField().get(i).getParent() + "." + currentClassToken.getStaticField().get(i).getVariableName();
                currentClassToken.getStaticField().get(i).setStringToCheck(stringToCheck);
            }
            else if (currentClassToken.getLocation().equals(classTokenToDetect.getLocation())) {
                currentClassToken.getStaticField().get(i).setStringToCheck(currentClassToken.getStaticField().get(i).getVariableName());
            }
        }

        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getMethodCalls().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInMethodCallExp(currentClassToken.getStaticField(), classTokenToDetect.getMethodCalls()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getAssignExpr().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInAssignExp(currentClassToken.getStaticField(), classTokenToDetect.getAssignExpr()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getObjectCreationExpr().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInObjectCreationExpr(currentClassToken.getStaticField(), classTokenToDetect.getObjectCreationExpr()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getIfStmt().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInIfStmt(currentClassToken.getStaticField(), classTokenToDetect.getIfStmt()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getForStmt().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInForStmt(currentClassToken.getStaticField(), classTokenToDetect.getForStmt()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getForeachStmt().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInForeachStmt(currentClassToken.getStaticField(), classTokenToDetect.getForeachStmt()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getReturnStmt().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInReturnStmt(currentClassToken.getStaticField(), classTokenToDetect.getReturnStmt()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getWhileStmt().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInWhileStmt(currentClassToken.getStaticField(), classTokenToDetect.getWhileStmt()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getDoStmt().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInDoStmt(currentClassToken.getStaticField(), classTokenToDetect.getDoStmt()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getSwitchStmt().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInSwitchStmt(currentClassToken.getStaticField(), classTokenToDetect.getSwitchStmt()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getVariableDeclarator().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInVariableDeclarator(currentClassToken.getStaticField(), classTokenToDetect.getVariableDeclarator()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getArrayAccess().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInArrayAccessIndex(currentClassToken.getStaticField(), classTokenToDetect.getArrayAccess()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getSwitchEntryStmt().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInSwitchEntryStmt(currentClassToken.getStaticField(), classTokenToDetect.getSwitchEntryStmt()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getSynchronizedStmt().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInSynchronizedStmt(currentClassToken.getStaticField(), classTokenToDetect.getSynchronizedStmt()));
            }
        }
        if (currentClassToken.getStaticField().size() > 0) {
            if (classTokenToDetect.getThrowStmt().size() > 0) {
                aliveStaticField.addAll(checkVariableUsageInThrowStmt(currentClassToken.getStaticField(), classTokenToDetect.getThrowStmt()));
            }
        }

        for (int i = 0; i < classTokenToDetect.getConstructorTokenList().size(); i++) {
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getMethodCalls().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInMethodCallExp(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getMethodCalls()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getAssignExpr().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInAssignExp(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getAssignExpr()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getObjectCreationExpr().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInObjectCreationExpr(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getObjectCreationExpr()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getIfStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInIfStmt(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getIfStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getForStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInForStmt(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getForStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getForeachStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInForeachStmt(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getForeachStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getReturnStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInReturnStmt(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getReturnStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getWhileStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInWhileStmt(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getWhileStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getDoStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInDoStmt(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getDoStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getSwitchStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInSwitchStmt(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getSwitchStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getVariableDeclarator().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInVariableDeclarator(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getVariableDeclarator()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getConstructorTokenList().get(i).getExplicitConstructorStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInExplicitConstructorStmt(currentClassToken.getStaticField(), classTokenToDetect.getConstructorTokenList().get(i).getExplicitConstructorStmt()));
                }
            }
        }

        for (int i = 0; i < classTokenToDetect.getMethodTokenList().size(); i++) {
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getMethodCalls().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInMethodCallExp(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getMethodCalls()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getAssignExpr().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInAssignExp(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getAssignExpr()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getObjectCreationExpr().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInObjectCreationExpr(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getObjectCreationExpr()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getIfStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInIfStmt(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getIfStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getForStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInForStmt(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getForStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getForeachStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInForeachStmt(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getForeachStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getReturnStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInReturnStmt(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getReturnStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getWhileStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInWhileStmt(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getWhileStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getDoStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInDoStmt(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getDoStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getSwitchStmt().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInSwitchStmt(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getSwitchStmt()));
                }
            }
            if (currentClassToken.getStaticField().size() > 0) {
                if (classTokenToDetect.getMethodTokenList().get(i).getVariableDeclarator().size() > 0) {
                    aliveStaticField.addAll(checkVariableUsageInVariableDeclarator(currentClassToken.getStaticField(), classTokenToDetect.getMethodTokenList().get(i).getVariableDeclarator()));
                }
            }
        }
    }

    //method to operate algorithm for checking regex for field
    public void checkRegexForField(ClassToken currentClassToken, List<ObjectVariableToken> aliveField, ClassToken classTokenToDetect, String detectType) {
        List<ObjectVariableToken> objectVariableTokenToDetect = new ArrayList<>();

        if (detectType.equals("childCase")) {
            for (int i = 0; i< currentClassToken.getStaticField().size(); i++) {
                currentClassToken.getStaticField().get(i).setStringToCheck(currentClassToken.getStaticField().get(i).getVariableName());
            }
            objectVariableTokenToDetect = currentClassToken.getStaticField();
        }
        else if (detectType.equals("general")) {
            for (int i = 0; i< currentClassToken.getField().size(); i++) {
                currentClassToken.getField().get(i).setStringToCheck(currentClassToken.getField().get(i).getVariableName());
            }
            objectVariableTokenToDetect = currentClassToken.getField();
        }

        //check for -> field in file accept any cases (not include within method) if found -> add to alive variable
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInMethodCallExp(objectVariableTokenToDetect, classTokenToDetect.getMethodCalls()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInAssignExp(objectVariableTokenToDetect, classTokenToDetect.getAssignExpr()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInObjectCreationExpr(objectVariableTokenToDetect, classTokenToDetect.getObjectCreationExpr()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInIfStmt(objectVariableTokenToDetect, classTokenToDetect.getIfStmt()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInForStmt(objectVariableTokenToDetect, classTokenToDetect.getForStmt()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInForeachStmt(objectVariableTokenToDetect, classTokenToDetect.getForeachStmt()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInReturnStmt(objectVariableTokenToDetect, classTokenToDetect.getReturnStmt()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInWhileStmt(objectVariableTokenToDetect, classTokenToDetect.getWhileStmt()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInDoStmt(objectVariableTokenToDetect, classTokenToDetect.getDoStmt()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInSwitchStmt(objectVariableTokenToDetect, classTokenToDetect.getSwitchStmt()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInVariableDeclarator(objectVariableTokenToDetect, classTokenToDetect.getVariableDeclarator()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInArrayAccessIndex(objectVariableTokenToDetect, classTokenToDetect.getArrayAccess()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInSwitchEntryStmt(objectVariableTokenToDetect, classTokenToDetect.getSwitchEntryStmt()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInSynchronizedStmt(objectVariableTokenToDetect, classTokenToDetect.getSynchronizedStmt()));
        }
        if (objectVariableTokenToDetect.size() > 0) {
            aliveField.addAll(checkVariableUsageInThrowStmt(objectVariableTokenToDetect, classTokenToDetect.getThrowStmt()));
        }

        //check for -> field in file accept any cases (within constructor) if found -> add to alive variable
        for (int i = 0; i < classTokenToDetect.getConstructorTokenList().size(); i++) {
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInMethodCallExp(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getMethodCalls()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInAssignExp(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getAssignExpr()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInObjectCreationExpr(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getObjectCreationExpr()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInIfStmt(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getIfStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInForStmt(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getForStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInForeachStmt(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getForeachStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInReturnStmt(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getReturnStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInWhileStmt(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getWhileStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInDoStmt(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getDoStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInSwitchStmt(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getSwitchStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInVariableDeclarator(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getVariableDeclarator()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInExplicitConstructorStmt(objectVariableTokenToDetect, classTokenToDetect.getConstructorTokenList().get(i).getExplicitConstructorStmt()));
            }
        }

        //check for -> field in file accept any cases (within method) if found -> add to alive variable
        for (int i = 0; i < classTokenToDetect.getMethodTokenList().size(); i++) {
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInMethodCallExp(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getMethodCalls()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInAssignExp(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getAssignExpr()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInObjectCreationExpr(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getObjectCreationExpr()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInIfStmt(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getIfStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInForStmt(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getForStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInForeachStmt(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getForeachStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInReturnStmt(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getReturnStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInWhileStmt(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getWhileStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInDoStmt(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getDoStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInSwitchStmt(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getSwitchStmt()));
            }
            if (objectVariableTokenToDetect.size() > 0) {
                aliveField.addAll(checkVariableUsageInVariableDeclarator(objectVariableTokenToDetect, classTokenToDetect.getMethodTokenList().get(i).getVariableDeclarator()));
            }
        }
    }

    //method to operate algorithm for checking regex for variable
    public void checkRegexForVariable(ClassToken currentClassToken, List<ObjectVariableToken> aliveObjectVariableToken, ClassToken classTokenToDetect) {
        for (int i = 0; i< currentClassToken.getConstructorTokenList().size(); i++) {
            for (int j = 0; j< currentClassToken.getConstructorTokenList().get(i).getVariable().size(); j++) {
                currentClassToken.getConstructorTokenList().get(i).getVariable().get(j).setStringToCheck(currentClassToken.getConstructorTokenList().get(i).getVariable().get(j).getVariableName());
            }
        }

        for (int i = 0; i< currentClassToken.getConstructorTokenList().size(); i++) {
            if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInMethodCallExp(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getMethodCalls()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInAssignExp(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getAssignExpr()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInObjectCreationExpr(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getObjectCreationExpr()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInIfStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getIfStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInForStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getForStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInForeachStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getForeachStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInReturnStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getReturnStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInWhileStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getWhileStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInDoStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getDoStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInSwitchStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getSwitchStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInVariableDeclarator(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getVariableDeclarator()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInArrayAccessIndex(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getArrayAccess()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInSwitchEntryStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getSwitchEntryStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInExplicitConstructorStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getConstructorTokenList().get(i).getExplicitConstructorStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInSynchronizedStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getSynchronizedStmt()));
                }
                if (currentClassToken.getConstructorTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInThrowStmt(currentClassToken.getConstructorTokenList().get(i).getVariable(), classTokenToDetect.getThrowStmt()));
                }
            }
        }

        for (int i = 0; i< currentClassToken.getMethodTokenList().size(); i++) {
            for (int j = 0; j< currentClassToken.getMethodTokenList().get(i).getVariable().size(); j++) {
                currentClassToken.getMethodTokenList().get(i).getVariable().get(j).setStringToCheck(currentClassToken.getMethodTokenList().get(i).getVariable().get(j).getVariableName());
            }
        }

        for (int i = 0; i< currentClassToken.getMethodTokenList().size(); i++) {
            if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInMethodCallExp(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getMethodCalls()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInAssignExp(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getAssignExpr()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInObjectCreationExpr(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getObjectCreationExpr()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInIfStmt(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getIfStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInForStmt(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getForStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInForeachStmt(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getForeachStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInReturnStmt(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getReturnStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInWhileStmt(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getWhileStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInDoStmt(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getDoStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInSwitchStmt(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getSwitchStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInVariableDeclarator(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getMethodTokenList().get(i).getVariableDeclarator()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInArrayAccessIndex(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getArrayAccess()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInSwitchEntryStmt(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getSwitchEntryStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInSynchronizedStmt(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getSynchronizedStmt()));
                }
                if (currentClassToken.getMethodTokenList().get(i).getVariable().size() > 0) {
                    aliveObjectVariableToken.addAll(checkVariableUsageInThrowStmt(currentClassToken.getMethodTokenList().get(i).getVariable(), classTokenToDetect.getThrowStmt()));
                }
            }
        }
    }

    //method to check regex for method call expr -> done
    private List<ObjectVariableToken> checkVariableUsageInMethodCallExp(List<ObjectVariableToken> objectVariableToken, List<String> methodCallExpr) {
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < methodCallExpr.size(); j++) {
                String statement = methodCallExpr.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("methodCall", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);

        return aliveObjectVariableToken;
    }

    //method to check regex for assign expr -> done
    private List<ObjectVariableToken> checkVariableUsageInAssignExp(List<ObjectVariableToken> objectVariableToken, List<String>  assignExpr) {
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {

            for (int j = 0; j < assignExpr.size(); j++) {
                String statement = assignExpr.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("assignExpr", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for object creation expr -> done
    private List<ObjectVariableToken> checkVariableUsageInObjectCreationExpr(List<ObjectVariableToken> objectVariableToken, List<String> objectCreationExpr){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {

            for (int j = 0; j < objectCreationExpr.size(); j++) {
                String statement = objectCreationExpr.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("objectCreationExpr", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for if stmt -> done
    private List<ObjectVariableToken> checkVariableUsageInIfStmt(List<ObjectVariableToken> objectVariableToken, List<String> ifStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < ifStmt.size(); j++) {
                String statement = ifStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("ifStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for for stmt -> done
    private List<ObjectVariableToken> checkVariableUsageInForStmt(List<ObjectVariableToken> objectVariableToken, List<String> forStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < forStmt.size(); j++) {
                String statement = forStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("forStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for foreach stmt -> done
    private List<ObjectVariableToken> checkVariableUsageInForeachStmt(List<ObjectVariableToken> objectVariableToken, List<String> foreachStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < foreachStmt.size(); j++) {
                String statement = foreachStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("foreachStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for return stmt -> done
    private List<ObjectVariableToken> checkVariableUsageInReturnStmt(List<ObjectVariableToken> objectVariableToken, List<String> returnStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < returnStmt.size(); j++) {
                String statement = returnStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("returnStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for while stmt -> done
    private List<ObjectVariableToken> checkVariableUsageInWhileStmt(List<ObjectVariableToken> objectVariableToken, List<String> whileStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < whileStmt.size(); j++) {
                String statement = whileStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("whileStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for do stmt -> done
    private List<ObjectVariableToken> checkVariableUsageInDoStmt(List<ObjectVariableToken> objectVariableToken, List<String> doStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < doStmt.size(); j++) {
                String statement = doStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("doStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for switch stmt -> done
    private List<ObjectVariableToken> checkVariableUsageInSwitchStmt(List<ObjectVariableToken> objectVariableToken, List<String> switchStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < switchStmt.size(); j++) {
                String statement = switchStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("switchStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for variable declarator -> done
    private List<ObjectVariableToken> checkVariableUsageInVariableDeclarator(List<ObjectVariableToken> objectVariableToken, List<String> variableDeclarator){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < variableDeclarator.size(); j++) {
                String statement = variableDeclarator.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("variableDeclarator", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for arrayAccessIndex -> done
    private List<ObjectVariableToken> checkVariableUsageInArrayAccessIndex(List<ObjectVariableToken> objectVariableToken, List<String> arrayAccessIndex){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < arrayAccessIndex.size(); j++) {
                String statement = arrayAccessIndex.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("arrayAccessIndex", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for SwitchEntryStmt -> done
    private List<ObjectVariableToken> checkVariableUsageInSwitchEntryStmt(List<ObjectVariableToken> objectVariableToken, List<String> switchEntryStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < switchEntryStmt.size(); j++) {
                String statement = switchEntryStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("switchEntryStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for ExplicitConstructorStmt -> done
    private List<ObjectVariableToken> checkVariableUsageInExplicitConstructorStmt(List<ObjectVariableToken> objectVariableToken, List<String> explicitConstructorStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < explicitConstructorStmt.size(); j++) {
                String statement = explicitConstructorStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("explicitConstructorStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for SynchronizedStmt -> done
    private List<ObjectVariableToken> checkVariableUsageInSynchronizedStmt(List<ObjectVariableToken> objectVariableToken, List<String> synchronizedStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < synchronizedStmt.size(); j++) {
                String statement = synchronizedStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("synchronizedStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
    }

    //method to check regex for ThrowStmt -> done
    private List<ObjectVariableToken> checkVariableUsageInThrowStmt(List<ObjectVariableToken> objectVariableToken, List<String> throwStmt){
        List<ObjectVariableToken> aliveObjectVariableToken = new ArrayList<>();
        List<ObjectVariableToken> toRemove = new ArrayList<>();

        for (int i = 0; i < objectVariableToken.size(); i++) {
            for (int j = 0; j < throwStmt.size(); j++) {
                String statement = throwStmt.get(j);
                String stringToCheck = objectVariableToken.get(i).getStringToCheck();

                if (checkRegex("throwStmt", statement, stringToCheck)) {
                    aliveObjectVariableToken.add(objectVariableToken.get(i));
                    toRemove.add(objectVariableToken.get(i));
                    break;
                }
            }
        }

        objectVariableToken.removeAll(toRemove);
        return aliveObjectVariableToken;
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