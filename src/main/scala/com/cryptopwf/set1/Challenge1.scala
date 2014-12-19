package com.cryptopwf.set1

import com.cryptopwf.util.HexBytesUtil
import sun.misc.BASE64Encoder

object Challenge1 {
  val encoder = new BASE64Encoder()

  def hex2base64(hex: String): String = {
    val bytes = HexBytesUtil.hex2bytes(hex)
    encoder.encode(bytes.toArray)
  }
}
