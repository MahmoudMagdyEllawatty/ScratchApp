package entity;

/**
 * Created by Mahmoud Ellawatty on 05/12/2016.
 */
public class Users {
    public String id;
    public String name;
    public String mail;
    public String phone;

    public Users(){}

    public Users(String id , String name , String mail ,String phone){
        this.id = id ;
        this.name = name ;
        this.mail = mail;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
