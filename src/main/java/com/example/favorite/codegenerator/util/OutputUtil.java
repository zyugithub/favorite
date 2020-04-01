package com.example.favorite.codegenerator.util;

import com.example.favorite.codegenerator.bean.Constant;
import com.example.favorite.codegenerator.exception.ApplicationException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class OutputUtil {


    public static void indent(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append(Constant.indent);
        }
    }

    public static void newLine(StringBuilder sb) {
        sb.append(Constant.newline);
    }

    public static StringBuilder newPackage(String parentDir, String jpackage) {
        StringBuilder sb = new StringBuilder();
        sb.append(parentDir);
        sb.append(File.separator);

        String[] dirs = jpackage.split("\\.");

        for (String dir : dirs) {
            sb.append(dir);
            sb.append(File.separator);
        }

        try {
            Files.createDirectories(Paths.get(sb.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb;
    }

    public static String writeFile(String filePath, String source) {
        try (FileOutputStream out = new FileOutputStream(new File(filePath));
             FileChannel channel = out.getChannel()
        ) {
            ByteBuffer buffer = Charset.forName("utf8").encode(source);

            while (buffer.hasRemaining()) {
                channel.write(buffer);
            }

            return filePath;
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

    public static void writeConsole(String content) {
        System.out.println(content);
    }

}
