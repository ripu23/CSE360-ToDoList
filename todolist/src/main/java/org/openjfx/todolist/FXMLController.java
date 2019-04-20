package org.openjfx.todolist;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FXMLController implements Initializable {
    
	private ObservableList<Item> tasks=FXCollections.observableArrayList();
	MenuItem update= new MenuItem("Update");
	MenuItem delete= new MenuItem("Delete");
	private final ContextMenu contextMenu=new ContextMenu(update,delete);
	
    @FXML
    private Button addItem;
    
    @FXML
    private Button save;

    @FXML
    private Button print;

    @FXML
    private Button restore;
    
    @FXML private TableView<Item> table;
    @FXML private TableColumn<Item,String>Description;
    @FXML private TableColumn<Item,String>DueDate;
    @FXML private TableColumn<Item,Integer>Priority;
    @FXML private TableColumn<Item,String>Status;
    
   
@FXML private void handleAddItemButtonAction(ActionEvent event) {//changes to add item scene
	try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/addItem.fxml"));
		Parent root = (Parent) loader.load();
		AddItemController control=loader.getController();
		control.setTableItems(table.getItems());
		Stage stage = new Stage();
		stage.setTitle("Add Task");
		stage.setScene(new Scene(root));
		stage.show();
	} catch(Exception e) {
		System.err.println(e.getMessage());
	}
}

	public void initialize(URL location, ResourceBundle resources) {
		Description.setCellValueFactory(new PropertyValueFactory<Item,String>("description"));
		DueDate.setCellValueFactory(new PropertyValueFactory<Item,String>("dueDate"));
		Priority.setCellValueFactory(new PropertyValueFactory<Item,Integer>("priority"));
		Status.setCellValueFactory(new PropertyValueFactory<Item,String>("status"));
		
		 table.setRowFactory(new Callback<TableView<Item>,TableRow<Item>>(){
		    	public TableRow<Item> call(TableView<Item> tableView){
		    		final TableRow<Item> row=new TableRow<>();
		    		delete.setOnAction(new EventHandler<ActionEvent>() {
		    			@Override
		    			public void handle(ActionEvent event) {
		    				Item remove=table.getSelectionModel().getSelectedItem();
		    				tasks.remove(remove);
		    			}
		    		});
		    		
		    		
		    		update.setOnAction(new EventHandler<ActionEvent>() {
		    			@Override
		    			public void handle(ActionEvent event) {
		    				Item update=table.getSelectionModel().getSelectedItem();
		    				try {
		    					FXMLLoader loader = new FXMLLoader(getClass().getResource("/addItem.fxml"));
		    					Parent root = (Parent) loader.load();
		    					AddItemController control=loader.getController();
		    					control.infoToUpdate(update,tasks.indexOf(update));
		    					control.setTableItems(table.getItems());
		    					Stage stage = new Stage();
		    					stage.setTitle("Update Task");
		    					stage.setScene(new Scene(root));
		    					stage.show();
		    				} catch(Exception e) {
		    					System.err.println(e.getMessage());
		    				}
		    			}
		    		});
		    		
		    		row.contextMenuProperty().bind(
		    				Bindings.when(row.emptyProperty()).then((ContextMenu)null).otherwise(contextMenu));
		    		return row;
		    	}
		    });
		
		table.setItems(getItem());
	}
	
	private ObservableList<Item> getItem(){
		return tasks;
	}
     
}