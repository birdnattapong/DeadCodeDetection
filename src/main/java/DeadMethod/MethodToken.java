package DeadMethod;

public class MethodToken {
    private String path;
    private String file;
    private String packageName;
    private String name;
    private String qualifiedSignature;
    private Boolean isDead = true;
    private String className;
    private int line;

    // Pattern for detect : ClassName.
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualifiedSignature() {
        return qualifiedSignature;
    }

    public void setQualifiedSignature(String qualifiedSignature) {
        this.qualifiedSignature = qualifiedSignature;
    }

    public Boolean getDead() {
        return isDead;
    }

    public void setDead(Boolean dead) {
        isDead = dead;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return "MethodToken{" +
                "file='" + file + '\'' +
                ", name='" + name + '\'' +
                ", line=" + line + '\'' +
                ", isDead=" +isDead +
                '}';
    }
}
