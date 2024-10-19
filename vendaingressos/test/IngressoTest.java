import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class IngressoTest {

    @Test
    public void testCriarIngresso() {
        Evento evento = criarEvento();
        Ingresso ingresso = criarIngresso(evento, 100.0);

        assertNotNull(ingresso);
        assertEquals(evento.getID(), ingresso.getEventoID());
        assertEquals(100.0, ingresso.getPreco(), 0.0001);
        assertTrue(ingresso.isAtivo());
    }

    @Test
    public void testCancelarIngresso() {
        Evento evento = criarEvento();
        Ingresso ingresso = criarIngresso(evento, 100.0);

        ingresso.setStatus(false);

        assertFalse(ingresso.isAtivo());
    }

    private Ingresso criarIngresso(Evento evento, double preco) {
        return new Ingresso(evento, preco);
    }

    private Evento criarEvento() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        return new Evento("Show de Rock", "Banda XYZ", data, 100);
    }

    @After
    public void cleanUp() {
        limparDiretorio("vendaingressos/Dados/Eventos");
        limparDiretorio("vendaingressos/Dados/Usuarios");
    }

    private void limparDiretorio(String caminho) {
        File directory = new File(caminho);
        deleteFilesInDirectory(directory);
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
}
