package com.naughtyspirit.uniinfosystemclient.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Mark {
	
	private String title;
	private String mark;
	private String date;
	@JsonProperty("form_of_control")
	private String formOfControl;
	private int year;
	private int semester;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFormOfControl() {
		return formOfControl;
	}
	public void setFormOfControl(String formOfControl) {
		this.formOfControl = formOfControl;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
}
