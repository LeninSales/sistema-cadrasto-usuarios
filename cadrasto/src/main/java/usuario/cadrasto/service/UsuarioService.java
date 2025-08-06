package usuario.cadrasto.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import usuario.cadrasto.dtos.UsuarioDto;
import usuario.cadrasto.models.UsuarioModel;
import usuario.cadrasto.repository.UsuarioRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    public UsuarioModel salvar( UsuarioDto userDto){
        if (repository.existsByEmail(userDto.email())) {
            throw new IllegalArgumentException("Email já cadastrado.");
        }

      UsuarioModel user = new UsuarioModel();
        BeanUtils.copyProperties(userDto, user);
        return repository.save(user);
    }

    public List<UsuarioModel> listaTodosUsuarios(){
        return repository.findAll();
    }

    public UsuarioModel atualizaUsuario(UUID id, UsuarioDto dto) {
        UsuarioModel user = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado."));
        BeanUtils.copyProperties(dto, user, "id");
        return repository.save(user);
    }

    public void deletaUsuario(UUID id){
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado para exclusão.");
        }
        repository.deleteById(id);
    }

}