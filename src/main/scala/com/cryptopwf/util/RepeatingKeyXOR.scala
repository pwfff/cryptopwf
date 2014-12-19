package com.cryptopwf.util

import com.cryptopwf.util.Helpers._

object RepeatingKeyXOR {
  def encrypt(plaintext: String, key: String): IndexedSeq[Byte] = {
    plaintext.repeatingXOR(key)
  }
}
