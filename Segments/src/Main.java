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

        List<Segment> segments;
        try {
            System.out.println("Reading file " + inputFile + "...");
            segments = FileParser.getSegmentsFromFile(inputFile);
            System.out.println("File read successful!");
        }
        catch (CustomException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        List<String> output = new ArrayList<>();

        output.addAll(outSegments(segments));
        output.addAll(outNormalizedDirectionVectors(segments));
        output.addAll(outSegmentLength(segments));
        output.addAll(outIntersects(segments));

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
    private static List<String> outSegments(List<Segment> segments)
    {
        List<String> output = new ArrayList<>();

        output.add("\nSEGMENTS");
        for(int i = 0; i < segments.size(); i++)
            output.add("\t[" + (i+1)+ "] = " + segments.get(i).toString());

        return output;
    }

    /*Получить нормированные направляющие векторы*/
    private static List<String> outNormalizedDirectionVectors(List<Segment> segments)
    {
        List<String> output = new ArrayList<>();

        output.add("\nNORMALIZED DIRECTION VECTORS");
        try
        {
            for(int i = 0; i < segments.size(); i++)
                output.add("\t[" + (i+1)+ "].ndv = " + segments.get(i).getStraightEqual().normalizedDirectionVector());
        }
        catch (IncorrectStraightEqual e)
        {

        }

        return output;
    }

    /*Получить длины отрезков*/
    public static List<String> outSegmentLength(List<Segment> segments)
    {
        List<String> output = new ArrayList<>();

        output.add("\nLENGTH OF SEGMENTS");
        for(int i = 0; i < segments.size(); i++)
            output.add("\t[" + (i+1) + "].length = " + segments.get(i).getLength());

        return output;
    }

    /*Получить пересечения отрезков*/
    private static List<String> outIntersects(List<Segment> segments)
    {
        List<String> output = new ArrayList<>();

        output.add("\nINTERSECTIONS");
        for(int i = 0; i < segments.size(); i++)
            for(int j = i+1; j < segments.size(); j++)
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
