package com.apps.scratch.scratchapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import db.DBHelper;
import entity.Item;
import entity.Order;
import entity.Orders_List;

public class Orders extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mFirebaseAuth;

    public ArrayList<HashMap<String,List<String>>> Orders_list = new ArrayList<HashMap<String, List<String>>>();
  //  public VerticalAdapter verticalAdapter;
  //  public RecyclerView order_view;
    ListView orders;
    TextView total;
    Button order_now;


    ArrayList<String> order_Names = new ArrayList<>();
    ArrayList<String> order_Quantity = new ArrayList<>();
    ArrayList<String> order_Prices = new ArrayList<>();

    OrderAdapter adapter;

    DBHelper  dbHelper;
    String total_money="";
    String phone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        mFirebaseAuth = FirebaseAuth.getInstance();

       // order_view = (RecyclerView) findViewById(R.id.Orders_recycler_view);

        Toolbar t = (Toolbar) findViewById(R.id.tToolbar_orders);
        ImageButton back = (ImageButton) t.findViewById(R.id.title_cart_1);

        TextView title = (TextView) t.findViewById(R.id.title_children);

        title.setText("My Order Details");

        String where = getIntent().getStringExtra("Where");

        dbHelper = new DBHelper(this);
        total = (TextView) findViewById(R.id.total);
        orders = (ListView) findViewById(R.id.orders_list);
        Orders_list = Orders_List.Orders_list ;
        order_now = (Button) findViewById(R.id.order_now);


        total_money = String.valueOf(createTable());
        total.setText(" "+total_money+" L.E");

        if(where.equals("0"))
            order_now.setEnabled(false);
        else
            order_now.setEnabled(true);

        Log.e("SIZE",String.valueOf( order_Names.size()));
        Log.e("SIZE",String.valueOf( order_Quantity.size()));
        Log.e("SIZE",String.valueOf( order_Prices.size()));

        adapter = new OrderAdapter(getApplicationContext(), order_Names,order_Quantity,order_Prices);

        orders.setAdapter(adapter);


        order_now.setOnClickListener(this);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    double createTable(){
        double sum=0;
        for(int i=0 ;i < Orders_list.size();i++){
            Set Keys = Orders_list.get(i).keySet();

            for (Iterator ii = Keys.iterator(); ii.hasNext();)
            {

                String key = (String) ii.next();
                order_Names.add(key);
                List<String> value = Orders_list.get(i).get(key);
                order_Prices.add(value.get(2));
                order_Quantity.add(value.get(3));

                sum +=Double.parseDouble(value.get(2))  * Double.parseDouble(value.get(3));

            }
        }

        return sum;
    }


    void showOrder(){
        for(int i=0 ;i < Orders_list.size();i++){
            Set Keys = Orders_list.get(i).keySet();
            Log.e("Orders_Keys", String.valueOf(Keys));

            for (Iterator ii = Keys.iterator(); ii.hasNext();)
            {
                String key = (String) ii.next();
                Log.e("Orders_Key",key);
                List<String> value = Orders_list.get(i).get(key);
                for (String val: value) {
                    Log.e("Orders_Value",val);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_orders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.order_now:
                saveOrders();
                selectMethod();
                break;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void sendOrderToFireBase() {

        if(isNetworkAvailable()){

        String user_name = mFirebaseAuth.getCurrentUser().getDisplayName();
        String uid = mFirebaseAuth.getCurrentUser().getUid();
        String user_phone = phone;
        String order_total = total_money;
        String Items = "";

        for(int i=0;i< order_Names.size() ; i++){
            Items +=order_Names.get(i)+"---"+order_Quantity.get(i)+"/";
        }


        Order order = new Order(user_name,Items,user_phone,order_total);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Orders");

        mDatabase.child(uid).setValue(order);

        Toast.makeText(getApplicationContext(),"تم الارسال.سيتم الاتصال بك قريبا منا. شكرا لثقتك بنا",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"لا يوجد اتصال بالانترنت. من فضلك تأكد من تواجد اتصال بالانترنت",Toast.LENGTH_LONG).show();
        }

    }


    void selectMethod(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText text = new EditText(this);
        text.setInputType(InputType.TYPE_CLASS_PHONE);
        builder.setTitle("من فضلك ادخل رقم تليفون متاح.")
                .setView(text)
                .setPositiveButton("ارسال", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface di, int i) {
                        final String name = text.getText().toString();
                        phone = name;
                        //do something with it
                        sendOrderToFireBase();
                    }
                });
        builder.setNegativeButton("الغاء", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface di, int i) {
            }
        });
        builder.show();
    }

    private void saveOrders() {
        String NAME =String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) +"/"+
                String.valueOf(Calendar.getInstance().get(Calendar.MONTH)+1)+"/"+
                        String.valueOf(Calendar.getInstance().get(Calendar.YEAR))+" "+
                String.valueOf(Calendar.getInstance().get(Calendar.HOUR))+":"+
                String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));

        Log.e("Name",NAME);

        ArrayList<Item> DBSave = new ArrayList<>();
        for(int i=0;i< order_Names.size() ; i++){

            Item item = new Item(order_Names.get(i),Double.parseDouble( order_Prices.get(i)),
                   Integer.parseInt( order_Quantity.get(i)),NAME,Double.parseDouble(total.getText().toString().substring(0,total.getText().toString().indexOf("L"))));

            DBSave.add(item);
        }

        dbHelper.addOrder(DBSave,NAME,total_money);
    }



    public class OrderAdapter extends BaseAdapter {

        ArrayList<String> order_name;
        ArrayList<String> order_qnt;
        ArrayList<String> order_price;
        Context context;

        private LayoutInflater inflater=null;

        public OrderAdapter(Context c,ArrayList<String> name , ArrayList<String> qnt , ArrayList<String> price){
            order_name = name;
            order_qnt = qnt;
            order_price = price;
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
                v = inflater.inflate(R.layout.order_row,null);
                holder = new Holder();
                holder.name = (TextView) v.findViewById(R.id.order_name);
                holder.price = (TextView) v.findViewById(R.id.order_price);
                holder.qnt = (TextView) v.findViewById(R.id.order_quantity);

                v.setTag(holder);
            }
            else
               holder = (Holder) v.getTag();

            Log.e("TRY","3");

            holder.name.setText(order_name.get(position));
            holder.price.setText(order_price.get(position));
            holder.qnt.setText(order_Quantity.get(position));
            Log.e("TRY","5");
            return v;
        }

        class Holder{
            TextView name;
            TextView price;
            TextView qnt;
        }
    }


}
