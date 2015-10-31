import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[])
    {
        List<String> content = readFile("vectors.txt");
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
            System.out.println(e.getMessage());
        }

        return lines;
    }
}
