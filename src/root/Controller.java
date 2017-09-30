package root;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;

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


    ObservableList<String> tvShows = FXCollections.observableArrayList();
    ObservableList<String> currentShows = FXCollections.observableArrayList();
    Map<Integer,ArrayList<String>> TVShows = new TreeMap<>();
    SimpleDateFormat date = new SimpleDateFormat("dd/MM/yy");
    String today = date.format(new Date());
    TV tv = null;
    int showID = 1;

    public void addtoList(){
        String show = tvName.getText();
        String userDate = tvDate.getText();
        ArrayList<String> list = new ArrayList<>();

        tv = new TV(showID,show ,userDate);

        list.add(tv.getname());
        list.add(tv.getDate());

        if(show.equals("")|| userDate.equals("")){
            validate.setText("Not null");
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
    }

    private void persist() throws IOException{
        try(FileOutputStream fos = new FileOutputStream("src/resources/files/tvlist.txt", true)){
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(TVShows);
            oos.close();
            fos.close();
        }
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
        }
        showCurrent();
    }

    public void showCurrent(){
        for(Map.Entry<Integer,ArrayList<String>> shows : TVShows.entrySet()){


            if(shows.getValue().get(1).equals(today)){
                currentShows.add(shows.getValue().get(0));
                System.out.println(shows.getValue().get(0));
                currentview.setItems(currentShows.sorted());
            }

        }

    }


}
