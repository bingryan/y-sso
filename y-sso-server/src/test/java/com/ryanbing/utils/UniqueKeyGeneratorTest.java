package com.ryanbing.utils;

import org.junit.Test;


public class UniqueKeyGeneratorTest {

    @Test
    public void md5Encrypt() {
        String admin = UniqueKeyGenerator.md5Encrypt("admin");
        System.out.println(admin);
    }
}