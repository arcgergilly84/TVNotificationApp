package root;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;

import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


public class Controller {

    @FXML TextField tvName;
    @FXML TextField tvDate;
    @FXML Label validate;
    @FXML ListView TVList;
    @FXML ListView currentview;
    @FXML TextField code;
    @FXML TextField urlEpisode;

    private ObservableList<String> tvShows = FXCollections.observableArrayList();
    private ObservableList<String> currentShows = FXCollections.observableArrayList();
    private TreeMap<Integer,ArrayList<String>> TVShows = new TreeMap<>();
    private SimpleDateFormat date = new SimpleDateFormat("dd MMM yy");
    private String today = date.format(new Date());
    private TV tv = null;
    private int showID = 1;

    public void addtoList(){
        String show = tvName.getText();
        String userDate = tvDate.getText();
        String episodeCode = code.getText();
        String episodeUrl = urlEpisode.getText();
        ArrayList<String> list = new ArrayList<>();

        tv = new TV(showID,show ,userDate);
        tv.setCode(episodeCode);
        tv.setEpisodeUrl(episodeUrl);

        list.add(tv.getname());
        list.add(tv.getDate());
        list.add(tv.getCode());
        list.add(tv.getEpisodeUrl());

        if(show.equals("")|| userDate.equals("")){
            validate.setText("Not null");
            showID -= 1;
        } else  {
            TVShows.put(tv.getID(),list);
            tvShows.add(tv.getname());
        }
        TVList.setItems(tvShows.sorted());
        showID += 1;
        clear();
    }

    public void exitApp(){
        try {
            persist();
        }catch(IOException e){
            System.out.println("file not located");
        }
        Platform.exit();
    }

    private void clear(){
        tvName.setText("");
        tvDate.setText("");
        code.setText("");
        urlEpisode.setText("");
    }

    private void persist() throws IOException{
        String fileResource = "src/resources/files/tvlist.txt";
        PrintWriter pw = new PrintWriter(fileResource);
        pw.close();
        try(FileOutputStream fos = new FileOutputStream(fileResource, true)){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(TVShows);
            oos.close();
            fos.close();
        }
        System.out.println(TVShows);
    }

    public void loadData() throws IOException{
        try(FileInputStream fis = new FileInputStream("src/resources/files/tvlist.txt")){
            ObjectInputStream ois = new ObjectInputStream(fis);
            try {
                TVShows = (TreeMap) ois.readObject();
                ois.close();
                fis.close();
            }catch (ClassNotFoundException e){
                System.out.println("Broke");
            }
        }

        for (Map.Entry<Integer,ArrayList<String>> shows : TVShows.entrySet()){
            tvShows.add(shows.getValue().get(0));
            TVList.setItems(tvShows);
            String currentEpsiode = dynamicEpisode(shows.getValue().get(3), shows.getValue().get(2));
            ArrayList<String> update = new ArrayList<>();
            update.add(shows.getValue().get(0));
            update.add(currentEpsiode);
            int code = Integer.parseInt(shows.getValue().get(2));
            code += 1;
            String updateCode = Integer.toString(code);
            update.add(updateCode);
            update.add(shows.getValue().get(3));
            TVShows.put(shows.getKey(),update);
        }
        System.out.println(TVShows);
        showCurrent();
    }

    private String dynamicEpisode(String epurl, String sCode)throws IOException{
        URL url = new URL("http://epguides.com/" + epurl);
        URLConnection uc = url.openConnection();
        InputStream is = uc.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;

        while ((line = br.readLine()) != null){
            if (line.contains(sCode)){
                if(line.substring(0,10).contains(sCode)) {
                    line = line.substring(27, 36);
                    return line;
                }
                //Add date dynamically
            }
        }
        is.close();
        br.close();
        return line;
    }

    private void showCurrent(){
        for(Map.Entry<Integer,ArrayList<String>> shows : TVShows.entrySet()){
            if(shows.getValue().get(1).equals(today)){
                currentShows.add(shows.getValue().get(0));
                currentview.setItems(currentShows.sorted());
            }

        }

    }


    //TODO add code to show current details of selected tv show...add 1 to each base number after checking current and
    // todays date.

}
