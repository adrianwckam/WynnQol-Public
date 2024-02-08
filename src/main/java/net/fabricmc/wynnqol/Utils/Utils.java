package net.fabricmc.wynnqol.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static List<String> groupList(String text, Pattern pattern) {
        List<String> list = new ArrayList<>();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                list.add(matcher.group(i));
            }
        }
        return list;
    }
}
