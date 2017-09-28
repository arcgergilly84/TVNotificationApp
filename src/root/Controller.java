package root;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.Map;
import java.util.TreeMap;


public class Controller {

    @FXML Button exitButton;
    @FXML Button addTVShow;
    @FXML TextField tvName;
    @FXML TextField tvDate;
    @FXML Label validate;
    @FXML ListView TVList;

    ObservableList<String> tvShows = FXCollections.observableArrayList();
    Map<String,String> TVShows = new TreeMap<>();


    public void addtoList(){
        String show = tvName.getText();
        String date = tvDate.getText();
        TV tv = new TV(show,date);

        if(show.equals("")|| date.equals("") ){
            validate.setText("Not null");
        } else  {
            TVShows.put(tv.getname(),tv.getDate());
            tvShows.add(show);
        }
        TVList.setItems(tvShows.sorted());
        clear();
    }

    public void exitApp(){
        Platform.exit();
    }

    private void clear(){
        tvName.setText("");
        tvDate.setText("");
    }


}
