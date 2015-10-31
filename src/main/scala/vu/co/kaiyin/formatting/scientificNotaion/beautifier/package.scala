package vu.co.kaiyin.formatting.scientificNotaion

import vu.co.kaiyin.clipboard.SysClip

/**
 * Created by IDEA on 31/10/15.
 */
package object beautifier {

  private val scientificPattern = """([+-]?\d+(\.\d+)?)[Ee]([+-]?\d+)""".r

  private val superscriptMap = Map(
    '0' -> '\u2070',
    '1' -> '\u00b9',
    '2' -> '\u00b2',
    '3' -> '\u00b3',
    '-' -> '⁻'
  ) ++ ('4' to '9').zip('\u2074' to '\u2079').toMap

  type ExponentTuple = (Int, String)

  private def dealWithSign(s: String): String = s.head match {
    case '-' => "-" + dealWithSign(s.tail)
    case '+' => dealWithSign(s.tail)
    case _ => s.dropWhile(_ == '0')
  }

  def snBeautify(s: String): Option[String] = {
    s match {
      case scientificPattern(coef, _, exponent) => {
        val res =
        // the coef part
        dealWithSign(coef) +
        // the x10 part
          '\u00d7' + "10" +
        // the exponent part
          dealWithSign(exponent).map(superscriptMap(_))
        Some(res)
      }
      case _ => None
    }
  }

  def snBeautify1(s: String): String = {
    s.split("\\s").map(snBeautify _).mkString("\n")
  }

  //  def snBeautify(printOut: Boolean): Unit = {
  //    val s1 = SysClip.getString
  //    val s2 = snBeautify1(s1)
  //    if (printOut) println(s2)
  //    SysClip.putString(s2)
  //  }
}
