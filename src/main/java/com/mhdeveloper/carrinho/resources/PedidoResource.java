package com.mhdeveloper.carrinho.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mhdeveloper.carrinho.domain.Pedido;
import com.mhdeveloper.carrinho.services.PedidoService;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {
	
	@Autowired
	PedidoService service;
	
	@GetMapping
	public List<Pedido> listar() {
		return service.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pedido> buscar(@PathVariable Long id) {
		Pedido pedido = service.buscar(id);
		return ResponseEntity.ok(pedido);
	}
}
