import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class FileCreator {
    public static void main(String[] args) {
        String filename1 = "file1.txt";
        String content1 = "welcome in FCI";
        String filename2 = "file2.txt";
        String content2 = "I am mohamed";
        String filename3 = "file3.txt";
        String content3 = "please give me a full mark in the Assignment";
        String filename4 = "file4.txt";
        String content4 = "city :- giza";
        String filename5 = "file5.txt";
        String content5 = "I am a student in FCI";
        String filename6 = "file6.txt";
        String content6 = "Cosine Similarity";
        String filename7 = "file7.txt";
        String content7 = "TF - IDF";
        String filename8 = "file8.txt";
        String content8 = "web crawler";
        String filename9 = "file9.txt";
        String content9 = "please give me a full mark in the Assignment";
        String filename10 = "file10.txt";
        String content10 = "please give me a full mark in the Assignment";
        buildFile(filename1, content1);
        buildFile(filename2, content2);
        buildFile(filename3, content3);
        buildFile(filename4, content4);
        buildFile(filename5, content5);
        buildFile(filename6, content6);
        buildFile(filename7, content7);
        buildFile(filename8, content8);
        buildFile(filename9, content9);
        buildFile(filename10, content10);
    }

    static void buildFile(String filename, String content) {
        File file = new File(filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}