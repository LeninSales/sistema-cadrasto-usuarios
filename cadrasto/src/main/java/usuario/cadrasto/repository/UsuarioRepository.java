package usuario.cadrasto.repository;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import usuario.cadrasto.models.UsuarioModel;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {

    boolean existsByEmail(
            @NotBlank(message = "O email é obrigatório")
            @Email(message = "Formato de email inválido")
            @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
            String email);
}
