package org.openjfx.todolist;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import org.openjfx.todolist.models.Item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private TableView<Item> todo_table;
    
    @FXML
    private TableColumn<Item, String> col_id;

    @FXML
    private TableColumn<Item, String> col_desc;

    @FXML
    private TableColumn<Item, String> col_dueDate;

    @FXML
    private TableColumn<Item, String> col_startDate;

    @FXML
    private TableColumn<Item, Button> col_actions;

    @FXML
    private Button addNew;

    @FXML
    private Button save;

    @FXML
    private Button print;

    @FXML
    private Button restore;
    
    
    ObservableList<Item> tableData = FXCollections.observableArrayList();
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		this.initTable();
		this.loadData();
		
	}
	
	private void initTable() {
		this.initCols();
	}
	
	private void initCols() {
		col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_desc.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_dueDate.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_startDate.setCellValueFactory(new PropertyValueFactory<>("id"));
		col_actions.setCellValueFactory(new PropertyValueFactory<>("update"));
	}
	
	private void loadData() {
		for(int i = 0; i < 20; i++) {
			tableData.add(new Item(i, "ripu", new Date().toString(), 1, 
					"Not Started", new Date().toString(), new Button("Update")));
		}
		this.todo_table.setItems(tableData);
	}
	
	@FXML
    void addNewItem(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/addItem.fxml"));
			Parent root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
    }

     
}