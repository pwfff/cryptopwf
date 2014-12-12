package com.cryptopwf

import com.cryptopwf.util.HexBytesUtil
import com.cryptopwf.util.ASCII._

object Challenge4 {
  def decrypt(hexes: List[String]): String = {
    val decrypted = hexes map Challenge3.decrypt

    val scores = decrypted map(s => s -> s.count(_.isASCIILetter))

    scores.maxBy(_._2)._1
  }
}
