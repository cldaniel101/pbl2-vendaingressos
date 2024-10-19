import java.util.Date;

public class Comprovante {
    private String fullName;
    private String cpf;
    private String email;
    private Ingresso ingresso;
    private String eventoID;
    private String pagamento; // Alterado para minúsculo
    private Date data;

    public Comprovante(String fullName, String cpf, String email, Ingresso ingresso, String pagamento, String eventoID, Date data) {
        this.fullName = fullName;
        this.cpf = cpf;
        this.email = email;
        this.ingresso = ingresso;
        this.pagamento = pagamento; // Alterado para minúsculo
        this.eventoID = eventoID;
        this.data = data;
    }

    public String getCpf() {
        return cpf;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public Ingresso getIngresso() {
        return ingresso;
    }

    public String getEventoID() {
        return eventoID;
    }

    public Date getData() {
        return data;
    }

    public String getPagamento() { // Renomeado para getPagamento
        return pagamento;
    }
}
