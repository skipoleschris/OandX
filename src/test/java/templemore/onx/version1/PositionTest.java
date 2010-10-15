package templemore.onx.version1;

import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Chris Turner
 */
@Test
public class PositionTest {

    public void testPosition() {
        final Position position = new Position(2, 3);
        assertThat(position.getRow(), equalTo(2));
        assertThat(position.getColumn(), equalTo(3));
    }

    public void testEquality() {
        final Position p1 = new Position(1, 2);
        final Position p2 = new Position(1, 2);
        final Position p3 = new Position(2, 1);

        assertThat(p1, equalTo(p1));
        assertThat(p1, equalTo(p2));
        assertThat(p1, not(equalTo(p3)));
    }
}
