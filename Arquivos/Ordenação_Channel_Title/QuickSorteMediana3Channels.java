import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuickSorteMediana3Channels {

    // Método para trocar duas linhas em uma lista de strings
    private static void trocar(List<String[]> lista, int a, int b) {
        String[] temp = lista.get(a);
        lista.set(a, lista.get(b));
        lista.set(b, temp);
    }

    // Método para escolher um pivô utilizando a estratégia da mediana de três
    private static String escolherPivo(List<String[]> lista, int baixo, int alto, int coluna, Comparator<String> comparador) {
        int meio = baixo + (alto - baixo) / 2;
        // Compara os valores do campo channel_title dos elementos baixo, meio e alto
        String valorBaixo = lista.get(baixo)[coluna];
        String valorMeio = lista.get(meio)[coluna];
        String valorAlto = lista.get(alto)[coluna];
        // Retorna o valor mediano entre os três
        if (comparador.compare(valorBaixo, valorMeio) > 0) {
            if (comparador.compare(valorMeio, valorAlto) > 0) {
                return valorMeio; // valorMeio é o mediano
            } else if (comparador.compare(valorBaixo, valorAlto) > 0) {
                return valorAlto; // valorAlto é o mediano
            } else {
                return valorBaixo; // valorBaixo é o mediano
            }
        } else {
            if (comparador.compare(valorBaixo, valorAlto) > 0) {
                return valorBaixo; // valorBaixo é o mediano
            } else if (comparador.compare(valorMeio, valorAlto) > 0) {
                return valorAlto; // valorAlto é o mediano
            } else {
                return valorMeio; // valorMeio é o mediano
            }
        }
    }

    // Método para escolher um pivô e particionar a lista ao redor dele
    private static int particionar(List<String[]> lista, int baixo, int alto, int coluna, Comparator<String> comparador) {
        String pivo = escolherPivo(lista, baixo, alto, coluna, comparador);
        int i = baixo - 1;

        for (int j = baixo; j < alto; j++) {
            // Se o elemento atual for menor ou igual ao pivô, troca com o próximo elemento após o último elemento menor que o pivô
            if (comparador.compare(lista.get(j)[coluna], pivo) <= 0) {
                i++;
                trocar(lista, i, j);
            }
        }

        // Troca o pivô para sua posição correta
        trocar(lista, i + 1, alto);
        return i + 1;
    }

    // Método principal que implementa o algoritmo Quick Sort
    private static void quickSort(List<String[]> lista, int baixo, int alto, int coluna, Comparator<String> comparador) {
        if (baixo < alto) {
            int indiceParticao = particionar(lista, baixo, alto, coluna, comparador);

            // Recursivamente ordena os elementos antes e depois do pivô
            quickSort(lista, baixo, indiceParticao - 1, coluna, comparador);
            quickSort(lista, indiceParticao + 1, alto, coluna, comparador);
        }
    }

    // Método para ler o arquivo CSV e retornar uma lista de arrays de strings representando as linhas do arquivo
    private static List<String[]> lerCSV(String nomeArquivo) throws IOException {
        List<String[]> dados = new ArrayList<>();
        BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
        String linha;
        // Lê cada linha do arquivo e adiciona à lista de dados
        while ((linha = leitor.readLine()) != null) {
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

            // Ordenação usando Quick Sort com mediana de três pelo campo channel_title
            quickSort(dados, 0, dados.size() - 1, 5, comparador); // 5 é o índice do campo channel_title

            // Escrever no arquivo CSV para melhor caso
            escreverCSV("videos_T1_channel_title_quickSortMedianaDeTres_melhorCaso.csv", dados);

            // Embaralhar os títulos para o caso médio
            dados.sort((linha1, linha2) -> Math.random() > 0.5 ? 1 : -1);

            // Escrever no arquivo CSV para o caso médio
            escreverCSV("videos_T1_channel_title_quickSortMedianaDeTres_medioCaso.csv", dados);

            // Ordenar em ordem reversa para o pior caso
            dados.sort((linha1, linha2) -> comparador.compare(linha2[5], linha1[5]));

            // Escrever no arquivo CSV para o pior caso
            escreverCSV("videos_T1_channel_title_quickSortMedianaDeTres_piorCaso.csv", dados);

            System.out.println("Arquivos gerados com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}
