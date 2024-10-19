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

public class EventoTest {

    @Test
    public void testCriarEvento() {
        Evento evento = criarEvento("Show de Rock", "Banda XYZ", 2024, Calendar.SEPTEMBER, 10, 100);

        assertNotNull(evento);
        assertEquals("Show de Rock", evento.getNome());
        assertEquals("Banda XYZ", evento.getDescricao());
        assertEquals(100, evento.getIngressos());
        assertEquals(getDate(2024, Calendar.SEPTEMBER, 10), evento.getData());
    }

    @Test
    public void testEventoAtivo() {
        Evento evento = criarEvento("Show de Rock", "Banda XYZ", 2033, Calendar.SEPTEMBER, 10, 100);

        assertTrue(evento.isAtivo(getDate(2033, Calendar.SEPTEMBER, 10)));
    }

    @Test
    public void testEventoInativo() {
        Evento evento = criarEvento("Show de Rock", "Banda XYZ", 2023, Calendar.JANUARY, 10, 100);

        assertFalse(evento.isAtivo(getDate(2024, Calendar.AUGUST, 10)));
    }

    @Test
    public void ArmazenamentoDadosEvento() {
        Controller controller = new Controller();
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", getDate(2024, Calendar.SEPTEMBER, 10), 100, dados);

        controller.ArmazenarEvento(evento, dados);
    }

    private Evento criarEvento(String nome, String descricao, int ano, int mes, int dia, int ingressos) {
        Date data = getDate(ano, mes, dia);
        return new Evento(nome, descricao, data, ingressos);
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
                    deleteFilesInDirectory(subFile); // Recursivamente deletar arquivos em subdiretórios
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
