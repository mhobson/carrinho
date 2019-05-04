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
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado. Id: " + id + ". Tipo: " + Categoria.class.getSimpleName()));
	}

	public Categoria inserir(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}

	public Categoria atualizar(Categoria updatedObj) {
		Categoria obj = buscar(updatedObj.getId());
		updateData(obj, updatedObj);
		return repository.save(obj);
	}

	public void excluir(Integer id) {
		buscar(id);
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos associados");
		}
	}
	
	private void updateData(Categoria obj, Categoria updatedObj) {
		obj.setNome(updatedObj.getNome());
	}
	
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
}
