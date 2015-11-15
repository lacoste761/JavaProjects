import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String args[])
    {
        String inputFile = args.length > 0? args[0] : null;
        String outputFile = args.length > 1? args[1] : null;

        if(inputFile != null && inputFile.equals("unit_tests"))
        {
            UnitTests.TestBusinessLogic();
            return;
        }

        List<Straight> straights;
        try {
            System.out.println("Reading file " + inputFile + "...");
            straights = FileParser.getStraightsFromFile(inputFile);
            System.out.println("File read successful!");
        }
        catch (CustomException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        List<String> output = new ArrayList<>();

        output.addAll(outStraights(straights));
        output.addAll(outNormalizedDirectionVectors(straights));
        output.addAll(outYAxisAngle(straights));
        output.addAll(outIntersectPoints(straights));

        printToConsole(output);

        if(outputFile != null)
            try
            {
                FileParser.WriteToFile(outputFile, output);
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
    }


    /*==============================*/
    /*Приватные методы вывода данных*/
    /*==============================*/

    /*Вывести данные в консоль*/
    private static void printToConsole(List<String> output)
    {
        for(String string : output)
            System.out.println(string);
    }

    /*Получить данные о векторах*/
    private static List<String> outStraights(List<Straight> straights)
    {
        List<String> output = new ArrayList<>();

        output.add("\nSTRAIGHT EQUALS");
        for(int i = 0; i < straights.size(); i++)
            output.add("\t[" + (i+1)+ "] = " + straights.get(i));

        return output;
    }

    /*Получить нормированные направляющие векторы*/
    private static List<String> outNormalizedDirectionVectors(List<Straight> straights)
    {
        List<String> output = new ArrayList<>();

        output.add("\nNORMALIZED DIRECTION VECTORS");
        for(int i = 0; i < straights.size(); i++)
            output.add("\t[" + (i+1)+ "].ndv = " + straights.get(i).normalizedDirectionVector());

        return output;
    }

    /*Получить углы прямых с осью Y*/
    private static List<String> outYAxisAngle(List<Straight> straights)
    {
        List<String> output = new ArrayList<>();

        output.add("\nANGLES");
        for(int i = 0; i < straights.size(); i++)
            output.add("\t[" + (i+1) + "].y-axis-angle = " + straights.get(i).yAxisAngle());

        return output;
    }

    /*Получить точки пересечения прямых*/
    private static List<String> outIntersectPoints(List<Straight> straights)
    {
        List<String> output = new ArrayList<>();

        output.add("\nINTERSECTION POINTS");
        for(int i = 0; i < straights.size(); i++)
            for(int j = i+1; j < straights.size(); j++)
            {
                try
                {
                    output.add("\t([" + (i+1) + "]x[" + (j+1) + "]).intersect = "
                            + straights.get(i).intersectionPoint(straights.get(j)));
                }
                catch (Parallel e)
                {
                    output.add("\t([" + (i+1) + "]x[" + (j+1) + "]) is parallel");
                }
                catch (IncorrectStraightEqual e)
                {

                }
            }

        return output;
    }
}
