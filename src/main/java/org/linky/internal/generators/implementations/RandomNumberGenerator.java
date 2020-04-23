package org.linky.internal.generators.implementations;

import org.linky.internal.generators.IGenerator;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

public class RandomNumberGenerator implements IGenerator {

    private static final char[] DICTIONARY = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z', 'A', 'B',
            'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3',
            '4', '5', '6', '7', '8', '9'
    };

    @Value("${org.linky.code.length}")
    private int codeLength = 10;

    /**
     * The chances of having a duplicated code is probably close to 0
     * As a starter code generation this should be fine, but if problem occur
     * a new implementation of IGenerator can be created and injected by spring
     * on the configuration bean class
     */
    @Override
    public String generate() {
        Random random = new Random();
        String generatedCode = "";
        for (int i = 0; i < codeLength; i++) {
            generatedCode = generatedCode.concat(String.valueOf(DICTIONARY[random.nextInt(DICTIONARY.length)]));
        }
        return generatedCode;
    }
}
