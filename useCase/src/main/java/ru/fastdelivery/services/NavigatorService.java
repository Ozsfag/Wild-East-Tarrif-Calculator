package ru.fastdelivery.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

@RequiredArgsConstructor
public class NavigatorService {

  private final String apiKey;

  public long calculateDistanceByCoordinates(
          double originLat, double originLng, double destinationLat, double destinationLng)
          throws Exception {

    String urlString = String.format("https://api.routing.yandex.net/v2/distancematrix?origins=%f,%f&destinations=%f,%f&apikey=%s",
            originLat, originLng, destinationLat, destinationLng, apiKey);

    JSONArray rows = getObjects(urlString);

    // Assuming the first row and first element contain the desired distance

      return rows.getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getLong("value");
  }

  private static JSONArray getObjects(String urlString) throws IOException {
    URL url = new URL(urlString);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String inputLine;
    StringBuilder content = new StringBuilder();

    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }

    in.close();
    conn.disconnect();

    JSONObject jsonResponse = new JSONObject(content.toString());
      return jsonResponse.getJSONArray("rows");
  }
}
