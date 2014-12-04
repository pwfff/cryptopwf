package com.cryptopwf

import com.cryptopwf.util.HexBytesUtil
import sun.misc.BASE64Encoder

object Challenge1 {
  val encoder = new BASE64Encoder()
  def main(args: Array[String]): Unit = {
    val hex = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"

    println("Input: " + hex)

    val bytes = HexBytesUtil.hex2bytes(hex)

    println("Output: " + encoder.encode(bytes))
  }
}
