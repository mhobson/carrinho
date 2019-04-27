package com.mhdeveloper.carrinho.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mhdeveloper.carrinho.domain.Categoria;
import com.mhdeveloper.carrinho.dto.CategoriaDTO;
import com.mhdeveloper.carrinho.repositories.CategoriaRepository;
import com.mhdeveloper.carrinho.services.exceptions.DataIntegrityException;
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
				"Objeto não encontrado. Id: " + id + ". Tipo: " + Categoria.class.getSimpleName()));
	}

	public Categoria inserir(Categoria categoria) {
		categoria.setId(null);
		return repository.save(categoria);
	}

	public Categoria atualizar(Categoria categoria) {
		buscar(categoria.getId());
		return repository.save(categoria);
	}

	public void excluir(Long id) {
		buscar(id);
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos associados");
		}
	}
	
	public Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
}
