package org.linky.internal.generators.implementations;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linky.Application;
import org.linky.application.config.InternalConfiguration;
import org.linky.internal.generators.IGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class, InternalConfiguration.class})
@ComponentScan("org.linky")
public class GeneratorTest {

    @Autowired
    private IGenerator generator;

    @Value("${linky.test.generation.values}")
    private int generateValues;


    @Test
    public void generatorTest() {
        Set<String> codes = new HashSet<>();
        for (int i = 0; i < generateValues; i++) {
            codes.add(generator.generate());
        }
        Assert.assertEquals(codes.size(), generateValues);
    }
}
