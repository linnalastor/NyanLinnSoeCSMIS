package com.csmis.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="authorities")
public class Authorities {

	@Id
	@Column(name="id")
	private String id;

	@Column(name="authority")
	private String authority;



	public Authorities() {
		super();
		// TODO Auto-generated constructor stub
	}






	public Authorities(String id, String authority) {
	super();
	this.id = id;
	this.authority = authority;

}


	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getAuthority() {
		return authority;
	}



	public void setAuthority(String authority) {
		this.authority = authority;
	}






	public void Authorities1( String id2, String authority2) {
		// TODO Auto-generated method stub
		this.id=id2;
		this.authority=authority2;
	}




}