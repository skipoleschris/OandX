package templemore.onx.version1;

/**
 * @author Chris Turner
 */
public enum Token {

    nought("O"),
    cross("X");

    private final String symbol;

    Token(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public Token flip() {
        if ( this == nought ) return cross;
        else return nought;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
