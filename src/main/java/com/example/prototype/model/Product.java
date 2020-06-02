package com.example.prototype.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
	
	private Attributes attributes;
	private String Id;
	private String OwnerId;
	private boolean IsDeleted;
	private String Name;
	@JsonFormat(timezone = "GMT", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") private Date CreatedDate;
	private String CreatedById;
	@JsonFormat(timezone = "GMT", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") private Date LastModifiedDate;
	private String LastModifiedById;
	@JsonFormat(timezone = "GMT", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") private Date SystemModstamp;
	@JsonFormat(timezone = "GMT", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") private Date LastActivityDate;
	@JsonFormat(timezone = "GMT", pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") private Date LastReferencedDate;
	private String Description__c;
	private String SKU__c;
	
	public Attributes getAttributes() {
		return attributes;
	}
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
	@JsonProperty(value="Id")
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	@JsonProperty(value="OwnerId")
	public String getOwnerId() {
		return OwnerId;
	}
	public void setOwnerId(String ownerId) {
		OwnerId = ownerId;
	}
	@JsonProperty(value="IsDeleted")
	public boolean isIsDeleted() {
		return IsDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		IsDeleted = isDeleted;
	}
	@JsonProperty(value="Name")
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	@JsonProperty(value="CreatedDate")
	public Date getCreatedDate() {
		return CreatedDate;
	}
	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}
	@JsonProperty(value="CreatedById")
	public String getCreatedById() {
		return CreatedById;
	}
	public void setCreatedById(String createdById) {
		CreatedById = createdById;
	}
	@JsonProperty(value="LastModifiedDate")
	public Date getLastModifiedDate() {
		return LastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		LastModifiedDate = lastModifiedDate;
	}
	@JsonProperty(value="LastModifiedById")
	public String getLastModifiedById() {
		return LastModifiedById;
	}
	public void setLastModifiedById(String lastModifiedById) {
		LastModifiedById = lastModifiedById;
	}
	@JsonProperty(value="SystemModstamp")
	public Date getSystemModstamp() {
		return SystemModstamp;
	}
	public void setSystemModstamp(Date systemModstamp) {
		SystemModstamp = systemModstamp;
	}
	@JsonProperty(value="LastActivityDate")
	public Date getLastActivityDate() {
		return LastActivityDate;
	}
	public void setLastActivityDate(Date lastActivityDate) {
		LastActivityDate = lastActivityDate;
	}
	@JsonProperty(value="LastReferencedDate")
	public Date getLastReferencedDate() {
		return LastReferencedDate;
	}
	public void setLastReferencedDate(Date lastReferencedDate) {
		LastReferencedDate = lastReferencedDate;
	}
	@JsonProperty(value="Description__c")
	public String getDescription__c() {
		return Description__c;
	}
	public void setDescription__c(String description__c) {
		Description__c = description__c;
	}
	@JsonProperty(value="SKU__c")
	public String getSKU__c() {
		return SKU__c;
	}
	public void setSKU__c(String sKU__c) {
		SKU__c = sKU__c;
	}
	
}
