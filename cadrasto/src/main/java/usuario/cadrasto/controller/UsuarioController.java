package usuario.cadrasto.controller;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import usuario.cadrasto.dtos.UsuarioDto;
import usuario.cadrasto.models.UsuarioModel;
import usuario.cadrasto.service.UsuarioService;

import java.util.List;
import java.util.UUID;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<UsuarioModel> salvaUsuario(@RequestBody @Valid UsuarioDto usuarioDto) {
        UsuarioModel user = service.salvar(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listaUsuarios() {
        List<UsuarioModel> listaUsers = service.listaTodosUsuarios();
        if (listaUsers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaUsers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizaUsuario(@PathVariable UUID id, @RequestBody @Valid UsuarioDto dto) {
        UsuarioModel usuarioAtualizado = service.atualizaUsuario(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        service.deletaUsuario(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
