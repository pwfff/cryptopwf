package com.cryptopwf

import com.cryptopwf.util.HexBytesUtil

object Challenge3 {
  implicit class CharProperties(val ch: Char) extends AnyVal {
    def isASCIILetter: Boolean = (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')
  }

  def decrypt(hex: String): String = {
    val bytes = HexBytesUtil.hex2bytes(hex)

    val allBytes = (0 to 255).view.map(_.toByte)

    val scores = allBytes map(b => b -> score(bytes, b)) toMap

    val best = scores.maxBy(_._2)._1

    val decrypted = bytes.map(_ ^ best)

    decrypted.map(_.toChar).mkString
  }

  def score(bytes: Array[Byte], candidate: Byte): Int = {
    val decrypted = bytes.map(_ ^ candidate)

    decrypted.foldLeft(0) {
      case (total, byte) if byte.toChar.isASCIILetter => total + 1
      case (total, _) => total
    }
  }
}
