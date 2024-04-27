import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HeapSortDatasEmAlta {

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

    // Função para obter a data completa de uma linha do CSV
    private static String getTrendingFullDate(String linha) {
        String[] valores = linha.split(",");
        return valores[2];
    }

    // Função para ordenar os dados pela data completa usando Heap Sort
    private static void heapSort(List<String> linhas, Comparator<String> comparator) {
        int n = linhas.size();

        // Construir heap (reorganizar array)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(linhas, n, i, comparator);
        }

        // Extrair elementos do heap um por um
        for (int i = n - 1; i > 0; i--) {
            // Mover a raiz atual para o final
            String temp = linhas.get(0);
            linhas.set(0, linhas.get(i));
            linhas.set(i, temp);

            // Chamar max heapify na subárvore reduzida
            heapify(linhas, i, 0, comparator);
        }
    }

    // Para heapify uma subárvore enraizada no nó 'i' que é um índice em 'linhas[]'. 'n' é o tamanho do heap
    private static void heapify(List<String> linhas, int n, int i, Comparator<String> comparator) {
        int maior = i; // Inicializar o maior como raiz
        int esquerda = 2 * i + 1; // índice do filho da esquerda = 2*i + 1
        int direita = 2 * i + 2; // índice do filho da direita = 2*i + 2

        // Se o filho da esquerda é maior que a raiz
        if (esquerda < n && comparator.compare(linhas.get(esquerda), linhas.get(maior)) > 0) {
            maior = esquerda;
        }

        // Se o filho da direita é maior que o maior até agora
        if (direita < n && comparator.compare(linhas.get(direita), linhas.get(maior)) > 0) {
            maior = direita;
        }

        // Se o maior não é a raiz
        if (maior != i) {
            String troca = linhas.get(i);
            linhas.set(i, linhas.get(maior));
            linhas.set(maior, troca);

            // Recursivamente heapify a subárvore afetada
            heapify(linhas, n, maior, comparator);
        }
    }

    // Função para gerar o caso melhor
    private static void gerarMelhorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pela data completa em ordem decrescente usando Heap Sort
        List<String> linhasOrdenadas = new ArrayList<>(linhas);
        Comparator<String> comparator = new Comparator<String>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy.dd.MM");

            @Override
            public int compare(String linha1, String linha2) {
                try {
                    return dateFormat.parse(getTrendingFullDate(linha2)).compareTo(dateFormat.parse(getTrendingFullDate(linha1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };
        heapSort(linhasOrdenadas, comparator);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_trending_full_date_heapSort_melhorCaso.csv", linhasOrdenadas);
    }

    // Função para gerar o caso médio
    private static void gerarCasoMedio(List<String> linhas) throws IOException {
        // Embaralhar os dados para criar um caso médio
        List<String> linhasEmbaralhadas = new ArrayList<>(linhas);
        Collections.shuffle(linhasEmbaralhadas);
        // Ordenar os dados pela data completa em ordem decrescente usando Heap Sort
        Comparator<String> comparator = new Comparator<String>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy.dd.MM");

            @Override
            public int compare(String linha1, String linha2) {
                try {
                    return dateFormat.parse(getTrendingFullDate(linha2)).compareTo(dateFormat.parse(getTrendingFullDate(linha1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };
        heapSort(linhasEmbaralhadas, comparator);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_trending_full_date_heapSort_casoMedio.csv", linhasEmbaralhadas);
    }

    // Função para gerar o caso pior
    private static void gerarPiorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pela data completa em ordem crescente usando Heap Sort
        Comparator<String> comparator = new Comparator<String>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yy.dd.MM");

            @Override
            public int compare(String linha1, String linha2) {
                try {
                    return dateFormat.parse(getTrendingFullDate(linha1)).compareTo(dateFormat.parse(getTrendingFullDate(linha2)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };
        heapSort(linhas, comparator);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_trending_full_date_heapSort_piorCaso.csv", linhas);
    }
}
