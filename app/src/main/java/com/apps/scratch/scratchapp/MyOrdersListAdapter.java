package com.apps.scratch.scratchapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DBHelper;
import entity.Item;
import entity.Orders_List;

/**
 * Created by Mahmoud Ellawatty on 06/01/2017.
 */

public class MyOrdersListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    public ArrayList<HashMap<String,List<String>>> Orders_list = new ArrayList<HashMap<String, List<String>>>();
    DBHelper db;

    public MyOrdersListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        db = new DBHelper(context);
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }




    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        TextView txtListChilds = (TextView) convertView
                .findViewById(R.id.lblListItems);

        txtListChild.setText(childText.substring(0,childText.indexOf("-")));
        txtListChilds.setText(childText.substring(childText.indexOf("-")+1));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Item> items =  db.getOrderByName(childText.substring(0,childText.indexOf("-")));
                Orders_list = new ArrayList<HashMap<String, List<String>>>();
                for(Item item : items)
                    addOrder(item.getItem_name(),1,String.valueOf(item.getItem_price()),item.getItem_quantity());


                Orders_List.Orders_list = new ArrayList<HashMap<String, List<String>>>();
                Orders_List.Orders_list = Orders_list;
                Intent intent = new Intent(_context , Orders.class);
                intent.putExtra("Where","0");
                _context.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void addOrder(String name, Integer image, String price, int quantity) {
        HashMap<String , List<String>> order = new HashMap<>();
        List<String> list = new ArrayList<>();
        list.add(name);
        list.add(String.valueOf(image));
        list.add(price);
        list.add(String.valueOf(quantity));

        order.put(name , list);

        Orders_list.add(order);
    }
}
