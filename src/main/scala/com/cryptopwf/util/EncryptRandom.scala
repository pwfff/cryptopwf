package com.cryptopwf.util

import com.cryptopwf.encryption._

import util.Random

object EncryptRandom {
  val generator = new Random()

  def getBytes(length: Int): Array[Byte] = {
    val bytes = new Array[Byte](length)
    generator.nextBytes(bytes)
    bytes
  }

  def randomEncryptionType(): EncryptionType.Value = if (generator.nextBoolean) EncryptionType.ECB else EncryptionType.CBC

  def encryptRandom(input: Array[Byte], encryptionType: EncryptionType.Value=randomEncryptionType): (Array[Byte], EncryptionType.Value) = {
    val key = getBytes(16)
    val iv = getBytes(16)

    // pad 5-10 random bytes before and after
    val padded = getBytes(generator.nextInt(5) + 5) ++ input ++ getBytes(generator.nextInt(5) + 5)

    val encrypted = encryptionType match {
      case EncryptionType.ECB => ECB.encryptPadded(padded, key)
      case EncryptionType.CBC => CBC.encrypt(padded, key, iv)
    }

    (encrypted, encryptionType)
  }
}
