package vu.co.kaiyin.formatting.html

import java.awt.Desktop
import java.io.{PrintWriter, File}
import java.net.URI

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import org.jsoup.select.Elements

object HtmlRemove {
  private val desktop: Desktop = if(Desktop.isDesktopSupported) Desktop.getDesktop else null
  def rmTextFromDoc(doc: Document, selector: String): String = {
    val selected = doc.select(selector)
    selected.toArray.foreach(elem => elem.asInstanceOf[Element].text(""))
    doc.toString
  }

  def rmTextFromHtml(html: String, selector: String): String = {
    val doc = Jsoup.parse(html)
    rmTextFromDoc(doc, selector)
  }

  def rmTextFromUrl(url: String, selector: String): String = {
    val doc = Jsoup.connect(url).get()
    rmTextFromDoc(doc, selector)
  }

  def rmTextFromUrl1(url: String, selector: String): Unit = {
    val s = rmTextFromUrl(url, selector)
    writeToTempFile1(s, suffix = ".html")
  }

  def writeToFile(file: File, content: String): Unit = {
    Some(new PrintWriter(file)).foreach({
      p =>
        p.write(content)
        p.close()
    })
  }

  def writeToFile1(filename: String, content: String): Unit = {
    Some(new File(filename)).foreach({
      f => writeToFile(f, content)
    })
  }

  def writeToTempFile(content: String, prefix: String = "tmp", suffix: String = ".temp"): Option[URI] = {
    val fOption: Some[File] = Some(File.createTempFile(prefix, suffix))
    fOption match {
      case Some(f) => {
        writeToFile(f, content)
        Some(f.toURI)
      }
      case _ => None
    }
  }

  def writeToTempFile1(content: String, prefix: String = "tmp", suffix: String = ".temp"): Unit = {
    writeToTempFile(content, prefix, suffix).foreach({
      uri => desktop.browse(uri)
    })
  }

}

private object Test {
  import HtmlRemove._
  writeToTempFile1("hello", suffix = ".txt")
  rmTextFromUrl1("https://www.dashingd3js.com/creating-svg-elements-based-on-data", ".line-numbers")
}
