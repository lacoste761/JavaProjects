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
        List<Integer> ints;
        List<Double> doubles;
        String input;


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
        incorrectData.add("3\n");
        incorrectData.add("1 2 3\n");
        incorrectData.add("4 5\n");
        if(Test_InputFileContaintsIncorrectData(incorrectData))success++;counter++;


        input = " 3     7";
        ints = new ArrayList<>();
        ints.add(3);
        ints.add(7);
        if(Test_CompareParsedDataWithReference(FileParser.parseStringToIntegers(input), ints))success++;counter++;


        input = "1.0 1.25     1.5   ";
        doubles = new ArrayList<>();
        doubles.add(1.0);
        doubles.add(1.25);
        doubles.add(1.5);
        if(Test_CompareParsedDataWithReference(FileParser.parseStringToDoubles(input, 3), doubles))success++;counter++;

        System.out.println("TESTS: " + success + "/" + counter);
    }

    private static boolean Test_InputFileNameIsEmpty()
    {
        try
        {
            FileParser.getStraightsFromFile(null);
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
            FileParser.getStraightsFromFile("nonexistent_file");
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
            FileParser.getStraightsFromFile(tempFileName);
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
            FileParser.getStraightsFromFile(tempFileName);
        }
        catch (CustomException e)
        {
            return true;
        }

        file.delete();
        return false;
    }

    private static boolean Test_InputFileContainsCorrectData(List<String> correctData)
    {
        String tempFileName = "temp_file";
        File file = new File(tempFileName);
        try
        {
            file.createNewFile();
            FileParser.WriteToFile(tempFileName, correctData);
        }
        catch (IOException e)
        {
            return false;
        }

        try
        {
            FileParser.getStraightsFromFile(tempFileName);
        }
        catch (CustomException e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        file.delete();
        return true;
    }

    private static <T> boolean Test_CompareParsedDataWithReference(List<T> function, List<T> expected)
    {
        if(function.size() != expected.size())
            return false;

        for(int i = 0; i < function.size(); i++)
            if(!function.get(i).equals(expected.get(i)))
                return false;

        return true;
    }
}
