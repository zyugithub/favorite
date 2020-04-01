package com.example.favorite.codegenerator;

import com.example.favorite.codegenerator.exception.ApplicationException;
import com.example.favorite.codegenerator.template.GeneratorEnumFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {
    private static final String TABLES = "-tables";
    private static final String OUTPUT = "-output";

    private Engine engine;

    @Autowired
    public ConsoleApplication(Engine engine) {
        this.engine = engine;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsoleApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, String> arguments = new HashMap<>();

        if (args.length == 0) {
            handleError("请输入参数");
        } else if (args.length == 1) {
            arguments.put(TABLES, args[0]);
        } else {
            for (int i = 0; i < args.length; i++) {
                if (TABLES.equalsIgnoreCase(args[i])) {
                    if (i + 1 < args.length) {
                        arguments.put(TABLES, args[i + 1]);
                    } else {
                        handleError("缺少 -tables 参数值");
                    }
                    i++;
                }
                if (OUTPUT.equalsIgnoreCase(args[i])) {
                    if (i + 1 < args.length) {
                        arguments.put(OUTPUT, args[i + 1]);
                    } else {
                        handleError("缺少 -output 参数值");
                    }
                    i++;
                }
            }
        }

        try {
            Context context = new Context();
            context.setOutputPath(arguments.get(OUTPUT));
            engine.setContext(context);
            String[] tableNames = arguments.get(TABLES).split(",");
            List<String> paths = engine.generate(Arrays.asList(tableNames), GeneratorEnumFactory.JAVA_GETTER_SETTER);
            paths.forEach(System.out::println);
        } catch (ApplicationException e) {
            handleError(e.getMessage());
        }
    }

    private void handleError(String message) {
        System.out.println(message);
        System.exit(-1);
    }

}
