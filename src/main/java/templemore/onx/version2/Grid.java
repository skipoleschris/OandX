package templemore.onx.version2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public Line[] getAllLines() {
        final Line[] lines = new Line[8];
        lines[0] = new Line(0, -1, new Token[] { getToken(Grid.TOP_LEFT),
                                                 getToken(Grid.TOP_MIDDLE),
                                                 getToken(Grid.TOP_RIGHT)});
        lines[1] = new Line(1, -1, new Token[] { getToken(Grid.MIDDLE_LEFT),
                                                 getToken(Grid.MIDDLE),
                                                 getToken(Grid.MIDDLE_RIGHT)});
        lines[2] = new Line(2, -1, new Token[] { getToken(Grid.BOTTOM_LEFT),
                                                 getToken(Grid.BOTTOM_MIDDLE),
                                                 getToken(Grid.BOTTOM_RIGHT)});
        lines[3] = new Line(-1, 0, new Token[] { getToken(Grid.TOP_LEFT),
                                                 getToken(Grid.MIDDLE_LEFT),
                                                 getToken(Grid.BOTTOM_LEFT)});
        lines[4] = new Line(-1, 1, new Token[] { getToken(Grid.TOP_MIDDLE),
                                                 getToken(Grid.MIDDLE),
                                                 getToken(Grid.BOTTOM_MIDDLE)});
        lines[5] = new Line(-1, 2, new Token[] { getToken(Grid.TOP_RIGHT),
                                                 getToken(Grid.MIDDLE_RIGHT),
                                                 getToken(Grid.BOTTOM_RIGHT)});
        lines[6] = new Line(0, 0, new Token[] { getToken(Grid.TOP_LEFT),
                                                getToken(Grid.MIDDLE),
                                                getToken(Grid.BOTTOM_RIGHT)});
        lines[7] = new Line(2, 0, new Token[] { getToken(Grid.BOTTOM_LEFT),
                                                getToken(Grid.MIDDLE),
                                                getToken(Grid.TOP_RIGHT)});
        return lines;
    }

    public boolean isCorner(final Position position) {
        return position.equals(TOP_LEFT) ||
               position.equals(TOP_RIGHT) ||
               position.equals(BOTTOM_LEFT) ||
               position.equals(BOTTOM_RIGHT);
    }

    public List<Position> getAllPositionsOnEmptyLines() {
        final List<Position> result = new ArrayList<Position>();
        for ( final Line line : getAllLines() ) {
            if ( line.emptySpaces() == 3 ) {
                result.addAll(Arrays.asList(line.getEmptyPositions()));
            }
        }
        return result;
    }

    public Line[] getLinesWithMatchingTokenAndTwoSpaces(final Token token) {
        final List<Line> result = new ArrayList<Line>();
        for ( final Line line : getAllLines() ) {
            if ( line.emptySpaces() == 2 && line.isAllSameToken(token) ) {
                result.add(line);
            }
        }
        return result.toArray(new Line[result.size()]);
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
