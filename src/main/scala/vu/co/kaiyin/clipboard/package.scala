package vu.co.kaiyin

import java.awt.Toolkit
import java.awt.datatransfer.{StringSelection, DataFlavor, Clipboard}


/**
 * Created by IDEA on 31/10/15.
 */
package object clipboard {
  val clipboard = sysClipboard

  private def sysClipboard: Clipboard = {
    val toolkit = Toolkit.getDefaultToolkit
    val clipboard = toolkit.getSystemClipboard
    clipboard
  }

  def getString: String = clipboard.getData(DataFlavor.stringFlavor).asInstanceOf[String]

  def putString(s: String): Unit = {
    val ss = new StringSelection(s)
    clipboard.setContents(ss, ss)
  }

  def transform1(f: String => String): Unit = {
    val s = getString
    f.andThen(putString)(s)
  }

  def transform2(f: String => Option[String]): Unit = {
    val s = getString
    f(s).foreach(putString(_))
  }
}

