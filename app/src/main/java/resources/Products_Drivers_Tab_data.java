package resources;

import com.apps.scratch.scratchapp.R;

import java.util.ArrayList;

/**
 * Created by Mahmoud Ellawatty on 11/12/2016.
 */
public class Products_Drivers_Tab_data {

    public ArrayList<Integer> Images = new ArrayList<Integer>();
    public ArrayList<String> prices = new ArrayList<String>();
    public ArrayList<String> names = new ArrayList<String>();

    public ArrayList<Integer> getImages() {
        Images.add(R.mipmap._8_h);
        Images.add(R.mipmap._8_d);
        return Images;
    }


    public ArrayList<String> getPrices() {
        prices.add("50");
        prices.add("25");
        return prices;
    }


    public ArrayList<String> getNames() {
        names.add("L298N Driver");
        names.add("L293D Driver");
        return names;
    }

}
