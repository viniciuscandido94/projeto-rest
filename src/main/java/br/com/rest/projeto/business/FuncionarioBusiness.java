package br.com.rest.projeto.business;

import br.com.rest.projeto.DTO.requestDTO.FuncionarioRequestDTO;
import br.com.rest.projeto.entity.Empresa;
import br.com.rest.projeto.entity.Funcionario;
import br.com.rest.projeto.entity.Usuario;
import br.com.rest.projeto.repository.EmpresaRepository;
import br.com.rest.projeto.repository.FuncionarioRepository;
import br.com.rest.projeto.repository.ProjetoRepository;
import br.com.rest.projeto.repository.UsuarioRepository;
import br.com.rest.projeto.DTO.responseDTO.FuncionarioResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class FuncionarioBusiness {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    public List<FuncionarioResponseDTO> findByEmpresaId(String userLogado) throws Exception {
        Long idEmpresa = findIdEmpresaByUser(userLogado);
        List<Funcionario> funcionarios = funcionarioRepository.findByEmpresaId(idEmpresa);
        List<FuncionarioResponseDTO> funcionariosResponseDTOS = new ArrayList<>();

        funcionarios.forEach( func -> funcionariosResponseDTOS.add(new FuncionarioResponseDTO(func.getId(), func.getNome(), func.getTelefone())));
        funcionariosResponseDTOS.sort(new Comparator<FuncionarioResponseDTO>() {
            @Override
            public int compare(FuncionarioResponseDTO funcionarioResponseDTO, FuncionarioResponseDTO t1) {
                return Collator.getInstance().compare(funcionarioResponseDTO.getNome(), t1.getNome());
            }
        });
        return funcionariosResponseDTOS;
    }

    public FuncionarioResponseDTO cadastra(FuncionarioRequestDTO funcionarioRequestDTO, String userLogado) throws Exception {
        Long idEmpresa = findIdEmpresaByUser(userLogado);

        Optional<Empresa> optionalEmpresa = empresaRepository.findById(idEmpresa);
        if (!optionalEmpresa.isPresent()){
            throw new Exception("Nao encontrado empresa com ID: " + idEmpresa);
        }

        Empresa empresa = optionalEmpresa.get();
        Funcionario funcionario = new Funcionario(funcionarioRequestDTO.getNome(), funcionarioRequestDTO.getTelefone(), empresa);
        funcionarioRepository.save(funcionario);

        return new FuncionarioResponseDTO(funcionario.getId(), funcionario.getNome(), funcionario.getTelefone());
    }

    public FuncionarioResponseDTO atualizar(Long id, FuncionarioRequestDTO funcionarioRequestDTO, String userLogado) throws Exception {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);
        if (!optionalFuncionario.isPresent()) {
            throw new Exception("Nao encontrado funcionario atraves do ID: " + id);
        }
        Long idEmpresa = findIdEmpresaByUser(userLogado);
        Funcionario funcionario = optionalFuncionario.get();
        if (!idEmpresa.equals(funcionario.getEmpresa().getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        funcionario.setNome(funcionarioRequestDTO.getNome());
        funcionario.setTelefone(funcionarioRequestDTO.getTelefone());
        funcionarioRepository.save(funcionario);

        return new FuncionarioResponseDTO(funcionario.getId(), funcionario.getNome(), funcionario.getTelefone());
    }

    private Long findIdEmpresaByUser(String userLogado) throws Exception {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByTelefoneLogin(userLogado);
        if (!usuarioOptional.isPresent()){
            throw new Exception("Nao encontrado usuario atraves do user/telefone: " + userLogado);
        }
        return usuarioOptional.get().getEmpresa().getId();
    }
}
