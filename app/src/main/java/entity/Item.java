package entity;

/**
 * Created by Mahmoud Ellawatty on 29/12/2016.
 */

public class Item {

    String item_name;
    double item_price;
    int item_quantity;
    boolean item_cart;
    boolean item_like;
    String order_name;
    double total_price;


    public Item(){}

    // constructor for Like
    public Item(String name, boolean like){
        this.item_name = name;
        this.item_like=like;
    }

    // constructor for Order
    public Item(String name,double price , int qnt , String order_name,double total ){
        this.item_name = name;
        this.item_quantity  =qnt;
        this.item_price = price;
        this.order_name = order_name;
        this.total_price = total;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public boolean isItem_cart() {
        return item_cart;
    }

    public void setItem_cart(boolean item_cart) {
        this.item_cart = item_cart;
    }

    public boolean isItem_like() {
        return item_like;
    }

    public void setItem_like(boolean item_like) {
        this.item_like = item_like;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }

    public int getItem_quantity() {
        return item_quantity;
    }

    public void setItem_quantity(int item_quantity) {
        this.item_quantity = item_quantity;
    }
}
