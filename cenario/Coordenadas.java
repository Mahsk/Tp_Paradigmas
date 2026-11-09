package cenario;

import java.util.Objects; # pra comparar coordenada

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
}


@Override
public boolean equals(Object other) {
    if(!(other instanceof Coordenadas)) {
        return false;
    }

    Coordenadas that = (Coordenadas) other;
    return this.x.equals(that.x) && this.y.equals(that.y);
}

@Override
public String toString() {
    return "(" + x + ", " + y + ")";
}