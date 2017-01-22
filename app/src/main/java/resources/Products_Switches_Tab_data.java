package resources;

import com.apps.scratch.scratchapp.R;

import java.util.ArrayList;

/**
 * Created by Mahmoud Ellawatty on 11/12/2016.
 */
public class Products_Switches_Tab_data {

    public ArrayList<Integer> Images = new ArrayList<Integer>();
    public ArrayList<String> prices = new ArrayList<String>();
    public ArrayList<String> names = new ArrayList<String>();

    public ArrayList<Integer> getImages() {
        Images.add(R.mipmap._5_dip_1);
        Images.add(R.mipmap._5_dip_2);
        Images.add(R.mipmap._5_switch);
        return Images;
    }


    public ArrayList<String> getPrices() {
        prices.add("1.5");
        prices.add("2");
        prices.add("0.75");
        return prices;
    }


    public ArrayList<String> getNames() {
        names.add("single DIP switch");
        names.add("double DIP switch");
        names.add("push button");
        return names;
    }


}
