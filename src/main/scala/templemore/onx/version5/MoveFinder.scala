package templemore.onx.version5

/**
 * @author Chris Turner
 */
trait MoveFinder {
  this: LineQueryDSL =>

  def findBestPosition(token: Token): Option[Position] = {
    // Winning/Blocking Position
    find.an(emptyPosition).on(first).line(having).one(position).matching(None).and(also).two(positions).matching(Some(token))
    find.an(emptyPosition).on(first).line(having).one(position).matching(None).and(also).two(positions).matching(Some(token.flip))

    // Double Win/Block Position
    find.an(emptyPosition).shared(by).lines(having).two(positions).matching(None).and(also).one(position).matching(Some(token))
    find.an(emptyPosition).shared(by).lines(having).two(positions).matching(None).and(also).one(position).matching(Some(token.flip))

    // Double Free Position
    find.the(emptyMiddlePosition).on(any).line(having).two(positions).matching(None).and(also).one(position).matching(Some(token))
    find.an(emptyPosition).on(first).line(having).two(positions).matching(None).and(also).one(position).matching(Some(token))

    // Empty Line Position
    //if ( random && freeSpaces > 5 ) {
      find.a(randomEmptyPosition).on(any).line(having).all(positions).matching(None)
    //}
    find.the(emptyMiddlePosition).on(any).line(having).all(positions).matching(None)
    find.an(emptyCornerPosition).on(any).line(having).all(positions).matching(None)
    find.an(emptyPosition).on(first).line(having).all(positions).matching(None)

    // Best Empty Position
    find.the(emptyMiddlePosition).on(any).line(having).any(position).matching(None)
    find.an(emptyCornerPosition).on(any).line(having).any(position).matching(None)
    find.an(emptyPosition).on(first).line(having).any(position).matching(None)

    None
  }


}