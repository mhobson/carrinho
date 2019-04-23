package com.mhdeveloper.carrinho.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdeveloper.carrinho.domain.Categoria;
import com.mhdeveloper.carrinho.repositories.CategoriaRepository;
import com.mhdeveloper.carrinho.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	public List<Categoria> listar() {
		return repository.findAll();
	}
	
	public Categoria buscar(Long id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public Categoria inserir(Categoria categoria) {
		categoria.setId(null);
		return repository.save(categoria);
	}

	public Categoria atualizar(Categoria categoria) {
		buscar(categoria.getId());
		return repository.save(categoria);
	}
}
