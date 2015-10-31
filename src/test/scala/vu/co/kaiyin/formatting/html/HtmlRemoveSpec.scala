package vu.co.kaiyin.formatting.html

import org.jsoup.Jsoup
import org.scalatest._
import vu.co.kaiyin.formatting.html.HtmlRemove._

class HtmlRemoveSpec extends FlatSpec with Matchers {
  "Remove by css selector" should "work" in {
    val html = """<html><div id = "part1">part1<p class = "part2>part2</p></div></html>"""
    val html1 = """<html><div id = "part1"></div></html>"""
    val html2 = """<html><div id = "part1">part1<p class = "part2></p></div></html>"""
    val doc1Original = Jsoup.parse(html)
    val doc1 = Jsoup.parse(html1)
    val doc2Original = Jsoup.parse(html)
    val doc2 = Jsoup.parse(html2)
    val test1 = rmTextFromDoc(doc1Original, "#part1")
    cleanHtmlSpaces(test1) should be(cleanHtmlSpaces(doc1.toString))
    val test2 = rmTextFromDoc(doc2Original, ".part2")
    cleanHtmlSpaces(test2) should be(cleanHtmlSpaces(doc2.toString))
  }

  def cleanHtmlSpaces(html3: String): String = {
    val htmlSpacePattern1 = """>\s+""".r
    val s1 = htmlSpacePattern1.replaceAllIn(html3, """>""")
    val htmlSpacePattern2 = """\s+<""".r
    val s2 = htmlSpacePattern2.replaceAllIn(s1, """<""")
    s2
  }

  val classPattern = """<(\w+)>([^<>]*)</\1>""".r.unanchored
  """<b>test</b>""" match {
    case classPattern(x@_*) => println(x)
    case x => println(x)
  }
}
