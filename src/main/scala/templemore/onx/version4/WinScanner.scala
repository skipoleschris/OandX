package templemore.onx.version4

/**
 * @author Chris Turner
 */
trait WinScanner {
  this: Grid =>

  def win_? = winningToken != None

  def winningToken = {
    lines.filter { line =>
      line.forall(_ == Some(Nought)) || line.forall(_ == Some(Cross))
    } match {
      case Nil => None
      case x :: xs => x.head
    }
  }
}