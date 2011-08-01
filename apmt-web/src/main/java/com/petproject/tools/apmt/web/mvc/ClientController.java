package com.petproject.tools.apmt.web.mvc;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.petproject.tools.apmt.dto.Client;
import com.petproject.tools.apmt.service.base.exceptions.ServiceException;
import com.petproject.tools.apmt.service.client.ClientService;

@Controller
public class ClientController {
	
	public static String PUBLIC_ACCESS_PATH = "web";
	public static String PRIVATE_USER_ACCESS_PATH = "user";
	public static String PRIVATE_GROUP_ACCESS_PATH = "group";
	
	public static String DEFAULT_APPLICATION_NAME = "defaultApp";
	public static String DEFAULT_MODULE_NAME = "defaultModule";
	public static String DEFAULT_ACTION_NAME = "show";
	
	@Autowired
	protected ClientService service;
	
	@RequestMapping(value="/clients/new", method=RequestMethod.GET)
	public String showClientForm(Model model) {
		System.out.println("ShowClientForm - INIT");

		Client client = new Client();
		System.out.println("Client "+client+" shown");
		model.addAttribute("result", client);
		model.addAttribute("operation", "new");
		String view = "/form";
		System.out.println("ShowClientForm - END");
		return view;
	}

	@RequestMapping(value="/clients/search", method=RequestMethod.GET)
	public String showClientSearchForm(Model model) {
		System.out.println("ShowClientSearchForm - INIT");

		Client client = new Client();
		System.out.println("Client "+client+" shown");
		model.addAttribute("result", client);
		model.addAttribute("operation", "search");
		String view = "/form";
		System.out.println("ShowClientSearchForm - END");
		return view;
	}

	@RequestMapping(value="/clients", params="op_show")
	public String showClient2(@RequestParam Serializable id, Model model) {
		System.out.println("ShowClient - "+id);
		return showClient(id, model);
	}

	@RequestMapping("/clients/add")
	public String addClient(Client client, Model model) {
		System.out.println("AddClient - INIT");
		String view = null;
		try {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Client to be created... "+client);
			Client created = service.add(client);
			System.out.println("Client "+created+" added");
			model.addAttribute("result", created);
			view = "/form";
		}
		catch (ServiceException ex) {
			model.addAttribute(ex);
			view = "/error";
		}
		return view;
	}

	@RequestMapping(value="/clients", params="op_add")
	public String addClient2(Client client, Model model) {
		System.out.println("AddClient2 "+client);
		return addClient(client, model);
	}

	@RequestMapping("/clients/update")
	public String updateClient(Client client, Model model) {
		System.out.println("UpdateClient - "+client);
		String view = null;
		try {
			Client updated = service.update(client);
			System.out.println("Client "+updated+" updated");
			model.addAttribute("result", updated);
			view = "/form";
		}
		catch (ServiceException ex) {
			model.addAttribute(ex);
			view = "/error";
		}
		return view;
	}

	@RequestMapping(value="/clients/{id}", params="op_update")
	public String updateClient2(Client client, Model model) {
		System.out.println("UpdateClient2");
		return updateClient(client, model);
	}

	@RequestMapping("/clients/delete/{id}")
	public String deleteClient(@PathVariable Serializable id, Model model) {
		System.out.println("DeleteClient - "+id+" - INIT");
		String view = null;
		try {
			Client client = service.delete(new Client(Long.valueOf(id.toString())));
			System.out.println("Client "+client+" deleted");
			view = "redirect:/web/clients";
		}
		catch (ServiceException ex) {
			model.addAttribute(ex);
			view = "/error";
		}
		return view;
	}

	@RequestMapping(value="/clients/{id}", params="op_delete")
	public String deleteClient2(@RequestParam Serializable id, Model model) {
		System.out.println("DeleteClient2");
		return deleteClient(id, model);
	}

	@RequestMapping("/clients/find/{attribute}/{value}")
	public String findClient(@PathVariable String attribute, @PathVariable Object value, Model model) {
		System.out.println("FindClient by "+attribute+" with value "+value.toString());
		String view = null;
		try {
			// TODO Review the dynamic query feature
			Client client = new Client();
			Field field = Client.class.getDeclaredField(attribute);
			field.setAccessible(true);
			field.set(client, value);
			List<Client> clientList = service.findAll(client);
			System.out.println(((clientList==null)?"null":clientList.size())+" clients found");
			model.addAttribute("results", clientList);
			view = "/list";
		}
		catch (ServiceException ex) {
			model.addAttribute(ex);
			view = "/error";
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return view;
	}

	@RequestMapping(value="/clients", params="op_find")
	public String findClient2(Client client, Model model) {
		System.out.println("findClient2: "+client);
		String view = null;
		try {
			// TODO Review the dynamic query feature using QueryDSL
			List<Client> clientList = service.findAll(client);
			System.out.println(((clientList==null)?"null":clientList.size())+" clients found");
			model.addAttribute("results", clientList);
			view = "/list";
		}
		catch (ServiceException ex) {
			model.addAttribute(ex);
			view = "/error";
		}
		catch (IllegalArgumentException ex) {
			model.addAttribute(ex);
			view = "/error";
		}
		catch (SecurityException ex) {
			model.addAttribute(ex);
			view = "/error";
		}
		return view;
	}

	@RequestMapping(value="/clients", method=RequestMethod.GET)
	public String showClients(Model model) {
		System.out.println("ShowClients - INIT");
		String view = null;
		try {
			List<Client> clientList = service.findAll();
			System.out.println(((clientList==null)?"null":clientList.size())+" clients found");
			model.addAttribute("results", clientList);
			view = "/list";
		}
		catch (ServiceException ex) {
			model.addAttribute(ex);
			view = "/error";
		}
		System.out.println("ShowClients - END");
		return view;
	}

	@RequestMapping(value="/clients/{id}", method=RequestMethod.GET)
	public String showClient(@PathVariable Serializable id, Model model) {
		System.out.println("ShowClient - "+id+" - INIT");
		String view = null;
		try {
			Client client = null;
			if (id instanceof String) {
				client = service.findByCode(new Client(id.toString()));
			}
			else {
				client = service.findOne(new Client(Long.valueOf(id.toString())));
			}
			System.out.println("Client "+client+" shown");
			model.addAttribute("result", client);
			view = "/form";
		}
		catch (ServiceException ex) {
			System.out.println("Exception showing client: ");
			ex.printStackTrace();
			model.addAttribute(ex);
			view = "/error";
		}
		System.out.println("ShowClient - "+id+" - END");
		return view;
	}
}