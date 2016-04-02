package com.altyazi;

import com.google.common.base.Joiner;

import java.util.Map;
import java.util.Random;
import java.util.Vector;

public class StringUtil {
    public static String mapJoin(Map<String, String> map) {
        Joiner.MapJoiner joiner = Joiner.on(",").withKeyValueSeparator("");

        return joiner.join(map);
    }

    public static String mapToString(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String key : map.keySet()) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(" , ");
            }
            String value = map.get(key);

            stringBuilder.append((key != null ? key : ""));
            stringBuilder.append("=");
            stringBuilder.append(value != null ? value : "");

        }

        return stringBuilder.toString();
    }

    public static boolean isUpper(String str) {
        boolean upperFound = false;
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperFound = true;
                break;
            }
        }
        return upperFound;
    }

    public static Double parseStringToDouble(String doubleStrIn) {
        doubleStrIn = doubleStrIn.replaceAll("[^\\d,\\.]++", "");
        if (doubleStrIn.matches(".+\\.\\d+,\\d+$"))
            return Double.parseDouble(doubleStrIn.replaceAll("\\.", "").replaceAll(",", "."));
        if (doubleStrIn.matches(".+,\\d+\\.\\d+$"))
            return Double.parseDouble(doubleStrIn.replaceAll(",", ""));
        return Double.parseDouble(doubleStrIn.replaceAll(",", "."));
    }

    public static String createRandomTCNo() {
        System.out.println("Generating TC Identity Number...");
        Vector<Integer> array = new Vector<>();
        Random randomGenerator = new Random();

        array.add(new Integer(1 + randomGenerator.nextInt(9)));

        for (int i = 1; i < 9; i++) array.add(randomGenerator.nextInt(10));

        int t1 = 0;
        for (int i = 0; i < 9; i += 2) t1 += array.elementAt(i);

        int t2 = 0;
        for (int i = 1; i < 8; i += 2) t2 += array.elementAt(i);

        int x = (t1 * 7 - t2) % 10;

        array.add(new Integer(x));

        x = 0;
        for (int i = 0; i < 10; i++) x += array.elementAt(i);

        x = x % 10;
        array.add(new Integer(x));

        String res = "";
        for (int i = 0; i < 11; i++) res = res + Integer.toString(array.elementAt(i));

        System.out.println("Generated TC Identity Number is: " + res);
        return res;
    }

    public static int generateRandomInt(int limit) {
        Random generator = new Random();
        int i = generator.nextInt(limit);

        return i + 1;
    }
}
