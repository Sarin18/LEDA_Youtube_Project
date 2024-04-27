public class Main {

    public static void main(String[] args) {

        VideosJuntos.main(args);

        DatasCorretas.main(args);

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
