package com.ryanbing.entity;


public class ResponseResultTest {

    @org.junit.Test
    public void build() {
        System.out.println(ResponseResult.builder().message("heh").status(1).build());
    }
}