package com.jorgeluisreis.askconsole.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.fusesource.jansi.Ansi.ansi;

public class TextFormatter {

    public static String formatText(String input) {
        Pattern boldPattern = Pattern.compile("\\*\\*(.*?)\\*\\*");
        Matcher boldMatcher = boldPattern.matcher(input);
        StringBuffer resultString = new StringBuffer();

        while (boldMatcher.find()) {
            boldMatcher.appendReplacement(resultString,
                    ansi().bold().a(boldMatcher.group(1)).reset().toString());
        }
        boldMatcher.appendTail(resultString);

        Pattern italicBoldPattern = Pattern.compile("\\*(.*?)\\*");
        Matcher italicBoldMatcher = italicBoldPattern.matcher(resultString.toString());
        resultString.setLength(0);

        while (italicBoldMatcher.find()) {
            italicBoldMatcher.appendReplacement(resultString,
                    "\033[3m" + ansi().bold().a(italicBoldMatcher.group(1)).reset() + "\033[0m");
        }
        italicBoldMatcher.appendTail(resultString);

        Pattern italicGravePattern = Pattern.compile("`(.*?)`");
        Matcher italicGraveMatcher = italicGravePattern.matcher(resultString.toString());
        StringBuffer finalString = new StringBuffer();

        while (italicGraveMatcher.find()) {
            italicGraveMatcher.appendReplacement(finalString,
                    "\033[3m" + italicGraveMatcher.group(1) + "\033[0m");
        }
        italicGraveMatcher.appendTail(finalString);

        return finalString.toString();
    }
}