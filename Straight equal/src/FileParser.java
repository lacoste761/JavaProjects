import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Базовый класс для всех исключений в бизнес логике</p>
 */
class CustomException extends Exception
{
    public CustomException(String message)
    {
        super(message);
    }
}

/**
 * <p>Исключение: входной файл не указан</p>
 */
class FileIsNotDefinedException extends CustomException
{
    public FileIsNotDefinedException(String message)
    {
        super(message);
    }
}

/**
 * <p>Исключение: неверный формат строки</p>
 */
class StringFormatException extends CustomException
{
    public StringFormatException(String message)
    {
        super(message);
    }
}

/**
 * <p>Исключение: неверный формат данных</p>
 */
class DataFormatException extends CustomException
{
    public DataFormatException(String message)
    {
        super(message);
    }
}

/**
 * <p>Исключение: недостаточно данных для продолжения работы</p>
 */
class NotEnoughDataException extends CustomException
{
    public NotEnoughDataException(String message)
    {
        super(message);
    }
}

/**
 * <p>Исключение: ошибка ввода/вывода</p>
 */
class CustomIOException extends CustomException
{
    public CustomIOException(String message)
    {
        super(message);
    }
}

/**
 * <p>Исключение: в файле обнаружены некорректные символы</p>
 */
class CustomNumberFormatException extends CustomException
{
    public CustomNumberFormatException(String message)
    {
        super(message);
    }
}

/**
 * <p>Класс для работы с файлом и полученными данными</p>
 */
public class FileParser {
    /**
     * <p>Чтение данных из файла</p>
     * @param fileName имя файла для чтения
     * @return Список строк из файла
     */
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

    /**
     * <p>Запись данных в файл</p>
     * @param fileName output file name
     * @param output string for out
     */
    public static void WriteToFile(String fileName, List<String> output) throws IOException
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)))
        {
            for(String string : output)
            {
                writer.write(string);
                writer.newLine();
            }

            writer.close();
        }
    }

    /**
     * <p>Разбор строки на список целых чисел</p>
     * @param s строка, которую нужно разобрать на числа
     * @return список чисел из входной строки
     */
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

    /**
     * <p>Разбор строки на список десятичных дробей</p>
     * @param s строка, которую нужно разобрать на числа
     * @return список чисел из входной строки
     * */
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

    /**
     * <p>Получение списка прямых из файла</p>
     * @param fileName имя файла, в котором лежат вектора
     * @return список прямых, считанных из файла
     * */
    public static List<Straight> getStraightsFromFile(String fileName) throws CustomException
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
        if(firstStringData.size() < 1)
            throw new StringFormatException(
                    "Error: First string has a wrong format.\n" +
                    "Insufficient data.\n" +
                    "ADVICE: Correct a file content and try again.");

        int nStraights = firstStringData.get(0);

        if(content.size() < nStraights + 1)
            throw new NotEnoughDataException(
                    "Not enough straights in file.\n" +
                    "ADVICE: Correct a file content and try again.");
        /*====================================================*/


        /*=========Разбор строк на коэффициенты прямых========*/
        List<Straight> straights = new ArrayList<>();
        try
        {
            for(int i = 0; i < nStraights; i++)
                straights.add(new Straight(parseStringToDoubles(content.get(i + 1), 3)));
        }
        catch (NumberFormatException e)
        {
            throw new CustomNumberFormatException(
                    e.getMessage() + " is forbidden." +
                            "ADVICE: Make sure the file contains only numbers.\n");
        }
        catch (IncorrectStraightEqual e)
        {
            throw new DataFormatException(
                    "Some equal is incorrect.\n" +
                            "ADVICE : Make sure the file contains correct equals only.\n");
        }
        /*====================================================*/

        return straights;
    }
}
