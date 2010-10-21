package templemore.onx.version6

/**
 * @author Chris Turner
 */
sealed trait Token {
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

