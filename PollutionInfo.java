import java.util.HashMap;

public class PollutionInfo {

    private HashMap<String, HashMap<String, Double>> vehicleInfo;
    PollutionInfo(){
        vehicleInfo = new HashMap<String, HashMap<String, Double>>(){};
        setUpInfo();
    }

     /*
      The following values below are average values.
     */
    private void setUpInfo(){

        // CARS //
        HashMap<String, Double> tableCar = new HashMap<String,Double>(){{
            put("Fuel_Consumption_Rate", 0.08);
            put("Carbon_Intensity", 0.1);
            put("Weight", 1900.0);

        }};

        // BIKE //
        HashMap<String, Double> tableBike = new HashMap<String, Double>(){{
            put("Fuel_Consumption_Rate", 0.05);
            put("Carbon_Intensity", 0.0);
            put("Weight", 180.0);

        }};

        // WALK //

        HashMap<String, Double> tableWalk = new HashMap<String, Double>(){{
            put("Fuel_Consumption_Rate", 0.0);
            put("Carbon_Intensity", 0.0);
            put("Weight", 62.0);

        }};

        //Add all information onto the main hashmap
        vehicleInfo.put("car", tableCar);
        vehicleInfo.put("bike", tableBike);
        vehicleInfo.put("walk", tableWalk);

    }

    public double get_Info(String vehicle, String option) {
        try {
            return vehicleInfo.get(vehicle).get(option);
        }
        catch(Exception e) {
            return 0.0;
        }
    }

    public void calculateLevelConsumption(double Distance, String vehicle){
        //double fuel = vehicleInfo.get(vehicle).get("Fuel_Consumption_Rate");
        double result = Distance  * vehicleInfo.get(vehicle).get("Fuel_Consumption_Rate") * vehicleInfo.get(vehicle).get("Carbon_Intensity");
        System.out.println("Carbon produced is: " + result + "kg/km");


    }

}
