package com.mhdeveloper.carrinho.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mhdeveloper.carrinho.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
