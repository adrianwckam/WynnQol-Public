package net.fabricmc.wynnqolPublicized.Utils;

import java.io.*;
import java.net.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Requests {
    public static String get(String urlToRead) throws Exception  {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        return result.toString();
    }
    public static JsonObject getAsJSON(String urlToRead) throws  Exception{
        String res = get(urlToRead);
        return JsonParser.parseString(res).getAsJsonObject();
    }
}