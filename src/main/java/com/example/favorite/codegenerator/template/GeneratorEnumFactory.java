package com.example.favorite.codegenerator.template;

import com.example.favorite.codegenerator.Context;
import org.springframework.stereotype.Component;

@Component
public enum  GeneratorEnumFactory {
    FILE_TEMPLATE {
        @Override
        public Generator create(Context context) {
            return new FileTemplate(context);
        }
    },

    SIMPLE {
        @Override
        public Generator create(Context context) {
            return new SimpleTemplate(context);
        }
    },

    JAVA_GETTER_SETTER {
        @Override
        public Generator create(Context context) {
            return new GetterAndSetter(context);
        }
    };

    public abstract Generator create(Context context);

}
