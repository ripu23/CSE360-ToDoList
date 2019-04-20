package org.openjfx.todolist.models;

public class Item {
	
	private String description;
	private String dueDate;
	private int priority;
	private String status;
	private String statusDate;  //date started or finished 
	
	//default constructor
	public Item() {
		description = "";
		dueDate ="";
		priority = 0;
		status = "";
		statusDate = "";
	}
	
	public Item(String description,String dueDate, int priority, String status, String statusDate) {
		this.description=description;
		this.dueDate=dueDate;
		this.priority=priority;
		this.status=status;
		this.statusDate=statusDate;
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
	
	
	/**
	 * Creates a string used for save file.
	 * @return  string
	 */
	public String getSaveString() {
		String str;
		str = this.description + "\n"
				+ this.priority + "\n"
				+ this.dueDate + "\n"
				+ this.status + "\n"
				+ this.statusDate;
		return str;
	}
	
	/**
	 * Creates a formatted string of Item components
	 * @return  string  formatted string
	 */
	public String getPrintString() {
		String str = String.format("|%-2d|%-20s|%-10s|%-20s|", 
				this.priority, this.description, this.dueDate, 
				this.status);
		return str;
	}


}