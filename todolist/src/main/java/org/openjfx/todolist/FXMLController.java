package org.openjfx.todolist;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import org.openjfx.todolist.models.Item;

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
import javafx.stage.FileChooser;
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
	
	@FXML
	private void saveButtonHandler(ActionEvent event) {
		try {
			PrintWriter pw = new PrintWriter("src/main/resources/saveFile.txt", "UTF-8");
			String itemString = "";
			int listSize = table.getItems().size();
			ObservableList<Item> tempList = table.getItems();
			pw.println(listSize);   // include number of items at beginning of file
			if(listSize > 0) {
				for(int i = 0; i < listSize; i++) {
					itemString = tempList.get(i).getSaveString();
					pw.println(itemString);
				}
			}
			pw.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			System.err.println("PrintWriter exception occured");
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void restoreButtonHandler(ActionEvent event) {
		if(this.table.getItems().size() < 1) { // check to make sure list is empty
			try {
				InputStream in = getClass().getResourceAsStream("/saveFile.txt");
				BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
				readFile(bfr);
				bfr.close();
			} catch (FileNotFoundException e) {
				System.err.println("FILE NOT FOUND EXCEPTION" + e.toString());
			} catch (IOException e) {
				System.err.println("IO exception in restore");
			}
		}
	}


	private void readFile(BufferedReader bfr) {
		int listSize;
		try {
			listSize = Integer.parseInt(bfr.readLine());
			for(int i = 0; i < listSize; i++) {
				String description = bfr.readLine();
				int priority = Integer.parseInt(bfr.readLine());
				String dueDate = bfr.readLine();
				String status = bfr.readLine();
				String statusDate = bfr.readLine();
				tasks.add(new Item(description, dueDate, priority, status, 
						statusDate));
				this.table.setItems(tasks);
			}
		} catch (NumberFormatException e) {
			System.err.println("Number Format Exception occurred");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception occurred");
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void printButtonHandler(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save List As...");
		FileChooser.ExtensionFilter txtExtentionFilter = new FileChooser.ExtensionFilter("TXT - Text Document", "*.txt");
		fileChooser.getExtensionFilters().add(txtExtentionFilter);
		fileChooser.setInitialFileName("To_Do_List.txt");
		fileChooser.setSelectedExtensionFilter(txtExtentionFilter);
		File file = fileChooser.showSaveDialog(null);
		if( file != null) {
			System.out.println("saving");
			try {
				PrintWriter pw = new PrintWriter(file);
				pw.println(getPrintFileHeader());
				int listSize = this.table.getItems().size();
				if(listSize > 0) {
					// if list has items, print each one to file, else it only prints file header
					for(int i = 0; i < listSize; i++) {
						pw.println(this.table.getItems().get(i).getPrintString());
					}
				}
				pw.close();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	private String getPrintFileHeader() {
		String str = String.format("|%-2s|%-23s|%-10s|%-20s|", "Pr", "Description", "Due Date",
				"Status");
		str += "\n=====================================================================";
		return str;
	}
     
}