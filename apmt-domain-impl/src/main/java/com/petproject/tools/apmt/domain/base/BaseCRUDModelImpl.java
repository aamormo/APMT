package com.petproject.tools.apmt.domain.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.MappedSuperclass;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.petproject.tools.apmt.domain.base.BaseCRUDModel;
import com.petproject.tools.apmt.domain.base.exceptions.ModelException;

@MappedSuperclass
public abstract class BaseCRUDModelImpl<ModelInterface, ModelImplementation extends ModelInterface, DataTransferObject> extends BaseModelImpl<ModelInterface, DataTransferObject> implements BaseCRUDModel<ModelInterface, DataTransferObject> {
	
	protected static final String CACHE_ITEM_SUFFIX = "Item";
	protected static final String CACHE_LIST_SUFFIX = "List";
	protected static final String ADD_MODEL_EXCEPTION_MESSAGE = "model.exception.message.add";
	protected static final String UPDATE_MODEL_EXCEPTION_MESSAGE = "model.exception.message.update";
	protected static final String DELETE_MODEL_EXCEPTION_MESSAGE = "model.exception.message.delete";
	protected static final String COUNT_MODEL_EXCEPTION_MESSAGE = "model.exception.message.count";
	protected static final String FIND_BY_SYNTHETIC_KEY_MODEL_EXCEPTION_MESSAGE = "model.exception.message.findBySyntheticKey";
	protected static final String FIND_BY_NATURAL_KEY_MODEL_EXCEPTION_MESSAGE = "model.exception.message.findByNaturalKey";
	protected static final String FIND_ALL_PAGEABLE_MODEL_EXCEPTION_MESSAGE = "model.exception.message.findAll";
	protected static final String FIND_ALL_MODEL_EXCEPTION_MESSAGE = "model.exception.message.findAll";

	protected BaseCRUDModelImpl(DataTransferObject element) {
		super(element);
	}
	
	protected BaseCRUDModelImpl() {
		super();
	}
	
	public ModelInterface add() {
		try {
			return getRepository().save(getModelImplementation());
		}
		catch(DataAccessException ex) {
			throw new ModelException(ADD_MODEL_EXCEPTION_MESSAGE, ex);
		}
	}

	public ModelInterface update() {
		try {
			return getRepository().save(getModelImplementation());
		}
		catch(DataAccessException ex) {
			throw new ModelException(UPDATE_MODEL_EXCEPTION_MESSAGE, ex);
		}
	}

	public ModelInterface delete() {
		ModelImplementation model = getModelImplementation();
		try {
			getRepository().delete(getModelIdentifier());
		}
		catch(DataAccessException ex) {
			throw new ModelException(DELETE_MODEL_EXCEPTION_MESSAGE, ex);
		}
		return model;
	}

	public ModelInterface findOne() {
		try {
			return getRepository().findOne(getModelIdentifier());
		}
		catch(DataAccessException ex) {
			throw new ModelException(FIND_BY_SYNTHETIC_KEY_MODEL_EXCEPTION_MESSAGE, ex);
		}
	}

	@SuppressWarnings("unchecked")
	public Page<ModelInterface> findAll(Pageable pageable) {
		// TODO Review and test the findAll method with pagination
		try {
			return (Page<ModelInterface>)getRepository().findAll(pageable);
		}
		catch(DataAccessException ex) {
			throw new ModelException(FIND_ALL_PAGEABLE_MODEL_EXCEPTION_MESSAGE, ex);
		}
	}
	
	public List<ModelInterface> findAll() {
		try {
			return (List)getRepository().findAll();
		}
		catch(DataAccessException ex) {
			throw new ModelException(FIND_ALL_MODEL_EXCEPTION_MESSAGE, ex);
		}
	}

	public Long count() {
		try {
			return getRepository().count();
		}
		catch(DataAccessException ex) {
			throw new ModelException(COUNT_MODEL_EXCEPTION_MESSAGE, ex);
		}
	}

	protected abstract ModelImplementation getModelImplementation();

	protected abstract Serializable getModelIdentifier();

	protected abstract PagingAndSortingRepository<ModelImplementation, Serializable> getRepository();
}