import java.util.List;

public class Vector
{
    private double[] coordinates;

    public Vector(double[] coordinates)
    {
        this.coordinates = coordinates;
    }

    public Vector(List<Double> coordinates, int length)
    {
        this.coordinates = new double[length];
        for(int i = 0; i < Math.min(coordinates.size(), length); i++) // Оставшиеся координаты - нули
            this.coordinates[i] = coordinates.get(i);
    }

    public int size()
    {
        return coordinates.length;
    }

    public double getCoord(int index)
    {
        return coordinates[index];
    }

    public String toString()
    {
        String result = "(";
        for (int i = 0; i < coordinates.length; i++)
        {
            result += coordinates[i];
            if(i+1 == coordinates.length)
                break;
            result += ", ";
        }

        return result + ")";
    }

    public double octahedralNorm()
    {
        /*Октаэдрическая норма - это сумма модулей координат*/
        double result = 0;
        for(double coordinate : coordinates)
            result += Math.abs(coordinate);
        return result;
    }

    public double sphericalNorm()
    {
        /*Сферическая(Евклидова) норма - это корень из суммы квадратов координат*/
        double squad = 0;
        for(double coordinate : coordinates)
            squad += coordinate * coordinate;
        return Math.sqrt(squad);
    }

    public double cubicNorm()
    {
        /*Кубическая норма - это максимальный модуль координаты*/
        double result = 0;
        for(double coordinate : coordinates)
            result = Math.max(Math.abs(coordinate), result);
        return result;
    }

    public static double ScalarProduct(Vector a, Vector b) // throws DifferentDimensionOfVectorsException
    {
        double scalarProduct = 0;

        for(int i = 0; i < a.size(); i++)
            scalarProduct += a.getCoord(i) * b.getCoord(i);

        return scalarProduct;
    }

    public static Vector summaryVector(List<Vector> vectors)
    {
        if(vectors.size() == 0)
        {
            System.out.println("Warning: Attempt to sum empty list of vectors.");
            return zero();
        }

        int vectorLength = vectors.get(0).size();
        double[] summary = new double[vectorLength];
        for(Vector vector : vectors)
            for(int i = 0; i < vectorLength; i++)
                summary[i] += vector.getCoord(i);

        return new Vector(summary);
    }

    public static Vector zero()
    {
        return new Vector(new double[]{0});
    }
}


