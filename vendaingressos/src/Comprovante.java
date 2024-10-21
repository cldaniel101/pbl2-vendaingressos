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
