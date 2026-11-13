package cenario;

public class Coordenadas {
    private int x;
    private int y;

    public Coordenadas(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object other) {
        if(!(other instanceof Coordenadas)) {
            return false;
        }

        Coordenadas that = (Coordenadas) other;
        return ((this.x == that.x) && (this.y == that.y));
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
