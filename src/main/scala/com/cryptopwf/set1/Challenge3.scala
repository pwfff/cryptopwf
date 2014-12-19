package com.cryptopwf.set1

import com.cryptopwf.util.Helpers._
import com.cryptopwf.util.FrequencyAnalysis

object Challenge3 {
  def decrypt(hexInput: String): String = {
    decrypt(hexInput.asHex)
  }

  def decrypt(input: IndexedSeq[Byte]): String = {
    val allBytes = (0 until 256).view.map(_.toByte)

    val best = allBytes.par.minBy(b => FrequencyAnalysis.englishScore(input.map(c => (c ^ b).toChar).mkString))

    val decrypted = input.map(_ ^ best)

    decrypted.map(_.toChar).mkString
  }
}
