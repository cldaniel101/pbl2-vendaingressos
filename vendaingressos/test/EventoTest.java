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
import java.io.File;
import java.util.Calendar;
import java.util.Objects;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Classe de teste para a funcionalidade de eventos.
 */
public class EventoTest {

    /**
     * Testa a criação de um evento e valida seus atributos.
     */
    @Test
    public void testCriarEvento() {
        Evento evento = criarEvento("Show de Rock", "Banda XYZ", 2024, Calendar.SEPTEMBER, 10, 100);

        assertNotNull(evento);
        assertEquals("Show de Rock", evento.getNome());
        assertEquals("Banda XYZ", evento.getDescricao());
        assertEquals(100, evento.getQuantidadeIngressos());
        assertEquals(getDate(2024, Calendar.SEPTEMBER, 10), evento.getData());
    }

    /**
     * Testa se o evento está ativo em uma data futura.
     */
    @Test
    public void testEventoAtivo() {
        Evento evento = criarEvento("Show de Rock", "Banda XYZ", 2033, Calendar.SEPTEMBER, 10, 100);

        assertTrue(evento.isAtivo(getDate(2033, Calendar.SEPTEMBER, 10)));
    }

    /**
     * Testa se o evento está inativo em uma data passada.
     */
    @Test
    public void testEventoInativo() {
        Evento evento = criarEvento("Show de Rock", "Banda XYZ", 2023, Calendar.JANUARY, 10, 100);

        assertFalse(evento.isAtivo(getDate(2024, Calendar.AUGUST, 10)));
    }

    /**
     * Testa o armazenamento de dados de um evento.
     */
    @Test
    public void armazenarDadosEvento() {
        Controller controller = new Controller();
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", getDate(2024, Calendar.SEPTEMBER, 10), 100, dados);

        controller.salvarEvento(evento, dados);
    }

    private Evento criarEvento(String nome, String informacoes, int ano, int mes, int dia, int ingressos) {
        Date data = getDate(ano, mes, dia);
        return new Evento(nome, informacoes, data, ingressos);
    }

    private Date getDate(int ano, int mes, int dia) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(ano, mes, dia, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private void deleteFilesInDirectory(File directory) {
        if (directory.isDirectory()) {
            for (File subFile : Objects.requireNonNull(directory.listFiles())) {
                if (subFile.isFile() && subFile.getName().endsWith(".json")) {
                    subFile.delete();
                } else if (subFile.isDirectory()) {
                    deleteFilesInDirectory(subFile);
                }
            }
        }
    }

    @After
    public void cleanUp() {
        deleteFilesInDirectory(new File("vendaingressos/Dados/Eventos"));
        deleteFilesInDirectory(new File("vendaingressos/Dados/Usuarios"));
    }
}
