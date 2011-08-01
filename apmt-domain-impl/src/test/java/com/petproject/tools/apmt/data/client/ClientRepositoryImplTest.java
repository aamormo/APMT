package com.petproject.tools.apmt.data.client;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.annotation.ExpectedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.petproject.tools.apmt.data.client.ClientRepository;
import com.petproject.tools.apmt.domain.client.ClientModel;
import com.petproject.tools.apmt.domain.client.ClientModelImpl;
import com.petproject.tools.apmt.dto.Client;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class ClientRepositoryImplTest {

	@Autowired private ClientRepository repository;
	@Autowired private ClientModel clientModel = null;
	
	private static final int NUMBER_OF_GENERATED_CLIENTS = 10;
	
	@Test
	public void add_one_client() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl addedClient = add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void add_repeated_client_with_exception() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl addedClient = add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);

		ClientModelImpl repeatedClient = prepareOneClient();
		add(repeatedClient);
	}

	private ClientModelImpl add(ClientModelImpl repeatedClient) {
		return repository.save(repeatedClient);
	}

	@Test
	public void add_some_clients() {
		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		List<ClientModelImpl> addedClients = add(preparedClients);
		
		compare(preparedClients, addedClients);
		
		checkStored(preparedClients.size());
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void add_some_repeated_clients_with_exception() {
		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		List<ClientModelImpl> addedClients = add(preparedClients);
		
		compare(preparedClients, addedClients);
		
		checkStored(preparedClients.size());

		List<ClientModelImpl> repeatedClients = prepareSomeClients();
		add(repeatedClients);
	}

	private List<ClientModelImpl> add(List<ClientModelImpl> repeatedClient) {
		return repository.save(repeatedClient);
	}

	@Test
	public void add_and_flush_one_client() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl addedClient = addAndFlush(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void add_and_flush_one_repeated_client_with_exception() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl addedClient = addAndFlush(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);

		ClientModelImpl repeatedClient = prepareOneClient();
		
		addAndFlush(repeatedClient);
	}

	private ClientModelImpl addAndFlush(ClientModelImpl repeatedClient) {
		return repository.saveAndFlush(repeatedClient);
	}

	@Test
	public void update_one_client() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl updatedClient = update(preparedClient);
		
		compare(preparedClient, updatedClient);
		
		checkStored(1);
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void update_repeated_client_with_exception() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl updatedClient = update(preparedClient);
		
		compare(preparedClient, updatedClient);
		
		checkStored(1);

		ClientModelImpl repeatedClient = prepareOneClient();
		update(repeatedClient);
	}

	private ClientModelImpl update(ClientModelImpl repeatedClient) {
		return repository.save(repeatedClient);
	}

	@Test
	public void update_some_clients() {
		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		List<ClientModelImpl> updatedClients = update(preparedClients);
		
		compare(preparedClients, updatedClients);
		
		checkStored(preparedClients.size());
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void update_some_repeated_clients_with_exception() {
		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		List<ClientModelImpl> updatedClients = update(preparedClients);
		
		compare(preparedClients, updatedClients);
		
		checkStored(preparedClients.size());

		List<ClientModelImpl> repeatedClients = prepareSomeClients();
		update(repeatedClients);
	}

	private List<ClientModelImpl> update(List<ClientModelImpl> repeatedClient) {
		return repository.save(repeatedClient);
	}

	@Test
	public void update_and_flush_one_client() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl updatedClient = updateAndFlush(preparedClient);
		
		compare(preparedClient, updatedClient);
		
		checkStored(1);
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void update_and_flush_one_repeated_client_with_exception() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl updatedClient = updateAndFlush(preparedClient);
		
		compare(preparedClient, updatedClient);
		
		checkStored(1);

		ClientModelImpl repeatedClient = prepareOneClient();
		
		updateAndFlush(repeatedClient);
	}

	private ClientModelImpl updateAndFlush(ClientModelImpl repeatedClient) {
		return repository.saveAndFlush(repeatedClient);
	}

	@Test
	public void delete_one_client() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl addedClient = add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		ClientModelImpl deletedClient = delete(addedClient);

		compare(preparedClient, deletedClient);
		
		checkStored(0);
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void delete_repeated_client_with_exception() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl addedClient = add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);

		ClientModelImpl deletedClient = delete(addedClient);

		compare(preparedClient, deletedClient);
		
		checkStored(0);

		delete(((Client)deletedClient.getAsDataTransferObject()).getId());
	}

	private ClientModelImpl delete(ClientModelImpl repeatedClient) {
		repository.delete(repeatedClient);
		return repeatedClient;
	}
	
	private void delete(Long id) {
		repository.delete(id);
	}
	
	@Test
	public void delete_some_clients() {
		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		List<ClientModelImpl> addedClients = add(preparedClients);
		
		compare(preparedClients, addedClients);
		
		checkStored(preparedClients.size());

		List<ClientModelImpl> deletedClients = delete(addedClients);

		compare(preparedClients, deletedClients);
		
		checkStored(0);
	}

	@Test
	@ExpectedException(DataAccessException.class)
	public void delete_some_repeated_clients_with_exception() {
		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		List<ClientModelImpl> addedClients = add(preparedClients);
		
		compare(preparedClients, addedClients);
		
		checkStored(preparedClients.size());

		List<ClientModelImpl> deletedClients = delete(addedClients);

		compare(preparedClients, deletedClients);
		
		checkStored(0);
		
		delete(((Client)deletedClients.get(0).getAsDataTransferObject()).getId());
	}

	private List<ClientModelImpl> delete(List<ClientModelImpl> repeatedClients) {
		repository.delete(repeatedClients);
		return repeatedClients;
	}

	@Test
	public void delete_all_clients() {
		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		List<ClientModelImpl> addedClients = add(preparedClients);
		
		compare(preparedClients, addedClients);
		
		checkStored(preparedClients.size());

		deleteAll();

		checkStored(0);
	}

	private void deleteAll() {
		repository.deleteAll();
	}

/*

	@Test
	public void testDeleteInBatch() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteLong() {
		fail("Not yet implemented");
	}

*/

	@Test
	public void count_of_empty_table() {
		long clientsCount = count();
		assertTrue(clientsCount == 0);
	}

	@Test
	public void count_of_filled_table() {

		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		add(preparedClients);
		
		long clientsCount = count();
		assertTrue(clientsCount == preparedClients.size());
	}
	
	private long count() {
		return repository.count();
	}

	@Test
	public void client_not_exists() {
		assertFalse(exists(Long.valueOf("100")));
	}

	@Test
	public void client_exists() {
		ClientModelImpl preparedClient = prepareOneClient();
		
		ClientModelImpl addedClient = add(preparedClient);
		
		compare(preparedClient, addedClient);
		
		checkStored(1);
		
		assertTrue(exists(Long.valueOf(((Client)addedClient.getAsDataTransferObject()).getId())));
	}
	
	private boolean exists(Long id) {
		return repository.exists(id);
	}

	@Test
	public void find_all_clients() {
		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		List<ClientModelImpl> addedClients = add(preparedClients);
		
		compare(preparedClients, addedClients);
		
		checkStored(preparedClients.size());
		
		List<ClientModelImpl> foundClients = findAll();
		
		assertTrue(preparedClients.size() == foundClients.size());
	}
	
	private List<ClientModelImpl> findAll() {
		return repository.findAll();
	}

	@Test
	public void find_one_client() {
		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		List<ClientModelImpl> addedClients = add(preparedClients);
		
		compare(preparedClients, addedClients);
		
		checkStored(preparedClients.size());
		
		
		ClientModelImpl foundClient = findOne(Long.valueOf(((Client)addedClients.get(0).getAsDataTransferObject()).getId()));
		
		assertNotNull(foundClient);
	}

	public ClientModelImpl findOne(Long id) {
		return repository.findOne(id);
	}

	@Test
	public void find_client_by_code() {
		List<ClientModelImpl> preparedClients = prepareSomeClients();
		
		List<ClientModelImpl> addedClients = add(preparedClients);
		
		compare(preparedClients, addedClients);
		
		checkStored(preparedClients.size());
		
		
		List<ClientModelImpl> foundClients = findByCode(((Client)addedClients.get(0).getAsDataTransferObject()).getCode());
		
		assertNotNull(foundClients);
		assertTrue(foundClients.size()==1);
	}
	
	private List<ClientModelImpl> findByCode(String code) {
		return repository.findByCode(code);
	}

/*
	@Test
	public void testFindAllSort() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllPageable() {
		fail("Not yet implemented");
	}
*/
	
	protected ClientModelImpl prepareOneClient() {
		Client client = prepareClientDTO(1);
		return (ClientModelImpl)prepareClientDomainObject(client);
	}

	protected List<ClientModelImpl> prepareSomeClients() {
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
	
	protected List<ClientModelImpl> prepareClientDomainObjects(List<Client> clients) {
		List<ClientModelImpl> clientDomainObjects = new LinkedList<ClientModelImpl>();
		for (Client client:clients) {
			clientDomainObjects.add((ClientModelImpl)clientModel.createDomainObject(client));
		}
		return clientDomainObjects;
	}

	protected ClientModelImpl prepareClientDomainObject(Client client) {
		return (ClientModelImpl)clientModel.createDomainObject(client);
	}

	protected void compare(List<ClientModelImpl> prepared, List<ClientModelImpl> modified) {
		for (int i = 0; i < prepared.size(); i++) {
			compare(prepared.get(i), modified.get(i));
		}
		assertNotNull("entity should never be null", modified);
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
		return repository.count();
	}
}