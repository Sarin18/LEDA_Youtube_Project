import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DatasCorretas {
    public static void main(String[] args) {
        // Arquivo de entrada gerado na primeira transformação
        String inputFile = "videos.csv";
        // Arquivo de saída com o novo campo trending_full_date
        String outputFile = "videos_T1.csv";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            FileWriter writer = new FileWriter(outputFile);
            PrintWriter printWriter = new PrintWriter(writer);

            // Cabeçalho do arquivo de saída com o novo campo
            printWriter.println("countries,video_id,trending_date,trending_full_date,title,channel_title,category_id,publish_time,tags,views,likes,dislikes,comment_count,thumbnail_link,comments_disabled,ratings_disabled,video_error_or_removed,description");

            String line;
            // Ler e ignorar o cabeçalho do arquivo de entrada
            reader.readLine();

            // Formato de data para a conversão
            SimpleDateFormat inputFormat = new SimpleDateFormat("yy.dd.MM");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Ler cada linha do arquivo de entrada
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Se a linha não tiver o formato esperado, pular para a próxima
                if (parts.length < 16) {
                    continue;
                }
                // Obter a data da coluna trending_date
                String trendingDateStr = parts[2];
                // Verificar se o valor é uma data válida antes de fazer o parse
                try {
                    Date trendingDate = inputFormat.parse(trendingDateStr);
                    String trendingFullDateStr = outputFormat.format(trendingDate);
                    // Escrever a linha no arquivo de saída com o novo campo trending_full_date
                    printWriter.println(parts[0] + "," + parts[1] + "," + trendingDateStr + "," + trendingFullDateStr + "," + String.join(",", Arrays.copyOfRange(parts, 3, parts.length)));
                } catch (ParseException e) {
                    // Se não for uma data válida, pular para a próxima linha
                    continue;
                }
            }

            // Fechar os leitores e escritores
            reader.close();
            printWriter.close();

            System.out.println("Arquivo 'videos_T1.csv' criado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}