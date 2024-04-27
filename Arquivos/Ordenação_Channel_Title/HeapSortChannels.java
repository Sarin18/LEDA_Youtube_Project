import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeapSortChannels {

    public static void main(String[] args) {
        // Melhor Caso: Já ordenado
        gerarMelhorCaso("videos_T1.csv", "videos_T1_channel_title_heapSort_melhorCaso.csv");

        // Médio Caso: Aleatório
        gerarMedioCaso("videos_T1.csv", "videos_T1_channel_title_heapSort_medioCaso.csv");

        // Pior Caso: Inversamente ordenado
        gerarPiorCaso("videos_T1.csv", "videos_T1_channel_title_heapSort_piorCaso.csv");
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
        heapSort(linhas);
        escreverCSV(saida, linhas);
    }

    // Função para aplicar Heap Sort
    private static void heapSort(List<String> linhas) {
        int n = linhas.size();

        // Construir heap máximo
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(linhas, n, i);
        }

        // Extrair elementos do heap um por um
        for (int i = n - 1; i > 0; i--) {
            Collections.swap(linhas, 0, i); // Mover a raiz atual para o final
            heapify(linhas, i, 0); // Chamar heapify na subárvore reduzida
        }
    }

    // Função para transformar a lista em um heap
    private static void heapify(List<String> linhas, int n, int i) {
        int maior = i; // Inicializar o maior como raiz
        int esquerda = 2 * i + 1; // Filho esquerdo
        int direita = 2 * i + 2; // Filho direito

        // Se o filho esquerdo for maior que a raiz
        if (esquerda < n && compararLinhas(linhas.get(esquerda), linhas.get(maior)) > 0) {
            maior = esquerda;
        }

        // Se o filho direito for maior que o maior até agora
        if (direita < n && compararLinhas(linhas.get(direita), linhas.get(maior)) > 0) {
            maior = direita;
        }

        // Se o maior não for a raiz
        if (maior != i) {
            Collections.swap(linhas, i, maior); // Trocar raiz e maior
            heapify(linhas, n, maior); // Chamar heapify recursivamente na subárvore afetada
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
