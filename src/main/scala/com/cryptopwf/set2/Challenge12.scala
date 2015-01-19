package com.cryptopwf.set2

import com.cryptopwf.encryption.EncryptionType
import com.cryptopwf.encryption.ECB
import com.cryptopwf.util.Helpers._

object Challenge12 {
  def encryptionOracle(
      myPlaintext: IndexedSeq[Byte],
      unknownPlaintext: IndexedSeq[Byte],
      key: IndexedSeq[Byte]
      ): IndexedSeq[Byte] = {
    ECB.encryptPadded(myPlaintext ++ unknownPlaintext, key)
  }

  def commonPrefixLength[T](
      seq1: IndexedSeq[T],
      seq2: IndexedSeq[T],
      common: Int=0
      ): Int = {
    seq1 match {
      case x +: xs => seq2 match {
        case y +: ys if x == y => commonPrefixLength(xs, ys, common + 1)
        case _ => common
      }
      case _ => common
    }
  }

  def detectKeySize(
      oracle: IndexedSeq[Byte] => IndexedSeq[Byte],
      testBytes: IndexedSeq[Byte]="A".getBytes,
      lastBytes: IndexedSeq[Byte]=Vector[Byte]()
      ): Int = {
    val encrypted = oracle(testBytes)
    val common = commonPrefixLength(encrypted, lastBytes)

    if (common > 0) common else detectKeySize(oracle, testBytes ++ "A".getBytes, encrypted)
  }

  def ecbDecryption(oracle: IndexedSeq[Byte] => IndexedSeq[Byte]): String = {
    // find the final size of encrypting with nothing to know when to stop
    val finalSize = oracle(Vector[Byte]()).length

    // get the keySize
    val keySize = detectKeySize(oracle)

    def decryptBlocks(knownBlocks: IndexedSeq[IndexedSeq[Byte]]=Vector[Vector[Byte]]()): IndexedSeq[IndexedSeq[Byte]] = {
      def decryptBlock(
          prefixBytes: IndexedSeq[Byte]=("A" * keySize).getBytes,
          blockIndex: Int=0,
          knownPlaintext: IndexedSeq[Byte]=Vector[Byte]()
          ): IndexedSeq[Byte] = {
        val prefixLength = keySize - knownPlaintext.length - 1
        val prefix = prefixBytes.slice(keySize - prefixLength, keySize)
        val dictionary = (0 to 255).par.map(_.toByte).map(guess => oracle(prefix ++ knownPlaintext :+ guess).grouped(keySize).next -> guess).toMap
        val correct = oracle(prefix).grouped(keySize).toIndexedSeq(blockIndex)
        dictionary.get(correct) match {
          case Some(nextByte) => {
            val newPlaintext = knownPlaintext :+ nextByte
            if (newPlaintext.length == keySize) newPlaintext else decryptBlock(prefixBytes, blockIndex, newPlaintext)
          }
          case None => knownPlaintext
        }
      }

      val nextBlock = knownBlocks match {
        case _ :+ last => decryptBlock(last, knownBlocks.length)
        case _ => decryptBlock()
      }

      val newBlocks = knownBlocks :+ nextBlock

      if ((newBlocks.length * keySize) == finalSize) newBlocks else decryptBlocks(newBlocks)
    }

    decryptBlocks().flatMap(b => b.map(_.toChar)).mkString
  }
}
