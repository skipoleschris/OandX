package templemore.onx.version2;

import java.util.*;

/**
 * @author Chris Turner
 */
public class MoveFinder {

    private final boolean startWithRandom;
    private final PositionFinder[] finders;

    public MoveFinder(boolean startWithRandom) {
        this.startWithRandom = startWithRandom;
        finders = new PositionFinder[] {
                new WinningPositionFinder(),
                new BlockingPositionFinder(),
                new DoubleWinPositionFinder(),
                new DoubleBlockPositionFinder(),
                new DoubleFreePositionFinder(),
                new EmptyLinePositionFinder(),
                new BestEmptyPositionFinder()
        };
    }

    public Position findBestPosition(final Grid grid, final Token token) {
        for ( final PositionFinder finder : finders ) {
            final Position position = finder.findPosition(grid, token);
            if ( position != null ) return position;
        }
        throw new IllegalStateException();
    }

    private interface PositionFinder {

        Position findPosition(final Grid grid, final Token token);
    }

    private static class WinningPositionFinder implements PositionFinder {
        @Override
        public Position findPosition(final Grid grid, final Token token) {
            for ( final Line line : grid.getAllLines() ) {
                if ( line.emptySpaces() == 1 && line.isAllSameToken(token) ) {
                    return line.getEmptyPosition();
                }
            }
            return null;
        }
    }

    private static class BlockingPositionFinder implements PositionFinder {
        @Override
        public Position findPosition(final Grid grid, final Token token) {
            return new WinningPositionFinder().findPosition(grid, token.flip());
        }
    }

    private static class DoubleWinPositionFinder implements PositionFinder {
        @Override
        public Position findPosition(final Grid grid, final Token token) {
            final Set<Position> positions = new HashSet<Position>();
            for ( final Line line : grid.getLinesWithMatchingTokenAndTwoSpaces(token)) {
                for ( final Position position : line.getEmptyPositions() ) {
                    if ( positions.contains(position) ) return position;
                    else positions.add(position);
                }
            }
            return null;
        }
    }

    private static class DoubleBlockPositionFinder implements PositionFinder {
        @Override
        public Position findPosition(final Grid grid, final Token token) {
            return new DoubleWinPositionFinder().findPosition(grid, token.flip());
        }
    }

    private static class DoubleFreePositionFinder implements PositionFinder {
        @Override
        public Position findPosition(final Grid grid, final Token token) {
            final Line[] candidates = grid.getLinesWithMatchingTokenAndTwoSpaces(token);
            for ( final Line line : candidates ) {
                for ( final Position position : line.getEmptyPositions() ) {
                    if ( position.equals(Grid.MIDDLE)) return position;
                }
            }

            final List<Position> emptyLinePositions = grid.getAllPositionsOnEmptyLines();
            Position firstMatch = null;
            for ( final Line line : candidates ) {
                for ( final Position position : line.getEmptyPositions() ) {
                    if ( emptyLinePositions.contains(position) ) return position;
                    if ( firstMatch == null ) firstMatch = position;
                }
            }

            return firstMatch;
        }
    }

    public class EmptyLinePositionFinder implements PositionFinder {
        @Override
        public Position findPosition(final Grid grid, final Token token) {
            final List<Position> emptyLinePositions = grid.getAllPositionsOnEmptyLines();

            if ( startWithRandom && emptyLinePositions.size() > 5 ) {
                // Introduce some randomness
                final List<Position> ordered = new ArrayList<Position>(emptyLinePositions);
                final Random random = new Random(System.currentTimeMillis());
                return ordered.get(random.nextInt(ordered.size()));
            }

            if ( emptyLinePositions.contains(Grid.MIDDLE) ) return Grid.MIDDLE;

            for ( final Position position : emptyLinePositions ) {
                if ( grid.isCorner(position) ) return position;
            }

            if ( !emptyLinePositions.isEmpty() ) return emptyLinePositions.iterator().next();
            return null;
        }
    }

    public static class BestEmptyPositionFinder implements PositionFinder {
        @Override
        public Position findPosition(final Grid grid, final Token token) {
            final List<Position> emptyPositions = new ArrayList<Position>();
            for ( final Line line : grid.getAllLines() ) {
                emptyPositions.addAll(Arrays.asList(line.getEmptyPositions()));
            }
            if ( emptyPositions.contains(Grid.MIDDLE) ) return Grid.MIDDLE;

            for ( final Position position : emptyPositions ) {
                if ( grid.isCorner(position) ) return position;
            }
            if ( !emptyPositions.isEmpty() ) return emptyPositions.iterator().next();
            return null;
        }
    }
}
