package com.zantolov;

public class AndroidKeyboardCommandHelper {

    public static final String gadgetPath = "/data/local/tmp/hid-gadget-test /dev/hidg0 keyboard";

    /**
     * Returns command in Gadget friendly format
     *
     * @param rawCommand
     * @return
     */
    public static String printCommand(String rawCommand) {

        String returnCommand = "for C in ";

        for (int i = 0, n = rawCommand.length(); i < n; i++) {
            returnCommand = returnCommand + AndroidKeyboardCommandHelper.parseChar(rawCommand.charAt(i)) + " ";
        }

        returnCommand = returnCommand + "; do echo \\\"$C\\\" ; sleep 0.01 ; done | " + AndroidKeyboardCommandHelper.gadgetPath + ";";

        return returnCommand;
    }

    /**
     * Returns string representation of special char or char itself if it isn't special.
     *
     * @param c
     * @return
     */
    public static String parseChar(char c) {
        switch (c) {
            case ' ':
                return "space";
            case ',':
                return "comma";
            case '.':
                return "stop";
            case '\t':
                return "tab";
            case '-':
                return "minus";
            case '=':
                return "equals";
            case '(':
                return "lbracket";
            case ')':
                return "rbracket";
            case '\\':
                return "backslash";
            case '#':
                return "hash";
            case ';':
                return "semicolon";
            case '~':
                return "tilde";
            case '/':
                return "slash";
            default:
                return String.valueOf(c);
        }
    }

}
