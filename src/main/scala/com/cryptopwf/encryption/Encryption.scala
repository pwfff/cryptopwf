package com.cryptopwf.encryption

import com.cryptopwf.util.Helpers._

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder

object ECB {
  val decoder = new BASE64Decoder()
  val encoder = new BASE64Encoder()

  def encrypt(plaintext: String, key: String): String = {
    val encryptedBytes = encrypt(plaintext.getBytes, key.getBytes)
    encoder.encode(encryptedBytes)
  }

  def encrypt(plaintext: Array[Byte], key: Array[Byte]): Array[Byte] = {
    val cipher = Cipher.getInstance("AES/ECB/NoPadding")
    val keySpec = new SecretKeySpec(key, "AES")

    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    cipher.doFinal(plaintext)
  }

  def decrypt(base64ciphertext: String, key: String): String = {
    val ciphertext = decoder.decodeBuffer(base64ciphertext)

    decrypt(ciphertext, key.getBytes).map(_.toChar).mkString
  }

  def decrypt(ciphertext: Array[Byte], key: Array[Byte]): Array[Byte] = {
    val cipher = Cipher.getInstance("AES/ECB/NoPadding")
    val keySpec = new SecretKeySpec(key, "AES")

    cipher.init(Cipher.DECRYPT_MODE, keySpec);
    cipher.doFinal(ciphertext)
  }
}

object CBC {
  val decoder = new BASE64Decoder()
  val encoder = new BASE64Encoder()

  def encrypt(plaintext: String, key: String, iv: String): String = {
    encoder.encode(encrypt(plaintext.getBytes, key.getBytes, iv.asHex.toArray))
  }

  def encrypt(plaintext: Array[Byte], key: Array[Byte], iv: Array[Byte] = Array.fill[Byte](16)(0)): Array[Byte] = {
    def encryptChunks(chunks: List[Array[Byte]], encryptedChunks: List[Array[Byte]] = List[Array[Byte]]()): List[Byte] = {
      chunks match {
        case List() => encryptedChunks.reverse.flatten
        case head :: rest => encryptedChunks match {
          case List() => encryptChunks(rest, ECB.encrypt(head ^ iv, key) :: encryptedChunks)
          case encHead :: _ => encryptChunks(rest, ECB.encrypt(head ^ encHead, key) :: encryptedChunks)
        }
      }
    }
    
    encryptChunks(plaintext.grouped(16).toList).toArray
  }

  def decrypt(ciphertext: String, key: String, iv: String): String = {
    decrypt(decoder.decodeBuffer(ciphertext), key.getBytes, iv.asHex.toArray).map(_.toChar).mkString
  }

  def decrypt(ciphertext: Array[Byte], key: Array[Byte], iv: Array[Byte] = Array.fill[Byte](16)(0)): Array[Byte] = {
    def decryptChunks(lastChunk: Array[Byte], chunks: List[Array[Byte]], decryptedChunks: List[Array[Byte]] = List[Array[Byte]]()): List[Byte] = {
      chunks match {
        case List() => decryptedChunks.reverse.flatten
        case head :: rest => decryptChunks(head, rest, (ECB.decrypt(head, key) ^ lastChunk) :: decryptedChunks)
      }
    }

    decryptChunks(iv, ciphertext.grouped(16).toList).toArray
  }
}
