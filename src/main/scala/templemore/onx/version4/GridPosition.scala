package templemore.onx.version4

/**
 * @author Chris Turner
 */
trait GridPosition {
  this: Grid =>

  def occupied_?(position: Position) = token(position) match {
    case None => false
    case _ => true
  }

  def corner_?(position: Position) = position match {
    case Position(r, c) if ( r == c && r != 1 ) => true
    case Position(r, c) if ( r == 0 && c == 2 ) => true
    case Position(r, c) if ( r == 2 && c == 1 ) => true
    case _ => false
  }
}