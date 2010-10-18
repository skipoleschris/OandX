package templemore.onx.version2;

/**
 * @author Chris Turner
 */
public class WinScanner {

    public boolean isWin(final Grid grid) {
        return null != winningToken(grid);
    }

    public Token winningToken(final Grid grid) {
        for ( final Line line : grid.getAllLines() ) {
            int noughtCount = 0;
            int crossCount = 0;
            for ( final Token token : line.getTokens() ) {
                if ( Token.nought == token ) noughtCount++;
                else if ( Token.cross == token ) crossCount++;
            }

            if ( noughtCount == Grid.WIDTH ) return Token.nought;
            if ( crossCount == Grid.WIDTH ) return Token.cross;
        }
        return null;
    }
}
