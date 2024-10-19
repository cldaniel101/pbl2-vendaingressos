import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * A classe {@code Ingresso} define um ingresso para um evento, contendo
 * informações como o ID do evento, preço, e status de validade.
 */
public class Ingresso {
    private String eventoID;
    private double valor;
    private String ingressoID;
    private boolean status;

    /**
     * Cria um ingresso associado a um evento com preço inicial de 0.0.
     *
     * @param evento evento ao qual o ingresso está vinculado
     */
    public Ingresso(Evento evento) {
        this.eventoID = evento.getId();
        this.valor = 0.0;
        this.status = true;
        this.ingressoID = gerarId(evento);
    }

    /**
     * Cria um ingresso com preço específico associado a um evento.
     *
     * @param evento evento ao qual o ingresso está vinculado
     * @param valor valor do ingresso
     */
    public Ingresso(Evento evento, double valor) {
        this.eventoID = evento.getId();
        this.valor = valor;
        this.status = true;
        this.ingressoID = gerarId(evento);
    }

    /**
     * Gera um ID único para o ingresso, baseado na data do evento e um UUID.
     *
     * @param evento evento ao qual o ingresso está vinculado
     * @return string contendo o ID gerado para o ingresso
     */
    private String gerarId(Evento evento) {
        Date data = evento.getData();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String dataEventoString = sdf.format(data);
        String uuidString = UUID.randomUUID().toString();
        return dataEventoString + "-" + uuidString;
    }

    /**
     * Retorna o preço do ingresso.
     *
     * @return valor do ingresso
     */
    public double getValor() {
        return valor;
    }

    /**
     * Retorna o ID do evento associado ao ingresso.
     *
     * @return ID do evento
     */
    public String getEventoID() {
        return this.eventoID;
    }

    /**
     * Retorna o ID único do ingresso.
     *
     * @return ID do ingresso
     */
    public String getId() {
        return this.ingressoID;
    }

    /**
     * Verifica se o ingresso está ativo.
     *
     * @return {@code true} se o ingresso estiver ativo, {@code false} caso contrário
     */
    public boolean isAtivo() {
        return status;
    }

    /**
     * Define o status do ingresso como ativo ou cancelado.
     *
     * @param status novo status do ingresso
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((eventoID == null) ? 0 : eventoID.hashCode());
        result = prime * result + ((ingressoID == null) ? 0 : ingressoID.hashCode());
        result = prime * result + (status ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ingresso other = (Ingresso) obj;
        if (eventoID == null) {
            if (other.eventoID != null) return false;
        } else if (!eventoID.equals(other.eventoID)) return false;
        if (ingressoID == null) {
            if (other.ingressoID != null) return false;
        } else if (!ingressoID.equals(other.ingressoID)) return false;
        return status == other.status;
    }
}
