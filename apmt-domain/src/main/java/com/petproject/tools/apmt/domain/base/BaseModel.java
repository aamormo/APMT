package com.petproject.tools.apmt.domain.base;

public interface BaseModel<ModelInterface, DataTransferObject> {
	public boolean contains(String attribute, String value);
	public boolean isEqual(String attribute, String value);
	public boolean startsWith(String attribute, String value);
	public boolean endsWith(String attribute, String value);
	public DataTransferObject getAsDataTransferObject();
	public ModelInterface createDomainObject(DataTransferObject dto);
	public ModelInterface createDomainObject();
}