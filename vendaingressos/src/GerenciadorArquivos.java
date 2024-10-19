import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Classe responsável pelo gerenciamento de arquivos do sistema.
 * Contém métodos para salvar e recuperar dados de eventos e usuários, bem como listar eventos disponíveis.
 */
public class GerenciadorArquivos {

    /**
     * Salva os dados de um evento em um arquivo JSON.
     * Se o diretório onde o arquivo deve ser salvo não existir, ele será criado.
     *
     * @param evento O evento a ser salvo.
     */
    public void salvarEvento(Evento evento) {
        Gson gsonFile = new Gson();
        String jsonFile = gsonFile.toJson(evento);
        String eventoID = evento.getId();

        String caminhoDiretorio = "vendaingressos" + File.separator + "Dados" + File.separator + "Eventos";
        String caminhoArquivo = caminhoDiretorio + File.separator + eventoID + ".json";

        File diretorio = new File(caminhoDiretorio);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            writer.write(jsonFile);
            System.out.println("Dados armazenados com sucesso!");
        } catch (IOException erro) {
            erro.printStackTrace();
        }
    }

    /**
     * Lê os dados de um evento a partir de um arquivo JSON.
     * Retorna o evento correspondente ou null se o arquivo não for encontrado ou houver erro na leitura.
     *
     * @param eventoid O ID do evento a ser lido.
     * @return O evento recuperado ou null caso ocorra algum erro.
     */
    public Evento lerEvento(String eventoid) {
        Gson gson = new Gson();
        String caminhoArquivo = "vendaingressos" + File.separator + "Dados" + File.separator + "Eventos" + File.separator + eventoid + ".json";

        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
            return null;
        }

        try (FileReader reader = new FileReader(caminhoArquivo)) {
            Evento evento = gson.fromJson(reader, Evento.class);
            System.out.println("Dados recuperados com sucesso!");
            return evento;
        } catch (IOException | JsonSyntaxException erro) {
            erro.printStackTrace();
            return null;
        }
    }

    /**
     * Salva os dados de um usuário em um arquivo JSON.
     * Se o diretório onde o arquivo deve ser salvo não existir, ele será criado.
     *
     * @param usuario O usuário a ser salvo.
     */
    public void salvarUsuario(Usuario usuario) {
        Gson gsonFile = new Gson();
        String jsonFile = gsonFile.toJson(usuario);
        String UserCPF = usuario.getCpf();

        String caminhoDiretorio = "vendaingressos" + File.separator + "Dados" + File.separator + "Usuarios";
        String caminhoArquivo = caminhoDiretorio + File.separator + UserCPF + ".json";

        File diretorio = new File(caminhoDiretorio);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            writer.write(jsonFile);
            System.out.println("Dados armazenados com sucesso!");
        } catch (IOException erro) {
            erro.printStackTrace();
        }
    }

    /**
     * Lê os dados de um usuário a partir de um arquivo JSON.
     * Retorna o usuário correspondente ou null se o arquivo não for encontrado ou houver erro na leitura.
     *
     * @param cpf O CPF do usuário a ser lido.
     * @return O usuário recuperado ou null caso ocorra algum erro.
     */
    public Usuario lerUsuario(String cpf) {
        Gson gson = new Gson();
        String caminhoArquivo = "vendaingressos" + File.separator + "Dados" + File.separator + "Usuarios" + File.separator + cpf + ".json";

        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
            return null;
        }

        try (FileReader reader = new FileReader(caminhoArquivo)) {
            Usuario usuario = gson.fromJson(reader, Usuario.class);
            System.out.println("Dados recuperados com sucesso!");
            return usuario;
        } catch (IOException | JsonSyntaxException erro) {
            erro.printStackTrace();
            return null;
        }
    }

    /**
     * Lista todos os arquivos de eventos disponíveis no diretório de eventos.
     *
     * @return Uma lista de nomes dos arquivos JSON dos eventos.
     */
    public List<String> listarEventosDisponiveis() {
        List<String> arquivosJson = new ArrayList<>();
        File diretorio = new File("vendaingressos" + File.separator + "Dados" + File.separator + "Eventos");

        if (diretorio.exists() && diretorio.isDirectory()) {
            File[] arquivos = diretorio.listFiles((dir, name) -> name.endsWith(".json"));

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    if (arquivo.isFile()) {
                        arquivosJson.add(arquivo.getName());
                    }
                }
            } else {
                System.out.println("Nenhum evento encontrado.");
            }
        } else {
            System.out.println("Diretório de eventos não encontrado.");
        }

        return arquivosJson;
    }
}
