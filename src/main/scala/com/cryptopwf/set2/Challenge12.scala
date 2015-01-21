package com.cryptopwf.set2

import scala.annotation.tailrec

import com.cryptopwf.encryption.EncryptionType
import com.cryptopwf.encryption.ECB
import com.cryptopwf.util.Helpers._

object Challenge12 {
  val allBytes = (0 to 255).par.map(_.toByte)

  def encryptionOracle(
      myPlaintext: IndexedSeq[Byte],
      unknownPlaintext: IndexedSeq[Byte],
      key: IndexedSeq[Byte]
      ): IndexedSeq[Byte] = {
    ECB.encryptPadded(myPlaintext ++ unknownPlaintext, key)
  }

  @tailrec def commonPrefixLength[T](
      seq1: IndexedSeq[T],
      seq2: IndexedSeq[T],
      common: Int=0
      ): Int = {
    seq1 match {
      case x +: xs => seq2 match {
        case `x` +: ys => commonPrefixLength(xs, ys, common + 1)
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
    // find the final size of encrypting with nothing added to know when to stop
    val finalSize = oracle(Vector[Byte]()).length

    // get the keySize
    val keySize = detectKeySize(oracle)

    @tailrec def decryptBlocks(
        knownBlocks: IndexedSeq[IndexedSeq[Byte]]=Vector[Vector[Byte]]()
        ): IndexedSeq[IndexedSeq[Byte]] = {

      @tailrec def decryptBlock(
          prefixBytes: IndexedSeq[Byte]=Vector.fill[Byte](keySize)('A'),
          blockIndex: Int=0,
          knownPlaintext: IndexedSeq[Byte]=Vector[Byte]()
          ): IndexedSeq[Byte] = {

        /*
          To decrypt a block, encrypt the prefix plus all one-byte values,
          then compare it to the prefix + the encrypted unknown value.

          Example:
          Unknown text: 'This is a test 1234.'
          4-byte blocks: |This| is |a te|st 1|234.|
          With padding:  |---T|his |is a| tes|t 12|34._|
          Our guesses:   |---*|This| is |a te|st 1|234.|
          One of our guesses will match and we'll have the first byte. Repeat.
        */
        val prefixLength = keySize - knownPlaintext.length - 1
        val prefix = prefixBytes.slice(keySize - prefixLength, keySize)

        val dictionary = allBytes.map(guess => oracle(prefix ++ knownPlaintext :+ guess).grouped(keySize).next -> guess).toMap

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

    val decryptedBlocks = decryptBlocks()

    decryptedBlocks.flatMap(b => b.map(_.toChar)).mkString
  }
}
