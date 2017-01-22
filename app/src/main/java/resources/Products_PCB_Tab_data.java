package resources;

import com.apps.scratch.scratchapp.R;

import java.util.ArrayList;

/**
 * Created by Mahmoud Ellawatty on 11/12/2016.
 */
public class Products_PCB_Tab_data {

    public ArrayList<Integer> Images = new ArrayList<Integer>();
    public ArrayList<String> prices = new ArrayList<String>();
    public ArrayList<String> names = new ArrayList<String>();

    public ArrayList<Integer> getImages() {
        Images.add(R.mipmap._4_connector_2);
        Images.add(R.mipmap._4_connector_3);
        Images.add(R.mipmap._4_resistor);
        Images.add(R.mipmap._4_breadboard_med);
        Images.add(R.mipmap._4_breadboard_smaller);
        Images.add(R.mipmap._4_prefboard);
        Images.add(R.mipmap._4_capacitor);
        Images.add(R.mipmap._4_diode);
        return Images;
    }


    public ArrayList<String> getPrices() {
        prices.add("0.75");
        prices.add("1");
        prices.add("0.125");
        prices.add("18");
        prices.add("10");
        prices.add("5");
        prices.add("0.5");
        prices.add("0.5");
        return prices;
    }


    public ArrayList<String> getNames() {
        names.add("Connector 2 pin");
        names.add("Connector 3 pin");
        names.add("Resistors");
        names.add("Medium BreadBoard");
        names.add("Small BreadBoard");
        names.add("PerfBoard");
        names.add("Capacitor");
        names.add("Diode");
        return names;
    }

}
