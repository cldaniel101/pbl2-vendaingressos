/*******************************
 Autor: Cláudio Daniel Figueredo Peruna
 Componente Curricular: EXA863 - MI - PROGRAMAÇÃO
 Concluído em: 19/10/2024
 Declaro que este código foi elaborado por mim de forma individual e não contêm nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 ********************************/

import java.util.Date;
import java.util.List;

/**
 * Classe que gerencia as operações relacionadas a eventos, usuários e compras de ingressos.
 */
public class Controller {

    /**
     * Registra um novo usuário no sistema.
     *
     * @param login o nome de usuário escolhido
     * @param senha a senha do usuário
     * @param nome o nome completo do usuário
     * @param cpf o CPF do usuário
     * @param email o endereço de e-mail do usuário
     * @param ativo define se o usuário está ativo no sistema
     * @return o objeto {@code Usuario} criado
     */
    public Usuario cadastrarUsuario(String login, String senha, String nome, String cpf, String email, boolean ativo) {
        return new Usuario(login, senha, nome, cpf, email, ativo);
    }

    /**
     * Registra um novo evento no sistema, desde que o usuário tenha permissões de administrador.
     *
     * @param admin o administrador que está cadastrando o evento
     * @param nome o nome do evento
     * @param informacoes as informações detalhadas do evento
     * @param data a data em que o evento ocorrerá
     * @param ingressos a quantidade de ingressos disponíveis para o evento
     * @param dados instância de {@code GerenciadorArquivos} responsável por salvar os dados
     * @return o objeto {@code Evento} criado
     * @throws SecurityException se o usuário não tiver permissões administrativas
     */
    public Evento cadastrarEvento(Usuario admin, String nome, String informacoes, Date data, int ingressos, GerenciadorArquivos dados) {
        if (admin.isAdmin()) {
            Evento evento = new Evento(nome, informacoes, data, ingressos);
            dados.salvarEvento(evento);
            return evento;
        } else {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
    }

    /**
     * Realiza a compra de um ingresso por um usuário para um evento específico.
     *
     * @param usuario o usuário que está efetuando a compra
     * @param eventoID o ID do evento para o qual o ingresso está sendo adquirido
     * @param dados instância de {@code GerenciadorArquivos} para persistir os dados
     * @param pagamento método de pagamento utilizado
     * @param data a data da compra do ingresso
     * @return o objeto {@code Ingresso} comprado
     */
    public Ingresso comprarIngresso(Usuario usuario, String eventoID, GerenciadorArquivos dados, String pagamento, Date data) {
        Evento eventoAtual = dados.lerEvento(eventoID);
        Ingresso ingresso = new Ingresso(eventoAtual);
        Comprovante comprovante = new Comprovante(usuario.getNomeCompleto(), usuario.getCpf(), usuario.getEmail(), ingresso, pagamento, eventoID, data);

        eventoAtual.setQuantidadeIngressos(eventoAtual.getQuantidadeIngressos() - 1);
        usuario.adicionarIngresso(ingresso);
        usuario.adicionarComprovante(comprovante);

        dados.salvarUsuario(usuario);
        dados.salvarEvento(eventoAtual);

        System.out.println("Comprovante enviado para " + usuario.getEmail());
        return ingresso;
    }

    /**
     * Cancela a compra de um ingresso feita por um usuário.
     *
     * @param usuario o usuário que deseja cancelar a compra
     * @param ingresso o ingresso que será cancelado
     * @param data a data do cancelamento
     * @param dados instância de {@code GerenciadorArquivos} para atualizar os dados
     * @return {@code true} se o cancelamento foi realizado com sucesso, {@code false} caso contrário
     */
    public boolean cancelarCompra(Usuario usuario, Ingresso ingresso, Date data, GerenciadorArquivos dados) {
        String eventoId = ingresso.getEventoID();
        Evento evento = dados.lerEvento(eventoId);
        if (usuario.removerIngresso(ingresso, evento, data)) {
            evento.setQuantidadeIngressos(evento.getQuantidadeIngressos() + 1);
            dados.salvarEvento(evento);
            dados.salvarUsuario(usuario);
            return true;
        }
        return false;
    }

    /**
     * Retorna a lista de ingressos comprados por um usuário.
     *
     * @param usuario o usuário que deseja consultar seus ingressos
     * @return a lista de ingressos adquiridos pelo usuário
     */
    public List<Ingresso> listarIngressosComprados(Usuario usuario) {
        return usuario.getQuantidadeIngressos();
    }

    /**
     * Retorna a lista de comprovantes de um usuário.
     *
     * @param usuario o usuário que deseja consultar seus comprovantes
     * @return a lista de comprovantes do usuário
     */
    public List<Comprovante> listarComprovantes(Usuario usuario) {
        return usuario.getComprovantes();
    }

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param usuario o usuário cujas informações serão atualizadas
     * @param login o novo nome de usuário
     * @param senha a nova senha
     * @param nome o novo nome completo
     * @param email o novo endereço de e-mail
     */
    public void novoCadastroUsuario(Usuario usuario, String login, String senha, String nome, String email) {
        usuario.atualizarDados(login, senha, nome, email);
    }

    /**
     * Armazena os dados de um usuário no sistema.
     *
     * @param usuario o usuário cujas informações serão salvas
     * @param dados instância de {@code GerenciadorArquivos} para persistir as informações
     */
    public void salvarDadosUsuario(Usuario usuario, GerenciadorArquivos dados) {
        dados.salvarUsuario(usuario);
    }

    /**
     * Recupera os dados de um usuário com base no CPF fornecido.
     *
     * @param cpf o CPF do usuário que será consultado
     * @param dados instância de {@code GerenciadorArquivos} para buscar as informações
     * @return o objeto {@code Usuario} correspondente ao CPF fornecido
     */
    public Usuario lerDadosUsuario(String cpf, GerenciadorArquivos dados) {
        return dados.lerUsuario(cpf);
    }

    /**
     * Armazena os dados de um evento no sistema.
     *
     * @param evento o evento a ser persistido
     * @param dados instância de {@code GerenciadorArquivos} para salvar o evento
     */
    public void salvarEvento(Evento evento, GerenciadorArquivos dados) {
        dados.salvarEvento(evento);
    }

    /**
     * Recupera os dados de um evento a partir de seu ID.
     *
     * @param eventoID o ID do evento a ser consultado
     * @param dados instância de {@code GerenciadorArquivos} para buscar o evento
     * @return o objeto {@code Evento} correspondente ao ID fornecido
     */
    public Evento lerDadosEvento(String eventoID, GerenciadorArquivos dados) {
        return dados.lerEvento(eventoID);
    }

    /**
     * Permite que um usuário faça a avaliação de um evento.
     *
     * @param evento o evento que será avaliado
     * @param usuario o usuário que está fazendo a avaliação
     * @param avaliacao a avaliação do evento
     */
    public void avaliarEvento(Evento evento, Usuario usuario, String avaliacao) {
        evento.adicionarAvaliacao(usuario.getLogin(), avaliacao);
    }
}
