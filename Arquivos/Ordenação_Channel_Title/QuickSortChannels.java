import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuickSortChannels {

    // Método para trocar duas linhas em uma lista de strings
    private static void swap(List<String[]> list, int a, int b) {
        String[] temp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, temp);
    }

    // Método para escolher um pivô e particionar a lista ao redor dele
    private static int partition(List<String[]> list, int low, int high, int column, Comparator<String> comparator) {
        String pivot = list.get(high)[column]; // escolhendo o último elemento como pivô
        int i = low - 1;

        for (int j = low; j < high; j++) {
            // Se o elemento atual for menor ou igual ao pivô, troca com o próximo elemento após o último elemento menor que o pivô
            if (comparator.compare(list.get(j)[column], pivot) <= 0) {
                i++;
                swap(list, i, j);
            }
        }

        // Troca o pivô para sua posição correta
        swap(list, i + 1, high);
        return i + 1;
    }

    // Método principal que implementa o algoritmo Quick Sort
    private static void quickSort(List<String[]> list, int low, int high, int column, Comparator<String> comparator) {
        if (low < high) {
            int partitionIndex = partition(list, low, high, column, comparator);

            // Recursivamente ordena os elementos antes e depois do pivô
            quickSort(list, low, partitionIndex - 1, column, comparator);
            quickSort(list, partitionIndex + 1, high, column, comparator);
        }
    }

    // Método para ler o arquivo CSV e retornar uma lista de arrays de strings representando as linhas do arquivo
    private static List<String[]> readCSV(String fileName) throws IOException {
        List<String[]> data = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        // Lê cada linha do arquivo e adiciona à lista de dados
        while ((line = reader.readLine()) != null) {
            data.add(line.split(","));
        }
        reader.close();
        return data;
    }

    // Método para escrever uma lista de arrays de strings em um arquivo CSV
    private static void writeCSV(String fileName, List<String[]> data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        // Escreve cada linha da lista no arquivo CSV
        for (String[] row : data) {
            writer.write(String.join(",", row) + "\n");
        }
        writer.close();
    }

    public static void main(String[] args) {
        try {
            // Leitura do arquivo CSV
            List<String[]> data = readCSV("videos_T1.csv");

            // Comparator para comparar os valores do campo channel_title de forma case-insensitive
            Comparator<String> comparator = String.CASE_INSENSITIVE_ORDER;

            // Ordenação usando Quick Sort pelo campo channel_title
            quickSort(data, 0, data.size() - 1, 5, comparator); // 5 é o índice do campo channel_title

            // Escrever no arquivo CSV para melhor caso
            writeCSV("videos_T1_channel_title_quickSort_melhorCaso.csv", data);

            // Embaralhar os títulos para o caso médio
            data.sort((row1, row2) -> Math.random() > 0.5 ? 1 : -1);

            // Escrever no arquivo CSV para o caso médio
            writeCSV("videos_T1_channel_title_quickSort_medioCaso.csv", data);

            // Ordenar em ordem reversa para o pior caso
            data.sort((row1, row2) -> comparator.compare(row2[5], row1[5]));

            // Escrever no arquivo CSV para o pior caso
            writeCSV("videos_T1_channel_title_quickSort_piorCaso.csv", data);

            System.out.println("Arquivos gerados com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
