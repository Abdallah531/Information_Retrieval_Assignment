import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

class InvertedIndex {
    private HashMap<String, DictEntry> index;

    public InvertedIndex() {
        this.index = new HashMap<>();
    }


    public HashMap<String, DictEntry> buildIndex(String[] filenames) {
        int docId = 1;
        for (String filename : filenames) {
            try (BufferedReader reader = new BufferedReader(new FileReader(new File(filename)))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        word = word.toLowerCase();
                        if (!index.containsKey(word)) {
                            index.put(word, new DictEntry());
                        }
                        DictEntry entry = index.get(word);
                        entry.term_freq++;
                        if (entry.pList == null || entry.pList.docId != docId) {
                            Posting newPosting = new Posting();
                            newPosting.docId = docId;
                            entry.doc_freq++;
                            newPosting.next = entry.pList;
                            entry.pList = newPosting;
                        } else {
                            entry.pList.dtf++;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            docId++;
        }
        return index;
    }


    private void listFilesContainingWord(String word, HashMap<String, DictEntry> index) {
        // Check if the index has been built
        if(index == null){
            System.out.println("Index has not been built\nbuild it first");
            return;
        }
        // Convert the word to lowercase
        word = word.toLowerCase();
        // If the word is not in the index, print a message saying so
        if (!index.containsKey(word)) {
            System.out.println(word + " does not exist in any document.");
        } else {
            // Otherwise, print the names of the files that contain the word
            DictEntry entry = index.get(word);
            Posting posting = entry.pList;
            System.out.println(word + " appears in the following docs:");
            while (posting != null) {
                System.out.println("file" + posting.docId + ".txt");
                posting = posting.next;
            }
        }
    }

    public void search(String word){
        this.listFilesContainingWord(word, index);
    }
}

