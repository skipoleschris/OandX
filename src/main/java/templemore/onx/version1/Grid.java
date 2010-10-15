package templemore.onx.version1;

/**
 * @author Chris Turner
 */
public class Grid {

    public static final Position TOP_LEFT = new Position(0, 0);
    public static final Position TOP_MIDDLE = new Position(0, 1);
    public static final Position TOP_RIGHT = new Position(0, 2);
    public static final Position MIDDLE_LEFT = new Position(1, 0);
    public static final Position MIDDLE = new Position(1, 1);
    public static final Position MIDDLE_RIGHT = new Position(1, 2);
    public static final Position BOTTOM_LEFT = new Position(2, 0);
    public static final Position BOTTOM_MIDDLE = new Position(2, 1);
    public static final Position BOTTOM_RIGHT = new Position(2, 2);

    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;

    private final Token[][] values;

    public Grid() {
        values = new Token[HEIGHT][WIDTH];
    }

    public Token getToken(final Position position) {
        checkPosition(position);
        return values[position.getRow()][position.getColumn()];
    }

    public void addToken(final Position position, final Token token) {
        if ( isPositionOccupied(position) ) {
            throw new IllegalStateException();
        }
        values[position.getRow()][position.getColumn()] = token;
    }

    public boolean isPositionOccupied(final Position position) {
        checkPosition(position);
        return null != values[position.getRow()][position.getColumn()];
    }

    private void checkPosition(final Position position) {
        if ( position.getRow() >= HEIGHT || position.getColumn() >= WIDTH ) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isFull() {
        for ( int row = 0; row < HEIGHT; row++ ) {
            for ( int col = 0; col < WIDTH; col++ ) {
                if ( null == values[row][col] ) return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for ( int row = 0; row < HEIGHT; row++ ) {
            sb.append("   |   |   \n");
            for ( int col = 0; col < WIDTH; col++ ) {
                sb.append(' ');
                if ( values[row][col] == null ) sb.append(' ');
                else sb.append(values[row][col].getSymbol());
                sb.append(' ');
                if ( col < (WIDTH - 1) ) sb.append('|');
            }
            sb.append("\n   |   |   \n");
            if ( row < (HEIGHT - 1) ) sb.append("-----------\n");
        }
        return sb.toString();
    }

    public static Grid build(final String pattern) {
        final Grid grid = new Grid();
        final String[] lines = pattern.split("\n");
        if (Grid.HEIGHT != lines.length) {
            throw new IllegalArgumentException("Grid does not have enough lines");
        }
        for ( int row = 0; row < lines.length; row++ ) {
            if (Grid.WIDTH != lines[row].length()) {
                throw new IllegalArgumentException("Grid does not have enough columns");
            }
            for ( int col = 0; col < lines[row].length(); col++ ) {
                switch (lines[row].charAt(col)) {
                    case 'O':
                        grid.addToken(new Position(row, col), Token.nought);
                        break;
                    case 'X':
                        grid.addToken(new Position(row, col), Token.cross);
                        break;
                }
            }
        }
        return grid;
    }
}
