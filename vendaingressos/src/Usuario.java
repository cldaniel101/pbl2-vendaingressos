import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * A classe {@code Usuario} representa um usuário do sistema de venda de ingressos.
 * Cada usuário possui um login, senha, nome completo, CPF, e-mail, status de administrador,
 * e uma lista de ingressos associados.
 */
public class Usuario {
    private String username;
    private String password;
    private String fullName;
    private String cpf;
    private String email;
    private boolean isAdmin;
    private List<Ingresso> ingressos;
    private List<Comprovante> comprovantes;

    /**
     * Construtor da classe {@code Usuario}.
     *
     * @param username o nome de usuário
     * @param password a senha do usuário
     * @param fullName o nome completo do usuário
     * @param cpf o CPF do usuário
     * @param email o e-mail do usuário
     * @param isAdmin se o usuário é administrador
     */
    public Usuario(String username, String password, String fullName, String cpf, String email, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.isAdmin = isAdmin;
        this.ingressos = new ArrayList<>();
        this.comprovantes = new ArrayList<>();
    }

    public String getLogin() {
        return username;
    }

    public String getNome() {
        return fullName;
    }

    public String getSenha() {
        return password;
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

    public void setLogin(String username) {
        this.username = username;
    }

    public void setSenha(String password) {
        this.password = password;
    }

    public void setNome(String fullName) {
        this.fullName = fullName;
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

    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public void adicionarIngresso(Ingresso ingresso) {
        this.ingressos.add(ingresso);
    }

    public List<Ingresso> getIngressos() {
        return this.ingressos;
    }

    public boolean removeIngresso(Ingresso ingresso, Evento evento, Date dataAtual) {
        if (evento.getData().before(dataAtual)) {
            ingressos.remove(ingresso);
            return true;
        }
        return false;
    }

    public void atualizarDados(String username, String password, String fullName, String email) {
        setLogin(username);
        setSenha(password);
        setNome(fullName);
        setEmail(email);
    }

    public void adicionarComprovante(Comprovante comprovante) {
        this.comprovantes.add(comprovante);
    }

    public List<Comprovante> getComprovantes() {
        return this.comprovantes;
    }

    @Override
    public int hashCode() {
        return cpf != null ? cpf.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario other = (Usuario) obj;
        return Objects.equals(cpf, other.cpf);
    }
}
