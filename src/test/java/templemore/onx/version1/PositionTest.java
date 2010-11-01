package templemore.onx.version1;

import org.testng.annotations.Test;

/**
 * @author Chris Turner
 */
@Test
public class PositionTest {

    public void testPosition() {
        final Position position = new Position(2, 3);
        assert(position.getRow() == 2);
        assert(position.getColumn() == 3);
    }

    public void testEquality() {
        final Position p1 = new Position(1, 2);
        final Position p2 = new Position(1, 2);
        final Position p3 = new Position(2, 1);

        assert(p1.equals(p1));
        assert(p1.equals(p2));
        assert(p1.equals(p3));
    }
}
