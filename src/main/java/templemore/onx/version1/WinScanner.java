package templemore.onx.version1;

/**
 * @author Chris Turner
 */
public class WinScanner {

    public boolean isWin(final Grid grid) {
        return null != winningToken(grid);
    }

    public Token winningToken(final Grid grid) {
        // Check each row
        for ( int row = 0; row < Grid.HEIGHT; row++) {
            int noughtCount = 0;
            int crossCount = 0;
            for ( int col = 0; col < Grid.WIDTH; col++ ) {
                final Token token =  grid.getToken(new Position(row, col));
                if ( Token.nought == token ) noughtCount++;
                else if ( Token.cross == token ) crossCount++;
            }

            if ( noughtCount == Grid.WIDTH ) return Token.nought;
            if ( crossCount == Grid.WIDTH ) return Token.cross;
        }

        // Check each column
        for ( int col = 0; col < Grid.WIDTH; col++ ) {
            int noughtCount = 0;
            int crossCount = 0;
            for ( int row = 0; row < Grid.HEIGHT; row++ ) {
                final Token token =  grid.getToken(new Position(row, col));
                if ( Token.nought == token ) noughtCount++;
                else if ( Token.cross == token ) crossCount++;
            }

            if ( noughtCount == Grid.WIDTH ) return Token.nought;
            if ( crossCount == Grid.WIDTH ) return Token.cross;
        }

        // Check top to bottom diagonal
        int noughtCount = 0;
        int crossCount = 0;
        for ( int col = 0, row = 0; col < Grid.WIDTH && row < Grid.HEIGHT; col++, row++ ) {
            final Token token =  grid.getToken(new Position(row, col));
            if ( Token.nought == token ) noughtCount++;
            else if ( Token.cross == token ) crossCount++;
        }
        if ( noughtCount == Grid.WIDTH ) return Token.nought;
        if ( crossCount == Grid.WIDTH ) return Token.cross;

        // Check bottom to top diagonal
        noughtCount = 0;
        crossCount = 0;
        for ( int col = 0, row = (Grid.HEIGHT - 1); col < Grid.WIDTH && row >= 0; col++, row-- ) {
            final Token token =  grid.getToken(new Position(row, col));
            if ( Token.nought == token ) noughtCount++;
            else if ( Token.cross == token ) crossCount++;
        }
        if ( noughtCount == Grid.WIDTH ) return Token.nought;
        if ( crossCount == Grid.WIDTH ) return Token.cross;

        return null;
    }
}
