package com.autobots.automanager.controles;

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

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.DocumentoSelecionador;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
    @Autowired
    private DocumentoRepositorio repositorio;
    @Autowired
    private DocumentoSelecionador selecionador;

    @GetMapping("/{id}")
    public ResponseEntity<?> obterDocumento(@PathVariable long id) {
        Documento documento = selecionador.selecionar(repositorio.findAll(), id);
        if (documento != null) {
            return ResponseEntity.ok(documento);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado.");
        }
    }

    @GetMapping("/documentos")
    public ResponseEntity<?> obterDocumentos() {
        Iterable<Documento> documentos = repositorio.findAll();
        return ResponseEntity.ok(documentos);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrarDocumento(@RequestBody Documento documento) {
        repositorio.save(documento);
        return ResponseEntity.status(HttpStatus.CREATED).body("Documento cadastrado com sucesso.");
    }

    @PutMapping("/atualizar")
    public ResponseEntity<String> atualizarDocumento(@RequestBody Documento atualizacao) {
        if (repositorio.existsById(atualizacao.getId())) {
            Documento documento = repositorio.findById(atualizacao.getId()).get();
            documento.setTipo(atualizacao.getTipo());
            documento.setNumero(atualizacao.getNumero());
            repositorio.save(documento);
            return ResponseEntity.ok("Documento atualizado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado.");
        }
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<String> excluirDocumento(@RequestBody Documento exclusao) {
        if (repositorio.existsById(exclusao.getId())) {
            Documento documento = repositorio.findById(exclusao.getId()).get();
            repositorio.delete(documento);
            return ResponseEntity.ok("Documento excluído com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento não encontrado.");
        }
    }
}
