import java.util.HashMap;

public class CosineSimilarity {
    public static double[] computeCosineSimilarity(String[] terms, HashMap<String, DictEntry> invertedIndex,
                                                   String[] files) {
        int N = files.length;
        double[] scores = new double[N];

        // Calculate the term scores for each document
        for (String term : terms) {
            term = term.toLowerCase();
            if (invertedIndex.containsKey(term)) {
                DictEntry entry = invertedIndex.get(term);
                int tdf = entry.doc_freq;
                double idf = Math.log10(N / (double) tdf);

                Posting posting = entry.pList;
                while (posting != null) {
                    double termScore = (1 + Math.log10((double) posting.dtf)) * idf;
                    scores[posting.docId - 1] += termScore;
                    posting = posting.next;
                }
            }
        }

        // Normalize the scores by the document lengths
        double[] lengths = calculateDocumentLengths(invertedIndex, files);
        for (int i = 0; i < N; i++) {
            scores[i] = scores[i] / lengths[i];
        }

        return scores;
    }

    private static double[] calculateDocumentLengths(HashMap<String, DictEntry> invertedIndex, String[] files) {
        int N = files.length;
        double[] lengths = new double[N];

        for (String term : invertedIndex.keySet()) {
            DictEntry entry = invertedIndex.get(term);
            Posting posting = entry.pList;
            while (posting != null) {
                lengths[posting.docId - 1] += Math.pow((1 + Math.log10((double) posting.dtf)), 2);
                posting = posting.next;
            }
        }

        for (int i = 0; i < N; i++) {
            lengths[i] = Math.sqrt(lengths[i]);
        }

        return lengths;
    }


}


