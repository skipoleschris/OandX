package templemore.onx.version2;

/**
 * @author Chris Turner
 */
public class Position {

    private final int column;
    private final int row;

    public Position(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;

        final Position position = (Position) o;

        if (column != position.column) return false;
        return row == position.row;

    }

    @Override
    public int hashCode() {
        int result = column;
        result = 31 * result + row;
        return result;
    }

    @Override
    public String toString() {
        return "row: " + row + ", column: " + column;
    }
}

