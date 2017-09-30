package root;


public class TV {

    private int showID = 0;
    private String name;
    private String date;
    private String code;
    private String episodeUrl;

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

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){return code;}

    public String getEpisodeUrl() {
        return episodeUrl;
    }

    public void setEpisodeUrl(String episodeUrl) {
        this.episodeUrl = episodeUrl;
    }

}
