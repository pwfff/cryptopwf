package com.cryptopwf.util

object ASCII {
  implicit class CharProperties(val ch: Char) extends AnyVal {
    def isASCIILetter: Boolean = (
      (ch >= 'a' && ch <= 'z') ||
      (ch >= 'A' && ch <= 'Z') ||
      (ch == ' ')
    )
  }
}
