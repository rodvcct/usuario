package com.rodrigo.usuario.business.converter;

import com.rodrigo.usuario.business.dto.EnderecoDTO;
import com.rodrigo.usuario.business.dto.TelefoneDTO;
import com.rodrigo.usuario.business.dto.UsuarioDTO;
import com.rodrigo.usuario.infrastructure.entity.Endereco;
import com.rodrigo.usuario.infrastructure.entity.Telefone;
import com.rodrigo.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario (UsuarioDTO usuarioDTO){

        return Usuario.builder()
                .nome(UsuarioDTO.getNome())
                .email(UsuarioDTO.getEmail())
                .senha(UsuarioDTO.getSenha())
                .endereco(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefone(paraTelefone(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){

        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){

        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .cep(enderecoDTO.getCep())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .numero(enderecoDTO.getNumero())
                .build();
    }

    public List<Telefone> paraTelefone(List<TelefoneDTO> telefoneDTOS){

        return telefoneDTOS.stream().map(this::paraTelefone).toList();

    }
    public Telefone paraTelefone(TelefoneDTO telefoneDTO){

        return Telefone.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO (Usuario usuarioDTO){

        return UsuarioDTO.builder()
                .nome(UsuarioDTO.getNome())
                .email(UsuarioDTO.getEmail())
                .senha(UsuarioDTO.getSenha())
                .endereco(paraListaEnderecoDTO(usuarioDTO.getEnderecos()))
                .telefone(paraTelefoneDTO(usuarioDTO.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS){

        return enderecoDTOS.stream().map(this::paraEnderecoDTO).toList();
    }

    public EnderecoDTO paraEnderecoDTO(Endereco enderecoDTO){

        return EnderecoDTO.builder()
                .rua(enderecoDTO.getRua())
                .cep(enderecoDTO.getCep())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .numero(enderecoDTO.getNumero())
                .build();
    }

    public List<TelefoneDTO> paraTelefoneDTO(List<Telefone> telefoneDTOS){

        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();

    }
    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO){

        return TelefoneDTO.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }
}
