package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import entity.Item;

/**
 * Created by Mahmoud Ellawatty on 29/12/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ScratchApp";

    public static final String LIKES_TABLE_NAME = "likes";
    public static final String LIKES_COLUMN_ID = "id";
    public static final String LIKES_COLUMN_NAME = "product";

    public static final String ORDERS_TABLE_NAME = "orders";
    public static final String ORDERS_COLUMN_ID = "id";
    public static final String ORDERS_COLUMN_NAME = "item_name";
    public static final String ORDERS_COLUMN_PRICE = "item_price";
    public static final String ORDERS_COLUMN_TOTAL_PRICE = "item_total_price";
    public static final String ORDERS_COLUMN_QUANTITY = "item_quantity";
    public static final String ORDERS_COLUMN_ORDER_NAME = "product";


    public static final String ORDERS_NAMES_TABLE_NAME = "orders_names";
    public static final String ORDERS_NAMES_COLUMN_ID = "id";
    public static final String ORDERS_NAMES_COLUMN_ORDER_NAME = "product";
    public static final String ORDERS_NAMES_COLUMN_TOTAL_PRICE = "item_total_price";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table "+ LIKES_TABLE_NAME +
                        "("+LIKES_COLUMN_ID+" integer primary key, "+
                        LIKES_COLUMN_NAME +" text )"
        );



        db.execSQL(
                "create table "+ ORDERS_NAMES_TABLE_NAME +
                        "("+ORDERS_NAMES_COLUMN_ID+" integer primary key, "+
                        ORDERS_NAMES_COLUMN_TOTAL_PRICE+" text, "+
                        ORDERS_NAMES_COLUMN_ORDER_NAME +" text )"
        );


        db.execSQL(
                "create table "+ ORDERS_TABLE_NAME +
                        "("+ORDERS_COLUMN_ID+" integer primary key, "+
                        ORDERS_COLUMN_ORDER_NAME+" text, "+
                        ORDERS_COLUMN_NAME +" text, "+
                        ORDERS_COLUMN_PRICE+" text, "+
                        ORDERS_COLUMN_TOTAL_PRICE+" text, "+
                        ORDERS_COLUMN_QUANTITY+" text )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ LIKES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ ORDERS_NAMES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ ORDERS_TABLE_NAME);
        onCreate(db);
    }


    public int numberOfRows(String table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, table_name);
        db.close();
        return numRows;
    }

    // likes section
    public long insertLike(String item_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIKES_COLUMN_NAME,item_name);

        return db.insert(LIKES_TABLE_NAME,null,contentValues);
    }

    public Integer removeLike(String item_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIKES_COLUMN_NAME,item_name);

        return db.delete(LIKES_TABLE_NAME, LIKES_COLUMN_NAME +" = '"+ item_name +"'",null);

    }

    public boolean selectLike(String item_name){
        if(numberOfRows(LIKES_TABLE_NAME) > 0) {
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor res =  db.rawQuery( "select * from "+ LIKES_TABLE_NAME +" where "+ LIKES_COLUMN_NAME+" = '" + item_name+"'", null );

            Cursor res = db.query(LIKES_TABLE_NAME
                    , new String[]{LIKES_COLUMN_ID, LIKES_COLUMN_NAME}
                    , LIKES_COLUMN_NAME + " = '" + item_name + "'"
                    , null, null, null, null);

            int id =0;
            if(res.moveToFirst()){
                do {
                    id = res.getInt(res.getColumnIndex(LIKES_COLUMN_ID));
                }while (res.moveToNext());
            }
            res.close();
            db.close();
            if (id > 0)
                return true;
        }else
            return false;
        return false;
    }

    public ArrayList<String> getAllLikes(){
        ArrayList<String> allLikes = new ArrayList<>();
        if(numberOfRows(LIKES_TABLE_NAME) > 0) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor res =  db.rawQuery( "select "+LIKES_COLUMN_NAME +" from "+ LIKES_TABLE_NAME +"", null );

           /* Cursor res = db.query(LIKES_TABLE_NAME
                    , new String[]{LIKES_COLUMN_ID, LIKES_COLUMN_NAME}
                    , null
                    , null, null, null, null);
*/
            if (res.moveToFirst()) {
                do {
                    if(!res.isNull(res.getColumnIndex(LIKES_COLUMN_NAME)))
                    {
                        Log.e("OBJECT",res.getString(res.getColumnIndex(LIKES_COLUMN_NAME)));
                        allLikes.add(res.getString(res.getColumnIndex(LIKES_COLUMN_NAME)));
                    }

                } while (res.moveToNext());
            }
            res.close();
            db.close();
        }
        return allLikes;
    }



    // Orders section
    public boolean addOrder(ArrayList<Item> order_list , String order_name,String total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ORDERS_NAMES_COLUMN_ORDER_NAME,order_name);
        contentValues.put(ORDERS_NAMES_COLUMN_TOTAL_PRICE,total);
        db.insert(ORDERS_NAMES_TABLE_NAME,null,contentValues);

        // insert order
        for(Item item : order_list){

            contentValues = new ContentValues();
            contentValues.put(ORDERS_COLUMN_NAME,item.getItem_name());
            contentValues.put(ORDERS_COLUMN_PRICE,item.getItem_price());
            contentValues.put(ORDERS_COLUMN_QUANTITY,item.getItem_quantity());
            contentValues.put(ORDERS_COLUMN_ORDER_NAME,item.getOrder_name());
            contentValues.put(ORDERS_COLUMN_TOTAL_PRICE,item.getTotal_price());

            db.insert(ORDERS_TABLE_NAME,null,contentValues);
        }
        return true;
    }


    public ArrayList<String> getAllOrdersName(){
        ArrayList<String> OrdersName = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
Log.e("START","START");
        Cursor res =  db.rawQuery( "select "+ORDERS_NAMES_COLUMN_ORDER_NAME+","+ORDERS_NAMES_COLUMN_TOTAL_PRICE+" from "+ ORDERS_NAMES_TABLE_NAME +"", null );
        Log.e("START","START");
        if (res.moveToFirst()) {
            do {
                if(!res.isNull(res.getColumnIndex(ORDERS_NAMES_COLUMN_ORDER_NAME)))
                {
                    OrdersName.add(res.getString(res.getColumnIndex(ORDERS_NAMES_COLUMN_ORDER_NAME))+"-"+res.getString(res.getColumnIndex(ORDERS_NAMES_COLUMN_TOTAL_PRICE)));
                }

            } while (res.moveToNext());
        }
        Log.e("START","START");
        res.close();
        Log.e("START","START");
        db.close();
        Log.e("START","START");
        return OrdersName;
    }


    public ArrayList<Item> getAllOrders(){
        ArrayList<Item> Orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res =  db.rawQuery( "select * from "+ ORDERS_TABLE_NAME +"", null );

        if (res.moveToFirst()) {
            do {
                    Item item = new Item();
                    item.setItem_name(res.getString(res.getColumnIndex(ORDERS_COLUMN_NAME)));
                    item.setItem_price(res.getDouble(res.getColumnIndex(ORDERS_COLUMN_PRICE)));
                    item.setItem_quantity(res.getInt(res.getColumnIndex(ORDERS_COLUMN_QUANTITY)));
                    item.setOrder_name(res.getString(res.getColumnIndex(ORDERS_COLUMN_ORDER_NAME)));
                    item.setTotal_price(res.getDouble(res.getColumnIndex(ORDERS_COLUMN_TOTAL_PRICE)));
                    Orders.add(item);

            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return Orders;
    }


    public ArrayList<Item> getOrderByName(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Item> items = new ArrayList<>();

        Cursor res = db.query(ORDERS_TABLE_NAME
                , new String[]{ORDERS_COLUMN_NAME, ORDERS_COLUMN_PRICE,ORDERS_COLUMN_QUANTITY}
                , ORDERS_COLUMN_ORDER_NAME + " = '" + name + "'"
                , null, null, null, null);

        int id =0;
        if(res.moveToFirst()){
            do {
                Item item = new Item();
                item.setItem_name(res.getString(res.getColumnIndex(ORDERS_COLUMN_NAME)));
                item.setItem_price(res.getDouble(res.getColumnIndex(ORDERS_COLUMN_PRICE)));
                item.setItem_quantity(res.getInt(res.getColumnIndex(ORDERS_COLUMN_QUANTITY)));
                items.add(item);
            }while (res.moveToNext());
        }
        res.close();
        db.close();


        return items;
    }














}
