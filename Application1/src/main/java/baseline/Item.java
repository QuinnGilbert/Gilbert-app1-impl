/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Quinn Gilbert
 */
package baseline;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;


public class Item {
    //store data for each row in table
    private final SimpleStringProperty description;
    private final SimpleStringProperty dueDate;
    private CheckBox completed;

    //Create new Item
    Item(String desc, String due){
        this.description = new SimpleStringProperty(desc);
        this.dueDate = new SimpleStringProperty(due);
        this.completed = new CheckBox();
    }
    //Load Item with properly checked list
    Item(String desc, String due,boolean checked){
        this.description = new SimpleStringProperty(desc);
        this.dueDate = new SimpleStringProperty(due);
        this.completed = new CheckBox();
        completed.setSelected(checked);
    }

    //need to implement getters and setters for CellValueFactory to work
    public String getDescription(){
        return description.get();
    }
    public void setDescription(String desc){
        description.set(desc);
    }

    public String getDueDate(){
        return dueDate.get();
    }
    public void setDueDate(String due){
        dueDate.set(due);
    }

    public CheckBox getCompleted(){
        return completed;
    }
    public void setCompleted(CheckBox checkbox){
        this.completed=checkbox;
    }

}
