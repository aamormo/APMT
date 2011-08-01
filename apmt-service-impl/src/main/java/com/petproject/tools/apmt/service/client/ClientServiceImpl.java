package com.petproject.tools.apmt.service.client;

import java.io.Serializable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;
import com.petproject.tools.apmt.domain.base.exceptions.ModelException;
import com.petproject.tools.apmt.domain.client.ClientModel;
import com.petproject.tools.apmt.dto.Client;
import com.petproject.tools.apmt.service.base.BaseServiceImpl;
import com.petproject.tools.apmt.service.base.exceptions.ServiceException;
import com.petproject.tools.apmt.service.client.ClientService;

@Service("clientService")
public class ClientServiceImpl extends BaseServiceImpl<Client, ClientModel> implements ClientService {

	public static final String CACHE_ITEM_NAME = "com.petproject.framework.service.Client"+CACHE_ITEM_SUFFIX;
	public static final String CACHE_LIST_NAME = "com.petproject.framework.service.Client"+CACHE_LIST_SUFFIX;
	
	@Autowired private ClientModel clientModel = null;
	
	//@Cacheable(cacheName=CACHE_ITEM_NAME)
	@TriggersRemove(cacheName={CACHE_ITEM_NAME, CACHE_LIST_NAME}, removeAll=true, when=When.BEFORE_METHOD_INVOCATION)
	public Client add(Client client) {
		return super.add(client);
	}

	//@Cacheable(cacheName=CACHE_ITEM_NAME)
	@TriggersRemove(cacheName={CACHE_ITEM_NAME, CACHE_LIST_NAME}, removeAll=true, when=When.BEFORE_METHOD_INVOCATION)
	public Client update(Client client) {
		return super.update(client);
	}

	@TriggersRemove(cacheName={CACHE_ITEM_NAME, CACHE_LIST_NAME}, removeAll=true, when=When.AFTER_METHOD_INVOCATION)
	public Client delete(Serializable id) {
		Client client = new Client(Long.valueOf(id.toString()));
		return super.delete(client);
	}

	@Cacheable(cacheName=CACHE_ITEM_NAME)
	public Client findOne(Serializable id) {
		Client client = new Client(Long.valueOf(id.toString()));
		return super.findOne(client);
	}

	@Cacheable(cacheName=CACHE_LIST_NAME)
	public List<Client> findAll() {
		return super.findAll();
	}

	@Cacheable(cacheName=CACHE_LIST_NAME)
	public List<Client> findAll(Client client) {
		return super.findAll(client);
	}

	@Cacheable(cacheName=CACHE_ITEM_NAME)
	public Client findByCode(Client client) {
		try {
			ClientModel model = getDomainObject(client).findByCode();
			return model.getAsDataTransferObject();
		}
		catch(ModelException ex) {
			throw new ServiceException(FIND_BY_NATURAL_KEY_SERVICE_EXCEPTION_MESSAGE, ex);
		}
	}

	protected ClientModel getDomainObject(Client client) {
		return clientModel.createDomainObject(client);
	}

	protected ClientModel getDomainObject() {
		return clientModel.createDomainObject();
	}
}