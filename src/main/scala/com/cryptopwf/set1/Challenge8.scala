package com.cryptopwf.set1

import com.cryptopwf.util.Helpers._

object Challenge8 {
  def detectECB(hexes: IndexedSeq[String]): String = {
    hexes.minBy(_.asHex.grouped(16).toIndexedSeq.distinct.length)
  }
}
