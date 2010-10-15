package templemore.onx.version1;

/**
 * @author Chris Turner
 */
public class OandX {

    public static void main(String[] args) {
        final Grid grid = new Grid();
        final WinScanner winScanner = new WinScanner();
        final MoveFinder moveFinder = new MoveFinder();
        Token token = Token.nought;

        while ( !grid.isFull() && !winScanner.isWin(grid) ) {
            System.out.println("Player: " + token.getSymbol());
            grid.addToken(moveFinder.findBestPosition(grid, token), token);
            System.out.println(grid.toString());
            token = token.flip();
        }

        if ( winScanner.isWin(grid) ) {
            System.out.println("Game won by: " + winScanner.winningToken(grid).getSymbol());
        }
        else {
            System.out.println("Game drawn");
        }
    }
}
