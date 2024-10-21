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

/**
 * Classe de teste para a funcionalidade de ingressos.
 */
public class IngressoTest {

    /**
     * Testa a criação de um ingresso e valida seus atributos.
     */
    @Test
    public void testCriarIngresso() {
        Evento evento = criarEvento();
        Ingresso ingresso = criarIngresso(evento, 100.0);

        assertNotNull(ingresso);
        assertEquals(evento.getId(), ingresso.getEventoID());
        assertEquals(100.0, ingresso.getValor(), 0.0001);
        assertTrue(ingresso.isAtivo());
    }

    /**
     * Testa o cancelamento de um ingresso.
     */
    @Test
    public void testCancelarIngresso() {
        Evento evento = criarEvento();
        Ingresso ingresso = criarIngresso(evento, 100.0);

        ingresso.setStatus(false);

        assertFalse(ingresso.isAtivo());
    }

    private Ingresso criarIngresso(Evento evento, double valor) {
        return new Ingresso(evento, valor);
    }

    private Evento criarEvento() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        return new Evento("Show de Rock", "Banda XYZ", data, 100);
    }

    /**
     * Método executado após cada teste para limpar os diretórios de eventos e usuários.
     */
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
                    deleteFilesInDirectory(subFile);
                }
            }
        }
    }
}
