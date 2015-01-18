package com.example.andro.android_hid;

public class AndroidKeyboardCommandHelper {

    public static final String rawTag = "#CMD ";
    public static final String bashTag = "#BASH ";
    public static final String gadgetPath = "/data/local/tmp/hid-gadget-test /dev/hidg0 keyboard";

    public static String processRow(String row) {
        if (row.startsWith(AndroidKeyboardCommandHelper.rawTag)) {
            return printRawCommand(row.replaceFirst(rawTag, ""));
        } else if (row.startsWith(AndroidKeyboardCommandHelper.bashTag)) {
            return row.replaceFirst(bashTag, "");
        } else {
            return printCommand(row);
        }
    }

    public static String printRawCommand(String command) {
        return command + " | " + AndroidKeyboardCommandHelper.gadgetPath + ";";
    }

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

        returnCommand = returnCommand + "; do echo \"$C\" ; sleep 0.02 ; done | " + AndroidKeyboardCommandHelper.gadgetPath + ";";

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
            case '_':
                return "'left-shift minus'";
            case '=':
                return "equals";
            case '(':
                return "lbracket";
            case ')':
                return "rbracket";
            case '\\':
                return "left-alt kp-9 kp-2";
            case '#':
                return "hash";
            case ';':
                return "semicolon";
            case ':':
                return "'left-shift semicolon'";
            case '~':
                return "tilde";
            case '/':
                return "slash";
            case '%':
                return "'left-shift 5";
            case '>':
                return "'left-shift stop'";
            default:
                if (Character.isUpperCase(c)) {
                    return "'left-shift " + Character.toLowerCase(c) + "'";
                } else {
                    return String.valueOf(c);
                }
        }
    }

}

