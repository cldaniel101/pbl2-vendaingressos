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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A classe {@code Evento} representa um evento disponível para a venda de ingressos.
 * Cada evento possui um nome, informação, data e uma lista de assentos disponíveis.
 */
public class Evento {
    private String id;
    private String nome;
    private Date data;
    private int quantidadeIngressos;
    private String informacoes;
    private Map<String, String> avaliacoes;

    /**
     * Construtor da classe {@code Evento}.
     *
     * @param nome o nome do evento
     * @param informacoes a informação do evento
     * @param data a data em que o evento ocorrerá
     * @param ingressos a quantidade de ingressos disponíveis para o evento
     */
    public Evento(String nome, String informacoes, Date data, int ingressos) {
        this.id = gerarId(data, nome);
        this.nome = nome;
        this.informacoes = informacoes;
        this.data = ajustarData(data);
        this.quantidadeIngressos = ingressos;
        this.avaliacoes = new HashMap<>();
    }

    /**
     * Verifica se o evento está ativo com base na data atual.
     *
     * @param data a data atual para comparação
     * @return true se o evento ainda não tiver ocorrido, false caso contrário
     */
    public boolean isAtivo(Date data) {
        return !this.data.before(data);
    }

    /**
     * Adiciona uma avaliação ao evento.
     *
     * @param usuario o nome do usuário que está avaliando
     * @param avaliacao a avaliação do evento
     */
    public void adicionarAvaliacao(String usuario, String avaliacao) {
        avaliacoes.put(usuario, avaliacao);
    }

    /**
     * Exibe todas as avaliações do evento.
     */
    public void exibirAvaliacoes() {
        for (Map.Entry<String, String> usuario : avaliacoes.entrySet()) {
            System.out.println("Usuário: " + usuario.getKey() + " - Avaliação: " + usuario.getValue());
        }
    }

    /**
     * Ajusta a data do evento para zerar as horas, minutos, segundos e milissegundos.
     *
     * @param data a data original do evento
     * @return a data ajustada
     */
    private Date ajustarData(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Gera um ID único para o evento com base na data e nome do evento.
     *
     * @param data a data do evento
     * @param nome o nome do evento
     * @return o ID gerado
     */
    private String gerarId(Date data, String nome) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String dataEventoString = sdf.format(data);
        return dataEventoString + "-" + nome;
    }

    // Getters e setters

    public Map<String, String> getAvaliacoes() {
        return avaliacoes;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return informacoes;
    }

    public Date getData() {
        return data;
    }

    public int getQuantidadeIngressos() {
        return quantidadeIngressos;
    }

    public void setQuantidadeIngressos(int ingressos) {
        this.quantidadeIngressos = ingressos;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Evento other = (Evento) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
