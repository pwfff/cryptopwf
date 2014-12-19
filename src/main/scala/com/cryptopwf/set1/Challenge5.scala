package com.cryptopwf.set1

import com.cryptopwf.util.RepeatingKeyXOR

object Challenge5 {
  def encrypt(plaintext: String, key: String): IndexedSeq[Byte] = {
    RepeatingKeyXOR.encrypt(plaintext, key)
  }
}
