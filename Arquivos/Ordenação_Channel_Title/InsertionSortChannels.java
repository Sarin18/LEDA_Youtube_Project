import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InsertionSortChannels {

    // Método para ordenar uma lista de arrays de strings por inserção
    private static void insertionSort(List<String[]> lista, int coluna, Comparator<String> comparador) {
        int tamanho = lista.size();

        for (int i = 1; i < tamanho; i++) {
            String[] chave = lista.get(i);
            int j = i - 1;

            // Move os elementos da lista[0..i-1], que são maiores que a chave,
            // para uma posição à frente de sua posição atual
            while (j >= 0 && comparador.compare(lista.get(j)[coluna], chave[coluna]) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, chave);
        }
    }

    // Método para ler o arquivo CSV e retornar uma lista de arrays de strings representando as linhas do arquivo
    private static List<String[]> lerCSV(String nomeArquivo) throws IOException {
        List<String[]> dados = new ArrayList<>();
        BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
        String linha;
        // Lê cada linha do arquivo e adiciona à lista de dados
        boolean cabecalho = true;
        while ((linha = leitor.readLine()) != null) {
            if (cabecalho) {
                cabecalho = false;
                continue; // Pular o cabeçalho
            }
            dados.add(linha.split(","));
        }
        leitor.close();
        return dados;
    }

    // Método para escrever uma lista de arrays de strings em um arquivo CSV
    private static void escreverCSV(String nomeArquivo, List<String[]> dados) throws IOException {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo));
        // Escreve cada linha da lista no arquivo CSV
        for (String[] linha : dados) {
            escritor.write(String.join(",", linha) + "\n");
        }
        escritor.close();
    }

    public static void main(String[] args) {
        try {
            // Leitura do arquivo CSV
            List<String[]> dados = lerCSV("videos_T1.csv");

            // Comparator para comparar os valores do campo channel_title de forma case-insensitive
            Comparator<String> comparador = String.CASE_INSENSITIVE_ORDER;

            // Ordenação usando Insertion Sort pelo campo channel_title
            insertionSort(dados, 5, comparador); // 5 é o índice do campo channel_title

            // Escrever no arquivo CSV para melhor caso
            escreverCSV("videos_T1_channel_title_insertionSort_melhorCaso.csv", dados);

            // Embaralhar os títulos para o caso médio
            dados.sort((linha1, linha2) -> Math.random() > 0.5 ? 1 : -1);

            // Escrever no arquivo CSV para o caso médio
            escreverCSV("videos_T1_channel_title_insertionSort_medioCaso.csv", dados);

            // Ordenar em ordem reversa para o pior caso
            dados.sort((linha1, linha2) -> comparador.compare(linha2[5], linha1[5]));

            // Escrever no arquivo CSV para o pior caso
            escreverCSV("videos_T1_channel_title_insertionSort_piorCaso.csv", dados);

            System.out.println("Arquivos gerados com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
