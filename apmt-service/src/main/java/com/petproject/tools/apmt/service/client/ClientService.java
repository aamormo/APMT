package com.petproject.tools.apmt.service.client;

import com.petproject.tools.apmt.domain.client.ClientModel;
import com.petproject.tools.apmt.dto.Client;
import com.petproject.tools.apmt.service.base.BaseService;

public interface ClientService extends BaseService<Client, ClientModel>{
	public Client findByCode(Client client);
}