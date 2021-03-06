package com.cryptopwf.set1

import com.cryptopwf.util.FrequencyAnalysis
import com.cryptopwf.util.HexBytesUtil
import com.cryptopwf.util.Helpers._

object Challenge6 {
  def breakRepeatingXOR(base64ciphertext: String, keysizes: Range = (2 to 40)): String = {
    val ciphertext = base64ciphertext.asBase64

    // use the average normalized hamming distance of adjacent blocks to guess the keysize
    val bestKeysize = keysizes.minBy(averageNormalizedHammingDistance(ciphertext, _))

    // brute-force decrypt each byte in the key for our chosen keysize
    tryKeySize(ciphertext, bestKeysize)
  }

  def tryKeySize(ciphertext: IndexedSeq[Byte], keysize: Int): String = {
    // break into chunks and pad with null bytes for the transpose
    val blocks = ciphertext.grouped(keysize).map(_.padTo(keysize, 0.toByte)).toIndexedSeq

    // transpose, remove null bytes
    val tBlocks = blocks.transpose.map {
      case c if (c.last == 0) => c.dropRight(1)
      case c => c
    }

    // brute-force decrypt each byte in the key
    val decryptedBlocks = tBlocks.par.map(Challenge3.decrypt)

    // pad for second transpose
    val paddedDecryptedBlocks = decryptedBlocks.map(_.padTo(math.ceil(ciphertext.length.toDouble / keysize).toInt, '\u0000'))

    // transpose and strip null bytes
    paddedDecryptedBlocks.transpose.map(_.mkString).mkString.replaceAll("\u0000", "")
  }

  def averageNormalizedHammingDistance(ciphertext: IndexedSeq[Byte], keysize: Int): Double = {
    // group ciphertext into blocks of keysize length
    val blocks = ciphertext.grouped(keysize).toIndexedSeq

    // get adjacent blocks
    val adjacentBlocks = blocks.sliding(2)

    // for each adjacent pair of blocks, calculate the normalized hamming distance
    val hammingDistances = adjacentBlocks.map(t => HexBytesUtil.normalizedHammingDistance(t(0), t(1))).toIndexedSeq

    // return the average of the normalized distances
    hammingDistances.sum / hammingDistances.length
  }
}
