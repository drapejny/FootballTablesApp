package by.slizh.lab_2.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class JsonTeamsLoader {

    private static final String API_TOKEN = "cf9d6ffa3a794a5e995398cfedf5dd88";

    public static String load(String urlString) throws IOException {

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("X-Auth-Token", API_TOKEN);
        connection.setRequestMethod("GET");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String output;
            StringBuffer response = new StringBuffer();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            return response.toString();
        }
    }
}
