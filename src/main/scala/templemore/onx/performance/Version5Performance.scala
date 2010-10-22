package templemore.onx.performance

import templemore.onx.version5.OandXVersion5

/**
 * @author Chris Turner
 */
object Version5Performance {

  def main(args: Array[String]) {
    for (i <- 1 to 500) {
      OandXVersion5.main(args)
    }
  }
}