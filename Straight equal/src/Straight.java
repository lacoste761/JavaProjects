import java.util.List;

/**
 * <p>Исключение: прямые параллельны</p>
 */
class Parallel extends Exception
{
    public Parallel()
    { }
}

/**
 * <p>Исключение: уравнение прямой некорректно</p>
 */
class IncorrectStraightEqual extends Exception
{
    public IncorrectStraightEqual(){}
}

/**
 * <p>Класс: Уравнение прямой</p>
 */
public class Straight
{
    private double A; // x coefficient
    private double B; // y coefficient
    private double C; // free member

    /**
     * <p>Constructor from list of doubles</p>
     * @param coefficients list of coefficients
     */
    public Straight(List<Double> coefficients) throws IncorrectStraightEqual
    {
        if(coefficients.size() < 2) throw new IncorrectStraightEqual();
        if(coefficients.get(0) == 0 && coefficients.get(1) == 0) throw new IncorrectStraightEqual();

        A = coefficients.get(0);
        B = coefficients.get(1);
        C = coefficients.size() > 2? coefficients.get(2) : 0;
    }

    /**
     * <p>Text representation of straight</p>
     * @return string representation of equal
     */
    public String toString()
    {
        return
                A + "x"
                + (B >= 0? (" + " + B) : " - " + (-B)) + "y"
                + (C >= 0? (" + " + C) : " - " + (-C))
                + " = 0";
    }

    /**
     * <p>Normalized direction vector</p>
     * @return Vector - normalized direction vector
     */
    public Vector normalizedDirectionVector()
    {
        return new Vector(-B, A).getNormalized();
    }

    /**
     * <p>Angle between straight and y-axis</p>
     *@return Double - angle between straight and y-axis in degrees
     */
    public double yAxisAngle()
    {
        if(B == 0)
            return 0;

        return 90 - (Math.atan(A/-B) * 180 / Math.PI); // 90 - arctg(y/x);
    }

    /**
     * <p>Intersection point</p>
     * @param straight other straight
     * @return  Vector - point of intersecting
     */
    public Vector intersectionPoint(Straight straight) throws IncorrectStraightEqual, Parallel
    {
        double D = straight.A;
        double E = straight.B;
        double F = straight.C;

        double x, y;

        if((A == 0 && B == 0) || (D == 0 && E == 0))
            throw new IncorrectStraightEqual();

        if(this.isParallel(straight))
            throw new Parallel();

        if(A == 0)
        {
            y = -C/B;
            x = (-E*y - F)/D;
            return new Vector(x, y);
        }

        if(D == 0)
        {
            y = -F/E;
            x = (-B*y - C)/A;
            return new Vector(x, y);
        }

        if(B == 0)
        {
            x = -C/A;
            y = (-D*x - F)/E;
            return new Vector(x, y);
        }

        if(E == 0)
        {
            x = -F/D;
            y = (-A*x - C)/B;
            return new Vector(x, y);
        }

        y = (F/D + C/A)/(E/D - B/A);
        x = -B/A * y - C/A;
        return new Vector(x, y);
    }

    /**
     * <p>Checking parallelism</p>
     * @param straight other straight
     * @return true, if straights is parallel or false, if intersects in point
     */
    public boolean isParallel(Straight straight)
    {
        if(A == 0 && straight.A == 0)
            return true;

        if(B == 0 && straight.B == 0)
            return  true;

        if(A / straight.A == B / straight.B)
            return true;

        return false;
    }
}
