package com.petproject.tools.apmt.domain.client;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import com.mysema.query.types.expr.BooleanExpression;
import com.petproject.tools.apmt.data.client.ClientRepository;
import com.petproject.tools.apmt.domain.base.BaseCRUDModelImpl;
import com.petproject.tools.apmt.domain.base.exceptions.ModelException;
import com.petproject.tools.apmt.domain.client.ClientModel;
import com.petproject.tools.apmt.dto.Client;

import javax.inject.Inject;

@Component
@Entity
@Table(name="clients")
@Configurable(preConstruction = true)
public class ClientModelImpl extends BaseCRUDModelImpl<ClientModel, ClientModelImpl, Client> implements ClientModel {

	protected static final String FIND_BY_CODE_MODEL_EXCEPTION_MESSAGE = "model.exception.message.findByCode";

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
	@Column(name="code", length=20, unique=true)
    private String code;
    
	@Column(name="name", length=100)
    private String name;
    
	@Column(name="alias", length=20)
    private String alias;
    
	@Column(name="email", length=45)
    private String email;
    
	@Column(name="telephone", length=25)
	private String telephone;
    
    @Transient
    @Inject
    protected ClientRepository repository;
    
    protected ClientModelImpl() {
    	super();
    }
    
	protected ClientModelImpl(Client client) {
		super(client);
		this.id = client.getId();
		this.code = client.getCode();
		this.name = client.getName();
		this.alias = client.getAlias();
		this.email = client.getEmail();
	}

	//Predicates
    private static QClientModelImpl client = QClientModelImpl.clientModelImpl;
    
    protected static BooleanExpression byCode(String code) {
    	return client.code.eq(code);
    }
    
    protected BooleanExpression getRestrictions() {
    	BooleanExpression expression = null;
    	if(id != null) {
    		System.out.println("ID: "+id);
    		expression = client.id.eq(id);
    	}
    	else if(code != null && !code.equals("")) {
    		expression = client.code.eq(code);
    	}
    	else if(name != null && !name.equals("")) {
    		expression = client.name.eq(name);
    	}
    	else if(alias != null && !alias.equals("")) {
    		expression = client.alias.eq(alias);
    	}
    	else if(email != null && !email.equals("")) {
    		expression = client.email.eq(email);
    	}
    	return expression;
    }
    
	public ClientModel findByCode() {
		try {
			List<ClientModelImpl> clients = (List<ClientModelImpl>)repository.findAll(byCode(code));
			return (ClientModel)clients.get(0);
		}
		catch(DataAccessException ex) {
			throw new ModelException(FIND_BY_CODE_MODEL_EXCEPTION_MESSAGE, ex);
		}
		catch(IndexOutOfBoundsException ex) {
			throw new ModelException(FIND_BY_CODE_MODEL_EXCEPTION_MESSAGE, ex);
		}
	}

	public List<ClientModel> findAll() {
		try {
			if (nullObject) {
				return (List)super.findAll();
			}
			else {
				return (List)repository.findAll(getRestrictions());
			}
		}
		catch(DataAccessException ex) {
			throw new ModelException(FIND_ALL_MODEL_EXCEPTION_MESSAGE, ex);
		}
	}

	@Override
	protected ClientModelImpl getModelImplementation() {
		return this;
	}


	protected Serializable getModelIdentifier() {
		return this.id;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected PagingAndSortingRepository<ClientModelImpl, Serializable> getRepository() {
		return (PagingAndSortingRepository)repository;
	}

	public Client getAsDataTransferObject() {
		return new Client(id, code, name, alias, email);
	}

	public ClientModel createDomainObject(Client client) {
		return new ClientModelImpl(client);
	}

	public ClientModel createDomainObject() {
		return new ClientModelImpl();
	}
}