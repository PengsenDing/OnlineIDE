package edu.tum.ase.compiler.model;

public class SourceCode {
    private String code;
    private String fileName;

    public SourceCode() {
    }

    public SourceCode(String code, String fileName) {
        this.code = code;
        this.fileName = fileName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
