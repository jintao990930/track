package cn.doocom.util;

public class StringUtil {

    public static String hump2UnderLine(String str) {
        StringBuilder result = new StringBuilder();
        int len = str.length();
        int p = 0;
        while (p < len) {
            char c = str.charAt(p);
            if (c >= 'A' && c <= 'Z') {
                if (p != 0) result.append('_');
                result.append((char)(c + 32));
            } else
                result.append(c);
            ++p;
        }
        return result.toString();
    }

}
