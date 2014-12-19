package com.cryptopwf.set1

import com.cryptopwf.util.HexBytesUtil

object Challenge8 {
  def detectECB(hexes: IndexedSeq[String]): String = {
    val bytes = hexes map HexBytesUtil.hex2bytes

    HexBytesUtil.bytes2hex(bytes.minBy(_.grouped(16).toIndexedSeq.distinct.length))
  }
}
