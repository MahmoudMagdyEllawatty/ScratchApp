package resources;

import com.apps.scratch.scratchapp.R;

import java.util.ArrayList;

/**
 * Created by Mahmoud Ellawatty on 11/12/2016.
 */
public class Products_Motors_Tab_data {
    public ArrayList<Integer> Images = new ArrayList<Integer>();
    public ArrayList<String> prices = new ArrayList<String>();
    public ArrayList<String> names = new ArrayList<String>();

    public ArrayList<Integer> getImages() {
        Images.add(R.mipmap._6_motor);
        Images.add(R.mipmap._6_wheel);
        Images.add(R.mipmap._6_servo_small);
        Images.add(R.mipmap._6_servo_large);
        return Images;
    }


    public ArrayList<String> getPrices() {
        prices.add("20");
        prices.add("15");
        prices.add("45");
        prices.add("65");
        return prices;
    }


    public ArrayList<String> getNames() {
        names.add("yellow Motor");
        names.add("Wheel");
        names.add("Micro Servo");
        names.add("Servo");
        return names;
    }

}
