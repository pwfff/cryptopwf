package com.cryptopwf.set1

import com.cryptopwf.encryption.ECB

object Challenge7 {
  def decrypt(base64ciphertext: String, key: String): String = {
    ECB.decrypt(base64ciphertext, key)
  }
}
