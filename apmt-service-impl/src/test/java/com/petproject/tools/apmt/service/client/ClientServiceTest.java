package com.petproject.tools.apmt.service.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.petproject.tools.apmt.dto.Client;
import com.petproject.tools.apmt.service.base.exceptions.ServiceException;
import com.petproject.tools.apmt.service.client.ClientService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class ClientServiceTest {

	@Autowired private ClientService service;
	
	private static final int NUMBER_OF_GENERATED_CLIENTS = 10;

	@Test
	public void adding_one_client() {
		Client preparedClient = prepareOneClient();
		
		Client addedClient = service.add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
	}
	
	@Test
	public void adding_some_clients() {
		List<Client> preparedClients = prepareSomeClients();
		int stored = 0;
		for (Client preparedClient: preparedClients) {
			Client addedClient = service.add(preparedClient);
			
			compare(preparedClient, addedClient);
			
			checkStored(++stored);
		}
	}
	
	@Test
	@ExpectedException(ServiceException.class)
	public void adding_repeated_client_with_exception() {
		Client preparedClient = prepareOneClient();
		
		Client addedClient = service.add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);

		Client repeatedClient = prepareOneClient();
		
		service.add(repeatedClient);
	}
	
	@Test
	public void update_one_client() {
		Client preparedClient = prepareOneClient();
		
		Client addedClient = service.add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		Client clientToUpdate = new Client(
				addedClient.getId(),
				addedClient.getCode(),
				"Changed name",
				addedClient.getAlias(),
				addedClient.getEmail());
		
		Client updatedClient = service.update(clientToUpdate);

		compareWithId(clientToUpdate, updatedClient);
		checkStored(1);
	}
	
	@Test
	public void update_one_new_item() {
		Client client = new Client(Long.valueOf(1), "CODE", "NAME", "ALIAS", "email@gmail.com");
		
		Client updatedClient = service.update(client);

		compareWithId(client, updatedClient);
		checkStored(1);
	}
	
	@Test
	public void deleting_one_client() {
		Client preparedClient = prepareOneClient();
		
		Client addedClient = service.add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		Client deletedClient = service.delete(addedClient);
		
		compareWithId(addedClient, deletedClient);

		checkStored(0);
	}
	
	@Test
	@ExpectedException(ServiceException.class)
	public void deleting_unexistent_item_with_exception() {
		Client preparedClient = prepareOneClient();
		
		Client addedClient = service.add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		Client deletedClient = service.delete(addedClient);
		
		compareWithId(addedClient, deletedClient);

		checkStored(0);
		
		service.delete(addedClient);
	}
	
	@Test
	public void finding_one_client() {
		Client preparedClient = prepareOneClient();
		
		Client addedClient = service.add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		Client foundClient = service.findOne(addedClient);
		
		compareWithId(addedClient, foundClient);
		
		checkStored(1);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void finding_one_with_exception() {
		Client preparedClient = prepareOneClient();
		
		Client addedClient = service.add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		Client deletedClient = service.delete(addedClient);
		
		compare(addedClient, deletedClient);
		
		checkStored(0);
		
		assertNull(service.findOne(deletedClient));
	}
/*	
	@Test
	public void loading_by_sinthetic_key() {
		Client preparedClient = prepareOneClient();
		
		Client addedClient = add(preparedClient);
		
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
		Client preparedClient = prepareOneClient();
		
		Client addedClient = service.add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		Client foundClient = service.findByCode(addedClient);
		
		compareWithId(addedClient, foundClient);
		
		checkStored(1);
	}

	@Test
	@ExpectedException(ServiceException.class)
	public void finding_by_client_with_exception() {
		Client preparedClient = prepareOneClient();
		
		Client addedClient = service.add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		Client deletedClient = service.delete(addedClient);
		
		compare(addedClient, deletedClient);
		
		checkStored(0);
		
		service.findByCode(deletedClient);
	}
/*	
	@Test
	public void loading_by_natural_key() {
		Client preparedClient = prepareOneClient();
		
		Client addedClient = add(preparedClient);
		
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
		List<Client> preparedClients = prepareSomeClients();

		List<Client> addedClients = new LinkedList<Client>();

		int stored = 0;
		for (Client preparedClient: preparedClients) {
			Client addedClient = service.add(preparedClient);

			compare(preparedClient, addedClient);
			
			checkStored(++stored);

			addedClients.add(addedClient);
		}
		
		List<Client> foundClients = service.findAll();
		
		compareWithId(addedClients, foundClients);
		
		assertEquals(addedClients.size(), foundClients.size());
	}

	@Test
	public void finding_all_clients_with_restrictions() {
		List<Client> preparedClients = prepareSomeClients();

		List<Client> addedClients = new LinkedList<Client>();

		int stored = 0;
		for (Client preparedClient: preparedClients) {
			Client addedClient = service.add(preparedClient);

			compare(preparedClient, addedClient);
			
			checkStored(++stored);

			addedClients.add(addedClient);
		}
		
		Client preparedClient = prepareOneClient();
		
		List<Client> foundClients = service.findAll(preparedClient);
		
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
	
	protected List<Client> prepareSomeClients() {
		List<Client> clients = new LinkedList<Client>();
		Client client = null;
		for (int i=0; i < NUMBER_OF_GENERATED_CLIENTS; i++) {
			client = prepareClient(i);
			clients.add(client);
		}
		return clients;
	}
	
	protected Client prepareOneClient() {
		return prepareClient(1);
	}

	protected Client prepareClient(int id) {
		return new Client("CLIENT000"+id, "Banco "+id, "banco"+id, "banco"+id+"@gmail.com");
	}
	
	protected void compare(List<Client> prepared, List<Client> modified) {
		assertNotNull("entity should never be null", modified);
		for (int i = 0; i < prepared.size(); i++) {
			compare(prepared.get(i), modified.get(i));
		}
	}

	protected void compareWithId(List<Client> prepared, List<Client> modified) {
		assertNotNull("entity should never be null", modified);
		for (int i = 0; i < prepared.size(); i++) {
			compareWithId(prepared.get(i), modified.get(i));
		}
	}

	protected void compare(Client prepared, Client modified) {
		assertNotNull("entity should never be null", modified);
	}

	protected void compareWithId(Client prepared, Client modified) {
		assertNotNull("entity should never be null", modified);
	}

	protected void checkStored(long numberOfEntities) {
		assertEquals(numberOfEntities, getNumberOfObjectsStored());
	}
	
	protected long getNumberOfObjectsStored() {
		return service.count();
	}
}