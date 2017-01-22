package com.apps.scratch.scratchapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import db.DBHelper;
import entity.Item;
import entity.Orders_List;
import resources.Products_Arduino_Accesories_Tab_data;
import resources.Products_Arduino_Tab_data;
import resources.Products_Drivers_Tab_data;
import resources.Products_Ics_Tab_data;
import resources.Products_Motors_Tab_data;
import resources.Products_Our_Products_Tab_data;
import resources.Products_PCB_Tab_data;
import resources.Products_Sensors_Tab_data;
import resources.Products_Switches_Tab_data;
import resources.SearchClass;

public class Products extends Activity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private RecyclerView horizontal_recycler_view;
    public RecyclerView product_view;

    private ArrayList<Integer> horizontalList;

    public int quantity=1;

    public ArrayList<Integer> product_image;
    public ArrayList<String> product_price;
    public ArrayList<String> product_name;

    private HorizontalAdapter horizontalAdapter;
    public VerticalAdapter verticalAdapter;

    public int pos=0;

    DBHelper db ;
    ArrayList<Item> Likes_List ;

    public ArrayList<HashMap<String,List<String>>> Orders_list = new ArrayList<HashMap<String, List<String>>>();

    Products_Arduino_Tab_data arduino_data;
    Products_Arduino_Accesories_Tab_data arduino_accesories_data;
    Products_Ics_Tab_data ics_data;
    Products_PCB_Tab_data pcb_data;
    Products_Switches_Tab_data switches_data;
    Products_Motors_Tab_data mototrs_data;
    Products_Sensors_Tab_data sensors_data;
    Products_Drivers_Tab_data drivers_data;
    Products_Our_Products_Tab_data our_products_data;

    EditText search;
    ImageButton menu_bar , cart_bar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        Toolbar t = (Toolbar) findViewById(R.id.tToolbar);
        search = (EditText) t.findViewById(R.id.title_search);
        cart_bar = (ImageButton) t.findViewById(R.id.title_cart);





        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not logged in, launch the Log In activity
            loadLogInView();
        }

        // slide menu section

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, t, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
        t.setNavigationIcon(R.mipmap.menu_icon);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        t.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.like_menu) {
                    // Handle the wishList  action
                    Intent i = new Intent(Products.this,MyWishList.class);
                    startActivity(i);
                } else if (id == R.id.order_menu) {
                    // Handle the Orders action
                    Intent i = new Intent(Products.this,MyOrders.class);
                    startActivity(i);
                }else if( id == R.id.about_us_menu){
                    // Handle the about action
                    Intent i = new Intent(Products.this,Aboutus.class);
                    startActivity(i);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        // end slide menu section
        Likes_List = new ArrayList<Item>(){};

        db = new DBHelper(this);

        horizontal_recycler_view= (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        product_view = (RecyclerView) findViewById(R.id.vertical_recycler_view);

        horizontalList=new ArrayList<>();
        horizontalList.add(R.mipmap._1_arduino);
        horizontalList.add(R.mipmap._2_arduino_accesories);
        horizontalList.add(R.mipmap._3_ics);
        horizontalList.add(R.mipmap._4_pcb);
        horizontalList.add(R.mipmap._5_switches);
        horizontalList.add(R.mipmap._6_motors_tab);
        horizontalList.add(R.mipmap._7_sensors_tab);
        horizontalList.add(R.mipmap._8_drivers);
        horizontalList.add(R.mipmap._9_our_products);

        horizontalAdapter=new HorizontalAdapter(horizontalList);

        LinearLayoutManager horizontalLayoutManagaer
                = new LinearLayoutManager(Products.this, LinearLayoutManager.HORIZONTAL, false);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        Log.e("WIDTH",String.valueOf(width));

        int num = width / 130;

        horizontal_recycler_view.setHasFixedSize(false);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),num);



        horizontal_recycler_view.setLayoutManager(layoutManager);

        horizontal_recycler_view.setAdapter(horizontalAdapter);


        arduino_data = new Products_Arduino_Tab_data();

        product_image = arduino_data.getImages();
        product_name = arduino_data.getNames();
        product_price = arduino_data.getPrices();



        verticalAdapter = new VerticalAdapter(product_image,product_name,product_price,pos);
        product_view.setHasFixedSize(true);
        RecyclerView.LayoutManager Manager = new GridLayoutManager(getApplicationContext(),1);

        product_view.setLayoutManager(Manager);

        product_view.setAdapter(verticalAdapter);


        cart_bar.setOnClickListener(this);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAlgorithm(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void searchAlgorithm(CharSequence s){
        SearchClass searchClass = new SearchClass();
        try {
            ArrayList<ArrayList> result_data = searchClass.getSearchResult(s);

            product_image = result_data.get(0);
            product_name = result_data.get(2);
            product_price = result_data.get(1);

            verticalAdapter = new VerticalAdapter(product_image, product_name, product_price, 0);
            product_view.setHasFixedSize(true);
            RecyclerView.LayoutManager Manager = new GridLayoutManager(getApplicationContext(), 1);

            product_view.setLayoutManager(Manager);

            product_view.setAdapter(verticalAdapter);
        }catch (Exception ex){Log.e("Error",ex.toString());}
    }


    private void loadLogInView() {
        Intent intent = new Intent(this, Sign.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products, menu);
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
            case R.id.title_cart:
                Orders_List.Orders_list = Orders_list;
                Intent intent = new Intent(Products.this,Orders.class);
                intent.putExtra("Where", "1");
                startActivity(intent);
                break;

        }
    }


    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<Integer> horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView imgView;
            public Space space;
            public MyViewHolder(View view) {
                super(view);
                imgView = (ImageView) view.findViewById(R.id.ivCategory);
                space = (Space) view.findViewById(R.id.space);

            }
        }


        public HorizontalAdapter(List<Integer> horizontalList) {
            this.horizontalList = horizontalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_list, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.imgView.setImageResource(horizontalList.get(position));

            holder.imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos = position;
                    Toast.makeText(Products.this,String.valueOf(position), Toast.LENGTH_SHORT).show();
                    if(pos ==0){
                        arduino_data = new Products_Arduino_Tab_data();

                        product_image = arduino_data.getImages();
                        product_name = arduino_data.getNames();
                        product_price = arduino_data.getPrices();
                    }else if(pos ==1){
                        arduino_accesories_data = new Products_Arduino_Accesories_Tab_data();

                        product_image = arduino_accesories_data.getImages();
                        product_name = arduino_accesories_data.getNames();
                        product_price = arduino_accesories_data.getPrices();
                    }
                    else if(pos ==2){
                        ics_data = new Products_Ics_Tab_data();

                        product_image = ics_data.getImages();
                        product_name = ics_data.getNames();
                        product_price = ics_data.getPrices();

                    }
                    else if(pos ==3){
                        pcb_data = new Products_PCB_Tab_data();

                        product_image = pcb_data.getImages();
                        product_name = pcb_data.getNames();
                        product_price = pcb_data.getPrices();
                    }
                    else if(pos ==4){
                        switches_data = new Products_Switches_Tab_data();

                        product_image = switches_data.getImages();
                        product_name = switches_data.getNames();
                        product_price = switches_data.getPrices();
                    }
                    else if(pos ==5){
                        mototrs_data = new Products_Motors_Tab_data();

                        product_image = mototrs_data.getImages();
                        product_name = mototrs_data.getNames();
                        product_price = mototrs_data.getPrices();
                    }
                    else if(pos ==6){
                        sensors_data = new Products_Sensors_Tab_data();

                        product_image = sensors_data.getImages();
                        product_name = sensors_data.getNames();
                        product_price = sensors_data.getPrices();
                    }
                    else if(pos ==7){
                        drivers_data = new Products_Drivers_Tab_data();

                        product_image = drivers_data.getImages();
                        product_name = drivers_data.getNames();
                        product_price = drivers_data.getPrices();
                    }
                    else if(pos ==8){
                        our_products_data = new Products_Our_Products_Tab_data();

                        product_image = our_products_data.getImages();
                        product_name = our_products_data.getNames();
                        product_price = our_products_data.getPrices();
                    }

                    verticalAdapter = new VerticalAdapter(product_image,product_name,product_price,pos);
                    product_view.setHasFixedSize(true);
                    RecyclerView.LayoutManager Manager = new GridLayoutManager(getApplicationContext(),1);

                    product_view.setLayoutManager(Manager);

                    product_view.setAdapter(verticalAdapter);

                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }






    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {

        private List<Integer> imagesList;
        private List<String> namesList;
        private List<String> pricesList;
        private int category=0;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView imgView;
            public TextView nameView;
            public TextView priceView;
            public ImageButton likeView;
            public ImageButton cartView;
            public View relative_view ;
            public Button plus_quantity;
            public Button minus_quantity;
            public TextView quantity;

            public MyViewHolder(View view) {
                super(view);
                imgView = (ImageView) view.findViewById(R.id.product_image);
                nameView =(TextView) view.findViewById(R.id.product_name);
                priceView =(TextView) view.findViewById(R.id.product_price);
                likeView = (ImageButton) view.findViewById(R.id.product_like);
                cartView = (ImageButton) view.findViewById(R.id.product_cart);
                relative_view = view.findViewById(R.id.rl_row);
                plus_quantity = (Button) view.findViewById(R.id.product_quantity_plus);
                minus_quantity = (Button) view.findViewById(R.id.product_quantity_minus);
                quantity = (TextView) view.findViewById(R.id.product_quantity);

                likeView.setImageResource(R.mipmap.like_deactive);
                likeView.setTag(R.mipmap.like_deactive);

                cartView.setImageResource(R.mipmap.cart_black);
                cartView.setTag(R.mipmap.cart_black);

            }
        }


        public VerticalAdapter(List<Integer> horizontalList, List<String> names , List<String> prices,int cat) {
            this.imagesList = horizontalList;
            this.namesList = names;
            this.pricesList = prices;
            this.category = cat;

            db = new DBHelper(getApplicationContext());
            Log.e("TAG",String.valueOf(imagesList.size()));
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_row, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            if(category == 0){
                holder.relative_view.setBackground(getResources().getDrawable(R.mipmap.arduino_tab));
            }else if(category == 1){
                holder.relative_view.setBackground(getResources().getDrawable(R.mipmap.arduino_accessories_tab));
            }else if(category == 2){
                holder.relative_view.setBackground(getResources().getDrawable(R.mipmap.ic_tab));
            }else if(category == 3){
                holder.relative_view.setBackground(getResources().getDrawable(R.mipmap.pcb_tab));
            }else if(category == 4){
                holder.relative_view.setBackground(getResources().getDrawable(R.mipmap.switchse_tab));
            }else if(category == 5){
                holder.relative_view.setBackground(getResources().getDrawable(R.mipmap.motors_tab));
            }else if(category == 6){
                holder.relative_view.setBackground(getResources().getDrawable(R.mipmap.sensors_tab));
            }else if(category == 7){
                holder.relative_view.setBackground(getResources().getDrawable(R.mipmap.driver_tab));
            }else if(category == 8){
                holder.relative_view.setBackground(getResources().getDrawable(R.mipmap.our_products_tab));
            }
            holder.nameView.setText(namesList.get(position));
            holder.imgView.setImageResource(imagesList.get(position));
            holder.priceView.setText(pricesList.get(position));


            if(db.selectLike(namesList.get(position))){
                holder.likeView.setImageResource(R.mipmap.like_active);
                holder.likeView.setTag(R.mipmap.like_active);
            }else{
                holder.likeView.setImageResource(R.mipmap.like_deactive);
                holder.likeView.setTag(R.mipmap.like_deactive);
            }

            holder.likeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = v.getTag();
                    if(String.valueOf(tag).equals(String.valueOf(R.mipmap.like_active))){
                        holder.likeView.setImageResource(R.mipmap.like_deactive);
                        holder.likeView.setTag(R.mipmap.like_deactive);
                        deleteLike(namesList.get(position));
                        Toast.makeText(getApplicationContext(), namesList.get(position) +" removed from wishList",Toast.LENGTH_LONG).show();
                    }else if(String.valueOf(tag).equals(String.valueOf(R.mipmap.like_deactive))){
                        holder.likeView.setImageResource(R.mipmap.like_active);
                        holder.likeView.setTag(R.mipmap.like_active);
                          addLike(namesList.get(position));
                        Toast.makeText(getApplicationContext(), namesList.get(position) +" added to wishList",Toast.LENGTH_LONG).show();
                    }

                }
            });


            holder.cartView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Object tag = v.getTag();
                    if(String.valueOf(tag).equals(String.valueOf(R.mipmap.cart_red))){
                        holder.cartView.setImageResource(R.mipmap.cart_black);
                        holder.cartView.setTag(R.mipmap.cart_black);

                        removeOrder(namesList.get(position));
                        Toast.makeText(getApplicationContext(), namesList.get(position) +" removed from cart",Toast.LENGTH_LONG).show();
                    }else if(String.valueOf(tag).equals(String.valueOf(R.mipmap.cart_black))){
                        holder.cartView.setImageResource(R.mipmap.cart_red);
                        holder.cartView.setTag(R.mipmap.cart_red);

                        addOrder(namesList.get(position),imagesList.get(position),pricesList.get(position),Integer.parseInt(holder.quantity.getText().toString()));
                        Toast.makeText(getApplicationContext(), namesList.get(position) +" added to cart",Toast.LENGTH_LONG).show();
                    }
                }
            });



            holder.plus_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.quantity.setText(String.valueOf(Integer.parseInt(holder.quantity.getText().toString())+1));
                }
            });


            holder.minus_quantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(holder.quantity.getText().toString()) > 1)
                        holder.quantity.setText(String.valueOf(Integer.parseInt(holder.quantity.getText().toString())-1));
                }
            });

        }

        @Override
        public int getItemCount() {
            return imagesList.size();
        }
    }

    private void removeOrder(String name) {
        HashMap<String , List<String>> order = new HashMap<>();
        for(int i=0 ;i < Orders_list.size();i++){
            HashMap<String , List<String>> order1 = new HashMap<>();
            order1 = Orders_list.get(i);
            if(order1.containsKey(name))
            {
                order=order1;
                break;
            }
        }
        Orders_list.remove(order);
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


    private void addLike(String name){
        db.insertLike(name);
    }

    private void deleteLike(String name){
        db.removeLike(name);
    }





}
