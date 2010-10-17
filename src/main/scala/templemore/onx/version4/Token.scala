package templemore.onx.version4

/**
 * @author Chris Turner
 */
trait Token {
  def flip(): Token
  def symbol(): String
}

case object Nought extends Token {
  def flip() = Cross
  def symbol() = "O"
}

case object Cross extends Token {
  def flip() = Nought
  def symbol() = "X"
}

