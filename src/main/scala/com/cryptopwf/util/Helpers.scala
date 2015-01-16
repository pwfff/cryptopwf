package com.cryptopwf.util

import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder

object Helpers {
  val decoder = new BASE64Decoder()
  val encoder = new BASE64Encoder()

  implicit def indexedSeqToArray(indexedSeq: IndexedSeq[Byte]): Array[Byte] = indexedSeq.toArray

  implicit class RichIndexedSeq(val self: IndexedSeq[Byte]) extends AnyVal {
  /*
    def ^(operand: IndexedSeq[Byte]): IndexedSeq[Byte] = {
      (self, operand).zipped map {(a, b) => (a ^ b).toByte}
    }
  */

    def toHex(): String = {
      HexBytesUtil.bytes2hex(self)
    }

    def toBase64(): String = {
      encoder.encode(self)
    }

    def ^(operand: IndexedSeq[Byte]): IndexedSeq[Byte] = {
      (self, operand).zipped map {(a, b) => (a ^ b).toByte}
    }

    def repeatingXOR(key: IndexedSeq[Byte]): IndexedSeq[Byte] = {
      (self zip (Stream continually key).flatten).map(t => (t._1 ^ t._2).toByte)
    }

    def padPKCS7(to: Int): IndexedSeq[Byte] = {
      val paddedLength = ((self.length / to) + 1) * to
      val paddingByte = (paddedLength - self.length).toByte
      self.padTo(paddedLength, paddingByte)
    }
  }

  implicit class RichString(val self: String) extends AnyVal {
    def asHex(): IndexedSeq[Byte] = {
      HexBytesUtil.hex2bytes(self)
    }

    def asBase64(): IndexedSeq[Byte] = {
      decoder.decodeBuffer(self)
    }

    def repeatingXOR(key: IndexedSeq[Byte]): IndexedSeq[Byte] = {
      self.getBytes.toIndexedSeq.repeatingXOR(key)
    }

    def repeatingXOR(key: String): IndexedSeq[Byte] = {
      self.repeatingXOR(key.getBytes)
    }

    def padPKCS7(to: Int): String = {
      self.getBytes.toIndexedSeq.padPKCS7(to).map(_.toChar).mkString
    }
  }
}
