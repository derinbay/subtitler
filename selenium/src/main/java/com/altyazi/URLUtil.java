package com.altyazi;

public class URLUtil {
    public static String https(String urlWithHttp) {
        return urlWithHttp.replace("http", "https");
    }

    public static boolean contains(String url1, String url2) {
        String url1Cropped = url1.replace("https", "").replace("http", "");
        String url2Cropped = url2.replace("https", "").replace("http", "");
        return url1Cropped.contains(url2Cropped);
    }
}
