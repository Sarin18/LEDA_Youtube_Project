import java.io.*;
import java.util.*;

public class SelectionSortComment {

    public static void main(String[] args) {
        // Melhor Caso: Já ordenado
        gerarMelhorCaso("videos_T1.csv", "videos_T1_comment_count_selectionSort_melhorCaso.csv");

        // Médio Caso: Aleatório
        gerarMedioCaso("videos_T1.csv", "videos_T1_comment_count_selectionSort_medioCaso.csv");

        // Pior Caso: Inversamente ordenado
        gerarPiorCaso("videos_T1.csv", "videos_T1_comment_count_selectionSort_piorCaso.csv");
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
        selectionSortPorComentarios(linhas);
        escreverCSV(saida, linhas);
    }

    // Função para aplicar Selection Sort
    private static void selectionSortPorComentarios(List<String> linhas) {
        for (int i = 0; i < linhas.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < linhas.size(); j++) {
                int commentCount1 = extrairCommentCount(linhas.get(j));
                int commentCount2 = extrairCommentCount(linhas.get(minIndex));
                if (commentCount1 < commentCount2) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Collections.swap(linhas, i, minIndex);
            }
        }
    }

    // Função para extrair o número de comentários de uma linha
    private static int extrairCommentCount(String linha) {
        String[] campos = linha.split(",");
        return Integer.parseInt(campos[12]); // Índice 12 para comment_count
    }
}
