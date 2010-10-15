package templemore.onx.version1;

import java.util.*;

/**
 * @author Chris Turner
 */
public class MoveFinder {

    public Position findBestPosition(final Grid grid, final Token token) {
        final Line[] lines = buildLines(grid);

        Position result = findWinningPosition(lines, token);

        if ( result == null ) {
            result = findBlockingPosition(lines, token);
        }

        if ( result == null ) {
            result = findDoubleWinPosition(lines, token);
        }

        if ( result == null ) {
            result = findDoubleBlockPosition(lines, token);
        }

        if ( result == null ) {
            result = findDoubleFreePosition(lines, token);
        }

        if ( result == null ) {
            result = findEmptyLinePosition(lines, token);
        }

        if ( result == null ) {
            result = findBestEmptyPosition(lines, token);
        }

        if ( result == null ) {
            throw new IllegalStateException();
        }
        return result;
    }

    private Position findWinningPosition(final Line[] lines, final Token token) {
        for ( final Line line : lines ) {
            if ( line.hasOneEmptySpace() && line.isAllSameToken(token) ) {
                return line.getEmptyPosition();
            }
        }
        return null;
    }

    private Position findBlockingPosition(final Line[] lines, final Token token) {
        return findWinningPosition(lines, token.flip());
    }

    private Position findDoubleWinPosition(final Line[] lines, final Token token) {
        final Set<Position> positions = new HashSet<Position>();
        for ( final Line line : filterLinesWithTwoSpaces(lines, token)) {
            for ( final Position position : line.getEmptyPositions() ) {
                if ( positions.contains(position) ) return position;
                else positions.add(position);
            }
        }
        return null;
    }

    private Position findDoubleBlockPosition(final Line[] lines, final Token token) {
        return findDoubleWinPosition(lines, token.flip());
    }

    private Position findDoubleFreePosition(final Line[] lines, final Token token) {
        final Line[] candidates = filterLinesWithTwoSpaces(lines, token);
        for ( final Line line : candidates ) {
            for ( final Position position : line.getEmptyPositions() ) {
                if ( position.equals(Grid.MIDDLE)) return position;
            }
        }

        final Set<Position> emptyLinePositions = filterAllPositionsOnEmptyLines(lines);
        Position firstMatch = null;
        for ( final Line line : candidates ) {
            for ( final Position position : line.getEmptyPositions() ) {
                if ( emptyLinePositions.contains(position) ) return position;
                if ( firstMatch == null ) firstMatch = position;
            }
        }

        return firstMatch;
    }

    private Position findEmptyLinePosition(final Line[] lines, final Token token) {
        final Set<Position> emptyLinePositions = filterAllPositionsOnEmptyLines(lines);

        if ( emptyLinePositions.size() > 5 ) {
            // Introduce some randomness
            final List<Position> ordered = new ArrayList<Position>(emptyLinePositions);
            final Random random = new Random(System.currentTimeMillis());
            return ordered.get(random.nextInt(ordered.size()));
        }

        if ( emptyLinePositions.contains(Grid.MIDDLE) ) return Grid.MIDDLE;

        for ( final Position position : emptyLinePositions ) {
            if ( isCorner(position) ) return position;
        }

        if ( !emptyLinePositions.isEmpty() ) return emptyLinePositions.iterator().next();
        return null;
    }

    private Position findBestEmptyPosition(final Line[] lines, final Token token) {
        final Set<Position> emptyPositions = new HashSet<Position>();
        for ( final Line line : lines ) {
            emptyPositions.addAll(Arrays.asList(line.getEmptyPositions()));
        }
        if ( emptyPositions.contains(Grid.MIDDLE) ) return Grid.MIDDLE;

        for ( final Position position : emptyPositions ) {
            if ( isCorner(position) ) return position;
        }
        if ( !emptyPositions.isEmpty() ) return emptyPositions.iterator().next();
        return null;
    }

    private boolean isCorner(final Position position) {
        return position.equals(Grid.TOP_LEFT) ||
               position.equals(Grid.TOP_RIGHT) ||
               position.equals(Grid.BOTTOM_LEFT) ||
               position.equals(Grid.BOTTOM_RIGHT);
    }

    private Line[] filterLinesWithTwoSpaces(final Line[] lines, final Token token) {
        final List<Line> result = new ArrayList<Line>();
        for ( final Line line : lines ) {
            if ( line.hasTwoEmptySpaces() && line.isAllSameToken(token) ) {
                result.add(line);
            }
        }
        return result.toArray(new Line[result.size()]);
    }

    private Set<Position> filterAllPositionsOnEmptyLines(final Line[] lines) {
        final Set<Position> result = new HashSet<Position>();
        for ( final Line line : lines ) {
            if ( line.hasThreeEmptySpaces() ) {
                result.addAll(Arrays.asList(line.getEmptyPositions()));
            }
        }
        return result;
    }


    private Line[] buildLines(final Grid grid) {
        final Line[] lines = new Line[8];
        lines[0] = new Line(0, -1, new Token[] { grid.getToken(Grid.TOP_LEFT),
                                                 grid.getToken(Grid.TOP_MIDDLE),
                                                 grid.getToken(Grid.TOP_RIGHT)});
        lines[1] = new Line(1, -1, new Token[] { grid.getToken(Grid.MIDDLE_LEFT),
                                                 grid.getToken(Grid.MIDDLE),
                                                 grid.getToken(Grid.MIDDLE_RIGHT)});
        lines[2] = new Line(2, -1, new Token[] { grid.getToken(Grid.BOTTOM_LEFT),
                                                 grid.getToken(Grid.BOTTOM_MIDDLE),
                                                 grid.getToken(Grid.BOTTOM_RIGHT)});
        lines[3] = new Line(-1, 0, new Token[] { grid.getToken(Grid.TOP_LEFT),
                                                 grid.getToken(Grid.MIDDLE_LEFT),
                                                 grid.getToken(Grid.BOTTOM_LEFT)});
        lines[4] = new Line(-1, 1, new Token[] { grid.getToken(Grid.TOP_MIDDLE),
                                                 grid.getToken(Grid.MIDDLE),
                                                 grid.getToken(Grid.BOTTOM_MIDDLE)});
        lines[5] = new Line(-1, 2, new Token[] { grid.getToken(Grid.TOP_RIGHT),
                                                 grid.getToken(Grid.MIDDLE_RIGHT),
                                                 grid.getToken(Grid.BOTTOM_RIGHT)});
        lines[6] = new Line(0, 0, new Token[] { grid.getToken(Grid.TOP_LEFT),
                                                 grid.getToken(Grid.MIDDLE),
                                                 grid.getToken(Grid.BOTTOM_RIGHT)});
        lines[7] = new Line(2, 0, new Token[] { grid.getToken(Grid.BOTTOM_LEFT),
                                                 grid.getToken(Grid.MIDDLE),
                                                 grid.getToken(Grid.TOP_RIGHT)});
        return lines;
    }

    private static class Line {

        private final int row;
        private final int column;
        private final Token[] tokens;

        private Line(int row, int column, Token[] tokens) {
            this.row = row;
            this.column = column;
            this.tokens = tokens;
        }

        public boolean hasOneEmptySpace() {
            return countEmptySpaces() == 1;
        }

        public boolean hasTwoEmptySpaces() {
            return countEmptySpaces() == 2;
        }

        public boolean hasThreeEmptySpaces() {
            return countEmptySpaces() == 3;
        }

        private int countEmptySpaces() {
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
}
