package br.com.ajufood.pedeai.service;

import br.com.ajufood.pedeai.exception.BusinessRuleException;
import br.com.ajufood.pedeai.exception.ConstraintException;
import br.com.ajufood.pedeai.exception.DataIntegrityException;
import br.com.ajufood.pedeai.exception.ObjectNotFoundException;
import br.com.ajufood.pedeai.model.ClienteModel;
import br.com.ajufood.pedeai.model.EnderecoModel;
import br.com.ajufood.pedeai.repositoty.ClienteRepository;
import br.com.ajufood.pedeai.repositoty.EnderecoRepository;
import br.com.ajufood.pedeai.rest.dto.request.ClienteAtualizarDadosRequestDTO;
import br.com.ajufood.pedeai.rest.dto.request.ClienteEnderecoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.request.ClienteRequestDTO;
import br.com.ajufood.pedeai.rest.dto.request.EnderecoRequestDTO;
import br.com.ajufood.pedeai.rest.dto.response.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço responsável pelas regras de negócio relacionadas aos clientes.
 *
 * Esta classe faz a ligação entre o Controller e o Repository, centralizando
 * validações, regras de negócio e operações de persistência.
 *
 * Exemplos:
 * clienteService.obterPorId(1);
 * clienteService.salvar(clienteModel);
 */
@Service
public class ClienteService {

    /**
     * Repositório responsável pelas operações de acesso ao banco de dados.
     */
    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Objeto responsável por converter Model em DTO e DTO em Model.
     */
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProdutoService produtoService;

//    @Autowired
//    private EnderecoService enderecoService;


    @Autowired
    private EnderecoRepository enderecoRepository;

    /**
     * Busca um cliente pelo ID.
     *
     * @param id identificador do cliente
     * @return DTO com os dados do cliente encontrado
     * @throws ObjectNotFoundException quando o cliente não for encontrado
     */
    @Transactional(readOnly = true)
    public ClienteResponseDTO obterPorId(int id) {
        ClienteModel cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Cliente com ID " + id + " não encontrado."
                ));

        return modelMapper.map(cliente, ClienteResponseDTO.class);
    }

    /**
     * Busca todos os clientes cadastrados.
     *
     * @return lista de clientes em formato DTO
     */
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> obterTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClienteResponseDTO.class))
                .toList();
    }

    /**
     * Salva um novo cliente na base de dados.
     *
     * Antes de salvar, verifica se já existe cliente cadastrado
     * com o mesmo CPF ou e-mail.
     *
     * @param clienteNovoDTO objeto contendo os dados do novo cliente
     * @return DTO com os dados do cliente salvo
     * @throws ConstraintException quando CPF ou e-mail já estiverem cadastrados
     * @throws DataIntegrityException quando ocorrer violação de integridade no banco
     */
    @Transactional
    public ClienteResponseDTO salvar(ClienteRequestDTO clienteNovoDTO) {
        try {

            ClienteModel clienteNovoModel = modelMapper.map(clienteNovoDTO, ClienteModel.class);
            validarCpfEmailParaCadastro(clienteNovoModel);
            ClienteModel clienteSalvo = clienteRepository.save(clienteNovoModel);

            return modelMapper.map(clienteSalvo, ClienteResponseDTO.class);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao salvar o cliente " + clienteNovoDTO.getNome() + ".", e
            );
        }
    }

    /**
     * Atualiza os dados de um cliente existente.
     *
     * Antes de atualizar, verifica se o cliente existe e se o CPF ou e-mail
     * informados não pertencem a outro cliente.
     *
     * @param id identificador do cliente que será atualizado
     * @param clienteAtualizadoDTO objeto com os novos dados do cliente
     * @return DTO com os dados atualizados
     * @throws ObjectNotFoundException quando o cliente não for encontrado
     * @throws ConstraintException quando CPF ou e-mail já pertencerem a outro cliente
     * @throws DataIntegrityException quando ocorrer violação de integridade no banco
     */
    @Transactional
    public ClienteResponseDTO atualizar(int id, ClienteRequestDTO clienteAtualizadoDTO) {
        try {

            ClienteModel clienteAtualizadoModel = modelMapper.map(clienteAtualizadoDTO, ClienteModel.class);
            ClienteModel clienteExistenteModel = clienteRepository.findById(id)
                    .orElseThrow(() -> new ObjectNotFoundException(
                            "Cliente com ID " + id + " não encontrado."
                    ));
            validarCpfEmailParaAtualizacao(id, clienteAtualizadoModel);

            clienteExistenteModel.setNome(clienteAtualizadoModel.getNome());
            clienteExistenteModel.setCpf(clienteAtualizadoModel.getCpf());
            clienteExistenteModel.setEmail(clienteAtualizadoModel.getEmail());
            clienteExistenteModel.setTelefone(clienteAtualizadoModel.getTelefone());

            ClienteModel clienteSalvo = clienteRepository.save(clienteExistenteModel);

            return modelMapper.map(clienteSalvo, ClienteResponseDTO.class);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao atualizar o cliente " + clienteAtualizadoDTO.getNome() + ".", e
            );
        }
    }

    /**
     * Remove um cliente da base de dados.
     *
     * @param id identificador do cliente que será removido
     * @throws ObjectNotFoundException quando o cliente não for encontrado
     * @throws DataIntegrityException quando o cliente não puder ser removido por possuir vínculos
     */
    @Transactional
    public void deletar(int id) {
        try {
            obterPorId(id);
            clienteRepository.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Não foi possível excluir o cliente, pois ele possui vínculos com outros registros.", e
            );
        }
    }

    // UC - 02 - Semana 01 - Médio
    public Page<PedidoResumoDTO> buscarHistoricoPorCliente(Integer clienteId, String status, Pageable pageable){

        if(!clienteRepository.existsById(clienteId)){
            throw new ObjectNotFoundException("O id: " + clienteId + " não foi encontrado.");
        }

        Page<PedidoResumoProjecao> paginaProjecao = clienteRepository.buscarHistoricoPorCliente(clienteId, status, pageable);

        List<ItemPedidoResumoDTO> listaItemPedido = paginaProjecao.stream()
                .map(item -> new ItemPedidoResumoDTO(
                        item.getNome(),
                        item.getQuantidade(),
                        item.getPrecoUnitario(),
                        item.getSubTotal()
                )).toList();

        Page<PedidoResumoDTO> pedidoResumo = paginaProjecao
                .map(pedido -> new PedidoResumoDTO(
                        pedido.getId(),
                        pedido.getDataHora(),
                        pedido.getStatus(),
                        pedido.getValorTotal(),
                        pedido.getEndereco(),
                        pedido.getNumero(),
                        pedido.getBairro(),
                        pedido.getCidade(),
                        listaItemPedido
                        ));


        return pedidoResumo;

    }


    // Semana 02 - Médio - Cadastrar Cliente com endereço
    @Transactional
    public ClienteResponseDTO criarClienteComEndereco(ClienteEnderecoRequestDTO dto) {

        try {

            EnderecoModel endereco = new EnderecoModel();
            endereco.setEndereco(dto.getEndereco());
            endereco.setNumero(dto.getNumero());
            endereco.setComplemento(dto.getComplemento());
            endereco.setBairro(dto.getBairro());
            endereco.setCidade(dto.getCidade());
            endereco.setEstado(dto.getEstado());
            endereco.setCep(dto.getCep());

            ClienteModel cliente = new ClienteModel();
            cliente.setNome(dto.getNome());
            cliente.setCpf(dto.getCpf());
            cliente.setEmail(dto.getEmail());
            cliente.setTelefone(dto.getTelefone());

            cliente.setEnderecos(new ArrayList<>());
            cliente.getEnderecos().add(endereco);

            endereco.setCliente(cliente);

            ClienteModel clienteSalvo = clienteRepository.save(cliente);

            ClienteResponseDTO clienteResponseDTO = modelMapper.map(clienteSalvo, ClienteResponseDTO.class);
            clienteResponseDTO.setCpf(mascararCPF(clienteSalvo.getCpf()));
            return clienteResponseDTO;

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException(
                    "Erro de integridade ao salvar o cliente " + dto.getNome() + ".", e
            );
        }
    }


    // UC 08 - Atualizar Dados do Cliente
    @Transactional
    public ClienteResponseDTO atualizaDadosCliente(int idCliente, ClienteAtualizarDadosRequestDTO dto){

        //Dando erro 500 (cpf invalido por algum motivo)

        ClienteModel cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ObjectNotFoundException("Cliente com o id: " + idCliente + " não encontrado." ));

        if(clienteRepository.existsByEmail(dto.email())){
            throw new ConstraintException("O email: '" + dto.email() + "' já está em uso.");
        }

        cliente.setNome(dto.nome());
        cliente.setTelefone(dto.telefone());
        cliente.setEmail(dto.email());

        clienteRepository.save(cliente);

        ClienteResponseDTO responseDTO = new ClienteResponseDTO();
        responseDTO.setId(cliente.getId());
        responseDTO.setCpf(mascararCPF(cliente.getCpf()));
        responseDTO.setNome(cliente.getNome());
        responseDTO.setTelefone(cliente.getTelefone());
        responseDTO.setEmail(cliente.getEmail());


        System.out.println(cliente);


        return responseDTO;

    }

    // UC 12 - Adicionar Endereco ao Cliente
    @Transactional
    public EnderecoListaAtualizadaResponseDTO adicionarEnderecoCliente(int idCliente, EnderecoRequestDTO dto, boolean padrao) {
        ClienteModel cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ObjectNotFoundException("O cliente com o id: " + idCliente + " não encontrado."));

        if (enderecoRepository.countByCliente(cliente) == 5) {
            throw new BusinessRuleException("Cada cliente deverá ter até 5 endereços.");
        }

        if (enderecoRepository.existsByClienteAndEnderecoAndCepAndNumero(cliente, dto.getEndereco(), dto.getCep(), dto.getNumero())) {
            throw new BusinessRuleException("Este endereço já existe para esse usuário.");
        }

        if (padrao) {
            cliente.getEnderecos().forEach(enderecoModel -> enderecoModel.setPadrao(false));
        }

        EnderecoModel novoEndereco = modelMapper.map(dto, EnderecoModel.class);
        novoEndereco.setPadrao(padrao);
        novoEndereco.setCliente(cliente);

        enderecoRepository.save(novoEndereco);


        List<EnderecoResponseDTO> enderecos = cliente.getEnderecos()
                .stream()
                .map(endereco -> modelMapper.map(endereco, EnderecoResponseDTO.class))
                .toList();

        return new EnderecoListaAtualizadaResponseDTO(enderecos);
    }



    // Método para mascarar CPF
    private String mascararCPF(String cpf){
        StringBuilder sb = new StringBuilder(cpf);
        sb.insert(3, ".");
        sb.insert(7, ".");
        sb.insert(11, "-");

        return sb.toString();

    }



    /**
     * Valida se CPF ou e-mail já existem antes de cadastrar um novo cliente.
     *
     * @param cliente cliente que será cadastrado
     * @throws ConstraintException quando CPF ou e-mail já estiverem cadastrados
     */
    private void validarCpfEmailParaCadastro(ClienteModel cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new ConstraintException(
                    "Já existe um cliente cadastrado com o CPF " + cliente.getCpf() + "."
            );
        }

        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new ConstraintException(
                    "Já existe um cliente cadastrado com o e-mail " + cliente.getEmail() + "."
            );
        }
    }

    /**
     * Valida se CPF ou e-mail pertencem a outro cliente durante a atualização.
     *
     * @param id id do cliente que está sendo atualizado
     * @param cliente cliente com os dados atualizados
     * @throws ConstraintException quando CPF ou e-mail já pertencerem a outro cliente
     */
    private void validarCpfEmailParaAtualizacao(int id, ClienteModel cliente) {
        clienteRepository.findByCpf(cliente.getCpf())
                .filter(clienteEncontrado -> clienteEncontrado.getId() != id)
                .ifPresent(clienteEncontrado -> {
                    throw new ConstraintException(
                            "Já existe outro cliente cadastrado com o CPF " + cliente.getCpf() + "."
                    );
                });

        clienteRepository.findByEmail(cliente.getEmail())
                .filter(clienteEncontrado -> clienteEncontrado.getId() != id)
                .ifPresent(clienteEncontrado -> {
                    throw new ConstraintException(
                            "Já existe outro cliente cadastrado com o e-mail " + cliente.getEmail() + "."
                    );
                });
    }
}