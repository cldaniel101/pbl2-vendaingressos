import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Objects;

import org.junit.After;

public class UsuarioTest {

    @Test
    public void testCadastrarUsuario() {
        Usuario usuario = criarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        validarUsuario(usuario, "johndoe", "John Doe", "12345678901", "john.doe@example.com", false);
    }

    @Test
    public void testCadastrarUsuarioAdmin() {
        Usuario admin = criarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);

        validarUsuario(admin, "admin", "Admin User", "00000000000", "admin@example.com", true);
    }

    @Test
    public void testLogin() {
        Usuario usuario = criarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertTrue(usuario.login("johndoe", "senha123"));
        assertFalse(usuario.login("johndoe", "senhaErrada"));
    }

    @Test
    public void testAtualizarSenha() {
        Usuario usuario = criarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setSenha("novaSenha123");

        assertTrue(usuario.login("johndoe", "novaSenha123"));
        assertFalse(usuario.login("johndoe", "senha123"));
    }

    @Test
    public void testDadosUsuario() {
        Usuario usuario = criarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setNome("Jonathan Doe");
        usuario.setCpf("10987654321");
        usuario.setEmail("jon.doe@example.com");

        assertEquals("Jonathan Doe", usuario.getNome());
        assertEquals("10987654321", usuario.getCpf());
        assertEquals("jon.doe@example.com", usuario.getEmail());
    }

    @Test
    public void testUsuarioDuplicado() {
        Usuario usuario1 = criarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Usuario usuario2 = criarUsuario("johndoe", "senha456", "John Doe", "12345678901", "john.doe@example.com", false);

        assertEquals(usuario1, usuario2);
    }

    // Métodos auxiliares para modularizar a criação e validação de usuário

    private Usuario criarUsuario(String login, String senha, String nome, String cpf, String email, boolean isAdmin) {
        return new Usuario(login, senha, nome, cpf, email, isAdmin);
    }

    private void validarUsuario(Usuario usuario, String login, String nome, String cpf, String email, boolean isAdmin) {
        assertNotNull(usuario);
        assertEquals(login, usuario.getLogin());
        assertEquals(nome, usuario.getNome());
        assertEquals(cpf, usuario.getCpf());
        assertEquals(email, usuario.getEmail());
        assertEquals(isAdmin, usuario.isAdmin());
    }

    // Método para limpar arquivos JSON após cada teste
    @After
    public void cleanUp() {
        limparDiretorios("vendaingressos/Dados/Eventos");
        limparDiretorios("vendaingressos/Dados/Usuarios");
    }

    private void limparDiretorios(String caminho) {
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
