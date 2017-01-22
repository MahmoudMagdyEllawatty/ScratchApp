package com.apps.scratch.scratchapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DBHelper;

public class MyOrders extends AppCompatActivity implements View.OnClickListener {

    MyOrdersListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        Toolbar t = (Toolbar) findViewById(R.id.tToolbar_myOrders);
        ImageButton back = (ImageButton) t.findViewById(R.id.title_cart_1);
        TextView title = (TextView) t.findViewById(R.id.title_children);

        title.setText("My Orders");

        dbHelper = new DBHelper(this);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.my_orders_list);

        // preparing list data
        prepareListData();

        listAdapter = new MyOrdersListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("January");
        listDataHeader.add("February");
        listDataHeader.add("March");
        listDataHeader.add("April");
        listDataHeader.add("May");
        listDataHeader.add("June");
        listDataHeader.add("July");
        listDataHeader.add("August");
        listDataHeader.add("September");
        listDataHeader.add("October");
        listDataHeader.add("November");
        listDataHeader.add("December");

        // Adding child data
        List<String> january = new ArrayList<String>();
        List<String> February = new ArrayList<String>();
        List<String> March = new ArrayList<String>();
        List<String> April = new ArrayList<String>();
        List<String> May = new ArrayList<String>();
        List<String> June = new ArrayList<String>();
        List<String> July = new ArrayList<String>();
        List<String> August = new ArrayList<String>();
        List<String> September = new ArrayList<String>();
        List<String> October = new ArrayList<String>();
        List<String> November = new ArrayList<String>();
        List<String> December = new ArrayList<String>();

        ArrayList<String> Orders = new ArrayList<>();

        Orders = dbHelper.getAllOrdersName();
        Log.e("START",String.valueOf(Orders.size()));


        for (String order : Orders){
            try{
                    String name = order.substring(0,order.indexOf("-"));
                    String order_name = name.substring(name.indexOf("/")+1);
                    String data = order;
                    Log.e("Order_NAME",order_name);
                    if(order_name.substring(0,order_name.indexOf("/")).equals("1")){
                        january.add(data );
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("2")){
                        February.add(data);
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("3")){
                        March.add(data);
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("4")){
                        April.add(data);
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("5")){
                        May.add(data);
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("6")){
                        June.add(data);
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("7")){
                        July.add(data);
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("8")){
                        August.add(data);
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("9")){
                        September.add(data);
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("10")){
                        October.add(data);
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("11")){
                        November.add(data);
                    }else if(order_name.substring(0,order_name.indexOf("/")).equals("12")){
                        December.add(data);
                    }
                    Log.e("ORDER_NAME",order_name.substring(0,order_name.indexOf("/")));
            }catch (Exception ex){
                Log.e("START",String.valueOf(ex));
            }
        }

        listDataChild.put(listDataHeader.get(0), january); // Header, Child data
        listDataChild.put(listDataHeader.get(1), February);
        listDataChild.put(listDataHeader.get(2), March);
        listDataChild.put(listDataHeader.get(3), April); // Header, Child data
        listDataChild.put(listDataHeader.get(4), May);
        listDataChild.put(listDataHeader.get(5), June);
        listDataChild.put(listDataHeader.get(6), July); // Header, Child data
        listDataChild.put(listDataHeader.get(7), August);
        listDataChild.put(listDataHeader.get(8), September);
        listDataChild.put(listDataHeader.get(9), October); // Header, Child data
        listDataChild.put(listDataHeader.get(10), November);
        listDataChild.put(listDataHeader.get(11), December);
    }


    @Override
    public void onClick(View v) {
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
}
