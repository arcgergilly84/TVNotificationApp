package root;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;

import java.util.Map;
import java.util.TreeMap;


public class Controller {

    @FXML TextField tvName;
    @FXML TextField tvDate;
    @FXML Label validate;
    @FXML ListView TVList;


    ObservableList<String> tvShows = FXCollections.observableArrayList();
    Map<Integer,String> TVShows = new TreeMap<>();


    public void addtoList(){
        String show = tvName.getText();
        String userDate = tvDate.getText();

        TV tv = new TV(show,userDate);

        if(show.equals("")|| userDate.equals("")){
            validate.setText("Not null");
        } else  {
            TVShows.put(tv.getID(),tv.getname() + "," + tv.getDate());
            tvShows.add(tv.getname());
        }
        TVList.setItems(tvShows.sorted());
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
        try(FileOutputStream fos = new FileOutputStream("src/resources/files/tvlist.txt")){
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
            System.out.println(TVShows);
        }
    }


}
