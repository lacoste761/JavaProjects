import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[])
    {
        List<String> content = readFile("vectors.txt");
        if(content == null)
        {
            System.out.println("File not found.\nCheck path to file and try again.\n");
            return;
        }
        if(content.size() == 0)
        {
            System.out.println("File is empty.\n" +
                    "Write in file and try again.\n");
            return;
        }

        try{
            int numOfVectors = parseNum(content.get(0));
        }
        catch (NumberFormatException e)
        {
            System.out.println("First string should contains a number of vectors.\n" +
                    "Correct a file content and try again.\n");
            return;
        }
    }

    private static List<String> readFile(String fileName)
    {
        String line;
        List<String> lines = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch(IOException e)
        {
            return null;
        }

        return lines;
    }

    private static int parseNum(String firstString)
    {
        return Integer.parseInt(firstString);
    }
}
