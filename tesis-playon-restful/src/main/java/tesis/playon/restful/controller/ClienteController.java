package tesis.playon.restful.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tesis.playon.restful.domain.Cliente;
import tesis.playon.restful.service.IClienteService;

@Controller("clienteController")
public class ClienteController {

    @Resource(name = "clienteService")
    private IClienteService clienteService;

    @RequestMapping(value = "/clientes", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
    public @ResponseBody
    List<Cliente> getCliente() {
	List<Cliente> result = new ArrayList<Cliente>();
	result = clienteService.findAll();
	return result;
    }

    @RequestMapping(value = "/cliente/{nroCliente}", method = RequestMethod.GET, headers = "Accept=application/xml, application/json")
    public @ResponseBody
    Cliente getCliente(@PathVariable("nroCliente") Integer nroCliente) {
	return clienteService.findByNumeroCliente(nroCliente);
    }
}