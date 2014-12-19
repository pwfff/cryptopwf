package com.cryptopwf.set1

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import sun.misc.BASE64Decoder

object Challenge7 {
  val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
  val decoder = new BASE64Decoder()

  def decrypt(base64ciphertext: String, key: String): String = {
    val ciphertext = decoder.decodeBuffer(base64ciphertext)

    decrypt(ciphertext, key.getBytes)
  }

  def decrypt(ciphertext: Array[Byte], key: Array[Byte]): String = {
    val keySpec = new SecretKeySpec(key, "AES")
    cipher.init(Cipher.DECRYPT_MODE, keySpec);

    cipher.doFinal(ciphertext).map(_.toChar).mkString
  }
}
