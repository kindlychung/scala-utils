package vu.co.kaiyin.formatting.scientificNotaion.beautifier

import org.scalatest._
import vu.co.kaiyin.clipboard

class ScientificNotationSpec extends FlatSpec with Matchers {
  private val scientificPattern = """([+-]?\d+(\.\d+)?)[Ee]([+-]?\d+)""".r
  "Regex" should "return correct matches" in {
    for {
      sign1 <- List("+", "-", "")
      int1 <- (0 to 9).map(_.toString)
      point <- List(".", "")
      int2 <- (0 to 9).map(_.toString)
      eSign <- List("E", "e")
      sign2 <- List("+", "-", "")
      int3 <- (0 to 9).map(_.toString)
    } {
      val coef = sign1 + int1 + point + int2
      val exponent = sign2 + int3
      val string = coef + eSign + exponent
      sciMatch(string, coef, exponent)
    }
  }

  def sciMatch(s: String, s1: String, s2: String): Unit = {
    s match {
      case scientificPattern(coef, _, exponent) => {
        coef should be(s1)
        exponent should be(s2)
      }
    }
  }

  "Scientific notation strings" should "be beautified" in {
    snBeautify("4.2e3")  should be (Some("4.2×10³"))
    snBeautify("4.2e-3")  should be (Some("4.2×10⁻³"))
    snBeautify("-4.2e3")  should be (Some("-4.2×10³"))
    snBeautify("-4.2e-3")  should be (Some("-4.2×10⁻³"))
    snBeautify1("-4.2e-3 and -4.2e3") should be ("-4.2×10⁻³\n-4.2×10³")
    snBeautify2("-4.2e-3 and -4.2e3") should be ("-4.2×10⁻³ and -4.2×10³")
    clipboard.putString("-4.2e-3 and -4.2e3")
    snBeautifyClip(true)
    clipboard.getString should be ("-4.2×10⁻³\n-4.2×10³")
    clipboard.putString("-4.2e-3 and -4.2e3")
    snBeautifyClip(false)
    clipboard.getString should be ("-4.2×10⁻³ and -4.2×10³")
  }
}
