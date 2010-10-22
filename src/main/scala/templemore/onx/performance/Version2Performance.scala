package templemore.onx.performance

import templemore.onx.version2.OandX

/**
 * @author Chris Turner
 */
object Version2Performance {

  def main(args: Array[String]) {
    for (i <- 1 to 500) {
      OandX.main(args)
    }
  }
}