package com.cryptopwf

import com.cryptopwf.util.HexBytesUtil

object Challenge3 {
  def decrypt(hex: String): String = {
    val bytes = HexBytesUtil.hex2bytes(hex)

    val allBytes = (0 to 255).view.map(_.toByte).toArray

    val scores = allBytes map(b => b -> score(bytes, b)) toMap

    val best = scores.maxBy(_._2)._1

    val unencrypted = bytes.map(_ ^ best)

    unencrypted.map(_.toChar).mkString
  }

  def score(bytes: Array[Byte], candidate: Byte): Int = {
    bytes.foldLeft(0) {
      case (total, byte) if ((byte ^ candidate) >= 'a' && (byte ^ candidate) <= 'z') || ((byte ^ candidate) >= 'A' && (byte ^ candidate) <= 'Z') => total + 1
      case (total, _) => total
    }
  }
}
