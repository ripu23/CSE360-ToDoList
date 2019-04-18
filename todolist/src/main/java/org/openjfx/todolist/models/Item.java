package org.openjfx.todolist.models;

import javafx.scene.control.Button;

public class Item {
	
	private int id;
	private String description;
	private String dueDate;
	private int priority;
	private String status;
	private Button update;
	private String statusDate;  //date started or finished
	
	
	public Item(int id, String description, String dueDate, int priority, String status, String statusDate, Button update) {
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.status = status;
		this.statusDate = statusDate;
		this.update = update;
	}

	
	//default constructor
	public Item() {
		description = "";
		dueDate ="";
		priority = 0;
		status = "";
		statusDate = "";
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Button getUpdate() {
		return update;
	}

	public void setUpdate(Button update) {
		this.update = update;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getDueDate() {
		return this.dueDate;
	}
	
	public int getPriority() {
		return this.priority;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public String getStatusDate() {
		return this.statusDate;
	}

}
