/**
 * <p>Класс: Вектор/точка на плоскости</p>
 */
public class Vector
{
    private double x; // x coordinate
    private double y; // y coordinate

    /**
     * <p>Constructor from two doubles</p>
     */
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * <p>Get X coordinate</p>
     * @return x coordinate
     */
    public double getX()
    {
        return x;
    }

    /**
     * <p>Get Y coordinate</p>
     * @return y coordinate
     */
    public double getY()
    {
        return x;
    }

    /**
     * <p>Text representation of vector</p>
     * @return string representation of vector
     */
    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    /**
     * <p>Get normalized vector</p>
     */
    public Vector getNormalized()
    {
        return new Vector(x/lenght(), y/lenght());
    }

    /**
     * <p>Get length of vector</p>*/
    public double lenght()
    {
        return Math.sqrt(x*x + y*y);
    }
}