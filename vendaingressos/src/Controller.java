import java.util.Date;
import java.util.List;


/**
 * Classe responsável pelo controle de operações relacionadas a eventos,
 * usuários e ingressos.
 */
public class Controller {


    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param username o nome de usuário do novo usuário
     * @param senha a senha do usuário
     * @param nome o nome completo do usuário
     * @param cpf o CPF do usuário
     * @param email o e-mail do usuário
     * @param ativo indica se o usuário está ativo
     * @return o objeto {@code Usuario} criado
     */
    public Usuario cadastrarUsuario(String username, String senha, String nome, String cpf, String email, boolean ativo) {
        return new Usuario(username, senha, nome, cpf, email, ativo);
        // OBS: Salvar o usuário no sistema
        //OBS : Login de usuario faltando
    }

    /**
     * Cadastra um novo evento no sistema, desde que o usuário seja administrador.
     *
     * @param admin o usuário que está tentando cadastrar o evento
     * @param nome o nome do evento
     * @param descricao a descrição do evento
     * @param data a data do evento
     * @return o objeto {@code Evento} criado
     * @throws SecurityException se o usuário não for administrador
     */

    public Evento cadastrarEvento(Usuario admin, String nome, String descricao, Date data, int ingressos, GerenciadorArquivos dados)  {
        if (admin.isAdmin()) {
            Evento evento = new Evento(nome, descricao, data, ingressos);
            dados.ArmazenarEvento(evento);
            return evento;
        } else {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
    }

    /**
     * Compra um ingresso para um usuário.
     *
     * @param usuario o usuário que está comprando o ingresso
     * @param eventoID o ID do evento para o qual o ingresso está sendo comprado
     * @param dados a instância de GerenciadorArquivos para salvar os dados
     * @param data a data da compra do ingresso
     * @return o objeto {@code Ingresso} comprado
     */
    public Ingresso comprarIngresso(Usuario usuario, String eventoID, GerenciadorArquivos dados, String pagamento,Date data) {
        Evento EventoAtual = dados.LerArquivoEvento(eventoID);
        Ingresso ingresso = new Ingresso(EventoAtual);
        Comprovante comprovante = new Comprovante(usuario.getNome(), usuario.getCpf(), usuario.getEmail(), ingresso, pagamento,eventoID, data);

        EventoAtual.setIngressos(EventoAtual.getIngressos() - 1);
        usuario.adicionarIngresso(ingresso);
        usuario.adicionarComprovante(comprovante);

        dados.ArmazenamentoUser(usuario);
        dados.ArmazenarEvento(EventoAtual);

        System.out.println("Comprovante enviado para " + usuario.getEmail());
        return ingresso;
    }

    /**
     * Cancela a compra de um ingresso de um usuário.
     *
     * @param usuario o usuário que está cancelando a compra
     * @param ingresso o ingresso que está sendo cancelado
     * @return {@code true} se o cancelamento foi bem-sucedido, {@code false} caso contrário
     */
    public boolean cancelarCompra(Usuario usuario, Ingresso ingresso, Date data, GerenciadorArquivos dados) {
        String eventoId = ingresso.getEventoID();
        Evento evento = dados.LerArquivoEvento(eventoId);
        if(usuario.removeIngresso(ingresso, evento, data)) {
            evento.setIngressos(evento.getIngressos()+1);
            dados.ArmazenarEvento(evento);
            dados.ArmazenamentoUser(usuario);
        }

        return usuario.removeIngresso(ingresso, evento, data);
    }

    /**
     * Lista os ingressos comprados por um usuário.
     *
     * @param usuario o usuário cujos ingressos serão listados
     * @return a lista de ingressos comprados pelo usuário
     */
    public List<Ingresso> listarIngressosComprados(Usuario usuario) {
        return usuario.getIngressos();
    }

    public List<Comprovante> listarComprovantes(Usuario usuario){
        return usuario.getComprovantes();
    }

    // ATUALIZAÇÕES DO CÓDIGO

    public void NovoCadastroUsuario(Usuario usuario, String username, String senha, String nome, String email) {
        usuario.atualizarDados(username, senha, nome, email);
    }

    public void ArmazenarDadosUsuario(Usuario usuario, GerenciadorArquivos dados){
        dados.ArmazenamentoUser(usuario);
    }

    public Usuario LerDadosUsuario(String cpf, GerenciadorArquivos dados){
        return dados.LerArquivoUsuario(cpf);
    }

    public void ArmazenarEvento(Evento evento, GerenciadorArquivos dados){
        dados.ArmazenarEvento(evento);
    }

    public Evento LerDadosEvento(String eventoID, GerenciadorArquivos dados){
        return dados.LerArquivoEvento(eventoID);
    }

    // verificar se o usuário participou do evento
    public void AvaliarEvento(Evento evento, Usuario usuario, String avaliacao){
        evento.adicionarAvaliacao(usuario.getLogin(), avaliacao);
    }
}