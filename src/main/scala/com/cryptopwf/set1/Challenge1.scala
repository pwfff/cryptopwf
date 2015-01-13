package com.cryptopwf.set1

import com.cryptopwf.util.Helpers._
import sun.misc.BASE64Encoder

object Challenge1 {
  val encoder = new BASE64Encoder()

  def hex2base64(hex: String): String = {
    encoder.encode(hex.asHex)
  }
}
