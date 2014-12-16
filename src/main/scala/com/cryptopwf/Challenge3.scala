package com.cryptopwf

import com.cryptopwf.util.HexBytesUtil
import com.cryptopwf.util.FrequencyAnalysis

object Challenge3 {
  def decrypt(hexInput: String): String = {
    val inputeBytes = HexBytesUtil.hex2bytes(hexInput)

    val allBytes = (0 to 255).view.map(_.toByte)

    val best = allBytes.par.minBy(b => FrequencyAnalysis.englishScore(inputeBytes.map(c => (c ^ b).toChar).mkString))

    val decrypted = inputeBytes.map(_ ^ best)

    decrypted.map(_.toChar).mkString
  }
}
