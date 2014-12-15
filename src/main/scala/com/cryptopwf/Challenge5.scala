package com.cryptopwf

import com.cryptopwf.util.RepeatingKeyXOR
import com.cryptopwf.util.ASCII._

object Challenge5 {
  def encrypt(plaintext: String, key: String): String = {
    RepeatingKeyXOR.encrypt(plaintext, key)
  }
}
