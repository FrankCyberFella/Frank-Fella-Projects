package com.frank.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class Employee {
	private Long employeeId;
	private Long departmentId;
	private String firstName;
	private String lastName;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate birthDay;
	private char gender;
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate hireDate;
	
	public Long getId() {
		return employeeId;
	}
	public void setId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public LocalDate getBirthDay() {
		return birthDay;
	}
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public LocalDate getHireDate() {
		return hireDate;
	}
	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}
	public String toString() {
		return lastName + ", " + firstName;
	}
}
