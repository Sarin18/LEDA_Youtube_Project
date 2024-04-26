import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MergeSortChannels {

    public static void main(String[] args) {
        // Melhor Caso: Já ordenado
        gerarMelhorCaso("videos_T1.csv", "videos_T1_channel_title_margeSort_maiorCaso.csv");

        // Médio Caso: Aleatório
        gerarMedioCaso("videos_T1.csv", "videos_T1_channel_title_margeSort_medioCaso.csv");

        // Pior Caso: Inversamente ordenado
        gerarPiorCaso("videos_T1.csv", "videos_T1_channel_title_margeSort_piorCaso.csv");
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
        Collections.sort(linhas, new MelhorCasoComparator());
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
        Collections.sort(linhas, new PiorCasoComparator());
        escreverCSV(saida, linhas);
    }

    // Função para extrair o campo 'channel_title' de uma linha CSV
    private static String extrairCampo(String linha) {
        String[] campos = linha.split(",");
        if (campos.length > 5) {
            return campos[5].trim(); // O campo 'channel_title' está na coluna 6 (índice 5)
        } else {
            return ""; // Se o campo não estiver presente, retornar uma string vazia
        }
    }

    // Classe estática aninhada para comparador de Melhor Caso
    private static class MelhorCasoComparator implements Comparator<String> {
        @Override
        public int compare(String linha1, String linha2) {
            // Comparação pelo campo 'channel_title' em ordem alfabética
            return extrairCampo(linha1).compareTo(extrairCampo(linha2));
        }
    }

    // Classe estática aninhada para comparador de Pior Caso
    private static class PiorCasoComparator implements Comparator<String> {
        @Override
        public int compare(String linha1, String linha2) {
            // Comparação inversa pelo campo 'channel_title' em ordem alfabética
            return extrairCampo(linha2).compareTo(extrairCampo(linha1));
        }
    }
}
