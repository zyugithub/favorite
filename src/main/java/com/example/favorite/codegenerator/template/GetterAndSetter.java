package com.example.favorite.codegenerator.template;

import com.example.favorite.codegenerator.bean.Field;
import com.example.favorite.codegenerator.Context;

import static com.example.favorite.codegenerator.util.OutputUtil.newLine;

public class GetterAndSetter extends JavaGenerator {
    public GetterAndSetter(Context context) {
        super(context);
    }

    @Override
    void createPackage() {
        if (!context.getTargetPackage().isEmpty()) {
            out.append("package ");
            out.append(context.getTargetPackage());
            out.append(";");
            newLine(out);
            newLine(out);
        }
    }

    @Override
    void createImport() {
        if (context.isEntityAnnotation()) {
            clazz.addImportType("javax.persistence.Table");
            clazz.addImportType("javax.persistence.Id");
            clazz.addImportType("javax.persistence.GeneratedValue");
            clazz.addImportType("javax.persistence.Column");
        }

        for (String importAnnotations : clazz.getImportTypes()) {
            out.append("import ");
            out.append(importAnnotations);
            out.append(';');
            newLine(out);
        }

        if (clazz.getImportTypes().size() > 0) {
            newLine(out);
        }
    }

    @Override
    void createClass() {
        if (context.isEntityAnnotation()) {
            clazz.addAnnotation("@Table(name = \"" + clazz.getTableName() + "\")");
        }

        for (String line : clazz.getAnnotations()) {
            out.append(line);
            newLine(out);
        }

        out.append("public class ").append(clazz.getClassName()).append(" {");
        newLine(out);
    }

    @Override
    void createVariable() {
        for (Field field : clazz.getFields()) {
            if (context.isEntityAnnotation()) {
                if (field.getColumn().getPrimaryKey()) {
                    field.addAnnotation("@Id");
                    field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
                }

                field.addAnnotation("@Column(name=\"" + field.getColumn().getColumnName() + "\")");
            }

            // TODO 添加注释

            out.append(field.formatFieldLine(1));
            newLine(out);
        }

        newLine(out);
    }

    @Override
    void createMethod() {
        for (Field field : clazz.getFields()) {
            out.append(field.formatGetterMethodLines(1));
            newLine(out);
            out.append(field.formatSetterMethodLines(1));
            newLine(out);
        }
    }

}
