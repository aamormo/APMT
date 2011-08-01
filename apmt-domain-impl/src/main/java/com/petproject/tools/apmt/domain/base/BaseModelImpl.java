package com.petproject.tools.apmt.domain.base;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.MappedSuperclass;

import com.petproject.tools.apmt.domain.base.BaseModel;

@MappedSuperclass
public abstract class BaseModelImpl<ModelInterface, DataTransferObject> implements BaseModel<ModelInterface, DataTransferObject> {
	
	protected boolean nullObject = true;
	
	protected BaseModelImpl(DataTransferObject element) {
		nullObject = false;
	}
	
	protected BaseModelImpl() {
		nullObject = true;
	}
	
	protected Object getAttributeValue(String attribute) {
		Object value = null;
		try {
			Field field = getClass().getDeclaredField(attribute);
			field.setAccessible(true);
			value = field.get(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return value;
	}

	protected List<String> getAttributeNames() {
		List<String> values = new LinkedList<String>();
		try {
			Field[] fields = getClass().getDeclaredFields();
			
			for (Field field:fields) {
				values.add(field.getName());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return values;
	}

	public boolean contains(String attribute, String value) {
		String attributeValue = (String)getAttributeValue(attribute);
		return (attributeValue != null)?attributeValue.contains(value):false;
	}

	public boolean isEqual(String attribute, String value) {
		String attributeValue = (String)getAttributeValue(attribute);
		return (attributeValue != null)?attributeValue.equals(value):false;
	}

	public boolean startsWith(String attribute, String value) {
		String attributeValue = (String)getAttributeValue(attribute);
		return (attributeValue != null)?attributeValue.startsWith(value):false;
	}

	public boolean endsWith(String attribute, String value) {
		String attributeValue = (String)getAttributeValue(attribute);
		return (attributeValue != null)?attributeValue.endsWith(value):false;
	}
	
	public String toString() {
		String objectToString = "DomainObject: {";
		List<String> attributeNames = getAttributeNames();
		for (String attributeName : attributeNames) {
			if(objectToString.length()>1) {
				objectToString += "; ";
			}
			objectToString += attributeName+": "+getAttributeValue(attributeName);
		}
		objectToString += "}";
		return objectToString;
	}
	
	public abstract DataTransferObject getAsDataTransferObject();
	public abstract ModelInterface createDomainObject(DataTransferObject dto);
	public abstract ModelInterface createDomainObject();
}