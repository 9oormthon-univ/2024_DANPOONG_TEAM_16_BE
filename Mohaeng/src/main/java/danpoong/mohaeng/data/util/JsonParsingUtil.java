package danpoong.mohaeng.data.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonParsingUtil {
    public static JSONArray parsingJsonFromUrl(URL localUrl, StringBuilder result) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) localUrl.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-type", "application/json");

        // BufferedReader로 응답을 UTF-8로 읽어오기
        BufferedReader bf = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8));

        String line;
        while ((line = bf.readLine()) != null) {
            result.append(line);
        }

        // JSON 파싱
        JSONObject jsonResponse = new JSONObject(result.toString());
        // 응답 구조 확인
        if (!jsonResponse.has("response")) {
            throw new IOException("Invalid response structure: missing 'response' object.");
        }

        JSONObject responseBody = jsonResponse.getJSONObject("response").optJSONObject("body");
        if (responseBody == null || !responseBody.has("items")) {
            return null;  // items가 없거나 body가 없는 경우 null 반환
        }

        JSONObject items = responseBody.optJSONObject("items");
        if (items == null || !items.has("item")) {
            return null;  // item이 없는 경우 null 반환
        }

        return items.optJSONArray("item");
    }
}

