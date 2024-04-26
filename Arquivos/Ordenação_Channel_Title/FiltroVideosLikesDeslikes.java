import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FiltroVideosLikesDeslikes {
    public static void main(String[] args) {
        // Caminho para o arquivo de entrada e saída
        String inputFilePath = "videos_T1.csv";
        String outputFilePath = "videos_T2.csv";

        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            // Abrir o arquivo de entrada para leitura
            reader = new BufferedReader(new FileReader(inputFilePath));

            // Abrir o arquivo de saída para escrita
            writer = new BufferedWriter(new FileWriter(outputFilePath));

            String line;
            // Ler a primeira linha (cabeçalho) e ignorá-la
            reader.readLine();

            // Ler cada linha do arquivo de entrada
            while ((line = reader.readLine()) != null) {
                // Dividir a linha em campos usando vírgula como separador
                String[] fields = splitCSVLine(line);

                // Verificar se há campos suficientes
                if (fields.length >= 13) {
                    // Converter os campos de likes e dislikes para números
                    int likes = Integer.parseInt(fields[10].trim());
                    int dislikes = Integer.parseInt(fields[11].trim());

                    // Verificar se dislikes são maiores que likes
                    if (dislikes > likes) {
                        // Se sim, escrever a linha no arquivo de saída
                        writer.write(line);
                        writer.newLine(); // Adicionar nova linha
                    }
                }
            }
            System.out.println("Filtro concluído com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao processar o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Erro de formato numérico: " + e.getMessage());
        } finally {
            try {
                // Fechar os recursos
                if (reader != null) {
                    reader.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                System.out.println("Erro ao fechar o arquivo: " + e.getMessage());
            }
        }
    }

    // Método para dividir a linha do CSV levando em conta as vírgulas dentro de citações
    private static String[] splitCSVLine(String line) {
        // Lista para armazenar os campos
        java.util.List<String> fields = new java.util.ArrayList<>();
        // Flag para indicar se estamos dentro de uma citação
        boolean inQuotes = false;
        // StringBuilder para construir cada campo
        StringBuilder sb = new StringBuilder();

        // Iterar sobre cada caractere na linha
        for (char c : line.toCharArray()) {
            // Se encontrarmos uma vírgula e não estivermos dentro de uma citação, adicionamos o campo à lista
            if (c == ',' && !inQuotes) {
                fields.add(sb.toString());
                sb.setLength(0); // Limpar StringBuilder para o próximo campo
            } else if (c == '"') { // Se encontrarmos uma citação, alteramos o estado da flag
                inQuotes = !inQuotes;
            } else { // Adicionamos o caractere ao campo atual
                sb.append(c);
            }
        }

        // Adicionamos o último campo à lista
        fields.add(sb.toString());

        // Convertendo a lista para um array de Strings e retornando
        return fields.toArray(new String[0]);
    }
}
