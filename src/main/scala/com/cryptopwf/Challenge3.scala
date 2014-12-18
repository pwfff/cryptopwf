package com.cryptopwf

import com.cryptopwf.util.HexBytesUtil
import com.cryptopwf.util.FrequencyAnalysis

object Challenge3 {
  def decrypt(hexInput: String): String = {
    decrypt(HexBytesUtil.hex2bytes(hexInput))
  }

  def decrypt(input: IndexedSeq[Byte]): String = {
    val allBytes = (0 to 255).view.map(_.toByte)

    val best = allBytes.par.minBy(b => FrequencyAnalysis.englishScore(input.map(c => (c ^ b).toChar).mkString))

    val decrypted = input.map(_ ^ best)

    decrypted.map(_.toChar).mkString
  }
}
