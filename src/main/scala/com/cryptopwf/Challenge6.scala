package com.cryptopwf

import com.cryptopwf.util.FrequencyAnalysis
import com.cryptopwf.util.HexBytesUtil

object Challenge6 {
  def breakRepeatingXOR(base64ciphertext: String, keysizes: Range = (2 to 255)): String = {
    val ciphertext = HexBytesUtil.hex2bytes(base64ciphertext)

    val keysizeGuesses = keysizes.sortBy(normalizedEditDistance(ciphertext.mkString, _)).take(3)

    keysizeGuesses.map(tryKeySize(ciphertext, _)).minBy(FrequencyAnalysis.englishScore(_))
  }

  def tryKeySize(ciphertext: IndexedSeq[Byte], keysize: Int): String = {
    val blocks = ciphertext.grouped(keysize).map(_.padTo(keysize, 0.toByte)).toIndexedSeq
    val tBlocks = blocks.transpose
    val decryptedBlocks = tBlocks.map(Challenge3.decrypt)
    decryptedBlocks.transpose.map(_.mkString).mkString
  }

  def normalizedEditDistance(ciphertext: String, keysize: Int): Double = {
    if (ciphertext.length < keysize * 4) throw new IllegalArgumentException("ciphertext must have at least four blocks of `keysize` length")

    val block1: String = ciphertext.slice(0, keysize)
    val block2: String = ciphertext.slice(keysize, keysize * 2)
    val block3: String = ciphertext.slice(keysize * 2, keysize * 3)
    val block4: String = ciphertext.slice(keysize * 3, keysize * 4)

    val distance1 = HexBytesUtil.hammingDistance(block1, block2).toDouble
    val distance2 = HexBytesUtil.hammingDistance(block3, block4).toDouble

    (distance1 + distance2) / 2 / keysize
  }
}
