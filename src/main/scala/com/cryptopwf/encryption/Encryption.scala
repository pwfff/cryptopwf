package com.cryptopwf.encryption

import com.cryptopwf.util.Helpers._

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptionType extends Enumeration {
  val ECB, CBC = Value
}

object ECB {
  def encrypt(plaintext: String, key: String): String = {
    val encryptedBytes = encrypt(plaintext.getBytes, key.getBytes)
    encryptedBytes.toBase64
  }

  def encryptPadded(plaintext: IndexedSeq[Byte], key: IndexedSeq[Byte]): IndexedSeq[Byte] = {
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    val keySpec = new SecretKeySpec(key, "AES")

    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    cipher.doFinal(plaintext)
  }

  def encrypt(plaintext: IndexedSeq[Byte], key: IndexedSeq[Byte]): IndexedSeq[Byte] = {
    val cipher = Cipher.getInstance("AES/ECB/NoPadding")
    val keySpec = new SecretKeySpec(key, "AES")

    cipher.init(Cipher.ENCRYPT_MODE, keySpec);
    cipher.doFinal(plaintext)
  }

  def decrypt(base64ciphertext: String, key: String): String = {
    val ciphertext = base64ciphertext.asBase64

    decrypt(ciphertext, key.getBytes).map(_.toChar).mkString
  }

  def decrypt(ciphertext: IndexedSeq[Byte], key: IndexedSeq[Byte]): IndexedSeq[Byte] = {
    val cipher = Cipher.getInstance("AES/ECB/NoPadding")
    val keySpec = new SecretKeySpec(key, "AES")

    cipher.init(Cipher.DECRYPT_MODE, keySpec);
    cipher.doFinal(ciphertext)
  }
}

object CBC {
  def encrypt(plaintext: String, key: String, iv: String): String = {
    encrypt(plaintext.getBytes, key.getBytes, iv.asHex).toBase64
  }

  def encrypt(plaintext: IndexedSeq[Byte], key: IndexedSeq[Byte], iv: IndexedSeq[Byte] = Vector.fill[Byte](16)(0)): IndexedSeq[Byte] = {
    def encryptChunks(chunks: List[IndexedSeq[Byte]], encryptedChunks: List[IndexedSeq[Byte]] = List[Vector[Byte]]()): List[Byte] = {
      chunks match {
        case List() => encryptedChunks.reverse.flatten
        case head :: rest => encryptedChunks match {
          case List() => encryptChunks(rest, ECB.encrypt(head ^ iv, key) :: encryptedChunks)
          case encHead :: _ => encryptChunks(rest, ECB.encrypt(head ^ encHead, key) :: encryptedChunks)
        }
      }
    }

    encryptChunks(plaintext.padPKCS7(16).grouped(16).toList).toIndexedSeq
  }

  def decrypt(ciphertext: String, key: String, iv: String): String = {
    decrypt(ciphertext.asBase64, key.getBytes, iv.asHex).map(_.toChar).mkString
  }

  def decrypt(ciphertext: IndexedSeq[Byte], key: IndexedSeq[Byte], iv: IndexedSeq[Byte] = Vector.fill[Byte](16)(0)): IndexedSeq[Byte] = {
    def decryptChunks(lastChunk: IndexedSeq[Byte], chunks: List[IndexedSeq[Byte]], decryptedChunks: List[IndexedSeq[Byte]] = List[Vector[Byte]]()): List[Byte] = {
      chunks match {
        case List() => decryptedChunks.reverse.flatten
        case head :: rest => decryptChunks(head, rest, (ECB.decrypt(head, key) ^ lastChunk) :: decryptedChunks)
      }
    }

    decryptChunks(iv, ciphertext.grouped(16).toList).toIndexedSeq
  }
}
