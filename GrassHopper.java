import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class GrassHopper {

    private final String API_KEY = "86afc34c-7b7e-46f5-af90-952e2653f8c2";
    private final String API_URL = "https://graphhopper.com/api/1/route";

    public GrassHopper(){
        testAPI();
    }

    public void testAPI(){
        try {
            // Import coordinates for start and end points
            String start = importCoordinates(51.51, -0.12); // Latitude, Longitude of start point
            String end = importCoordinates(51.51, -0.13);   // Latitude, Longitude of end point

            // on foot
            String urlString = API_URL + "?point=" + URLEncoder.encode(start, "UTF-8") + "&point=" + URLEncoder.encode(end, "UTF-8") + "&profile=foot" +  "&key=" + API_KEY;

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Process the response data
                String[] lines = response.toString().split("\\r?\\n");

                for (String line : lines) {
                    System.out.println(line);
                }
            } else {
                System.out.println("HTTP request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to import coordinates
    public String importCoordinates(double latitude, double longitude) {
        return latitude + "," + longitude;
    }

    public static void main(String[] args) {
        new GrassHopper();
    }
}