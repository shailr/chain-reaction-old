package generic;

class Vector {

	public int x;
	public int y;

    public Vector()
    {
        x = -1;
        y = -1;
    }

    public Vector(int i, int j)
    {
        x = i;
        y = j;
    }

    public Vector minus(Vector v)
    {
        Vector vector1 = new Vector();
        vector1.x = x - v.x;
        vector1.y = y - v.y;
        return vector1;
    }

    public Vector plus(Vector v)
    {
        Vector vector1 = new Vector();
        vector1.x = x + v.x;
        vector1.y = y + v.y;
        return vector1;
    }

    public void setVals(int i, int j)
    {
        x = i;
        y = j;
    }
}
