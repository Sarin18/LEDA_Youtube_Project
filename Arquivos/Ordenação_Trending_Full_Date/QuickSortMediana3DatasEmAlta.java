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

public class QuickSortMediana3DatasEmAlta {

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

    // Função para ordenar os dados pela data completa usando Quick Sort com mediana de três
    private static void quickSortMedianOfThree(List<String> linhas, Comparator<String> comparator) {
        quickSortMedianOfThree(linhas, 0, linhas.size() - 1, comparator);
    }

    private static void quickSortMedianOfThree(List<String> linhas, int inicio, int fim, Comparator<String> comparator) {
        if (inicio < fim) {
            int indicePivo = particionarMedianaDeTres(linhas, inicio, fim, comparator);
            quickSortMedianOfThree(linhas, inicio, indicePivo - 1, comparator);
            quickSortMedianOfThree(linhas, indicePivo + 1, fim, comparator);
        }
    }

    private static int particionarMedianaDeTres(List<String> linhas, int inicio, int fim, Comparator<String> comparator) {
        int meio = inicio + (fim - inicio) / 2;
        String primeiro = linhas.get(inicio);
        String meioValor = linhas.get(meio);
        String ultimo = linhas.get(fim);

        // Encontrar a mediana entre o primeiro, o meio e o último elemento
        String mediana;
        if (comparator.compare(primeiro, meioValor) < 0) {
            if (comparator.compare(meioValor, ultimo) < 0) {
                mediana = meioValor;
            } else if (comparator.compare(primeiro, ultimo) < 0) {
                mediana = ultimo;
            } else {
                mediana = primeiro;
            }
        } else {
            if (comparator.compare(primeiro, ultimo) < 0) {
                mediana = primeiro;
            } else if (comparator.compare(meioValor, ultimo) < 0) {
                mediana = ultimo;
            } else {
                mediana = meioValor;
            }
        }

        // Colocar a mediana no final do intervalo para ser usada como pivô
        int indiceMediana;
        if (mediana.equals(primeiro)) {
            indiceMediana = inicio;
        } else if (mediana.equals(meioValor)) {
            indiceMediana = meio;
        } else {
            indiceMediana = fim;
        }
        Collections.swap(linhas, indiceMediana, fim);

        // Particionar o array usando o pivô mediana de três
        String pivo = linhas.get(fim);
        int indiceMenor = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (comparator.compare(linhas.get(j), pivo) <= 0) {
                indiceMenor++;
                Collections.swap(linhas, indiceMenor, j);
            }
        }

        Collections.swap(linhas, indiceMenor + 1, fim);
        return indiceMenor + 1;
    }

    // Função para gerar o caso melhor
    private static void gerarMelhorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pela data completa em ordem decrescente usando Quick Sort com mediana de três
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
        quickSortMedianOfThree(linhasOrdenadas, comparator);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_trending_full_date_quickSort_medianOfThree_melhorCaso.csv", linhasOrdenadas);
    }

    // Função para gerar o caso médio
    private static void gerarCasoMedio(List<String> linhas) throws IOException {
        // Embaralhar os dados para criar um caso médio
        List<String> linhasEmbaralhadas = new ArrayList<>(linhas);
        Collections.shuffle(linhasEmbaralhadas);
        // Ordenar os dados pela data completa em ordem decrescente usando Quick Sort com mediana de três
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
        quickSortMedianOfThree(linhasEmbaralhadas, comparator);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_trending_full_date_quickSort_medianOfThree_casoMedio.csv", linhasEmbaralhadas);
    }

    // Função para gerar o caso pior
    private static void gerarPiorCaso(List<String> linhas) throws IOException {
        // Ordenar os dados pela data completa em ordem crescente usando Quick Sort com mediana de três
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
        quickSortMedianOfThree(linhas, comparator);
        // Escrever os dados ordenados em um novo arquivo CSV
        escreverCSV("videos_T1_trending_full_date_quickSort_medianOfThree_piorCaso.csv", linhas);
    }
}
