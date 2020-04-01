package com.example.favorite.codegenerator;

import com.example.favorite.codegenerator.exception.ApplicationException;
import com.example.favorite.codegenerator.util.DataTypeUtil;
import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.springframework.stereotype.Component;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

@Component
public class Context {
    private String outputPath;
    private String outputType;
    private String templateType;
    private String template;
    private String targetPackage;
    private boolean entityAnnotation;
    private boolean ignoredColumns;
    private String mappingPath;
    private Map<String, String> mapping;

    private static String OUTPUT_PATH;

    static {
        File desktop = FileSystemView.getFileSystemView().getHomeDirectory();
        OUTPUT_PATH = desktop.getAbsolutePath();
    }

    public String getOutputPath() {
        return outputPath == null ? OUTPUT_PATH : outputPath;
    }

    public void setOutputPath(String outputPath) {
        if (outputPath != null && Files.notExists(Paths.get(outputPath))) {
            throw new ApplicationException("目录不存在：{0}", outputPath);
        }

        this.outputPath = outputPath;
    }

    public String getOutputType() {
        return outputType == null ? "" : outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getTemplate() {
        return template == null ? "" : template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTargetPackage() {
        return targetPackage == null ? "" : targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public boolean isEntityAnnotation() {
        return entityAnnotation;
    }

    public void setEntityAnnotation(boolean entityAnnotation) {
        this.entityAnnotation = entityAnnotation;
    }

    public boolean isIgnoredColumns() {
        return ignoredColumns;
    }

    public void setIgnoredColumns(boolean ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }

    public String getMappingPath() {
        return mappingPath;
    }

    public void setMappingPath(String mappingPath) {
        if (mappingPath != null && Files.notExists(Paths.get(mappingPath))) {
            throw new ApplicationException("文件不存在：{0}", mappingPath);
        }

        this.mappingPath = mappingPath;
    }

    public CaseInsensitiveMap getMapping() {
        if (mapping == null) {
            if (mappingPath != null) {
                try (InputStream in = Context.class.getResourceAsStream("/" + mappingPath)) {
                    Properties properties = new Properties();
                    properties.load(in);
                    return new CaseInsensitiveMap(properties);
                } catch (IOException e) {
                    throw new ApplicationException("读取文件错误：{0}" + mappingPath);
                }
            } else {
                return DataTypeUtil.getJdbc2Java();
            }
        }

        return new CaseInsensitiveMap(mapping);
    }

    public void setMapping(Map<String, String> mapping) {
        this.mapping = mapping;
    }

}
