package com.cryptopwf

import com.cryptopwf.util.HexBytesUtil
import com.cryptopwf.util.FrequencyAnalysis

object Challenge4 {
  def decrypt(hexes: List[String]): String = {
    val candidates = hexes.par.map(Challenge3.decrypt)

    FrequencyAnalysis.mostEnglishText(candidates)
  }
}
