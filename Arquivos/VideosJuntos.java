import java.io.*;

public class VideosJuntos {
    public static void main(String[] args) {
        // Lista de arquivos CSV a serem mesclados
        String[] files = {"GBvideos.csv", "CAvideos.csv", "MXvideos.csv", "INvideos.csv",
                          "DEvideos.csv", "RUvideos.csv", "JPvideos.csv", "FRvideos.csv",
                          "USvideos.csv", "KRvideos.csv"};
        
        // Nome do arquivo de saída
        String outputFile = "videos.csv";
        
        try {
            // Criação do arquivo de saída
            FileWriter writer = new FileWriter(outputFile);
            PrintWriter printWriter = new PrintWriter(writer);

            // Adiciona o cabeçalho ao arquivo de saída
            printWriter.println("countries,video_id,trending_date,title,channel_title,category_id,publish_time,tags,views,likes,dislikes,comment_count,thumbnail_link,comments_disabled,ratings_disabled,video_error_or_removed,description");

            // Loop através dos arquivos CSV
            for (String file : files) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;

                // Ignora o cabeçalho do arquivo de entrada
                reader.readLine();

                // Lê cada linha do arquivo de entrada e a escreve no arquivo de saída, adicionando o país
                while ((line = reader.readLine()) != null) {
                    printWriter.println(file.substring(0, 2) + "," + line);
                }

                // Fecha o leitor do arquivo atual
                reader.close();
            }

            // Fecha o escritor do arquivo de saída
            printWriter.close();
            System.out.println("Arquivo 'videos.csv' criado com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}