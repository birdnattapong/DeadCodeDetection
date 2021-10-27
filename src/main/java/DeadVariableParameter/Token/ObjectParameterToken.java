package DeadVariableParameter.Token;

public class ObjectParameterToken {
    private String parameterName;
    private Integer beginLine;
    private String stringToCheck;

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public Integer getBeginLine() {
        return beginLine;
    }

    public void setBeginLine(Integer beginLine) {
        this.beginLine = beginLine;
    }

    public String getStringToCheck() {
        return stringToCheck;
    }

    public void setStringToCheck(String stringToCheck) {
        this.stringToCheck = stringToCheck;
    }
}
