package entity;

/**
 * Created by Mahmoud Ellawatty on 20/01/2017.
 */

public class Order {
    public String Order_User_Name ;
    public String details;
    public String Order_User_Phone;
    public String Order_Total;



    public Order(){}

    public Order(String User_Name , String detail , String phone , String total ){
        this.Order_User_Name = User_Name;
        this.details = detail;
        this.Order_User_Phone = phone;
        this.Order_Total = total;
    }

/*
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getOrder_User_Name() {
        return Order_User_Name;
    }

    public void setOrder_User_Name(String order_User_Name) {
        Order_User_Name = order_User_Name;
    }


    public String getOrder_User_Phone() {
        return Order_User_Phone;
    }

    public void setOrder_User_Phone(String order_User_Phone) {
        Order_User_Phone = order_User_Phone;
    }

    public String getOrder_Total() {
        return Order_Total;
    }

    public void setOrder_Total(String order_Total) {
        Order_Total = order_Total;
    }
    */
}
