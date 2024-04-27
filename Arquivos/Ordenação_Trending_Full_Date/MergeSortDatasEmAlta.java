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

public class MergeSortDatasEmAlta {

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

    // Função para ordenar os dados pela data completa usando Merge Sort
    private static void mergeSort(List<String> linhas, Comparator<String> comparator) {
        mergeSort(linhas, 0, linhas.size() - 1, comparator);
    }

    private static void mergeSort(List<String> linhas, int inicio, int fim, Comparator<String> comparator) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;

            mergeSort(linhas, inicio, meio, comparator);
            mergeSort(linhas, meio + 1, fim, comparator);

            merge(linhas, inicio, meio, fim, comparator);
        }
    }

    private static void merge(List<String> linhas, int inicio, int meio, int fim, Comparator<String> comparator) {
        int tamanhoEsquerda = meio - inicio + 1;
        int tamanhoDireita = fim - meio;

        List<String> esquerda = new ArrayList<>();
        List<String> direita = new ArrayList<>();

        for (int i = 0; i < tamanhoEsquerda; i++) {
            esquerda.add(linhas.get(inicio + i));
        }
        for (int j = 0; j < tamanhoDireita; j++) {
            direita.add(linhas.get(meio + 1 + j));
        }

        int i = 0, j = 0;
        int k = inicio;
        while (i < tamanhoEsquerda && j < tamanhoDireita) {
            if (comparator.compare(esquerda.get(i), direita.get(j)) <= 0) {
                linhas.set(k, esquerda.get(i));
                i++;
            } else {
                linhas.set(k, direita.get(j));
                j++;
            }
            k++;
        }

        while (i < tamanhoEsquerda) {
            linhas.set(k, esquerda.get(i));
            i++;
            k++;
        }

        while (j < tamanhoDireita) {
            linhas.set(k, direita.get(j));
            j++;
            k++;
        }
    }

    // Função para gerar o caso melhor
    private static void gerarMelhorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pela data completa em ordem decrescente usando Merge Sort
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
        mergeSort(linhasOrdenadas, comparator);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_trending_full_date_mergeSort_melhorCaso.csv", linhasOrdenadas);
    }

    // Função para gerar o caso médio
    private static void gerarCasoMedio(List<String> linhas) throws IOException {
        // Embaralhar os dados para criar um caso médio
        List<String> linhasEmbaralhadas = new ArrayList<>(linhas);
        Collections.shuffle(linhasEmbaralhadas);
        // Ordenar os dados pela data completa em ordem decrescente usando Merge Sort
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
        mergeSort(linhasEmbaralhadas, comparator);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_trending_full_date_mergeSort_casoMedio.csv", linhasEmbaralhadas);
    }

    // Função para gerar o caso pior
    private static void gerarPiorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pela data completa em ordem crescente usando Merge Sort
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
        mergeSort(linhas, comparator);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_trending_full_date_mergeSort_piorCaso.csv", linhas);
    }
}

