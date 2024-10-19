import java.util.*;
import java.io.File;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testes da classe Controller, que incluem funcionalidades como cadastro de eventos,
 * compra de ingressos, e gerenciamento de usuários.
 */
public class ControllerTest {

    /**
     * Testa o cadastro de um evento realizado por um administrador.
     */
    @Test
    public void testCadastrarEventoPorAdmin() {
        Controller controller = new Controller();
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);

        assertNotNull(evento);
        assertEquals("Show de Rock", evento.getNome());
        assertEquals("Banda XYZ", evento.getDescricao());
        assertEquals(data, evento.getData());
    }

    /**
     * Testa a tentativa de cadastro de um evento por um usuário comum.
     * O sistema deve lançar uma exceção de segurança.
     */
    @Test
    public void testCadastrarEventoPorUsuarioComum() {
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Exception exception = assertThrows(SecurityException.class, () -> {
            controller.cadastrarEvento(usuario, "Peça de Teatro", "Grupo ABC", data, 100, dados);
        });

        assertEquals("Somente administradores podem cadastrar eventos.", exception.getMessage());
    }

    /**
     * Testa a compra de um ingresso por um usuário comum.
     */
    @Test
    public void testComprarIngresso() {
        Controller controller = new Controller();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);
        String eventoId = evento.getId();

        String pagamento = "Credito";
        Ingresso ingresso = controller.comprarIngresso(usuario, eventoId, dados, pagamento, data);

        assertNotNull(ingresso);
        assertEquals(eventoId, ingresso.getEventoID());
        assertTrue(usuario.getQuantidadeIngressos().contains(ingresso));
    }

    /**
     * Testa o cancelamento de uma compra de ingresso por um usuário.
     */
    @Test
    public void testCancelarCompra() {
        Controller controller = new Controller();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);
        String eventoId = evento.getId();
        String pagamento = "Credito";

        Ingresso ingresso = controller.comprarIngresso(usuario, eventoId, dados, pagamento, data);

        boolean cancelado = controller.cancelarCompra(usuario, ingresso, data, dados);
        assertTrue(cancelado);
        assertFalse(usuario.getQuantidadeIngressos().contains(ingresso));
    }

    /**
     * Testa a listagem de eventos disponíveis.
     */
    @Test
    public void testListarEventosDisponiveis() {
        Controller controller = new Controller();
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2034, Calendar.SEPTEMBER, 10);
        Date data1 = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2034, Calendar.SEPTEMBER, 15);
        Date data2 = calendar2.getTime();

        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data1, 100, dados);
        controller.cadastrarEvento(admin, "Peça de Teatro", "Grupo ABC", data2, 100, dados);

        List<String> eventos = dados.listarEventosDisponiveis();
        assertEquals(2, eventos.size());
    }

    /**
     * Testa a listagem de ingressos comprados por um usuário.
     */
    @Test
    public void testListarIngressosComprados() {
        Controller controller = new Controller();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);
        String pagamento = "Credito";

        controller.comprarIngresso(usuario, evento.getId(), dados, pagamento, data);
        List<Ingresso> ingressos = controller.listarIngressosComprados(usuario);

        assertEquals(1, ingressos.size());
    }

    /**
     * Testa o armazenamento e leitura dos dados de um usuário.
     */
    @Test
    public void ArmazenamentoDadosUsuario() {
        Controller controller = new Controller();
        GerenciadorArquivos dados = new GerenciadorArquivos();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        controller.salvarDadosUsuario(usuario, dados);
        Usuario login = controller.lerDadosUsuario("12345678901", dados);

        assertNotNull(login);
        assertEquals("johndoe", login.getLogin());
        assertEquals("John Doe", login.getNomeCompleto());
        assertEquals("12345678901", login.getCpf());
        assertEquals("john.doe@example.com", login.getEmail());
        assertFalse(login.isAdmin());

        assertEquals(usuario, login);
    }

    /**
     * Testa a criação de um novo cadastro de usuário.
     */
    @Test
    public void testNovocadastroUsuario() {
        Controller controller = new Controller();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        controller.novoCadastroUsuario(usuario, "randomUser456", "senha789", "Random User", "random.User@example.com");

        assertEquals("Random User", usuario.getNomeCompleto());
        assertEquals("randomUser456", usuario.getLogin());
        assertEquals("senha789", usuario.getSenha());
        assertEquals("random.User@example.com", usuario.getEmail());
    }

    /**
     * Testa a avaliação de um evento por um usuário.
     */
    @Test
    public void avaliarEvento() {
        Controller controller = new Controller();
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);
        String avaliacao = "Evento ótimo e divertido";

        controller.avaliarEvento(evento, usuario, avaliacao);

        Map<String, String>avaliações = evento.getAvaliacoes();

        assertEquals(1, avaliações.size());
    }

    /**
     * Testa o armazenamento e leitura dos dados de um evento.
     */
    @Test
    public void testArmazenamentoDadosEvento() {
        Controller controller = new Controller();
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);
        controller.salvarEvento(evento, dados);

        Evento testeevento = controller.lerDadosEvento(evento.getId(), dados);

        assertNotNull(testeevento);
        assertEquals("Show de Rock", testeevento.getNome());
        assertEquals(data, testeevento.getData());
        assertEquals(100, testeevento.getQuantidadeIngressos());
        assertEquals(testeevento, evento);
    }

    /**
     * Testa a listagem de comprovantes de compras feitas por um usuário.
     */
    @Test
    public void testListarComprovantes() {
        Controller controller = new Controller();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10); // Ajustado para 2 argumentos
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);
        String pagamento = "Credito";

        controller.comprarIngresso(usuario, evento.getId(), dados, pagamento, data);
        controller.comprarIngresso(usuario, evento.getId(), dados, pagamento, data);

        List<Comprovante> comprovantes = controller.listarComprovantes(usuario);
        assertEquals(2, comprovantes.size());
    }

    /**
     * Limpa os arquivos gerados após a execução dos testes.
     */
    @After
    public void cleanUp() {
        File directoryEvento = new File("vendaingressos/Dados/Eventos");
        deleteFilesInDirectory(directoryEvento);

        File directoryUser = new File("vendaingressos/Dados/Usuarios");
        deleteFilesInDirectory(directoryUser);
    }

    /**
     * Método auxiliar para deletar arquivos em um diretório.
     *
     * @param directory O diretório onde os arquivos serão deletados.
     */
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
