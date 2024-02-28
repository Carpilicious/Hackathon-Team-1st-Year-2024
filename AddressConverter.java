
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AddressConverter {

    private final String  API_KEY = "https://geocode.maps.co/search?q=address&api_key=65df3297331f5068188173sfw8030a9";
    private String address;
    private double latitude;
    private double longitude;

     /*
        EMPTY CONSTRUCTOR for AddressConverter
     */

    AddressConverter(){

    }

    public double[] convertAddress(String address) throws IOException, InterruptedException {
        this.address = address;
        convertAddress();
        return new double[]{latitude, longitude};
    }



    public void convertAddress() throws IOException, InterruptedException {

        String url = "https://geocode.maps.co/search?q="+address.replace(" ", "+")+"&api_key=65df3297331f5068188173sfw8030a9";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString()); //sending the request to web API.

        if (response.statusCode() == 200) {        // check if status code was a success. If not, throw an error

            String responseBody = response.body(); //parse JSON received
            System.out.println(responseBody);
            latitude = extractCoordinates(responseBody, "lat");
            longitude = extractCoordinates(responseBody, "lon");

        } else {
            System.out.println("Error: Unable to geocode the address.");
        }
    }

    /*
     Method regular expressions to extract the latitude and longitude of the address.
     */

    private double extractCoordinates(String returnInfo, String cmd){
        String RegPattern = "";
        if(cmd=="lat"){
            RegPattern = "\"lat\":\"([^\"]+)\"";
        }
        else if(cmd=="lon") {
            RegPattern = "\"lon\":\"([^\"]+)\"";
        }

        Pattern locPatternCompiled = Pattern.compile(RegPattern);
        Matcher locMatcher = locPatternCompiled.matcher(returnInfo);

        if (locMatcher.find()) {
            double loc =  Double.parseDouble(locMatcher.group(1));
            return loc;
        }
        return 0;

    }

    // ACCESSOR METHODS //
    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }



}
