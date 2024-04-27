import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickSortMediana3Comment {

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
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return 0; // Retorna 0 se não for possível converter para inteiro ou se o índice estiver fora do alcance
        }
    }

    // Função para ordenar os dados pelo número de comentários usando Quick Sort com mediana de três
    private static void quickSortMediana3(List<String> linhas, int inicio, int fim) {
        if (inicio < fim) {
            int indicePivo = particionarMediana3(linhas, inicio, fim);
            quickSortMediana3(linhas, inicio, indicePivo - 1);
            quickSortMediana3(linhas, indicePivo + 1, fim);
        }
    }

    // Função para particionar a lista usando mediana de três e retornar o índice do pivô
    private static int particionarMediana3(List<String> linhas, int inicio, int fim) {
        int meio = inicio + (fim - inicio) / 2;
        int a = getQuantidadeComentarios(linhas.get(inicio));
        int b = getQuantidadeComentarios(linhas.get(meio));
        int c = getQuantidadeComentarios(linhas.get(fim));

        // Encontrar o pivô usando mediana de três
        int pivô;
        if ((a <= b && b <= c) || (c <= b && b <= a)) {
            pivô = meio;
        } else if ((b <= a && a <= c) || (c <= a && a <= b)) {
            pivô = inicio;
        } else {
            pivô = fim;
        }

        // Colocar o pivô no final
        Collections.swap(linhas, pivô, fim);

        // Particionar como no Quick Sort convencional
        int valorPivo = getQuantidadeComentarios(linhas.get(fim));
        int i = inicio - 1;
        for (int j = inicio; j < fim; j++) {
            if (getQuantidadeComentarios(linhas.get(j)) <= valorPivo) {
                i++;
                Collections.swap(linhas, i, j);
            }
        }
        Collections.swap(linhas, i + 1, fim);
        return i + 1;
    }

    // Função para gerar o caso melhor
    private static void gerarMelhorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pelo número de comentários em ordem crescente
        List<String> linhasOrdenadas = new ArrayList<>(linhas);
        quickSortMediana3(linhasOrdenadas, 0, linhasOrdenadas.size() - 1);
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
        quickSortMediana3(linhasOrdenadas, 0, linhasOrdenadas.size() - 1);
        Collections.reverse(linhasOrdenadas);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_comment_count_piorCaso.csv", linhasOrdenadas);
    }
}
