import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        double[] coord1 = new double[2]; //includes latitude and longtitude
        double[] coord2 = new double[2];
        String address1 = "97 Hayes Road, Bromley";
        String address2 = "57 North Road, Bath";
        String modeOfTransport = "";

        Scanner scanner = new Scanner(System.in);
        AddressConverter addressCovert = new AddressConverter();

        // GET THE ADDRESSES FROM THE INPUT

        coord1 = addressCovert.convertAddress(address1);
        TimeUnit.SECONDS.sleep(1);
        coord2 = addressCovert.convertAddress(address2);

        System.out.println("Enter the mode of transport: foot, car, bike, scooter"); //note bike refers to a cycling bike.
        modeOfTransport = scanner.nextLine();


        GrassHopper test = new GrassHopper(modeOfTransport, coord1, coord2);
        PollutionInfo test2 = new PollutionInfo();

        test.calculateTravelRoute();

        double distance = test.getDistanceTravelled();
        test2.calculateLevelConsumption(distance, modeOfTransport);




    }
}
