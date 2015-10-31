package vu.co.kaiyin.formatting.scientificNotaion

import vu.co.kaiyin.clipboard


/**
 * Created by IDEA on 31/10/15.
 */
package object beautifier {

  private val scientificPattern = """([+-]?\d+(\.\d+)?)[Ee]([+-]?\d+)""".r

//  val date = """(\d\d\d\d)-(\d\d)-(\d\d)""".r
//  val dates = "Important dates in history: 2004-01-20, 1958-09-05, 2010-10-06, 2011-07-15"
//  val firstDate = date findFirstIn dates getOrElse "No date found."
//  val firstYear = for (m <- date findFirstMatchIn dates) yield m group 1
//  val allYears = for (m <- date findAllMatchIn dates) yield m group 1
//  allYears.foreach(println _)
//  val mi = date findAllIn dates
//  val oldies = mi filter (_ => (mi group 1).toInt < 1960) map (s => s"$s: An oldie but goodie.")
//  oldies.foreach(println _)
//  val redacted    = date replaceAllIn (dates, "XXXX-XX-XX")
//  val yearsOnly   = date replaceAllIn (dates, m => m group 1)


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
    s.split("""\s+""").map(snBeautify _).collect({case Some(x) => x}).mkString("\n")
  }

  def snBeautify2(s: String): String = {
    scientificPattern.replaceAllIn(s, x => {
      snBeautify(x.group(0)).getOrElse({
        throw new Exception("Fail to beautify: " + x.group(0))
      })
    })
  }


  def snBeautifyClip(extract: Boolean = false, printOut: Boolean = true): Unit = {
    val beautify: (String) => String = extract match {
      case true => snBeautify1 _
      case false => snBeautify2 _
    }
    clipboard.transform1(beautify)
  }
}
