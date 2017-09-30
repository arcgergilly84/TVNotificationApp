package root;


public class TV {

    private int showID = 0;
    private String name;
    private String date;

    public TV(int ID, String name, String date){
        showID = ID;
        this.name = name;
        this.date = date;
    }

    public String getname(){
        return name;
    }

    public String getDate(){
        return  date;
    }

    public int getID(){return showID;}

}
