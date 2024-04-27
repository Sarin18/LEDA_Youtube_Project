import java.io.*;
import java.util.*;

public class MergeSortComment {

    public static void main(String[] args) {
        // Melhor Caso: Já ordenado
        gerarMelhorCaso("videos_T1.csv", "melhor_caso_videos_T1.csv");

        // Médio Caso: Aleatório
        gerarMedioCaso("videos_T1.csv", "medio_caso_videos_T1.csv");

        // Pior Caso: Inversamente ordenado
        gerarPiorCaso("videos_T1.csv", "pior_caso_videos_T1.csv");
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
        mergeSortPorComentarios(linhas);
        escreverCSV(saida, linhas);
    }

    // Função para aplicar Merge Sort
    private static void mergeSortPorComentarios(List<String> linhas) {
        mergeSortPorComentarios(linhas, 0, linhas.size() - 1);
    }

    private static void mergeSortPorComentarios(List<String> linhas, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;
            mergeSortPorComentarios(linhas, inicio, meio);
            mergeSortPorComentarios(linhas, meio + 1, fim);
            merge(linhas, inicio, meio, fim);
        }
    }

    private static void merge(List<String> linhas, int inicio, int meio, int fim) {
        List<String> temp = new ArrayList<>();
        int i = inicio;
        int j = meio + 1;

        while (i <= meio && j <= fim) {
            int commentCount1 = extrairCommentCount(linhas.get(i));
            int commentCount2 = extrairCommentCount(linhas.get(j));
            if (commentCount1 <= commentCount2) {
                temp.add(linhas.get(i++));
            } else {
                temp.add(linhas.get(j++));
            }
        }

        while (i <= meio) {
            temp.add(linhas.get(i++));
        }

        while (j <= fim) {
            temp.add(linhas.get(j++));
        }

        for (int k = inicio; k <= fim; k++) {
            linhas.set(k, temp.get(k - inicio));
        }
    }

    // Função para extrair o número de comentários de uma linha
    private static int extrairCommentCount(String linha) {
        String[] campos = linha.split(",");
        return Integer.parseInt(campos[12]); // Índice 12 para comment_count
    }
}
