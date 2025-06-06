package Erik;

import java.util.Objects;

public class Field {

    private short x;
    private short y;
    public static final int ISLAND_LENGTH = 20;
    public static final  int ISLAND_WIDTH = 10;

    public Field(short x, short y) {
        this.x = x;
        this.y = y;
    }

    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Field{" +
                "width=" + x +
                ", length=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return x == field.x && y == field.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
