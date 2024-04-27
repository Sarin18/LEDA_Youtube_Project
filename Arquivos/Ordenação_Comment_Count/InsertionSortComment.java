import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InsertionSortComment {

    public static void main(String[] args) {
        // Caminho do arquivo de entrada
        String arquivoEntrada = "videos_T1.csv";

        // Gerar e ordenar os casos
        gerarEOrdenarCasos(arquivoEntrada);
        
        System.out.println("Casos gerados e ordenados com sucesso.");
    }

    // Função para gerar e ordenar os casos melhor, médio e pior
    private static void gerarEOrdenarCasos(String arquivoEntrada) {
        try {
            // Ler os dados do arquivo CSV
            List<String> linhas = lerCSV(arquivoEntrada);

            // Gerar os casos
            gerarMelhorCaso(linhas);
            gerarCasoMedio(linhas);
            gerarPiorCaso(linhas);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Função para ler o arquivo CSV e retornar os dados como uma lista de strings
    private static List<String> lerCSV(String arquivoEntrada) throws IOException {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoEntrada))) {
            String linha;
            // Ler cada linha do arquivo
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        }
        // Remover o cabeçalho
        linhas.remove(0);
        return linhas;
    }

    // Função para escrever os dados ordenados em um novo arquivo CSV
    private static void escreverCSV(String arquivoSaida, List<String> linhas) throws IOException {
        try (FileWriter writer = new FileWriter(arquivoSaida)) {
            // Escrever o cabeçalho
            writer.write("countries,video_id,trending_date,trending_full_date,title,channel_title,category_id,publish_time,tags,views,likes,dislikes,comment_count,thumbnail_link,comments_disabled,ratings_disabled,video_error_or_removed,description\n");
            // Escrever os dados ordenados
            for (String linha : linhas) {
                writer.write(linha + "\n");
            }
        }
    }

    // Função para obter o número de comentários de uma linha do CSV
    private static Integer getQuantidadeComentarios(String linha) {
        String[] valores = linha.split(",");
        try {
            return Integer.parseInt(valores[12]);
        } catch (NumberFormatException e) {
            return null; // Retorna null se não for possível converter para inteiro
        }
    }

    // Função para gerar o caso melhor
    private static void gerarMelhorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pelo número de comentários em ordem crescente
        List<String> linhasOrdenadas = new ArrayList<>(linhas);
        linhasOrdenadas.sort(Comparator.comparingInt(linha -> {
            Integer comentarios = getQuantidadeComentarios(linha);
            return comentarios != null ? comentarios : Integer.MAX_VALUE; // Coloca as linhas não convertíveis no final
        }));
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_comment_count_melhorCaso.csv", linhasOrdenadas);
    }

    // Função para gerar o caso médio
    private static void gerarCasoMedio(List<String> linhas) throws IOException {
        // Embaralhar os dados para criar um caso médio
        List<String> linhasEmbaralhadas = new ArrayList<>(linhas);
        Collections.shuffle(linhasEmbaralhadas);
        // Escrever os dados embaralhados em um novo arquivo CSV
        escreverCSV("videos_T1_comment_count_casoMedio.csv", linhasEmbaralhadas);
    }

    // Função para gerar o caso pior
    private static void gerarPiorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pelo número de comentários em ordem decrescente
        List<String> linhasOrdenadas = new ArrayList<>(linhas);
        linhasOrdenadas.sort((linha1, linha2) -> {
            Integer comentarios1 = getQuantidadeComentarios(linha1);
            Integer comentarios2 = getQuantidadeComentarios(linha2);
            // Coloca as linhas não convertíveis no final
            if (comentarios1 == null && comentarios2 == null) {
                return 0;
            } else if (comentarios1 == null) {
                return 1;
            } else if (comentarios2 == null) {
                return -1;
            } else {
                return comentarios2 - comentarios1;
            }
        });
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_comment_count_piorCaso.csv", linhasOrdenadas);
    }
}
