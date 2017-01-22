package resources;

import com.apps.scratch.scratchapp.R;

import java.util.ArrayList;

/**
 * Created by Mahmoud Ellawatty on 10/12/2016.
 */
public class Products_Arduino_Tab_data {


    public ArrayList<Integer> Images = new ArrayList<Integer>();
    public ArrayList<String> prices = new ArrayList<String>();
    public ArrayList<String> names = new ArrayList<String>();

    public ArrayList<Integer> getImages() {
        Images.add(R.mipmap._1_mega);
        Images.add(R.mipmap._1_eathernet);
        Images.add(R.mipmap._1_arduino_italy);
        Images.add(R.mipmap._1_arduino_china);
        Images.add(R.mipmap._1_rasper);
        return Images;
    }


    public ArrayList<String> getPrices() {
        prices.add("150");
        prices.add("150");
        prices.add("105");
        prices.add("95");
        prices.add("700");
        return prices;
    }


    public ArrayList<String> getNames() {
        names.add("Arduino Mega");
        names.add("Ethernet Shield");
        names.add("Arduino Uno Italy High Copy");
        names.add("Arduino Uno China");
        names.add("Rasperry Pi 3");
        return names;
    }



}
