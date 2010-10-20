package templemore.onx.version5

/**
 * @author Chris Turner
 */
trait MoveFinder {
  this: LineQueryDSL =>

  def findBestPosition(token: Token): Option[Position] = {
    // Winning/Blocking Position
//    find.an(EmptyPosition).on(First).line(Having).one(Position).matching(None).and(Also).two(Positions).matching(Some(token))
//    find.an(EmptyPosition).on(First).line(Having).one(Position).matching(None).and(Also).two(Positions).matching(Some(token.flip))

    // Double Win/Block Position
//    find.an(EmptyPosition).shared(By).lines(Having).two(Positions).matching(None).and(Also).one(Position).matching(Some(token))
//    find.an(EmptyPosition).shared(By).lines(Having).two(Positions).matching(None).and(Also).one(Position).matching(Some(token.flip))

    // Double Free Position
//    find.the(EmptyMiddlePosition).on(Any).line(Having).two(Positions).matching(None).and(Also).one(Position).matching(Some(token))
//    find.an(EmptyPosition).on(First).line(Having).two(Positions).matching(None).and(Also).one(Position).matching(Some(token))

    // Empty Line Position
    //if ( random && freeSpaces > 5 ) {
//      find.a(RandomEmptyPosition).on(Any).line(Having).all(Positions).matching(None)
    //}
//    find.the(EmptyMiddlePosition).on(Any).line(Having).all(Positions).matching(None)
//    find.an(EmptyCornerPosition).on(Any).line(Having).all(Positions).matching(None)
//    find.an(EmptyPosition).on(First).line(Having).all(Positions).matching(None)

    // Best Empty Position
//    find.the(EmptyMiddlePosition).on(Any).line(Having).any(Position).matching(None)
//    find.an(EmptyCornerPosition).on(Any).line(Having).any(Position).matching(None)
//    find.an(EmptyPosition).on(First).line(Having).any(Position).matching(None)

    None
  }


}