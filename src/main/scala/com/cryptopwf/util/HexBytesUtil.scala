/*
 * hex2bytes and bytes2hex ripped from https://gist.github.com/tmyymmt/3727124 and modified
 */
package com.cryptopwf.util

import java.lang.{Integer=>JavaInteger}

object HexBytesUtil {
  def hex2bytes(hex: String): IndexedSeq[Byte] = {
    hex.replaceAll("[^0-9A-Fa-f]", "").sliding(2, 2).toIndexedSeq.map(Integer.parseInt(_, 16).toByte)
  }

  def bytes2hex(bytes: IndexedSeq[Byte]): String = {
    bytes.map("%02x".format(_)).mkString
  }

  def hammingDistance(a: IndexedSeq[Byte], b: IndexedSeq[Byte]): Int = {
    (a zip b).map(t => JavaInteger.bitCount(t._1 ^ t._2)).sum
  }

  def normalizedHammingDistance(a: IndexedSeq[Byte], b: IndexedSeq[Byte]): Double = {
    hammingDistance(a, b).toDouble / math.min(a.length, b.length)
  }
}
