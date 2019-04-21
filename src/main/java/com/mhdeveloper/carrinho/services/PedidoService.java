package com.mhdeveloper.carrinho.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mhdeveloper.carrinho.domain.Pedido;
import com.mhdeveloper.carrinho.repositories.PedidoRepository;
import com.mhdeveloper.carrinho.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repository;
	
	public List<Pedido> listar() {
		return repository.findAll();
	}
	
	public Pedido buscar(Long id) {
		Optional<Pedido> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
