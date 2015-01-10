package com.zantolov;

public class Main {

    public static void main(String[] args) {
	    Main.testClass();
    }

    public static void testClass(){

        String parsedCommand = AndroidKeyboardCommandHelper.printCommand("echo hello world ,.-;:");

        System.out.print(parsedCommand);
    }
}
