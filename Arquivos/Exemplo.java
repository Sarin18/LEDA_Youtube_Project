public class Main {

    public static void main(String[] args) {

        /*VideosJuntos.main(args);*/ //Comenta essa linha e executa o VideoJuntos.java direto do arquivo dele

        /*DatasCorretas.main(args);*/ //Comenta essa linha e executa o DatasCorretas.java direto do arquivo dele, após gerar o arquivo da linha 5
      
        /*Após os 2 primeiros arquivos gerarem o videos.csv e videos_T1.csv, você entra no videos_T1.csv e diminui a quantidade de dados 
        do .csv para agilizar a execução do código. Após a redução dos dados, você executa o arquivo Main com as linhas ainda comentadas
        para evitar reset do .csv reduzido. Caso não veja necessidade para isso, só executar diretamente o Main sem fazer nenhuma mudança e esperar.*/
      
        FiltroVideosLikesDeslikes.main(args);

        HeapSortChannels.main(args);
        InsertionSortChannels.main(args);
        MergeSortChannels.main(args);
        QuickSortChannels.main(args);
        QuickSortMediana3Channels.main(args);
        SelectionSortChannels.main(args);

        //Counting sort se faz necessário apenas na contagem de comentarios. Nos outros não tem como cogitar ele de nenhum modo.
        CountingSortComment.main(args); 
        HeapSortComment.main(args);
        InsertionSortComment.main(args);
        MergeSortComment.main(args);
        QuickSortComment.main(args);
        QuickSortMediana3Comment.main(args);
        SelectionSortComment.main(args);

        HeapSortDatasEmAlta.main(args);
        InsertionSortDatasEmAlta.main(args);
        MergeSortDatasEmAlta.main(args);
        QuickSortDatasEmAlta.main(args);
        QuickSortMediana3DatasEmAlta.main(args);
        SelectionSortDatasEmAlta.main(args);
        
        System.out.println("Processo concluído.");
    }
}
