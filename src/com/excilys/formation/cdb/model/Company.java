package com.excilys.formation.cdb.model;

public class Company {
	private int id;
	private String name;
	
	public Company() {
		
	}

	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("Company: ");
		s.append(this.name);
		s.append(", id: ");
		s.append(this.id);
		return s.toString();
	}
	
	
}
