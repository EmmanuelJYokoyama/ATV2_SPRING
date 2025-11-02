package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.modelo.ClienteAtualizador;
import com.autobots.automanager.modelo.ClienteSelecionador;
import com.autobots.automanager.repositorios.ClienteRepositorio;

@RestController
@RequestMapping("/cliente")
public class ClienteControle {
    @Autowired
    private ClienteRepositorio repositorio;
    @Autowired
    private ClienteSelecionador selecionador;

    @GetMapping("/{id}")
    public ResponseEntity<?> obterCliente(@PathVariable long id) {
        List<Cliente> clientes = repositorio.findAll();
        Cliente cliente = selecionador.selecionar(clientes, id);
        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
    }

    @GetMapping("/clientes")
    public ResponseEntity<?> obterClientes() {
        List<Cliente> clientes = repositorio.findAll();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarCliente(@RequestBody Cliente cliente) {
        repositorio.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente cadastrado com sucesso.");
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarCliente(@RequestBody Cliente atualizacao) {
        if (repositorio.existsById(atualizacao.getId())) {
            Cliente cliente = repositorio.findById(atualizacao.getId()).get();
            ClienteAtualizador atualizador = new ClienteAtualizador();
            atualizador.atualizar(cliente, atualizacao);
            repositorio.save(cliente);
            return ResponseEntity.ok("Cliente atualizado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluirCliente(@RequestBody Cliente exclusao) {
        if (repositorio.existsById(exclusao.getId())) {
            Cliente cliente = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(cliente);
            return ResponseEntity.ok("Cliente excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado.");
        }
    }
}
