import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountingSortComment {

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
    private static int getQuantidadeComentarios(String linha) {
        String[] valores = linha.split(",");
        try {
            return Integer.parseInt(valores[12]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return 0; // Retorna 0 se não for possível converter para inteiro ou se o índice estiver fora do alcance
        }
    }

    // Função para ordenar os dados pelo número de comentários usando Counting Sort
    private static void countingSort(List<String> linhas, int maxCommentCount) {
        // Criar array de contagem e inicializá-lo com zeros
        int[] count = new int[maxCommentCount + 1];
        for (String linha : linhas) {
            int comentario = getQuantidadeComentarios(linha);
            count[comentario]++;
        }

        // Atualizar o array de contagem para armazenar as posições finais dos elementos ordenados
        for (int i = 1; i <= maxCommentCount; i++) {
            count[i] += count[i - 1];
        }

        // Criar array para armazenar os elementos ordenados
        String[] ordenado = new String[linhas.size()];

        // Preencher o array ordenado com base nas contagens e decrementar as contagens
        for (int i = linhas.size() - 1; i >= 0; i--) {
            int comentario = getQuantidadeComentarios(linhas.get(i));
            ordenado[count[comentario] - 1] = linhas.get(i);
            count[comentario]--;
        }

        // Copiar o array ordenado de volta para a lista original
        for (int i = 0; i < linhas.size(); i++) {
            linhas.set(i, ordenado[i]);
        }
    }

    // Função para gerar o caso melhor
    private static void gerarMelhorCaso(List<String> linhas) throws IOException {
        // Encontrar o maior número de comentários
        int maxCommentCount = 0;
        for (String linha : linhas) {
            int comentario = getQuantidadeComentarios(linha);
            if (comentario > maxCommentCount) {
                maxCommentCount = comentario;
            }
        }

        // Ordenar os dados pelo número de comentários usando Counting Sort
        countingSort(linhas, maxCommentCount);

        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_comment_count_melhorCaso.csv", linhas);
    }

    // Função para gerar o caso médio
    private static void gerarCasoMedio(List<String> linhas) throws IOException {
        // Embaralhar os dados para criar um caso médio
        Collections.shuffle(linhas);

        // Encontrar o maior número de comentários
        int maxCommentCount = 0;
        for (String linha : linhas) {
            int comentario = getQuantidadeComentarios(linha);
            if (comentario > maxCommentCount) {
                maxCommentCount = comentario;
            }
        }

        // Ordenar os dados pelo número de comentários usando Counting Sort
        countingSort(linhas, maxCommentCount);

        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_comment_count_casoMedio.csv", linhas);
    }

    // Função para gerar o caso pior
    private static void gerarPiorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pelo número de comentários em ordem decrescente
        Collections.sort(linhas, (a, b) -> Integer.compare(getQuantidadeComentarios(b), getQuantidadeComentarios(a)));

        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_comment_count_piorCaso.csv", linhas);
    }
}
