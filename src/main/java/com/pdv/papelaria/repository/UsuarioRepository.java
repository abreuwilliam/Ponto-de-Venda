package com.pdv.papelaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pdv.papelaria.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findAll();

     Usuario findByUsername(String username);

     Usuario  findByPassword(String password);

     Usuario  findById(int id);

     Usuario findByRole( String role);
  
}
