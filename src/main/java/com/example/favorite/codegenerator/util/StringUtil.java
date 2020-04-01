package com.example.favorite.codegenerator.util;

import com.example.favorite.codegenerator.bean.Constant;
import com.example.favorite.codegenerator.bean.SymbolEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static String getCamelCase(String inputString, boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();
        boolean nextUpperCase = false;

        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;
                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

    public static String capitalUpper(String str) {
        if (str.isEmpty()) return "";

        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }

    public static String capitalLower(String str) {
        if (str.isEmpty()) return "";

        StringBuilder sb = new StringBuilder(str);
        sb.setCharAt(0, Character.toLowerCase(sb.charAt(0)));
        return sb.toString();
    }

    public static String ignorePrefix(String inputString) {
        Pattern pattern = Pattern.compile(Constant.entity_prefix);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.replaceAll("");
    }

    public static String getPrefix(String inputString) {
        Pattern pattern = Pattern.compile(Constant.entity_prefix);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.find() ? matcher.group(1) : "";
    }

    public static void main(String[] args) {
        System.out.println(SymbolEnum.valueOf("classname"));
    }

}
