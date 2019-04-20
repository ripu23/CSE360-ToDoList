package org.openjfx.todolist;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.openjfx.todolist.models.Item;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddItemController implements Initializable{
	@FXML
	private TextField inputDesc;

	@FXML
	private DatePicker inputDueDate;

	@FXML
	private TextField inputPriority;

	@FXML private Button cancel;
	@FXML private Button save;
	
	@FXML
	private ChoiceBox<String> inputStatus;
	
	@FXML
	private DatePicker inputDateFinished;

	@FXML
	private DatePicker inputDateStarted;
	
	@FXML
	private Label startedLabel;
	@FXML
	private Label finishedLabel;
	
	private ObservableList<Item> table;
	private Item newTask=new Item();
	
	private DateTimeFormatter formatDate=DateTimeFormatter.ofPattern("MM/dd/yyyy");
	
	private String description="";
	private String dueDate= "";
	private int priority=0;
	private String status="";
	private String statusDate="";
	private int indexOfUpdated = 0;
	
	public void setTableItems(ObservableList<Item> table)//gets table info from FXMLController
	{
		this.table=table;
	}
	
	public void infoToUpdate(Item item,int index)//gets info about the item that is to be updated
	{
		description=item.getDescription();
		dueDate=item.getDueDate();
		priority=item.getPriority();
		
		inputDesc.setText(description);
		inputDueDate.setValue(LocalDate.parse(dueDate,formatDate));
		inputPriority.setText(Integer.toString(priority));
		
		indexOfUpdated=index;
	}


	@FXML private void handleButtonAction(ActionEvent event) throws Exception {//changes to add item scene
		if(event.getSource()==cancel)
		{
			Stage stage = (Stage) cancel.getScene().getWindow();
		    stage.close();
		}
		
		if(event.getSource()==save)
		{
			description=inputDesc.getText();
			dueDate= inputDueDate.getValue().format(formatDate);
			priority=Integer.parseInt(inputPriority.getText());
			status=inputStatus.getValue();
			statusDate="";
			
			boolean unique=isUnique(description,table);
			
			if(unique)//decides whether or not to display error message
			{
				Stage stage=(Stage) save.getScene().getWindow();
				if(stage.getTitle().contentEquals("Update Task"))
				{
					Save();
				}
				else {
					inputDesc.setText("Description must be Unique");
				}
			}
			else {
				Save();
			}
		}
	}
	
	private void Save()
	{
		Stage stage = (Stage) save.getScene().getWindow();
		boolean onlyPriority=false;
		onlyPriority=checkPriority(priority,table);
		
		if(onlyPriority)
		{
			updatePriority(table);//increase priority of others
		}
		
		if(!status.equals("Not started"))
		{
			if(status.contentEquals("Started"))
			{
				statusDate=inputDateStarted.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			}
			else {
				statusDate=inputDateFinished.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
			}
			status=status+"("+statusDate+")";
		}
		
		newTask=new Item(description, dueDate, priority, status, statusDate);
		if(stage.getTitle().contentEquals("Update Task"))
		{
			table.set(indexOfUpdated, newTask);
		}
		else {
		table.add(newTask);
		}
		
		stage.close();
	}
	
	private boolean isUnique(String description,ObservableList<Item>list)//checks if description is unique
	{
		boolean notUnique=false;
		for(int index=0; (index<list.size()) && (notUnique!=true); index++)
		{
			notUnique=description.equalsIgnoreCase(list.get(index).getDescription());
		}
		
		return notUnique;
	}
	
	private boolean checkPriority(int priority,ObservableList<Item>list)//checks if there are other items with the same priority
	{
		boolean onlyPriority=false;
		for(int index=0; (index<list.size()) && (onlyPriority!=true); index++)
		{
			if(!list.get(index).getDescription().contentEquals(description)) {//since it also checks upon update, don't compare
																				//element to itself
				if(priority==list.get(index).getPriority())
				{
					onlyPriority=true;
				}
			}
		}
		return onlyPriority;
	}
	
	private void updatePriority(ObservableList<Item>list)//changes the priorities of other items in the list if necessary
	{
		for(int index=0; index<list.size(); index++)
		{
			list.get(index).setPriority(list.get(index).getPriority()+1);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inputStatus.getItems().addAll("Not started", "Started", "Finished");
		inputStatus.getSelectionModel().selectedItemProperty().addListener( new//listener for status menu
				ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, String value, String newValue) {//changes visibility of items
				if(value.equals(newValue))
				{
					
				}
				else if((newValue.equals("Started")&&value.equals("Finished")))
				{
					finishedLabel.setVisible(false);
					inputDateFinished.setVisible(false);
					startedLabel.setVisible(true);
					inputDateStarted.setVisible(true);
				}
				
				else if((newValue.equals("Finished")&&value.equals("Started")))
				{
					startedLabel.setVisible(false);
					inputDateStarted.setVisible(false);
					finishedLabel.setVisible(true);
					inputDateFinished.setVisible(true);
				}
				else{
					if(newValue.equals("Finished"))
					{
						finishedLabel.setVisible(true);
						inputDateFinished.setVisible(true);
					}
					
					if(newValue.equals("Started"))
					{
						startedLabel.setVisible(true);
						inputDateStarted.setVisible(true);
					}
					else {
						startedLabel.setVisible(false);
						inputDateStarted.setVisible(false);
						finishedLabel.setVisible(false);
						inputDateFinished.setVisible(false);
					}
				}
			}
		});

	}

}
