package com.todoapplication;

import java.time.Instant;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date);
        Instant instant = date.toInstant();
        System.out.println(instant);
        System.out.println(date.toInstant().isBefore(instant));
    }
}
