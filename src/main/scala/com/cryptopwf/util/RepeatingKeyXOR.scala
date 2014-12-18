package com.cryptopwf.util

object RepeatingKeyXOR {
  def encrypt(plaintext: String, key: String): String = {
    val zipped: IndexedSeq[(Char, Char)] = plaintext zip (Stream continually key).flatten

    val encryptedBytes: IndexedSeq[Byte] = zipped.map {
      case (a, b) => (a.toByte ^ b.toByte).toByte
    }

    HexBytesUtil.bytes2hex(encryptedBytes)
  }
}
