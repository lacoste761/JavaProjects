import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UnitTests
{
    public static void TestBusinessLogic()
    {
        int counter = 0;
        int success = 0;
        List<String> incorrectData;

        if(Test_InputFileNameIsEmpty())success++;counter++;
        if(Test_InputFileDoesNotExists())success++;counter++;
        if(Test_InputFileIsEmpty())success++;counter++;


        incorrectData = new ArrayList<>();
        incorrectData.add("1 2 3 f 5\n");
        incorrectData.add("sd j2 12\n");
        if(Test_InputFileContaintsIncorrectData(incorrectData))success++;counter++;


        incorrectData.clear();
        incorrectData.add("5");
        if(Test_InputFileContaintsIncorrectData(incorrectData))success++;counter++;


        incorrectData.clear();
        incorrectData.add("3 3\n");
        incorrectData.add("1 2 3\n");
        incorrectData.add("4 5\n");
        if(Test_InputFileContaintsIncorrectData(incorrectData))success++;counter++;

        System.out.println("TESTS: " + success + "/" + counter);
    }

    private static boolean Test_InputFileNameIsEmpty()
    {
        try
        {
            FileParser.getVectorsFromFile(null);
        }
        catch (CustomException e)
        {
            return true;
        }
        return false;
    }

    private static boolean Test_InputFileDoesNotExists()
    {
        try
        {
            FileParser.getVectorsFromFile("nonexistent_file");
        }
        catch (CustomException e)
        {
            return true;
        }
        return false;
    }

    private static boolean Test_InputFileIsEmpty()
    {
        String tempFileName = "temp_file";
        File file = new File(tempFileName);
        try
        {
            file.createNewFile();
        }
        catch (IOException e)
        {
            return false;
        }

        try
        {
            FileParser.getVectorsFromFile(tempFileName);
        }
        catch (CustomException e)
        {
            return true;
        }

        file.delete();
        return false;
    }

    private static boolean Test_InputFileContaintsIncorrectData(List<String> incorrectData)
    {
        String tempFileName = "temp_file";
        File file = new File(tempFileName);
        try
        {
            file.createNewFile();
            FileParser.WriteToFile(tempFileName, incorrectData);
        }
        catch (IOException e)
        {
            return false;
        }

        try
        {
            FileParser.getVectorsFromFile(tempFileName);
        }
        catch (CustomException e)
        {
            System.out.println(e.getMessage());
            return true;
        }

        file.delete();
        return false;
    }
}
