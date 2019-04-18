package org.openjfx.todolist.models;

import javafx.scene.control.Button;

public class Item {

	private int id;
	private String description;
	private String dueDate;
	private int priority;
	private String status;
	private String dateStarted;
	private String dateFinished;
	private Button delete;
	private Button edit;

	public Item() {

	}

	public Item(int id, String description, String dueDate, int priority, String status, String dateStarted,
			String dateFinished, Button delete, Button edit) {
		super();
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.status = status;
		this.dateStarted = dateStarted;
		this.dateFinished = dateFinished;
		this.delete = delete;
		this.edit = edit;
	}

	public String getDateStarted() {
		return dateStarted;
	}

	public void setDateStarted(String dateStarted) {
		this.dateStarted = dateStarted;
	}

	public String getDateFinished() {
		return dateFinished;
	}

	public void setDateFinished(String dateFinished) {
		this.dateFinished = dateFinished;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
