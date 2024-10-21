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

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Objects;

import org.junit.After;

/**
 * Classe de teste para validar as operações da classe Usuario, incluindo 
 * cadastro, login, atualização de senha e manipulação de dados pessoais.
 */
public class UsuarioTest {

    /**
     * Testa o cadastro de um usuário comum.
     */
    @Test
    public void testCadastrarUsuario() {
        Usuario usuario = criarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        validarUsuario(usuario, "johndoe", "John Doe", "12345678901", "john.doe@example.com", false);
    }

    /**
     * Testa o cadastro de um administrador.
     */
    @Test
    public void testCadastrarUsuarioAdmin() {
        Usuario admin = criarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        validarUsuario(admin, "admin", "Admin User", "00000000000", "admin@example.com", true);
    }

    /**
     * Testa o login de um usuário com as credenciais corretas e incorretas.
     */
    @Test
    public void testLogin() {
        Usuario usuario = criarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertTrue(usuario.login("johndoe", "senha123"));
        assertFalse(usuario.login("johndoe", "senhaErrada"));
    }

    /**
     * Testa a atualização de senha do usuário.
     */
    @Test
    public void testAtualizarSenha() {
        Usuario usuario = criarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setSenha("novaSenha123");

        assertTrue(usuario.login("johndoe", "novaSenha123"));
        assertFalse(usuario.login("johndoe", "senha123"));
    }

    /**
     * Testa a atualização dos dados pessoais do usuário.
     */
    @Test
    public void testDadosUsuario() {
        Usuario usuario = criarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setNomeCompleto("Jonathan Doe");
        usuario.setCpf("10987654321");
        usuario.setEmail("jon.doe@example.com");

        assertEquals("Jonathan Doe", usuario.getNomeCompleto());
        assertEquals("10987654321", usuario.getCpf());
        assertEquals("jon.doe@example.com", usuario.getEmail());
    }

    /**
     * Testa se o sistema permite o cadastro de dois usuários com o mesmo login e CPF.
     */
    @Test
    public void testUsuarioDuplicado() {
        Usuario usuario1 = criarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Usuario usuario2 = criarUsuario("johndoe", "senha456", "John Doe", "12345678901", "john.doe@example.com", false);

        assertEquals(usuario1, usuario2);
    }

    /**
     * Método auxiliar para criar um novo usuário.
     */
    private Usuario criarUsuario(String login, String senha, String nome, String cpf, String email, boolean isAdmin) {
        return new Usuario(login, senha, nome, cpf, email, isAdmin);
    }

    /**
     * Método auxiliar para validar os dados de um usuário.
     */
    private void validarUsuario(Usuario usuario, String login, String nome, String cpf, String email, boolean isAdmin) {
        assertNotNull(usuario);
        assertEquals(login, usuario.getLogin());
        assertEquals(nome, usuario.getNomeCompleto());
        assertEquals(cpf, usuario.getCpf());
        assertEquals(email, usuario.getEmail());
        assertEquals(isAdmin, usuario.isAdmin());
    }

    /**
     * Limpa arquivos de dados após cada teste para evitar dados residuais.
     */
    @After
    public void cleanUp() {
        limparDiretorios("vendaingressos/Dados/Eventos");
        limparDiretorios("vendaingressos/Dados/Usuarios");
    }

    /**
     * Remove arquivos JSON dos diretórios especificados.
     */
    private void limparDiretorios(String caminho) {
        File directory = new File(caminho);
        deleteFilesInDirectory(directory);
    }

    /**
     * Remove recursivamente arquivos JSON de um diretório.
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
