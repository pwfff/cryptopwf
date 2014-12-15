package com.cryptopwf.util

object RepeatingKeyXOR {
  def encrypt(plaintext: String, key: String): String = {
    val zipped: Seq[(Char, Char)] = plaintext zip (Stream continually key).flatten

    val encryptedBytes: collection.GenSeq[Byte] = zipped.map {
      case (a, b) => (a.toByte ^ b.toByte).toByte
    }

    HexBytesUtil.bytes2hex(encryptedBytes)
  }
}
