package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mahmoud Ellawatty on 25/12/2016.
 */

public class Orders_List {

    public static ArrayList<HashMap<String,List<String>>> Orders_list = new ArrayList<HashMap<String, List<String>>>();

    public ArrayList<HashMap<String, List<String>>> getOrders_list() {
        return Orders_list;
    }

    public void setOrders_list(ArrayList<HashMap<String, List<String>>> orders_list) {
        Orders_list = orders_list;
    }
}
