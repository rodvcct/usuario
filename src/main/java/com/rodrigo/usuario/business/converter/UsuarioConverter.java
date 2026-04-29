package com.rodrigo.usuario.business.converter;

import com.rodrigo.usuario.business.dto.EnderecoDTO;
import com.rodrigo.usuario.business.dto.TelefoneDTO;
import com.rodrigo.usuario.business.dto.UsuarioDTO;
import com.rodrigo.usuario.infrastructure.entity.Endereco;
import com.rodrigo.usuario.infrastructure.entity.Telefone;
import com.rodrigo.usuario.infrastructure.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario (UsuarioDTO usuarioDTO){
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .endereco(paraListaEndereco(usuarioDTO.getEnderecos()))
                .telefone(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecoDTOS){
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTO enderecoDTO : enderecoDTOS){
            enderecos.add(paraEndereco(enderecoDTO));
        }
        return enderecos;
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

    public List<Telefone> paraListaTelefones(List<TelefoneDTO> telefoneDTOS){
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
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecoDTO(usuarioDTO.getEndereco()))
                .telefones(paraListaTelefonesDTO(usuarioDTO.getTelefone()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecoDTOS){
        List<EnderecoDTO> enderecos = new ArrayList<>();
        for (Endereco enderecoDTO : enderecoDTOS){
            enderecos.add(paraEnderecoDTO(enderecoDTO));
        }
        return enderecos;
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

    public List<TelefoneDTO> paraListaTelefonesDTO(List<Telefone> telefoneDTOS){

        return telefoneDTOS.stream().map(this::paraTelefoneDTO).toList();

    }
    public TelefoneDTO paraTelefoneDTO(Telefone telefoneDTO){

        return TelefoneDTO.builder()
                .numero(telefoneDTO.getNumero())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Usuario entity) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : entity.getNome())
                .id(entity.getId())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : entity.getSenha())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : entity.getEmail())
                .endereco(entity.getEndereco())
                .telefone(entity.getTelefone())
                .build();
    }
}
