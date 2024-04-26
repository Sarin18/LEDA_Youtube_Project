import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectionSortChannels {

    public static void main(String[] args) {
        // Melhor Caso: Já ordenado
        gerarMelhorCaso("videos_T1.csv", "videos_T1_channel_title_selectionSort_maiorCaso.csv");

        // Médio Caso: Aleatório
        gerarMedioCaso("videos_T1.csv", "videos_T1_channel_title_selectionSort_medioCaso.csv");

        // Pior Caso: Inversamente ordenado
        gerarPiorCaso("videos_T1.csv", "videos_T1_channel_title_selectionSort_piorCaso.csv");
    }

    // Função para ler o arquivo CSV
    private static List<String> lerCSV(String filename) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linhas;
    }

    // Função para escrever em um arquivo CSV
    private static void escreverCSV(String filename, List<String> linhas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Função para gerar o Melhor Caso (lista já ordenada)
    private static void gerarMelhorCaso(String entrada, String saida) {
        List<String> linhas = lerCSV(entrada);
        escreverCSV(saida, linhas);
    }

    // Função para gerar o Médio Caso (lista aleatória)
    private static void gerarMedioCaso(String entrada, String saida) {
        List<String> linhas = lerCSV(entrada);
        Collections.shuffle(linhas);
        escreverCSV(saida, linhas);
    }

    // Função para gerar o Pior Caso (lista inversamente ordenada)
    private static void gerarPiorCaso(String entrada, String saida) {
        List<String> linhas = lerCSV(entrada);
        selectionSort(linhas);
        escreverCSV(saida, linhas);
    }

    // Função para aplicar Selection Sort
    private static void selectionSort(List<String> linhas) {
        for (int i = 0; i < linhas.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < linhas.size(); j++) {
                if (compararLinhas(linhas.get(j), linhas.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Collections.swap(linhas, i, minIndex);
            }
        }
    }

    // Função para comparar duas linhas pelo campo 'channel_title'
    private static int compararLinhas(String linha1, String linha2) {
        String[] campos1 = linha1.split(",");
        String[] campos2 = linha2.split(",");
        String channelTitle1 = (campos1.length > 5) ? campos1[5].trim() : "";
        String channelTitle2 = (campos2.length > 5) ? campos2[5].trim() : "";
        return channelTitle1.compareTo(channelTitle2);
    }
}