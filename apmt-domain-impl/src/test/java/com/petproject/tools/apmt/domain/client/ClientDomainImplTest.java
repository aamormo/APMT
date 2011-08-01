package com.petproject.tools.apmt.domain.client;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.petproject.tools.apmt.domain.base.exceptions.ModelException;
import com.petproject.tools.apmt.domain.client.ClientModel;
import com.petproject.tools.apmt.domain.client.ClientModelImpl;
import com.petproject.tools.apmt.dto.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class ClientDomainImplTest {

	private static final int NUMBER_OF_GENERATED_CLIENTS = 10;
	
	@Autowired
	private ClientModel clientModel = null;
	
	@Test
	public void adding_one_client() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = preparedClient.add();
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
	}
	
	@Test
	public void adding_some_clients() {
		List<ClientModel> preparedClients = prepareSomeClients();
		int stored = 0;
		for (ClientModel preparedClient: preparedClients) {
			ClientModel addedClient = preparedClient.add();
			
			compare(preparedClient, addedClient);
			
			checkStored(++stored);
		}
	}
	
	@Test
	@ExpectedException(ModelException.class)
	public void adding_repeated_client_with_exception() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = preparedClient.add();
		
		compare(preparedClient, addedClient);
		
		checkStored(1);

		ClientModel repeatedClient = prepareOneClient();
		
		repeatedClient.add();
	}
	
	@Test
	public void update_one_client() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = preparedClient.add();
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		Client addedClientDTO = (Client)addedClient.getAsDataTransferObject();
		Client clientDTOToUpdate = new Client(
				addedClientDTO.getId(),
				addedClientDTO.getCode(),
				"Changed name",
				addedClientDTO.getAlias(),
				addedClientDTO.getEmail());
		
		ClientModel clientToUpdate = prepareClientDomainObject(clientDTOToUpdate);
		
		ClientModel updatedClient = clientToUpdate.update();

		compareWithId(clientToUpdate, updatedClient);
		checkStored(1);
	}
	
	@Test
	public void update_one_new_item() {
		Client client = new Client(Long.valueOf(1), "CODE", "NAME", "ALIAS", "email@gmail.com");
		
		ClientModel clientToUpdate = prepareClientDomainObject(client);
		
		ClientModel updatedClient = clientToUpdate.update();

		compareWithId(clientToUpdate, updatedClient);
		checkStored(1);
	}
	
	@Test
	public void deleting_one_client() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = preparedClient.add();
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		ClientModel deletedClient = addedClient.delete();
		
		compareWithId(addedClient, deletedClient);

		checkStored(0);
	}
	
	@Test
	@ExpectedException(ModelException.class)
	public void deleting_unexistent_item_with_exception() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = preparedClient.add();
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		ClientModel deletedClient = addedClient.delete();
		
		compareWithId(addedClient, deletedClient);

		checkStored(0);
		
		addedClient.delete();
	}
	
	@Test
	public void finding_one_client() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = preparedClient.add();
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		ClientModel foundClient = addedClient.findOne();
		
		compareWithId(addedClient, foundClient);
		
		checkStored(1);
	}

	@Test
	public void finding_wrong_sinthetic_key() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = preparedClient.add();
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		ClientModel deletedClient = addedClient.delete();
		
		compare(addedClient, deletedClient);
		
		checkStored(0);
		
		assertNull(deletedClient.findOne());
	}
/*	
	@Test
	public void loading_by_sinthetic_key() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = add(preparedClient);
		
		compareEntities(preparedClient, createdEntity);
		checkStoredObjects(1);
		
		MockEntity entityFound = loadBySintheticKey(preparedClient.getId());

		compareEntitiesWithId(createdEntity, entityFound);
		checkStoredObjects(1);
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void loading_wrong_sinthetic_key() {
		loadBySintheticKey(Integer.valueOf(1));
	}
*/	
	@Test
	public void finding_by_client_code() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = preparedClient.add();
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		ClientModel foundClient = addedClient.findByCode();
		
		compareWithId(addedClient, foundClient);
		
		checkStored(1);
	}

	@Test
	@ExpectedException(ModelException.class)
	public void finding_by_wrong_client_code() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = preparedClient.add();
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		ClientModel deletedClient = addedClient.delete();
		
		compare(addedClient, deletedClient);
		
		checkStored(0);
		
		deletedClient.findByCode();
	}
/*	
	@Test
	public void loading_by_natural_key() {
		ClientModel preparedClient = prepareOneClient();
		
		ClientModel addedClient = add(preparedClient);
		
		compareEntities(preparedClient, createdEntity);
		checkStoredObjects(1);
		
		MockEntity entityFound = loadByNaturalKey(preparedClient.getCode());

		compareEntitiesWithId(createdEntity, entityFound);
		checkStoredObjects(1);
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void loading_wrong_natural_key() {
		MockEntity entityFound = loadByNaturalKey("CODE0001");
		assertNotNull(entityFound);
	}
*/	
	@Test
	public void finding_all_clients() {
		List<ClientModel> preparedClients = prepareSomeClients();

		List<ClientModel> addedClients = new LinkedList<ClientModel>();

		int stored = 0;
		for (ClientModel preparedClient: preparedClients) {
			ClientModel addedClient = preparedClient.add();

			compare(preparedClient, addedClient);
			
			checkStored(++stored);

			addedClients.add(addedClient);
		}
		
		ClientModel preparedClient = prepareEmptyClient();
		
		List<ClientModel> foundClients = preparedClient.findAll();
		
		compareWithId(addedClients, foundClients);
		
		assertEquals(addedClients.size(), foundClients.size());
	}

	@Test
	public void finding_all_clients_with_restrictions() {
		List<ClientModel> preparedClients = prepareSomeClients();

		List<ClientModel> addedClients = new LinkedList<ClientModel>();

		int stored = 0;
		for (ClientModel preparedClient: preparedClients) {
			ClientModel addedClient = preparedClient.add();

			compare(preparedClient, addedClient);
			
			checkStored(++stored);

			addedClients.add(addedClient);
		}
		
		ClientModel preparedClient = prepareOneClient();
		
		List<ClientModel> foundClients = preparedClient.findAll();
		
		compare(preparedClient, foundClients.get(0));
		
		assertEquals(1, foundClients.size());
	}
/*
	@Test
	public void finding_all_with_max_results() {
		MockEntity preparedFirstEntity = prepareOneClient();
		MockEntity preparedSecondEntity = prepareAnotherEntity();
		add(preparedFirstEntity);
		add(preparedSecondEntity);
		
		checkStoredObjects(2);

//		Map<String, Object> restrictions = new HashMap<String, Object>();
//		restrictions.put(RestrictionConstants.RESTRICTION_MAX_RESULTS, 2);
		Criteria criteria = prepareCriteria();
		criteria.setMaxResults(2);
		List<MockEntity> entitiesFound = findBy(criteria);
		
		assertEquals(2, entitiesFound.size());
		compareEntities(preparedFirstEntity, entitiesFound.get(0));
		compareEntities(preparedSecondEntity, entitiesFound.get(1));

		criteria.setMaxResults(1);
//		restrictions.put(RestrictionConstants.RESTRICTION_MAX_RESULTS, 1);
		entitiesFound = findBy(criteria);
		
		assertEquals(1, entitiesFound.size());
		compareEntities(preparedFirstEntity, entitiesFound.get(0));

//		restrictions.put(RestrictionConstants.RESTRICTION_MAX_RESULTS, 0);
		criteria.setMaxResults(0);
		entitiesFound = findBy(criteria);
		
		assertEquals(0, entitiesFound.size());
	}

	@Test
	public void finding_all_with_first_result() {
		MockEntity preparedFirstEntity = prepareOneClient();
		MockEntity preparedSecondEntity = prepareAnotherEntity();
		add(preparedFirstEntity);
		add(preparedSecondEntity);
		
		checkStoredObjects(2);

//		Map<String, Object> restrictions = new HashMap<String, Object>();
//		restrictions.put(RestrictionConstants.RESTRICTION_FIRST_RESULT, 0);
		Criteria criteria = prepareCriteria();
		criteria.setFirstResult(0);		
		List<MockEntity> entitiesFound = findBy(criteria);
		
		assertEquals(2, entitiesFound.size());
		compareEntities(preparedFirstEntity, entitiesFound.get(0));
		compareEntities(preparedSecondEntity, entitiesFound.get(1));

//		restrictions.put(RestrictionConstants.RESTRICTION_FIRST_RESULT, 1);
		criteria.setFirstResult(1);		
		entitiesFound = findBy(criteria);
		
		assertEquals(1, entitiesFound.size());
		compareEntities(preparedSecondEntity, entitiesFound.get(0));

//		restrictions.put(RestrictionConstants.RESTRICTION_FIRST_RESULT, 2);
		criteria.setFirstResult(2);		
		entitiesFound = findBy(criteria);
		
		assertEquals(0, entitiesFound.size());
	}

	@Test
	public void finding_all_with_first_result_max_results() {
		MockEntity preparedFirstEntity = prepareOneClient();
		MockEntity preparedSecondEntity = prepareAnotherEntity();
		add(preparedFirstEntity);
		add(preparedSecondEntity);
		
		checkStoredObjects(2);

//		Map<String, Object> restrictions = new HashMap<String, Object>();
//		restrictions.put(RestrictionConstants.RESTRICTION_FIRST_RESULT, 0);
		Criteria criteria = prepareCriteria();
		criteria.setFirstResult(0);		
		List<MockEntity> entitiesFound = findBy(criteria);
		
		assertEquals(2, entitiesFound.size());
		compareEntities(preparedFirstEntity, entitiesFound.get(0));
		compareEntities(preparedSecondEntity, entitiesFound.get(1));

//		restrictions.put(RestrictionConstants.RESTRICTION_FIRST_RESULT, 1);
		criteria.setFirstResult(1);		
		entitiesFound = findBy(criteria);
		
		assertEquals(1, entitiesFound.size());
		compareEntities(preparedSecondEntity, entitiesFound.get(0));

//		restrictions.put(RestrictionConstants.RESTRICTION_FIRST_RESULT, 2);
		criteria.setFirstResult(2);		
		entitiesFound = findBy(criteria);
		
		assertEquals(0, entitiesFound.size());

//		restrictions.put(RestrictionConstants.RESTRICTION_FIRST_RESULT, 0);
//		restrictions.put(RestrictionConstants.RESTRICTION_MAX_RESULTS, 1);
		criteria.setFirstResult(0);
		criteria.setMaxResults(1);
		entitiesFound = findBy(criteria);
		
		assertEquals(1, entitiesFound.size());
		compareEntities(preparedFirstEntity, entitiesFound.get(0));
	}

	@Test
	public void finding_results_by_properies() {
		MockEntity preparedFirstEntity = prepareOneClient();
		MockEntity preparedSecondEntity = prepareAnotherEntity();
		add(preparedFirstEntity);
		add(preparedSecondEntity);
		
		checkStoredObjects(2);

//		Map<String, Object> restrictions = new HashMap<String, Object>();
//		restrictions.put("name", preparedSecondEntity.getName());
		Criteria criteria = prepareCriteria();
		Restriction restriction = criteria.createRestriction();
		criteria.add(restriction.equals("name", preparedSecondEntity.getName()));		
		List<MockEntity> entitiesFound = findBy(criteria);
		
		assertEquals(1, entitiesFound.size());
		compareEntities(preparedSecondEntity, entitiesFound.get(0));
	}
*/
	
	protected ClientModel prepareEmptyClient() {
		return prepareClientDomainObject();
	}

	protected ClientModel prepareOneClient() {
		Client client = prepareClientDTO(1);
		return prepareClientDomainObject(client);
	}

	protected List<ClientModel> prepareSomeClients() {
		List<Client> clients = prepareSomeClientsDTO();
		return prepareClientDomainObjects(clients);
	}

	protected List<Client> prepareSomeClientsDTO() {
		List<Client> clients = new LinkedList<Client>();
		Client client = null;
		for (int i=0; i < NUMBER_OF_GENERATED_CLIENTS; i++) {
			client = prepareClientDTO(i);
			clients.add(client);
		}
		return clients;
	}
	
	protected Client prepareClientDTO(int id) {
		return new Client("CLIENT000"+id, "Banco "+id, "banco"+id, "banco"+id+"@gmail.com");
	}
	
	protected List<ClientModel> prepareClientDomainObjects(List<Client> clients) {
		List<ClientModel> clientDomainObjects = new LinkedList<ClientModel>();
		for (Client client:clients) {
			clientDomainObjects.add(clientModel.createDomainObject(client));
		}
		return clientDomainObjects;
	}

	protected ClientModel prepareClientDomainObject() {
		return clientModel.createDomainObject();
	}

	protected ClientModel prepareClientDomainObject(Client client) {
		return clientModel.createDomainObject(client);
	}

	protected void compare(List<ClientModelImpl> prepared, List<ClientModelImpl> modified) {
		assertNotNull("entity should never be null", modified);
		for (int i = 0; i < prepared.size(); i++) {
			compare(prepared.get(i), modified.get(i));
		}
	}

	protected void compareWithId(List<ClientModel> prepared, List<ClientModel> modified) {
		assertNotNull("entity should never be null", modified);
		for (int i = 0; i < prepared.size(); i++) {
			compareWithId(prepared.get(i), modified.get(i));
		}
	}

	protected void compare(ClientModel prepared, ClientModel modified) {
		assertNotNull("entity should never be null", modified);
	}

	protected void compareWithId(ClientModel prepared, ClientModel modified) {
		assertNotNull("entity should never be null", modified);
	}

	protected void checkStored(long numberOfEntities) {
		assertEquals(numberOfEntities, getNumberOfObjectsStored());
	}
	
	protected long getNumberOfObjectsStored() {
		return prepareOneClient().count();
	}
}