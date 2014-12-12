package com.cryptopwf

import com.cryptopwf.util.HexBytesUtil
import com.cryptopwf.util.ASCII._

object Challenge3 {
  def decrypt(hex: String): String = {
    val bytes = HexBytesUtil.hex2bytes(hex)

    val allBytes = (0 to 255).view.map(_.toByte)

    val scores = allBytes.par.map(b =>
      (b, bytes.map(_ ^ b).count(_.toChar.isASCIILetter)))

    val best = scores.maxBy(_._2)._1

    val decrypted = bytes.map(_ ^ best)

    decrypted.map(_.toChar).mkString
  }
}
