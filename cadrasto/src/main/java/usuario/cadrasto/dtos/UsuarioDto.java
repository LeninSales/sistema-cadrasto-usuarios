package usuario.cadrasto.dtos;

import jakarta.validation.constraints.*;

// id, nome, idade, email (local

public record UsuarioDto(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "A idade é obrigatória")
        @Min(value = 0, message = "A idade não pode ser negativa")
        Integer idade,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Formato de email inválido")
        @Size(max = 255, message = "O email deve ter no máximo 255 caracteres")
        String email
) {}

