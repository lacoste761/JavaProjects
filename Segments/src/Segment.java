import java.util.List;


public class Segment
{
    private Vector A;
    private Vector B;

    /**
     * <p>Constructor from double components</p>
     */
    public Segment(List<Double> coordComponents)
    {
        if(coordComponents.size() < 2)
        {

        }
        A = new Vector(coordComponents.get(0), coordComponents.get(1));
        B = new Vector(
                coordComponents.size() > 2
                        ? coordComponents.get(2) : 0,
                coordComponents.size() > 3
                        ? coordComponents.get(3) : 0);
    }

    /**
     * <p>Text representation of vector</p>
     * @return string representation of vector
     */
    public String toString()
    {
        return "([" + A.getX() + "," + A.getY() + "],[" + B.getX() + "," + B.getY() + "])";
    }

    /**
     * <p>Уравнение прямой для этого отрезка</p>
     * @return уравнение прямой
     * @throws IncorrectStraightEqual
     */
    public Straight getStraightEqual() throws IncorrectStraightEqual
    {
        if(A.getX() == B.getX()) // Вертикальный отрезок
        {
            return new Straight(1, 0, -A.getX());
        }

        if(A.getY() == B.getY()) // Горизонтальный отрезок
        {
            return new Straight(0, 1, -A.getY());
        }

        return new Straight(
                1/(B.getX()-A.getX()),
                1/(A.getY()-B.getY()),
                1/(B.getY()-A.getY()) + 1/(A.getX()-B.getX())
        );
    }

    /**
     * <p>Длина отрезка</p>
     */
    public double getLength()
    {
        return Math.sqrt(A.getX()-B.getX())*(A.getX()-B.getX())
                + (A.getY()-B.getY())*(A.getY()-B.getY());
    }
}
