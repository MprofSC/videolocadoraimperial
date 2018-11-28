package com.sde.videoImperial.controller;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sde.videoImperial.model.Cliente;
import com.sde.videoImperial.service.ClienteService;

@Controller
@RequestMapping(value={"/clientes",""})
public class ClienteController {

	private static final String MSG_SUCESS_INSERT = "Cliente inserted successfully.";
	private static final String MSG_SUCESS_UPDATE = "Cliente successfully changed.";
	private static final String MSG_SUCESS_DELETE = "Deleted Cliente successfully.";
	private static final String MSG_ERROR = "Error.";

	@Autowired
	private ClienteService clienteService;

	@GetMapping
	public String index(Model model) {
		List<Cliente> all = clienteService.findAll();
		model.addAttribute("listCliente", all);
		return "cliente/index";
	}
	
	@GetMapping("/{id}")
	public String show(Model model, @PathVariable("id") Integer id) {
		if (id != null) {
			Cliente cliente = clienteService.findOne(id);
			model.addAttribute("cliente", cliente);
		}
		return "cliente/show";
	}

	@GetMapping(value = "/new")
	public String create(Model model, @ModelAttribute Cliente entity) {
		model.addAttribute("cliente", entity);
		return "cliente/form";
	}
	
	@PostMapping
	public String create(@Valid @ModelAttribute Cliente entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Cliente cliente = null;
		try {
			cliente = clienteService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_INSERT);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/clientes/" + cliente.getId();
	}
	
	@GetMapping("/{id}/edit")
	public String update(Model model, @PathVariable("id") Integer id) {
		try {
			if (id != null) {
				Cliente entity = clienteService.findOne(id);
				model.addAttribute("cliente", entity);
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return "cliente/form";
	}
	
	@PutMapping
	public String update(@Valid @ModelAttribute Cliente entity, BindingResult result, RedirectAttributes redirectAttributes) {
		Cliente cliente = null;
		try {
			cliente = clienteService.save(entity);
			redirectAttributes.addFlashAttribute("success", MSG_SUCESS_UPDATE);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			e.printStackTrace();
		}
		return "redirect:/clientes/" + cliente.getId();
	}
	
	@DeleteMapping("/{id}/delete")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (id != null) {
				Cliente entity = clienteService.findOne(id);
				clienteService.delete(entity);
				redirectAttributes.addFlashAttribute("success", MSG_SUCESS_DELETE);
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", MSG_ERROR);
			throw new ServiceException(e.getMessage());
		}
		return "redirect:/clientes/index";
	}

}
