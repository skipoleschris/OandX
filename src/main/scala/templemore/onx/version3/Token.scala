package templemore.onx.version3

/**
 * @author Chris Turner
 */
object Token extends Enumeration {

  type Token = Value
  val Nought, Cross = Value

  def symbol(token: Token) = token match {
    case Nought => "O"
    case _ => "X"
  }
}