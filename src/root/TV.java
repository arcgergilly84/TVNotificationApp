package root;


public class TV {

    private int showID = 0;
    private String name;
    private String date;

    public TV(String name, String date){
        showID += 1;
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
