package com.cryptopwf.set1

import com.cryptopwf.util.Helpers._

object Challenge1 {
  def hex2base64(hex: String): String = {
    hex.asHex.toBase64
  }
}
