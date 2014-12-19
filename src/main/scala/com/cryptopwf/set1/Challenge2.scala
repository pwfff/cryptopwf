package com.cryptopwf.set1

import com.cryptopwf.util.Helpers._

object Challenge2 {
  def xorhex(hex1: String, hex2: String): String = {
    (hex1.asHex ^ hex2.asHex).toHex
  }
}
