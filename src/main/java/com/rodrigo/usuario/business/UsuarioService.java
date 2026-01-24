package com.rodrigo.usuario.business;

import com.rodrigo.usuario.business.converter.UsuarioConverter;
import com.rodrigo.usuario.business.dto.UsuarioDTO;
import com.rodrigo.usuario.infrastructure.entity.Usuario;
import com.rodrigo.usuario.infrastructure.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){

        Usuario usuario =usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuario = usuarioRepository.save(usuario));}
}
