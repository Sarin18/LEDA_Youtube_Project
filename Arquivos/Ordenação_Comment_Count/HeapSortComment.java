import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeapSortComment {

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

    // Função para ordenar os dados pelo número de comentários usando Heap Sort
    private static void heapSort(List<String> linhas) {
        int n = linhas.size();

        // Construir heap (rearranjar array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(linhas, n, i);
        }

        // Extrair elementos um por um do heap
        for (int i = n - 1; i > 0; i--) {
            // Mover a raiz atual para o fim
            Collections.swap(linhas, 0, i);

            // Chamar o heapify na subárvore reduzida
            heapify(linhas, i, 0);
        }
    }

    // Função para fazer heapify de uma subárvore enraizada no índice i que é um inteiro em linhas[]
    private static void heapify(List<String> linhas, int n, int i) {
        int maior = i; // Inicializar o maior como raiz
        int esquerda = 2 * i + 1; // índice da filha esquerda de i
        int direita = 2 * i + 2; // índice da filha direita de i

        // Se a filha esquerda é maior que a raiz
        if (esquerda < n && getQuantidadeComentarios(linhas.get(esquerda)) > getQuantidadeComentarios(linhas.get(maior))) {
            maior = esquerda;
        }

        // Se a filha direita é maior que a raiz
        if (direita < n && getQuantidadeComentarios(linhas.get(direita)) > getQuantidadeComentarios(linhas.get(maior))) {
            maior = direita;
        }

        // Se o maior não é a raiz
        if (maior != i) {
            Collections.swap(linhas, i, maior);

            // Recursivamente fazer o heapify na subárvore afetada
            heapify(linhas, n, maior);
        }
    }

    // Função para gerar o caso melhor
    private static void gerarMelhorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pelo número de comentários em ordem crescente
        List<String> linhasOrdenadas = new ArrayList<>(linhas);
        heapSort(linhasOrdenadas);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_comment_count_HeapSort_melhorCaso.csv", linhasOrdenadas);
    }

    // Função para gerar o caso médio
    private static void gerarCasoMedio(List<String> linhas) throws IOException {
        // Embaralhar os dados para criar um caso médio
        List<String> linhasEmbaralhadas = new ArrayList<>(linhas);
        Collections.shuffle(linhasEmbaralhadas);
        // Escrever os dados embaralhados em um novo arquivo CSV
        escreverCSV("videos_T1_comment_count_HeapSort_casoMedio.csv", linhasEmbaralhadas);
    }

    // Função para gerar o caso pior
    private static void gerarPiorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pelo número de comentários em ordem decrescente
        List<String> linhasOrdenadas = new ArrayList<>(linhas);
        heapSort(linhasOrdenadas);
        Collections.reverse(linhasOrdenadas);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_comment_count_HeapSort_piorCaso.csv", linhasOrdenadas);
    }
}
