/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Quinn Gilbert
 */
package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewListController implements Initializable {


    @FXML
    private TableView<Item> table;
    @FXML
    private TextField addDescription;
    @FXML
    private DatePicker addDate;


    private ObservableList<Item> data;

    @FXML
    //Uses description text and date (if chosen) to create new Item and add it to the list
    void addItem(ActionEvent event) {
        String desc = addDescription.getText();
        String date = "";
        if(addDate.getValue()!=null){
            LocalDate localDate = addDate.getValue();
            date = localDate.toString();
        }
        data.add(new Item(desc,date));
    }

    @FXML
    //Clears both viewed list and backend list
    void clearAll(ActionEvent event){
        data.clear();
        table.setItems(data);
    }

    @FXML
    //Creates new file with all data as a .todo file
    void save(ActionEvent e){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ToDo List files (*.todo)", "*.todo");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);

        System.out.println(file);

        StringBuilder listData=new StringBuilder();
        for(Item i : data){
            listData.append(i.getDescription()+","+i.getDueDate()+","+i.getCompleted().isSelected()+"\n");
        }

        if(file!=null){
            try{
                PrintWriter out = new PrintWriter(file);
                out.println(listData);
                out.close();
            } catch(IOException ex){
                Logger.getLogger(ViewListController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    //loads data into backend list from a .todo file
    void load(ActionEvent e){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ToDo List files (*.todo)", "*.todo");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);

        if(file!=null) {
            data.clear();
            try {
                BufferedReader in = new BufferedReader(new FileReader(file));
                while (in.ready()) {
                    String[] s = in.readLine().split(",");
                    data.add(new Item(s[0], s[1], s[2].equals("true")));
                    System.out.println(s[2].equals("true"));
                }
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(ViewListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            table.setItems(data);
        }
    }

    @FXML
    //changes table to only display checked items (unchecked items remain in backend)
    void showComplete(ActionEvent e){
        ObservableList<Item> temp = FXCollections.observableArrayList();
        for(Item i:data){
            if(!i.getCompleted().isSelected()){
                temp.add(i);
            }
        }
        table.setItems(temp);
    }

    @FXML
    //changes table to only display unchecked items (checked items remain in backend)
    void showIncomplete(ActionEvent e){
        ObservableList<Item> temp = FXCollections.observableArrayList();
        for(Item i:data){
            if(i.getCompleted().isSelected()){
                temp.add(i);
            }
        }
        table.setItems(temp);
    }

    @FXML
    //removes the item that is highlighted by tableview
    void remove(ActionEvent e){
        table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
    }

    @FXML
    void showAll(ActionEvent e){
        table.setItems(data);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        System.out.println("CHECK");

        table.setEditable(true);
        TableColumn description = new TableColumn("Description");
        TableColumn dueDate = new TableColumn("Due Date");
        TableColumn completed = new TableColumn("Completed");

        description.setPrefWidth(420);

        table.getColumns().addAll(description,dueDate,completed);

        data = FXCollections.observableArrayList(
                new Item("desc1","due date"),
                new Item("desc1","due date"),
                new Item("desc1","due date"),
                new Item("desc1","due date")
        );
        table.setItems(data);

        //Need these so that rows display and handle data properly
        description.setCellValueFactory(new PropertyValueFactory<Item,String>("description"));
        description.setCellFactory(TextFieldTableCell.forTableColumn());
        description.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item,String>>(){
            @Override
            public void handle(TableColumn.CellEditEvent<Item,String> event){
                Item item = event.getRowValue();
                item.setDescription(event.getNewValue());
            }
        });

        dueDate.setCellValueFactory(new PropertyValueFactory<Item,String>("dueDate"));
        dueDate.setCellFactory(TextFieldTableCell.forTableColumn());
        dueDate.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Item,String>>(){
            @Override
            public void handle(TableColumn.CellEditEvent<Item,String> event){
                Item item = event.getRowValue();
                item.setDescription(event.getNewValue());
            }
        });

        completed.setCellValueFactory(new PropertyValueFactory<Item,String>("completed"));

    }

}

