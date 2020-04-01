package com.example.favorite.codegenerator.template;

import com.example.favorite.codegenerator.bean.Column;
import com.example.favorite.codegenerator.bean.Constant;
import com.example.favorite.codegenerator.bean.OutputTypeEnum;
import com.example.favorite.codegenerator.bean.SymbolEnum;
import com.example.favorite.codegenerator.Context;
import com.example.favorite.codegenerator.exception.ApplicationException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.favorite.codegenerator.bean.SymbolEnum.hasSymbol;
import static com.example.favorite.codegenerator.util.OutputUtil.*;
import static com.example.favorite.codegenerator.util.SymbolUtil.replace;

public class FileTemplate implements Generator {
    private Context context;
    private StringBuilder out = new StringBuilder();

    public FileTemplate(Context context) {
        this.context = context;
    }

    @Override
    public String generate(String tableName, List<Column> columns) {
        List<String> lines = new ArrayList<>();

        try (InputStream in = getClass().getResourceAsStream('/' + context.getTemplate());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String str;
            while ((str = reader.readLine()) != null) {
                lines.add(str);
            }
        } catch (IOException e) {
            throw new ApplicationException("文件不存在：{0}", context.getTemplate());
        }

        StringBuilder multiLine = new StringBuilder();
        boolean start = false;

        for (String line : lines) {
            // package只在java中有意义，且只出现一次，没必要symbo处理，反而增加了复杂度
            if (hasSymbol(line, SymbolEnum.PACKAGENAME.getSymbol())) {
                line = line.replace(SymbolEnum.PACKAGENAME.getSymbol(), context.getTargetPackage());
                out.append(line);
                newLine(out);
                continue;
            }

            if (line.trim().startsWith(Constant.multiple_line_symbol)) {
                if (!start) {
                    multiLine.delete(0, multiLine.length());
                    multiLine.append(line.replaceFirst(Constant.multiple_line_symbol, ""));
                    newLine(multiLine);
                    start = true;
                    continue;
                }
            }

            if (line.endsWith(Constant.multiple_line_symbol)) {
                if (start) {
                    multiLine.append(line.substring(0, line.lastIndexOf(Constant.multiple_line_symbol)));
                    newLine(multiLine);
                    start = false;
                }
            }

            if (start) {
                multiLine.append(line);
                newLine(multiLine);
                continue;
            }

            if (multiLine.length() > 0) {
                line = multiLine.toString();
            }

            List<String> strs = replace(line, tableName, columns, context.getMapping());

            for (String str : strs) {
                out.append(str);
                newLine(out);
            }
        }

        if (OutputTypeEnum.valueOf(context.getOutputType()) == OutputTypeEnum.CONSOLE) {
            writeConsole(out.toString());
            return Constant.output_separator;
        }

        return output(tableName);
    }

    private String output(String tableName) {
        StringBuilder sb = newPackage(context.getOutputPath(), context.getTargetPackage());
        sb.append(replace(shortFileName(context.getTemplate()), tableName));
        sb.append(".java");

        return writeFile(sb.toString(), out.toString());
    }

    private String shortFileName(String fileName) {
        return fileName.substring(fileName.lastIndexOf(File.separator)+1, fileName.lastIndexOf('.'));
    }
}
