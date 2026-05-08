package com.rodrigo.usuario.business;

import com.rodrigo.usuario.business.converter.UsuarioConverter;
import com.rodrigo.usuario.business.dto.EnderecoDTO;
import com.rodrigo.usuario.business.dto.TelefoneDTO;
import com.rodrigo.usuario.business.dto.UsuarioDTO;
import com.rodrigo.usuario.infrastructure.entity.Endereco;
import com.rodrigo.usuario.infrastructure.entity.Telefone;
import com.rodrigo.usuario.infrastructure.entity.Usuario;
import com.rodrigo.usuario.infrastructure.exceptions.ConflictException;
import com.rodrigo.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.rodrigo.usuario.infrastructure.repository.EnderecoRepository;
import com.rodrigo.usuario.infrastructure.repository.TelefoneRepository;
import com.rodrigo.usuario.infrastructure.repository.UsuarioRepository;
import com.rodrigo.usuario.infrastructure.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;


    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuario = usuarioRepository.save(usuario));
    }


    public void emailExiste(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email já cadastrado" + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado", e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {

        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        try {
            return usuarioConverter.paraUsuarioDTO(
                    usuarioRepository.findByEmail(email)
                            .orElseThrow(
                                    () -> new ResourceNotFoundException("Email não encontrado" + email)
                            )
            );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Email não encontrado " + email);
        }
    }

        public void deletaUsuarioPorEmail (String email){

            usuarioRepository.deleteByEmail(email);
        }

        public UsuarioDTO atualizarDadosUsuario (String token, UsuarioDTO dto){
            //Aqui buscamos o email do usuario através do token(tirar a obrigatioriedade do email)
            String email = jwtUtil.extrairEmailDoToken(token.substring(7));
            //Criptografia de senha
            dto.setSenha(dto.getSenha() != null ? passwordEncoder.encode(dto.getSenha()) : null);
            //Busca os dados do usuario no banco de dados
            Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                    new ResourceNotFoundException("Email não localizado"));
            //Mesclou os dados que recebeu na requisição DTO com os dados
            Usuario usuario = usuarioConverter.updateUsuario(dto, usuarioEntity);
            //Coloquei criptografia na senha caso ela não tivesse
            usuario.setSenha(passwordEncoder.encode(usuario.getPassword()));
            //Salvou os dados do usuario convertido e depois pegou o retorno e converteu para usuarioDTO

            return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
        }
        public EnderecoDTO atualizaEndereco (Long idEndereco, EnderecoDTO enderecoDTO){

            Endereco entity = enderecoRepository.findById(idEndereco).orElseThrow(() ->
                    new ResourceNotFoundException("Id não encontrado" + idEndereco));
            Endereco endereco = usuarioConverter.updateEndereco(enderecoDTO, entity);

            return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
        }

        public TelefoneDTO atualizaTelefone (Long idTelefone, TelefoneDTO dto){
            Telefone entity = telefoneRepository.findById(idTelefone).orElseThrow(() ->
                    new ResourceNotFoundException("Id não encontrado " + idTelefone));
            Telefone telefone = usuarioConverter.updateTelefone(dto, entity);

            return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));

        }

        public EnderecoDTO cadastraEndereco(String token, EnderecoDTO dto){

        String email = jwtUtil.extrairEmailDoToken(token.substring(7));
        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFoundException("Email não localizado" + email));

        Endereco endereco = usuarioConverter.paraEnderecoEntity(dto, usuario.getId());
        Endereco enderecoEntity = enderecoRepository.save(endereco);
        return usuarioConverter.paraEnderecoDTO(enderecoEntity);
        }

        public TelefoneDTO cadastraTelefone(String token, TelefoneDTO dto){

            String email = jwtUtil.extrairEmailDoToken(token.substring(7));
            Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(()->
                    new ResourceNotFoundException("Email não localizado" + email));

            Telefone telefone = usuarioConverter.paraTelefoneEntity(dto, usuario.getId());
            return usuarioConverter.paraTelefoneDTO(
                    telefoneRepository.save(telefone)
            );
        }



    }


