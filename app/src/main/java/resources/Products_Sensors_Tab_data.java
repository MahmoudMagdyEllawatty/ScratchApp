package resources;

import com.apps.scratch.scratchapp.R;

import java.util.ArrayList;

/**
 * Created by Mahmoud Ellawatty on 11/12/2016.
 */
public class Products_Sensors_Tab_data {
    public ArrayList<Integer> Images = new ArrayList<Integer>();
    public ArrayList<String> prices = new ArrayList<String>();
    public ArrayList<String> names = new ArrayList<String>();

    public ArrayList<Integer> getImages() {
        Images.add(R.mipmap._7_ir);
        Images.add(R.mipmap._7_ldr);
        Images.add(R.mipmap._7_proximity);
        Images.add(R.mipmap._7_ultrasonic);
        Images.add(R.mipmap._7_wireless);
        Images.add(R.mipmap._7_buzzer);
        Images.add(R.mipmap._7_lm35);

        return Images;
    }


    public ArrayList<String> getPrices() {
        prices.add("5");
        prices.add("2");
        prices.add("65");
        prices.add("35");
        prices.add("35");
        prices.add("5");
        prices.add("10");
        return prices;
    }


    public ArrayList<String> getNames() {
        names.add("IR sensor");
        names.add("LDR");
        names.add("Proximity Sensor");
        names.add("Ultrasonic");
        names.add("WireLess");
        names.add("Buzzer");
        names.add("LM35 Temprature Sensor");
        return names;
    }

}
