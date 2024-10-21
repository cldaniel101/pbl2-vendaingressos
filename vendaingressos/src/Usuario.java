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

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Representa um usuário do sistema de venda de ingressos.
 * Um usuário possui login, senha, nome completo, CPF, e-mail, 
 * status de administrador, além de uma lista de ingressos e comprovantes.
 */
public class Usuario {
    private String login;
    private String senha;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private boolean isAdmin;
    private List<Ingresso> ingressos;
    private List<Comprovante> comprovantes;

    /**
     * Construtor da classe Usuario, inicializa os atributos do usuário.
     *
     * @param login o login do usuário
     * @param senha a senha do usuário
     * @param nomeCompleto o nome completo do usuário
     * @param cpf o CPF do usuário
     * @param email o e-mail do usuário
     * @param isAdmin indica se o usuário possui permissões de administrador
     */
    public Usuario(String login, String senha, String nomeCompleto, String cpf, String email, boolean isAdmin) {
        this.login = login;
        this.senha = senha;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.isAdmin = isAdmin;
        this.ingressos = new ArrayList<>();
        this.comprovantes = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getSenha() {
        return senha;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * Verifica se o login e senha fornecidos correspondem ao usuário.
     *
     * @param login o login inserido
     * @param senha a senha inserida
     * @return true se as credenciais forem válidas, caso contrário false
     */
    public boolean login(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    /**
     * Adiciona um ingresso à lista de ingressos do usuário.
     *
     * @param ingresso o ingresso a ser adicionado
     */
    public void adicionarIngresso(Ingresso ingresso) {
        this.ingressos.add(ingresso);
    }

    /**
     * Retorna a lista de ingressos do usuário.
     *
     * @return lista de ingressos
     */
    public List<Ingresso> getQuantidadeIngressos() {
        return this.ingressos;
    }

    /**
     * Remove um ingresso da lista de ingressos, se o evento já ocorreu.
     *
     * @param ingresso o ingresso a ser removido
     * @param evento o evento relacionado ao ingresso
     * @param dataAtual a data atual para comparação
     * @return true se o ingresso foi removido, caso contrário false
     */
    public boolean removerIngresso(Ingresso ingresso, Evento evento, Date dataAtual) {
        if (evento.getData().before(dataAtual)) {
            ingressos.remove(ingresso);
            return true;
        }
        return false;
    }

    /**
     * Atualiza os dados principais do usuário.
     *
     * @param login o novo login
     * @param senha a nova senha
     * @param nomeCompleto o novo nome completo
     * @param email o novo e-mail
     */
    public void atualizarDados(String login, String senha, String nomeCompleto, String email) {
        setLogin(login);
        setSenha(senha);
        setNomeCompleto(nomeCompleto);
        setEmail(email);
    }

    /**
     * Adiciona um comprovante à lista de comprovantes do usuário.
     *
     * @param comprovante o comprovante a ser adicionado
     */
    public void adicionarComprovante(Comprovante comprovante) {
        this.comprovantes.add(comprovante);
    }

    /**
     * Retorna a lista de comprovantes do usuário.
     *
     * @return lista de comprovantes
     */
    public List<Comprovante> getComprovantes() {
        return this.comprovantes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario other = (Usuario) obj;
        return Objects.equals(cpf, other.cpf);
    }
}
