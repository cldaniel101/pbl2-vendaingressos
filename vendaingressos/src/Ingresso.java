import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * A classe {@code Ingresso} representa um ingresso de evento. Cada ingresso contém
 * informações sobre o evento, preço, assento e status (ativo ou cancelado).
 */
public class Ingresso {
    private String eventoID;
    private double preco;
    private String ingressoID;
    private boolean status;

    /**
     * Constrói um ingresso com preço inicial de 0.0.
     *
     * @param evento o evento associado ao ingresso
     */
    public Ingresso(Evento evento) {
        this.eventoID = evento.getID();
        this.preco = 0.0;
        this.status = true;
        this.ingressoID = gerarId(evento);
    }

    /**
     * Constrói um ingresso com um preço específico.
     *
     * @param evento o evento associado ao ingresso
     * @param preco o preço do ingresso
     */
    public Ingresso(Evento evento, double preco) {
        this.eventoID = evento.getID();
        this.preco = preco;
        this.status = true;
        this.ingressoID = gerarId(evento);
    }

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
     * @return o preço
     */
    public double getPreco() {
        return preco;
    }

    public String getEventoID() {
        return this.eventoID;
    }

    public String getId() {
        return this.ingressoID;
    }

    /**
     * Verifica se o ingresso está ativo.
     *
     * @return {@code true} se o ingresso está ativo, {@code false} se está cancelado
     */
    public boolean isAtivo() {
        return status;
    }

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
