package com.petproject.tools.apmt.service.base;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface BaseService<DataTransferObject, DomainObject> {
	@Transactional
	public DataTransferObject add(DataTransferObject element);

	@Transactional
	public DataTransferObject update(DataTransferObject element);

	@Transactional
	public DataTransferObject delete(DataTransferObject element);

	@Transactional(readOnly=true)
	public DataTransferObject findOne(DataTransferObject element);

	@Transactional(readOnly=true)
	public List<DataTransferObject> findAll(DataTransferObject element);

	@Transactional(readOnly=true)
	public List<DataTransferObject> findAll();

	@Transactional(readOnly=true)
	public Long count();
}