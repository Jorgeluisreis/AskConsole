package com.jorgeluisreis.askconsole.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.fusesource.jansi.Ansi.ansi;

public class TextFormatter {

    public static String formatText(String input) {
        Pattern boldPattern = Pattern.compile("\\*\\*(.*?)\\*\\*");
        Matcher matcher = boldPattern.matcher(input);

        StringBuffer resultString = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(resultString, ansi().bold().a(matcher.group(1)).reset().toString());
        }
        matcher.appendTail(resultString);

        return resultString.toString();
    }
}