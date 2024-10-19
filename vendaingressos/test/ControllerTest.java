import java.util.*;
import java.io.File;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;


public class ControllerTest {

    @Test
    public void testCadastrarEventoPorAdmin() {
        Controller controller = new Controller();
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);

        assertNotNull(evento);
        assertEquals("Show de Rock", evento.getNome());
        assertEquals("Banda XYZ", evento.getDescricao());
        assertEquals(data, evento.getData());
    }

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
        String eventoid = evento.getID();

        String pagamento = "Credito";

        Ingresso ingresso = controller.comprarIngresso(usuario, eventoid, dados, pagamento, data);

        assertNotNull(ingresso);
        assertEquals(eventoid, ingresso.getEventoID());
        assertTrue(usuario.getIngressos().contains(ingresso));
    }

    @Test
    public void testCancelarCompra() {
        Controller controller = new Controller();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Evento evento =controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);
        String eventoId = evento.getID();
        String pagamento = "Credito";

        Ingresso ingresso = controller.comprarIngresso(usuario, eventoId, dados, pagamento, data);

        boolean cancelado = controller.cancelarCompra(usuario, ingresso, data, dados);
        assertTrue(cancelado);
        assertFalse(usuario.getIngressos().contains(ingresso));
    }

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

        controller.comprarIngresso(usuario, evento.getID(), dados, pagamento, data);

        List<Ingresso> ingressos = controller.listarIngressosComprados(usuario);

        assertEquals(1, ingressos.size());
    }

    @Test
    public void ArmazenamentoDadosUsuario(){
        Controller controller = new Controller();
        GerenciadorArquivos dados = new GerenciadorArquivos();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        controller.ArmazenarDadosUsuario(usuario, dados);

        Usuario user = controller.LerDadosUsuario("12345678901", dados);

        assertNotNull(user);
        assertEquals("johndoe", user.getLogin());
        assertEquals("John Doe", user.getNome());
        assertEquals("12345678901", user.getCpf());
        assertEquals("john.doe@example.com", user.getEmail());
        assertFalse(user.isAdmin());

        assertEquals(usuario, user);
    }

    @Test
    public void testNovocadastroUsuario(){
        Controller controller = new Controller();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        controller.NovoCadastroUsuario(usuario,"randomUser456", "password789", "Random User", "random.User@example.com");

        assertEquals("Random User", usuario.getNome());
        assertEquals("randomUser456", usuario.getLogin());
        assertEquals("password789", usuario.getSenha());
        assertEquals("random.User@example.com", usuario.getEmail());
    }

    @Test
    public void AvaliarEvento(){
        Controller controller = new Controller();
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);
        String avaliacao = "Evento ótimo e divertido";

        controller.AvaliarEvento(evento, usuario, avaliacao);

        Map<String, String>avaliações = evento.getAvaliacoes();

        assertEquals(1, avaliações.size());
    }

    @Test
    public void ArmazenamentoDadosEvento(){
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

        controller.ArmazenarEvento(evento, dados);
        Evento Testeevento = controller.LerDadosEvento(evento.getID(), dados);

        assertNotNull(Testeevento);
        assertEquals("Show de Rock", Testeevento.getNome());
        assertEquals(data, Testeevento.getData());
        assertEquals(100, Testeevento.getIngressos());

        assertEquals(Testeevento, evento);
    }

    @Test
    public void testListarcomprovantes() {
        Controller controller = new Controller();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        GerenciadorArquivos dados = new GerenciadorArquivos();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data, 100, dados);
        String pagamento = "Credito";

        controller.comprarIngresso(usuario, evento.getID(), dados, pagamento, data);
        controller.comprarIngresso(usuario, evento.getID(), dados, pagamento, data);

        List<Comprovante> comprovantes = controller.listarComprovantes(usuario);

        assertEquals(2, comprovantes.size());
    }

    @After
    public void cleanUp() {
        File directoryEvento = new File("vendaingressos/Dados/Eventos");
        deleteFilesInDirectory(directoryEvento);

        File directoryUser = new File("vendaingressos/Dados/Usuarios");
        deleteFilesInDirectory(directoryUser);
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
