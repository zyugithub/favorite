package com.example.favorite;

import com.example.favorite.codegenerator.Engine;
import com.example.favorite.codegenerator.extension.SchematicGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FavoriteApplicationTest {
    @Autowired
    private Engine engine;
    @Autowired
    private SchematicGenerator schematicGenerator;

    @Test
    public void test() {
        schematicGenerator.generate();
    }
}
