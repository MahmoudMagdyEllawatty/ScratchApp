package resources;

import com.apps.scratch.scratchapp.R;

import java.util.ArrayList;

/**
 * Created by Mahmoud Ellawatty on 11/12/2016.
 */
public class Products_Ics_Tab_data {

    public ArrayList<Integer> Images = new ArrayList<Integer>();
    public ArrayList<String> prices = new ArrayList<String>();
    public ArrayList<String> names = new ArrayList<String>();

    public ArrayList<Integer> getImages() {
        Images.add(R.mipmap._3_ic);
        return Images;
    }


    public ArrayList<String> getPrices() {
        prices.add("2");
        return prices;
    }


    public ArrayList<String> getNames() {
        names.add("Or ic (7432)");
        return names;
    }

}
