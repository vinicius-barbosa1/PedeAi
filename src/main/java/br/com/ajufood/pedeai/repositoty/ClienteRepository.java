package br.com.ajufood.pedeai.repositoty;

import br.com.ajufood.pedeai.model.ClienteModel;
import br.com.ajufood.pedeai.model.PedidoModel;
import br.com.ajufood.pedeai.rest.dto.response.PedidoResumoDTO;
import br.com.ajufood.pedeai.rest.dto.response.PedidoResumoProjecao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

/**
 * Repositório responsável pelas operações de acesso aos dados de clientes.
 *
 * Estende {@link JpaRepository}, herdando métodos prontos como
 * save, findById, findAll, deleteById, entre outros.
 *
 * Exemplos:
 * clienteRepository.findByCpf("12345678901");
 * clienteRepository.findByNomeContainingIgnoreCase("maria");
 */
@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Integer> {

    // =========================================================
    // 1. QUERY METHODS
    // O Spring Data JPA cria a consulta com base no nome do método.
    // =========================================================

    /**
     * Busca um cliente pelo CPF.
     *
     * O Spring entende o método como:
     * buscar ClienteModel onde o atributo cpf seja igual ao valor informado.
     *
     * @param cpf CPF do cliente
     * @return cliente encontrado, caso exista
     */
    Optional<ClienteModel> findByCpf(String cpf);

    /**
     * Busca um cliente pelo e-mail.
     *
     * @param email e-mail do cliente
     * @return cliente encontrado, caso exista
     */
    Optional<ClienteModel> findByEmail(String email);

    /**
     * Busca clientes cujo nome contenha o texto informado, ignorando maiúsculas e minúsculas.
     *
     * @param nome parte do nome do cliente
     * @return lista de clientes encontrados
     */
    List<ClienteModel> findByNomeContainingIgnoreCase(String nome);

    /**
     * Verifica se já existe cliente cadastrado com o CPF informado.
     *
     * @param cpf CPF do cliente
     * @return true se existir, false caso contrário
     */
    boolean existsByCpf(String cpf);

    /**
     * Verifica se já existe cliente cadastrado com o e-mail informado.
     *
     * @param email e-mail do cliente
     * @return true se existir, false caso contrário
     */
    boolean existsByEmail(String email);


    // =========================================================
    // 2. JPQL
    // Usa o nome da classe Java e os atributos do Model.
    // Não usa diretamente o nome da tabela do banco.
    // =========================================================

    /**
     * Busca clientes pelo nome usando JPQL.
     *
     * Em JPQL usamos {@code ClienteModel} e {@code nome},
     * e não diretamente a tabela {@code cliente}.
     *
     * @param nome parte do nome do cliente
     * @return lista de clientes encontrados
     */
    @Query(value = """
           SELECT c
           FROM ClienteModel c
           WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
           """)
    List<ClienteModel> buscarPorNomeJpql(String nome);

    /**
     * Busca cliente por CPF usando JPQL.
     *
     * @param cpf CPF do cliente
     * @return cliente encontrado, caso exista
     */
    @Query(value = """
           SELECT c
           FROM ClienteModel c
           WHERE c.cpf = :cpf
           """)
    Optional<ClienteModel> buscarPorCpfJpql(String cpf);

    /**
     * Busca clientes pelo telefone usando JPQL.
     *
     * @param telefone telefone do cliente
     * @return lista de clientes encontrados
     */
    @Query(value = """
           SELECT c
           FROM ClienteModel c
           WHERE c.telefone = :telefone
           """)
    List<ClienteModel> buscarPorTelefoneJpql(String telefone);


    // =========================================================
    // 3. SQL NATIVO
    // Usa o nome real da tabela e das colunas no banco de dados.
    // =========================================================

    /**
     * Busca todos os clientes usando SQL nativo.
     *
     * Em SQL nativo usamos diretamente a tabela {@code cliente}
     * e suas colunas reais.
     *
     * @return lista de clientes encontrados
     */
    @Query(value = """
           SELECT *
           FROM cliente
           ORDER BY nome
           """, nativeQuery = true)
    List<ClienteModel> buscarTodosSqlNativo();

    /**
     * Busca cliente por CPF usando SQL nativo.
     *
     * @param cpf CPF do cliente
     * @return cliente encontrado, caso exista
     */
    @Query(value = """
           SELECT *
           FROM cliente
           WHERE cpf = :cpf
           ORDER BY nome
           """, nativeQuery = true)
    Optional<ClienteModel> buscarPorCpfSqlNativo(String cpf);

    /**
     * Busca clientes pelo nome usando SQL nativo.
     *
     * @param nome parte do nome do cliente
     * @return lista de clientes encontrados
     */
    @Query(value = """
           SELECT *
           FROM cliente
           WHERE LOWER(nome) LIKE LOWER(CONCAT('%', :nome, '%'))
           ORDER BY nome
           """, nativeQuery = true)
    List<ClienteModel> buscarPorNomeSqlNativo(String nome);

    /**
     * Conta a quantidade total de clientes cadastrados usando SQL nativo.
     *
     * @return quantidade de clientes
     */
    @Query(value = """
           SELECT COUNT(*)
           FROM cliente
           """, nativeQuery = true)
    Long contarClientesSqlNativo();


    // UC - 02 - Semana 01 - Médio
    @Query(value = """
    SELECT
        p.id as id, p.data_hora as dataHora, p.valor_total as valorTotal, p.status as status,
        e.endereco as endereco, e.numero as numero, e.bairro as bairro, e.cidade as cidade,
        pr.nome as nome,
        ip.quantidade as quantidade, ip.preco_unitario as precoUnitario, ip.sub_total as subTotal
    FROM pedido p
    JOIN endereco e ON p.endereco_id = e.id
    JOIN itens_pedido ip ON p.id = ip.pedido_id
    JOIN produto pr ON ip.produto_id = pr.id

    WHERE p.cliente_id = :clienteId
        AND (:status IS NULL OR LOWER(p.status) = LOWER(:status))
""", nativeQuery = true)
    Page<PedidoResumoProjecao> buscarHistoricoPorCliente(
            @Param("clienteId") Integer clienteId,
            @Param("status") String status,
            Pageable pageable
    );
}