package com.example.favorite.codegenerator.extension;

import com.alibaba.fastjson.JSON;
import com.example.favorite.codegenerator.Context;
import com.example.favorite.codegenerator.Engine;
import com.example.favorite.codegenerator.exception.ApplicationException;
import com.example.favorite.codegenerator.template.GeneratorEnumFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static com.example.favorite.codegenerator.util.SymbolUtil.replace;

@Component
public class SchematicGenerator {
    @Value("classpath:schematic.json")
    private Resource schematic;

    private Engine engine;

    @Autowired
    public SchematicGenerator(Engine engine) {
        this.engine = engine;
    }

    public void generate() {
        try {
            byte[] bytes = Files.readAllBytes(schematic.getFile().toPath());
            String json = new String(bytes);
            Schematic schematic = JSON.parseObject(json, Schematic.class);

            List<Context> contexts = schematic.getContexts();
            for (Context context : contexts) {
                context.setOutputPath(schematic.getOutputPath());
                context.setOutputType(schematic.getOutputType());
                context.setTemplate("templates" + File.separator + context.getTemplate());
                context.setTargetPackage(schematic.getProject() + "." + replace(context.getTargetPackage(), schematic.getSource()));

                engine.setContext(context);
                engine.generate(schematic.getSource(), GeneratorEnumFactory.valueOf(context.getTemplateType()));
            }
        } catch (IOException e) {
            throw new ApplicationException(e);
        }
    }

}
