package resources;

import com.apps.scratch.scratchapp.R;

import java.util.ArrayList;

/**
 * Created by Mahmoud Ellawatty on 11/12/2016.
 */
public class Products_Arduino_Accesories_Tab_data {

    public ArrayList<Integer> Images = new ArrayList<Integer>();
    public ArrayList<String> prices = new ArrayList<String>();
    public ArrayList<String> names = new ArrayList<String>();

    public ArrayList<Integer> getImages() {
        Images.add(R.mipmap._2_bluetooth);
        Images.add(R.mipmap._2_lcd);
        Images.add(R.mipmap._2_65wires);
        Images.add(R.mipmap._2_keybad);
        Images.add(R.mipmap._2_adaptor);
        Images.add(R.mipmap._2_male_female);
        Images.add(R.mipmap._2_female_female);
        Images.add(R.mipmap._2_male_male);
        Images.add(R.mipmap._2_battery);
        Images.add(R.mipmap._2_socket);

        return Images;
    }


    public ArrayList<String> getPrices() {
        prices.add("85");
        prices.add("25");
        prices.add("25");
        prices.add("25");
        prices.add("25");
        prices.add("0.75");
        prices.add("0.75");
        prices.add("0.75");
        prices.add("5");
        prices.add("5");
        return prices;
    }


    public ArrayList<String> getNames() {
        names.add("Bluetooth Module hc-06");
        names.add("LCD 16*2");
        names.add("65 wire");
        names.add("Key bad");
        names.add("Adaptor");
        names.add("Male - Female");
        names.add("Female - Female");
        names.add("Male - Male");
        names.add("Battery Holder");
        names.add("Power Socket");

        return names;
    }

}
