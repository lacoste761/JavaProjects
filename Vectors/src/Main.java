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

        List<Vector> vectors;
        try {
            System.out.println("Reading file " + inputFile + "...");
            vectors = FileParser.getVectorsFromFile(inputFile);
            System.out.println("File read successful!");
        }
        catch (CustomException e)
        {
            System.out.println(e.getMessage());
            return;
        }

        List<String> output = new ArrayList<>();

        output.addAll(outVectors(vectors));
        output.addAll(outOctahedralNorm(vectors));
        output.addAll(outSphericalNorm(vectors));
        output.addAll(outCubicNorm(vectors));
        output.addAll(outScalars(vectors));
        output.add(outSummary(vectors));

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
    private static List<String> outVectors(List<Vector> vectors)
    {
        List<String> output = new ArrayList<>();

        output.add("\nVECTORS");
        for(int i = 0; i < vectors.size(); i++)
            output.add("\t[" + (i+1)+ "] = " + vectors.get(i));

        return output;
    }

    /*Получить октаэдрические нормы векторов*/
    private static List<String> outOctahedralNorm(List<Vector> vectors)
    {
        List<String> output = new ArrayList<>();

        output.add("\nOCTAHEDRAL NORM");
        for(int i = 0; i < vectors.size(); i++)
            output.add("\t[" + (i+1)+ "].oct = " + vectors.get(i).octahedralNorm());

        return output;
    }

    /*Получить сферические нормы векторов*/
    private static List<String> outSphericalNorm(List<Vector> vectors)
    {
        List<String> output = new ArrayList<>();

        output.add("\nSPHERICAL NORM");
        for(int i = 0; i < vectors.size(); i++)
            output.add("\t[" + (i+1)+ "].sph = " + vectors.get(i).sphericalNorm());

        return output;
    }

    /*Получить кубические нормы векторов*/
    private static List<String> outCubicNorm(List<Vector> vectors)
    {
        List<String> output = new ArrayList<>();

        output.add("\nCUBIC NORM");
        for(int i = 0; i < vectors.size(); i++)
            output.add("\t[" + (i+1)+ "].cub = " + vectors.get(i).cubicNorm());

        return output;
    }

    /*Получить скалярные произведения векторов*/
    private static List<String> outScalars(List<Vector> vectors)
    {
        List<String> output = new ArrayList<>();

        output.add("\nSCALARS");
        for(int i = 0; i < vectors.size(); i++)
            for(int j = i; j < vectors.size(); j++)
                output.add("\t([" + (i+1) + "]*[" + (j+1) + "]) = "
                        + Vector.ScalarProduct(vectors.get(i), vectors.get(j)));

        return output;
    }

    /*Получить суммарный вектор*/
    private static String outSummary(List<Vector> vectors)
    {
        return "\nSUMMARY VECTOR = " + Vector.summaryVector(vectors);
    }
}
