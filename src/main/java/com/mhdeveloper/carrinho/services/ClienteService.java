package com.mhdeveloper.carrinho.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mhdeveloper.carrinho.domain.Cidade;
import com.mhdeveloper.carrinho.domain.Cliente;
import com.mhdeveloper.carrinho.domain.Endereco;
import com.mhdeveloper.carrinho.domain.enums.TipoCliente;
import com.mhdeveloper.carrinho.dto.ClienteDTO;
import com.mhdeveloper.carrinho.dto.ClienteNewDTO;
import com.mhdeveloper.carrinho.repositories.ClienteRepository;
import com.mhdeveloper.carrinho.repositories.EnderecoRepository;
import com.mhdeveloper.carrinho.services.exceptions.DataIntegrityException;
import com.mhdeveloper.carrinho.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public List<Cliente> listar() {
		return repository.findAll();
	}
	
	public Cliente buscar(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getSimpleName()));
	}
	
	@Transactional
	public Cliente inserir(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente atualizar(Cliente updatedObj) {
		Cliente obj = buscar(updatedObj.getId());
		updateData(obj, updatedObj);
		return repository.save(obj);
	}

	public void excluir(Integer id) {
		buscar(id);
		try {
			repository.deleteById(id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir um cliente que possui dados associados");
		}
	}
	
	private void updateData(Cliente obj, Cliente updatedObj) {
		obj.setNome(updatedObj.getNome());
		obj.setEmail(updatedObj.getEmail());
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
		
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		
		return cli;
	}
}
