package com.code_studio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table ( name = "task")
public class Task {
	
	@Id
	@GeneratedValue
	@Column(name = "task_id")
	private Integer id;
	
	@Column(name= "todo")
	private String todo;
	
	@Column(name= "priority")
	private short priority;
	
	@Column(name= "completed")
	private boolean isDone;

	public Task() {
	}

	public Task(Integer id, String todo, short priority, boolean isDone) {
		this.id = id;
		this.todo = todo;
		this.priority = priority;
		this.isDone = isDone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTodo() {
		return todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public short getPriority() {
		return priority;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	public boolean get_isDone() {
		return isDone;
	}

	public void set_isDone(boolean isDone) {
		this.isDone = isDone;
	}	
	
}
