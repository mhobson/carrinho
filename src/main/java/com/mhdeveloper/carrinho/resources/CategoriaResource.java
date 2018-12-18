package com.mhdeveloper.carrinho.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mhdeveloper.carrinho.domain.Categoria;
import com.mhdeveloper.carrinho.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	CategoriaService service;
	
	@GetMapping("/listar")
	public List<Categoria> listar() {
		return service.listar();
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscar(@PathVariable long id) {
		Categoria categoria = service.buscar(id);
		
		if (!service.existe(id)) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(categoria);
	}
}
