package templemore.onx.version2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chris Turner
 */
class Line {

    private final int row;
    private final int column;
    private final Token[] tokens;

    public Line(int row, int column, Token[] tokens) {
        this.row = row;
        this.column = column;
        this.tokens = tokens;
    }

    public Token[] getTokens() {
        return tokens;
    }

    public int emptySpaces() {
        int empty = 0;
        for ( final Token token : tokens ) {
            if ( token == null ) empty++;
        }
        return empty;
    }

    public Position getEmptyPosition() {
        return getEmptyPositions()[0];
    }

    public Position[] getEmptyPositions() {
        final List<Position> result = new ArrayList<Position>();
        for ( int i = 0; i < tokens.length; i++ ) {
            if ( tokens[i] == null ) {
                if ( column == -1 ) result.add(new Position(row, i));
                else if ( row == -1 ) result.add(new Position(i, column));
                else if ( i == 0 ) result.add(new Position(row, column));
                else if ( i == 1 ) result.add(new Position(1, 1));
                else if ( row == 0 ) result.add(new Position(2, 2));
                else result.add(new Position(column, row));
            }
        }
        return result.toArray(new Position[result.size()]);
    }

    public boolean isAllSameToken(final Token token) {
        for ( final Token t : tokens ) {
            if ( (t != null) && (!t.equals(token)) ) return false;
        }
        return true;
    }
}
