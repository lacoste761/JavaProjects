import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*Базовый класс для всех исключений в бизнес логике*/
class CustomException extends Exception
{
    public CustomException(String message)
    {
        super(message);
    }
}

/*Исключение: входной файл не указан*/
class FileIsNotDefinedException extends CustomException
{
    public FileIsNotDefinedException(String message)
    {
        super(message);
    }
}

/*Исключение: неверный формат строки*/
class StringFormatException extends CustomException
{
    public StringFormatException(String message)
    {
        super(message);
    }
}

/*Исключение: неверный формат данных*/
class DataFormatException extends CustomException
{
    public DataFormatException(String message)
    {
        super(message);
    }
}

/*Исключение: недостаточно данных для продолжения работы*/
class NotEnoughDataException extends CustomException
{
    public NotEnoughDataException(String message)
    {
        super(message);
    }
}

/*Исключение: ошибка ввода/вывода*/
class CustomIOException extends CustomException
{
    public CustomIOException(String message)
    {
        super(message);
    }
}

/*Исключение: в файле обнаружены некорректные символы*/
class CustomNumberFormatException extends CustomException
{
    public CustomNumberFormatException(String message)
    {
        super(message);
    }
}

/*Класс для работы с файлом и полученными данными*/
public class FileParser {
    /*Чтение данных из файла*/
    private static List<String> readFile(String fileName) throws IOException, FileIsNotDefinedException
    {
        String line;
        List<String> lines = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        }
        catch (NullPointerException e)
        {
            throw new FileIsNotDefinedException("Input file is not defined.\n" +
                    "ADVICE: Check command line arguments.");
        }

        return lines;
    }

    /*Запись данных в файл*/
    public static void WriteToFile(String fileName, List<String> output) throws IOException
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            for(String string : output)
                writer.write(string);
            writer.close();
        }
    }

    /*Разбор строки на список целых чисел*/
    public static List<Integer> parseStringToIntegers(String s)
    {
        String[] parts = s.split(" ");

        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < parts.length; i++)
            if(!parts[i].isEmpty())
                result.add(Integer.parseInt(parts[i]));

        if(result.size() > 2)
            System.out.println("Warning: Line has a wrong format.");

        return result;
    }

    /*Разбор строки на список десятичных дробей*/
    public static List<Double> parseStringToDoubles(String s, int expectedSize)
    {
        String[] parts = s.split(" ");

        List<Double> result = new ArrayList<>();
        for(int i = 0; i < parts.length; i++)
            if(!parts[i].isEmpty())
                result.add(Double.parseDouble(parts[i]));

        if(result.size() != expectedSize)
            System.out.println("Warning: Line has a wrong format.");

        return result;
    }

    /*Получение списка векторов из файла*/
    public static List<Vector> getVectorsFromFile(String fileName) throws CustomException
    {
        /*=================Чтение из файла====================*/
        List<String> content;
        try
        {
            content = readFile(fileName);
        }
        catch (IOException e)
        {
            throw new CustomIOException(
                    e.getMessage() +
                    "\nADVICE: Check path to file and try again.\n");
        }

        if(content.size() == 0)
            throw new DataFormatException(
                    "File is empty.\n" +
                    "ADVICE: Write in file and try again.");
        /*====================================================*/


        /*=================Обработка первой строки============*/
        List<Integer> firstStringData;
        try
        {
            firstStringData = parseStringToIntegers(content.get(0));
        }
        catch (NumberFormatException e)
        {
            throw new CustomNumberFormatException(
                    e.getMessage() + " is forbidden.\n" +
                    "ADVICE: Make sure the first string contains only integer numbers.");
        }
        if(firstStringData.size() < 2)
            throw new StringFormatException(
                    "Error: First string has a wrong format.\n" +
                    "Insufficient data.\n" +
                    "ADVICE: Correct a file content and try again.");

        int nVectors = firstStringData.get(0);

        if(content.size() < nVectors + 1)
            throw new NotEnoughDataException(
                    "Not enough vectors in file.\n" +
                    "ADVICE: Correct a file content and try again.");
        /*====================================================*/


        /*=================Разбор строк на вектора============*/
        int nDimensions = firstStringData.get(1);
        List<Vector> vectors = new ArrayList<>();
        try
        {
            for(int i = 0; i < nVectors; i++)
                vectors.add(new Vector(parseStringToDoubles(content.get(i + 1), nDimensions), nDimensions));
        }
        catch (NumberFormatException e)
        {
            throw new CustomNumberFormatException(
                    e.getMessage() + " is forbidden." +
                            "ADVICE: Make sure the file contains only numbers.\n");
        }
        /*====================================================*/

        return vectors;
    }
}
