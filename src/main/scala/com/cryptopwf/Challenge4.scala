package com.cryptopwf

import com.cryptopwf.util.HexBytesUtil
import com.cryptopwf.util.ASCII._

object Challenge4 {
  def decrypt(hexes: List[String]): String = {
    val decrypted = hexes.par.map(Challenge3.decrypt)

    decrypted.maxBy(_.count(_.isASCIILetter))
  }
}
