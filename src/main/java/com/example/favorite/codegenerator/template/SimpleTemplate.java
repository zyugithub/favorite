package com.example.favorite.codegenerator.template;

import com.example.favorite.codegenerator.bean.Column;
import com.example.favorite.codegenerator.bean.Constant;
import com.example.favorite.codegenerator.bean.OutputTypeEnum;
import com.example.favorite.codegenerator.Context;
import com.example.favorite.codegenerator.exception.ApplicationException;
import com.example.favorite.codegenerator.util.SymbolUtil;

import java.util.List;

import static com.example.favorite.codegenerator.util.OutputUtil.*;
import static com.example.favorite.codegenerator.util.StringUtil.getCamelCase;
import static com.example.favorite.codegenerator.util.StringUtil.ignorePrefix;

public class SimpleTemplate implements Generator {
    private Context context;
    private StringBuilder out = new StringBuilder();

    public SimpleTemplate(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        if (context.getTemplate().isEmpty()) {
            throw new ApplicationException("模板字符串为空");
        }
    }

    public String generate(String tableName, List<Column> columns) {
        List<String> strs = SymbolUtil.replace(context.getTemplate(), tableName, columns, context.getMapping());

        for (String str : strs) {
            out.append(str);
            newLine(out);
        }

        if (OutputTypeEnum.valueOf(context.getOutputType()) == OutputTypeEnum.FILE) {
            return ouput(tableName);
        }

        writeConsole(out.toString());
        return Constant.output_separator;
    }

    private String ouput(String tableName) {
        StringBuilder sb = newPackage(context.getOutputPath(), context.getTargetPackage());
        sb.append(getCamelCase(ignorePrefix(tableName), true));
        sb.append(".txt");

        return writeFile(sb.toString(), out.toString());
    }
}
