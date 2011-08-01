package com.petproject.tools.apmt.domain.client;

import java.util.List;

import com.petproject.tools.apmt.domain.base.BaseCRUDModel;
import com.petproject.tools.apmt.dto.Client;

public interface ClientModel extends BaseCRUDModel<ClientModel, Client> {
	public List<ClientModel> findAll();
	public ClientModel findByCode();
}