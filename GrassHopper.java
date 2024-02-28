import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GrassHopper {

    private final String API_KEY = "86afc34c-7b7e-46f5-af90-952e2653f8c2";
    private final String API_URL = "https://graphhopper.com/api/1/route";

    private String modeOfTransport = "";
    private double[] startCoord;
    private double[] endCoord;

    private double distanceTravelled;  //in kilometers
    private double timeTaken;         //in hours

    public GrassHopper(String modeOfTransport, double[] startCoord, double[] endCoord){
        this.modeOfTransport = modeOfTransport;
        this.startCoord = startCoord;
        this.endCoord = endCoord;
    }

    public void calculateTravelRoute(){
        try {
            // Import coordinates for start and end points
            String start = importCoordinates(startCoord[0], startCoord[1]); // Latitude, Longitude of start point
            String end = importCoordinates(endCoord[0], endCoord[1]);   // Latitude, Longitude of end point

            String urlString = API_URL + "?point=" + URLEncoder.encode(start, "UTF-8") + "&point=" + URLEncoder.encode(end, "UTF-8") + "&profile="+modeOfTransport + "&key=" + API_KEY;

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
                for(String line: lines){

                    distanceTravelled = getDistance(line)/1000;
                    timeTaken = getTime(line)/(1000*60*60);
                    System.out.println("Time to travel is "+ timeTaken + "hours " + " & Distance to travel is " + distanceTravelled + "km");
                }


            } else {
                System.out.println("HTTP request failed with response code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String importCoordinates(double latitude, double longitude) {
        return latitude + "," + longitude;
    }

    private double getTime(String data){
        Pattern pattern = Pattern.compile("\"time\":(\\d+\\.?\\d*)");
        Matcher matcher = pattern.matcher(data);
        double distance = 0;

        // Check if the pattern is found
        if (matcher.find()) {
            distance = Double.parseDouble(matcher.group(1));
            return distance;
        }
        return 0;
    }

    private double getDistance(String data){
        Pattern pattern = Pattern.compile("\"distance\":(\\d+\\.?\\d*)");
        Matcher matcher = pattern.matcher(data);
        double distance = 0;

        // Check if the pattern is found
        if (matcher.find()) {
            distance = Double.parseDouble(matcher.group(1));
            return distance;
        }
        return 0;
    }


    public double getDistanceTravelled(){
        return distanceTravelled;
    }
    public double getTimeTaken(){
        return timeTaken;
    }


}
