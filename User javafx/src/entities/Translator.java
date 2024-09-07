package entities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Translator {
    private final String apiKey;
    private final String apiUrl;

    public Translator() {
        this.apiKey = "994e7cd89756ee466ab4";
        this.apiUrl = "https://api.mymemory.translated.net/get?q=%s&langpair=%s";
    }

    public String translate(String text, String fromLang, String toLang) throws Exception {
        String url = String.format(apiUrl, URLEncoder.encode(text, "UTF-8"), fromLang + "|" + toLang);
        url += "&key=" + apiKey;

        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
        String response = in.readLine();
        in.close();

        if (status != 200) {
            throw new Exception("Error translating text. Status code: " + status);
        }

        // Parse the JSON response and extract the translated text
        String translation = response.substring(response.indexOf("\"translatedText\":\"") + 18);
        translation = translation.substring(0, translation.indexOf("\""));
        System.out.println(translation);
        return translation;
    }
}
