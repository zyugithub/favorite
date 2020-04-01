package com.example.favorite.codegenerator.template;

import com.example.favorite.codegenerator.Context;
import com.example.favorite.codegenerator.bean.*;
import com.example.favorite.codegenerator.util.DataTypeUtil;

import java.util.List;

import static com.example.favorite.codegenerator.util.OutputUtil.*;
import static com.example.favorite.codegenerator.util.StringUtil.getCamelCase;
import static com.example.favorite.codegenerator.util.StringUtil.ignorePrefix;

public abstract class JavaGenerator implements Generator {
    Context context;
    Clazz clazz;
    StringBuilder out = new StringBuilder();

    JavaGenerator(Context context) {
        this.context = context;
    }

    public String generate(String tableName, List<Column> columns) {
        clazz = new Clazz();
        clazz.setTableName(tableName);
        clazz.setClassName(getCamelCase(ignorePrefix(tableName), true));

        for (Column column : columns) {
            Field field = new Field();
            field.setColumn(column);
            field.setName(getCamelCase(column.getColumnName(), false));
            String type = DataTypeUtil.getJavaDataType(column.getDataType());
            field.setType(type);

            if (!DataTypeUtil.isPrimitive(type)) {
                clazz.addImportType(type);
            }

            clazz.addField(field);
        }

        createPackage();
        createImport();
        createClass();
        createVariable();
        createMethod();
        out.append("}");
        newLine(out);

        if (OutputTypeEnum.valueOf(context.getOutputType()) == OutputTypeEnum.CONSOLE) {
            writeConsole(out.toString());
            return Constant.output_separator;
        } else {
            return ouput();
        }
    }

    private String ouput() {
        StringBuilder sb = newPackage(context.getOutputPath(), context.getTargetPackage());
        sb.append(clazz.getClassName());
        sb.append(".java");

        return writeFile(sb.toString(), out.toString());
    }

    abstract void createPackage();

    abstract void createImport();

    abstract void createClass();

    abstract void createVariable();

    abstract void createMethod();
}
