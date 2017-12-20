package in.skylinelabs.yhacks;

public class LendDetails {

    private String name;
    private int amount;
    private float rating;

    public String get_name(){
        return name;
    }
    public int get_amount(){
        return amount;
    }
    public float get_rating(){
        return rating;
    }


    void set_name(String name){
        this.name = name;
    }
    void set_amount(int amount){
        this.amount = amount;
    }
    void set_rating(float rating){ this.rating = rating; }


}