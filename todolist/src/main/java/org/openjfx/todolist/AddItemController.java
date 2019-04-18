package org.openjfx.todolist;

import java.net.URL;
import java.util.ResourceBundle;

import org.openjfx.todolist.models.Item;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddItemController implements Initializable {

	@FXML
	private TextField inputDesc;

	@FXML
	private DatePicker inputDueDate;

	@FXML
	private TextField inputPriority;

	@FXML
	private Button save;

	@FXML
	private Button cancel;

	@FXML
	private ChoiceBox<String> inputStatus;

	@FXML
	private DatePicker inputDateFinished;

	@FXML
	private DatePicker inputDateStarted;

	@FXML
	void cancel(ActionEvent event) {
		Stage stage = (Stage) cancel.getScene().getWindow();
	    stage.close();
	}

	@FXML
	void save(ActionEvent event) {
//		String desc = inputDesc.getText().toString();
//		LocalDate dueDate = inputDueDate.getValue();
//		String priority = inputPriority.getText().toString();
//		LocalDate dateStarted = inputDateStarted.getValue();
//		LocalDate dateFinished = inputDateFinished.getValue();
//		String status = inputStatus.getValue().toString();
//		Item item = new Item(1, desc, dueDate.toString(), Integer.parseInt(priority), status, dateStarted.toString(), 
//				dateFinished.toString(), new Button("Delete"), new Button("Edit"));
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/Users/ripu/Documents/projects/Workspace_360/todolist/src/main/resources/todolist.fxml"));
		FXMLController fxmlController = loader.getController();
		fxmlController.save(new Item());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		inputStatus.getItems().addAll("Not started", "Started", "Finished");
	}

}
