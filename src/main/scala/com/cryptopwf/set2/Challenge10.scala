package com.cryptopwf.set1

import com.cryptopwf.encryption.CBC

object Challenge10 {
  def decrypt(base64ciphertext: String, key: String, iv: String): String = {
    CBC.decrypt(base64ciphertext, key, iv)
  }
}
