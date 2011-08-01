package com.petproject.tools.apmt.service.base;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petproject.tools.apmt.domain.base.BaseCRUDModel;
import com.petproject.tools.apmt.domain.base.exceptions.ModelException;
import com.petproject.tools.apmt.service.base.BaseService;
import com.petproject.tools.apmt.service.base.exceptions.ServiceException;

public abstract class BaseServiceImpl<DataTransferObject, DomainObject extends BaseCRUDModel<DomainObject, DataTransferObject>> implements BaseService<DataTransferObject, DomainObject> {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	protected static final String CACHE_ITEM_SUFFIX = "Item";
	protected static final String CACHE_LIST_SUFFIX = "List";
	protected static final String ADD_SERVICE_EXCEPTION_MESSAGE = "service.exception.message.add";
	protected static final String UPDATE_SERVICE_EXCEPTION_MESSAGE = "service.exception.message.update";
	protected static final String DELETE_SERVICE_EXCEPTION_MESSAGE = "service.exception.message.delete";
	protected static final String FIND_BY_KEY_SERVICE_EXCEPTION_MESSAGE = "service.exception.message.findBySyntheticKey";
	protected static final String FIND_BY_NATURAL_KEY_SERVICE_EXCEPTION_MESSAGE = "service.exception.message.findByNaturalKey";
	protected static final String FIND_ALL_SERVICE_EXCEPTION_MESSAGE = "service.exception.message.findBy";
	protected static final String COUNT_SERVICE_EXCEPTION_MESSAGE = "service.exception.message.count";

	public DataTransferObject add(DataTransferObject element) {
		try {
			DomainObject added = getDomainObject(element).add();
			System.out.println("Service Added: "+added.toString());
			return added.getAsDataTransferObject();
		}
		catch(ModelException ex) {
			throw new ServiceException(ADD_SERVICE_EXCEPTION_MESSAGE, ex);
		}
	}

	public DataTransferObject update(DataTransferObject element) {
		try {
			DomainObject updated = getDomainObject(element).update();
			return updated.getAsDataTransferObject();
		}
		catch(ModelException ex) {
			throw new ServiceException(UPDATE_SERVICE_EXCEPTION_MESSAGE, ex);
		}
	}

	public DataTransferObject delete(DataTransferObject element) {
		try {
			DomainObject deleted = getDomainObject(element).delete();
			return deleted.getAsDataTransferObject();
		}
		catch(ModelException ex) {
			throw new ServiceException(DELETE_SERVICE_EXCEPTION_MESSAGE, ex);
		}
	}

	public DataTransferObject findOne(DataTransferObject element) {
		try {
			DomainObject found = getDomainObject(element).findOne();
			if (found != null) {
				return found.getAsDataTransferObject();
			}
			throw new ServiceException(FIND_BY_KEY_SERVICE_EXCEPTION_MESSAGE);
		}
		catch(ModelException ex) {
			throw new ServiceException(FIND_BY_KEY_SERVICE_EXCEPTION_MESSAGE, ex);
		}
	}

	public List<DataTransferObject> findAll() {
		logger.debug("START - FindAll()");
		try {
			List<DomainObject> foundDomainObjects = getDomainObject().findAll();
			List<DataTransferObject> foundDataTrasferObjects = new LinkedList<DataTransferObject>();
			if (foundDomainObjects != null) {
				for(DomainObject domainObject : foundDomainObjects) {
					foundDataTrasferObjects.add(domainObject.getAsDataTransferObject());
				}
				logger.debug("EXIT - FindAll()"+foundDataTrasferObjects.size());
				return foundDataTrasferObjects;
			}
			throw new ServiceException(FIND_ALL_SERVICE_EXCEPTION_MESSAGE);
		}
		catch(ModelException ex) {
			throw new ServiceException(FIND_ALL_SERVICE_EXCEPTION_MESSAGE, ex);
		}
	}
	
	public List<DataTransferObject> findAll(DataTransferObject element) {
		try {
			List<DomainObject> foundDomainObjects = getDomainObject(element).findAll();
			List<DataTransferObject> foundDataTrasferObjects = new LinkedList<DataTransferObject>();
			if (foundDomainObjects != null) {
				for(DomainObject domainObject : foundDomainObjects) {
					foundDataTrasferObjects.add(domainObject.getAsDataTransferObject());
				}
				return foundDataTrasferObjects;
			}
			throw new ServiceException(FIND_ALL_SERVICE_EXCEPTION_MESSAGE);
		}
		catch(ModelException ex) {
			throw new ServiceException(FIND_ALL_SERVICE_EXCEPTION_MESSAGE, ex);
		}
	}
	
	public Long count() {
		try {
			return getDomainObject().count();
		}
		catch(ModelException ex) {
			throw new ServiceException(COUNT_SERVICE_EXCEPTION_MESSAGE, ex);
		}
	}

	protected abstract DomainObject getDomainObject(DataTransferObject element);

	protected abstract DomainObject getDomainObject();
}