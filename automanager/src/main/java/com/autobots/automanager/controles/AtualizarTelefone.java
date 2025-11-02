package com.autobots.automanager.controles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/atualizar")
public class AtualizarTelefone {
    
    @Autowired
    private ClienteRepositorio repositorio;
    
    @PutMapping("/telefone/{id}")
    public void atualizarClienteTelefone(@PathVariable("id") Long id, @RequestBody Cliente cliente) {
        Cliente selecionado = repositorio.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
        selecionado.getTelefones().addAll(cliente.getTelefones());
        repositorio.save(selecionado);
    }
}