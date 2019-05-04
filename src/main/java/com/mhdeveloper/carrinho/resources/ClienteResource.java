package com.mhdeveloper.carrinho.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mhdeveloper.carrinho.domain.Cliente;
import com.mhdeveloper.carrinho.dto.ClienteDTO;
import com.mhdeveloper.carrinho.dto.ClienteNewDTO;
import com.mhdeveloper.carrinho.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	ClienteService service;
	
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> listar() {
		List<Cliente> lista = service.listar();
		List<ClienteDTO> listaDTO = lista.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok(listaDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> buscar(@PathVariable Integer id) {
		Cliente cliente = service.buscar(id);
		return ResponseEntity.ok(cliente);
	}
	
	@PostMapping
	public ResponseEntity<Void> adicionar(@Valid @RequestBody ClienteNewDTO objDTO) {
		Cliente obj = service.fromDTO(objDTO);
		obj = service.inserir(obj);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}
