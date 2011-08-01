package com.petproject.tools.apmt.domain.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface BaseCRUDModel<ModelInterface, DataTransferObject> extends BaseModel<ModelInterface, DataTransferObject> {
	@Transactional
	public ModelInterface add();

	@Transactional
	public ModelInterface update();

	@Transactional
	public ModelInterface delete();

	@Transactional(readOnly=true)
	public ModelInterface findOne();

	@Transactional(readOnly=true)
	public Page<ModelInterface> findAll(Pageable page);

	@Transactional(readOnly=true)
	public List<ModelInterface> findAll();

	@Transactional(readOnly=true)
	public Long count();

}