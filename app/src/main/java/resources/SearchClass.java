package resources;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by Mahmoud Ellawatty on 13/01/2017.
 */

public class SearchClass {

    Object[] dataClasses = new Object[9];

    public ArrayList<Integer> Images = new ArrayList<Integer>();
    public ArrayList<String> prices = new ArrayList<String>();
    public ArrayList<String> names = new ArrayList<String>();


    public ArrayList<Integer> result_Images = new ArrayList<Integer>();
    public ArrayList<String> result_prices = new ArrayList<String>();
    public ArrayList<String> result_names = new ArrayList<String>();

    public SearchClass(){
        dataClasses[0] = new Products_Arduino_Accesories_Tab_data();
        dataClasses[1] = new Products_Arduino_Tab_data();
        dataClasses[2] = new Products_Drivers_Tab_data();
        dataClasses[3] = new Products_Ics_Tab_data();
        dataClasses[4] = new Products_Motors_Tab_data();
        dataClasses[5] = new Products_Our_Products_Tab_data();
        dataClasses[6] = new Products_PCB_Tab_data();
        dataClasses[7] = new Products_Sensors_Tab_data();
        dataClasses[8] = new Products_Switches_Tab_data();
    }


    public ArrayList<ArrayList> getSearchResult(CharSequence word) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        setData();

        ArrayList<ArrayList> result = new ArrayList<ArrayList>() ;

        for(int i = 0 ; i < names.size() ; i++){
            if(names.get(i).toLowerCase().contains(word.toString().toLowerCase())){
                result_Images.add(Images.get(i));
                result_prices.add(prices.get(i));
                result_names.add(names.get(i));
            }
        }

        result.add(result_Images);
        result.add(result_prices);
        result.add(result_names);


        return result;
    }


    public void setData()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Object cls: dataClasses) {
            Object uses = cls;
            names.addAll((ArrayList<String>) cls.getClass().getMethod("getNames").invoke(uses));
            Images.addAll((ArrayList<Integer>) cls.getClass().getMethod("getImages").invoke(uses));
            prices.addAll((ArrayList<String>) cls.getClass().getMethod("getPrices").invoke(uses));
        }
    }


}
