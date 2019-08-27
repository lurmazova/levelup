package utils;

import org.apache.commons.lang.RandomStringUtils;

public class StringRandomizer {
    public StringRandomizer() {
    }

    public String makeRandomString(int length, boolean useLetters, boolean useNumbers) {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }

}
