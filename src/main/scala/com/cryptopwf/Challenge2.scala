package com.cryptopwf

import com.cryptopwf.util.HexBytesUtil

object Challenge2 {
  def xorhex(hex1: String, hex2: String): String = {
    val bytes1 = HexBytesUtil.hex2bytes(hex1)
    val bytes2 = HexBytesUtil.hex2bytes(hex2)

    val xored: IndexedSeq[Byte] = (bytes1, bytes2).zipped map {(a, b) => (a ^ b).toByte}

    HexBytesUtil.bytes2hex(xored)
  }
}
