package com.mhdeveloper.carrinho.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdeveloper.carrinho.domain.Categoria;
import com.mhdeveloper.carrinho.repositories.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public List<Categoria> listar() {
		return repository.findAll();
	}
	
	public Categoria buscar(long id) {
		return repository.getOne(id);
	}

	public boolean existe(long id) {
		return repository.existsById(id);
	}
}
