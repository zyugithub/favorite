package com.example.favorite.codegenerator.bean;

import java.util.ArrayList;
import java.util.List;

import static com.example.favorite.codegenerator.util.DataTypeUtil.getSimpleName;
import static com.example.favorite.codegenerator.util.OutputUtil.indent;
import static com.example.favorite.codegenerator.util.OutputUtil.newLine;

public class Field {
    private Column column;
    private String name;
    private String type;
    private List<String> annotations;

    public Field() {
        annotations = new ArrayList<>();
    }

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void addAnnotation(String annotation) {
        annotations.add(annotation);
    }

    public String formatFieldLine(int indentLevel) {
        StringBuilder sb = new StringBuilder();

        for (String annotation : getAnnotations()) {
            indent(sb, indentLevel);
            sb.append(annotation);
            newLine(sb);
        }

        indent(sb, indentLevel);
        sb.append("private ");
        sb.append(getSimpleName(type));
        sb.append(' ');
        sb.append(name);
        sb.append(';');
        return sb.toString();
    }

    public String formatGetterMethodLines(int indentLevel) {
        String fieldName = name;

        StringBuilder sb = new StringBuilder();
        indent(sb, indentLevel);
        sb.append("public ");
        sb.append(getSimpleName(getType()));
        sb.append(" ");
        sb.append(getterMethodName(fieldName));
        sb.append("() {");
        newLine(sb);
        indent(sb, ++indentLevel);
        sb.append("return ");
        sb.append(fieldName);
        sb.append(";");
        newLine(sb);
        indent(sb, --indentLevel);
        sb.append("}");
        newLine(sb);
        return sb.toString();
    }

    public String formatSetterMethodLines(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        indent(sb, indentLevel);
        sb.append("public void ");
        sb.append(setterMethodName(getName()));
        sb.append("(");
        sb.append(getSimpleName(getType()));
        sb.append(" ");
        sb.append(getName());
        sb.append(") {");
        newLine(sb);
        indent(sb, ++indentLevel);
        sb.append("this.");
        sb.append(getName());
        sb.append(" = ");
        sb.append(getName());
        sb.append(";");
        newLine(sb);
        indent(sb, --indentLevel);
        sb.append("}");
        newLine(sb);
        return sb.toString();
    }

    private String getterMethodName(String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append(fieldName);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "get");

        return sb.toString();
    }

    private String setterMethodName(String fieldName) {
        StringBuilder sb = new StringBuilder();
        sb.append(fieldName);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        sb.insert(0, "set");

        return sb.toString();
    }

}
