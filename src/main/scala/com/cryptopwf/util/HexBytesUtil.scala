/*
 * hex2bytes and bytes2hex ripped from https://gist.github.com/tmyymmt/3727124 and modified
 */
package com.cryptopwf.util

import java.lang.{Integer=>JavaInteger}

object HexBytesUtil {
  def hex2bytes(hex: String): IndexedSeq[Byte] = {
    hex.replaceAll("[^0-9A-Fa-f]", "").sliding(2, 2).toArray.map(Integer.parseInt(_, 16).toByte)
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

object Helpers {
  implicit class RichIndexedSeq(val self: IndexedSeq[Byte]) extends AnyVal {
    def toHex(): String = {
      HexBytesUtil.bytes2hex(self)
    }

    def ^(operand: IndexedSeq[Byte]): IndexedSeq[Byte] = {
      (self, operand).zipped map {(a, b) => (a ^ b).toByte}
    }

    def repeatingXOR(key: IndexedSeq[Byte]): IndexedSeq[Byte] = {
      (self zip (Stream continually key).flatten).map(t => (t._1 ^ t._2).toByte)
    }
  }

  implicit class RichString(val self: String) extends AnyVal {
    def asHex(): IndexedSeq[Byte] = {
      HexBytesUtil.hex2bytes(self)
    }

    def repeatingXOR(key: IndexedSeq[Byte]): IndexedSeq[Byte] = {
      self.getBytes.toIndexedSeq.repeatingXOR(key)
    }

    def repeatingXOR(key: String): IndexedSeq[Byte] = {
      self.repeatingXOR(key.getBytes.toIndexedSeq)
    }
  }
}
