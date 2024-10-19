import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GerenciadorArquivos {

    public void ArmazenarEvento(Evento evento) {
        Gson gsonFile = new Gson();
        String jsonFile = gsonFile.toJson(evento);
        String eventoID = evento.getID();

        String caminhoDiretorio = "vendaingressos" + File.separator + "Dados" + File.separator + "Eventos";
        String caminhoArquivo = caminhoDiretorio + File.separator + eventoID + ".json";

        // Verifica se o diretório existe, caso contrário, cria-o
        File diretorio = new File(caminhoDiretorio);
        if (!diretorio.exists()) {
            diretorio.mkdirs(); // Cria os diretórios necessários
        }

        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            writer.write(jsonFile);
            System.out.println("Dados Armazenados com sucesso!");
        } catch (IOException erro) {
            erro.printStackTrace();
        }
    }

    public Evento LerArquivoEvento(String eventoid) {
        Gson gson = new Gson();
        String caminhoArquivo = "vendaingressos" + File.separator + "Dados" + File.separator + "Eventos" + File.separator + eventoid + ".json";

        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            System.out.println("Arquivo não encontrado: " + caminhoArquivo);
            return null; // Ou uma outra ação de tratamento, como lançar uma exceção personalizada
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

    public void ArmazenamentoUser(Usuario usuario) {
        Gson gsonFile = new Gson();
        String jsonFile = gsonFile.toJson(usuario);
        String UserCPF = usuario.getCpf();

        String caminhoDiretorio = "vendaingressos" + File.separator + "Dados" + File.separator + "Usuarios";
        String caminhoArquivo = caminhoDiretorio + File.separator + UserCPF + ".json";

        // Verifica se o diretório existe, caso contrário, cria-o
        File diretorio = new File(caminhoDiretorio);
        if (!diretorio.exists()) {
            diretorio.mkdirs(); // Cria os diretórios necessários
        }

        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            writer.write(jsonFile);
            System.out.println("Dados Armazenados com sucesso!");
        } catch (IOException erro) {
            erro.printStackTrace();
        }
    }

    public Usuario LerArquivoUsuario(String cpf) {
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
