package com.petproject.tools.apmt.dto;

public class Client {
    private Long id;
    
    private String code;
    
    private String name;
    
    private String alias;
    
    private String email;
    
    

	public Client() {

	}
	public Client(Long id) {
		this.id = id;
	}
	public Client(String code) {
		this.code = code;
	}
	public Client(String code, String name, String alias, String email) {
		this(null, code, name, alias, email);
	}
	public Client(Long id, String code, String name, String alias, String email) {
		this(id);
		this.code = code;
		this.name = name;
		this.alias = alias;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}

	public String getEmail() {
		return email;
	}

	public String toString() {
		return "Client.class - {id: "+id+"; code: "+code+"; name: "+name;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
