package com.apps.scratch.scratchapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import db.DBHelper;

public class MyWishList extends AppCompatActivity implements View.OnClickListener {

    DBHelper db ;
    EditText search;
    ImageButton menu_bar , cart_bar;

    WishListAdapter wishListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wish_list);

        Toolbar t = (Toolbar) findViewById(R.id.tToolbar_wish_list);
        ImageButton back = (ImageButton) t.findViewById(R.id.title_cart_1);
        TextView title = (TextView) t.findViewById(R.id.title_children);

        title.setText("My Wish List");

        ListView list = (ListView) findViewById(R.id.myOrdersList);



        // Database section
        db = new DBHelper(this);
        ArrayList<String> myWishList = new ArrayList<>();
        myWishList = db.getAllLikes();

        Log.e("NO",String.valueOf(myWishList.size()));

        wishListAdapter = new WishListAdapter(getApplicationContext(),myWishList);
        list.setAdapter(wishListAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }




    @Override
    public void onClick(View v) {
    }



    public class WishListAdapter extends BaseAdapter {

        ArrayList<String> order_name;
        Context context;

        private LayoutInflater inflater=null;

        public WishListAdapter(Context c,ArrayList<String> name){
            order_name = name;
            context  =c;

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }

        @Override
        public int getCount() {
            return order_name.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            Holder holder;
            Log.e("TRY","1");
            if(convertView == null){
                Log.e("TRY","2");
                v = inflater.inflate(R.layout.wish_list_row,null);
                holder = new Holder();
                holder.name = (TextView) v.findViewById(R.id.wish_list_row_text);

                v.setTag(holder);
            }
            else
                holder = (Holder) v.getTag();

            holder.name.setText(order_name.get(position));
            return v;
        }

        class Holder{
            TextView name;
        }
    }


    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
}
