import java.util.Date;

public class Comprovante {
    private String nomeCompleto;
    private String cpf;
    private String email;
    private Ingresso ingresso;
    private String eventoID;
    private String pagamento; 
    private Date data;

    public Comprovante(String nomeCompleto, String cpf, String email, Ingresso ingresso, String pagamento, String eventoID, Date data) {
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.ingresso = ingresso;
        this.pagamento = pagamento;
        this.eventoID = eventoID;
        this.data = data;
    }

    public String getCpf() {
        return cpf;
    }

    public String getnomeCompleto() {
        return nomeCompleto;
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

    public String getPagamento() {
        return pagamento;
    }
}
