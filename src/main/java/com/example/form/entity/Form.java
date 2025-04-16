package com.example.form.entity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="studentform")
public class Form {
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private int stdId;
	 
	@Column
	private String name;

	@Column
	private String mobile;

	@Column
	private String email;

	@Column
	private String education;

	@Column
	private LocalDate dob;

	@Column
	private String gender;

	@Column
	private String address;

	@Lob
    @Column(columnDefinition = "LONGBLOB")
	private byte[] photo;

	
	public int getStdId() {
		return stdId;
	}

	public void setStdId(int stdId) {
		this.stdId = stdId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	
	public Form(String name, String mobile, String email, String education, LocalDate dob, String gender,
			String address, byte[] photo) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.email = email;
		this.education = education;
		this.dob = dob;
		this.gender = gender;
		this.address = address;
		this.photo = photo;
	}
public Form() {
	
}
	@Override
	public String toString() {
		return "Form [stdId=" + stdId + ", name=" + name + ", mobile=" + mobile + ", email=" + email + ", education="
				+ education + ", dob=" + dob + ", gender=" + gender + ", address=" + address + ", photo="
				+ Arrays.toString(photo) + "]";
	}
	
	
	

}
