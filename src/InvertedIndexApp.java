import java.util.HashMap;
import java.util.Scanner;

import java.util.*;

public class InvertedIndexApp {
    public static void main(String[] args) throws Exception {
        String[] files = { "file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt", "file6.txt",
                "file7.txt", "file8.txt", "file9.txt", "file10.txt" };
        InvertedIndex index = new InvertedIndex();
        HashMap<String, DictEntry> invertedIndex = index.buildIndex(files);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter a query (set of words):");
            String query = scanner.nextLine();

            // Process the query and get the terms
            String[] terms = query.toLowerCase().split("\\W+");

            // Compute the cosine similarity between each file and the query
            double[] similarities = CosineSimilarity.computeCosineSimilarity(terms, invertedIndex, files);
            // Rank the files based on the cosine similarity values
            List<Map.Entry<String, Double>> rankedFiles = rankFiles(similarities, files);

            // Display the ranked files
            System.out.println("Ranked files based on cosine similarity:");
            for (Map.Entry<String, Double> file : rankedFiles) {
                System.out.println(file.getKey());
                System.out.println("Cosine Similarity:-  " + file.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static List<Map.Entry<String, Double>> rankFiles(double[] similarities, String[] files) {
        int N = files.length;


        // Create a map to store the file names and their corresponding similarities
        Map<String, Double> fileSimilarityMap = new HashMap<>();
        for (int i = 0; i < N; i++) {
            fileSimilarityMap.put(files[i], similarities[i]);
        }

        // Sort the map entries based on similarity values in descending order
        List<Map.Entry<String, Double>> sortedEntries = new ArrayList<>(fileSimilarityMap.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));


        return sortedEntries;
    }
}