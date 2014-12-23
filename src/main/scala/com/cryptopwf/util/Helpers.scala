package com.cryptopwf.util

object Helpers {
  implicit class RichArray(val self: Array[Byte]) extends AnyVal {
    def ^(operand: Array[Byte]): Array[Byte] = {
      (self, operand).zipped map {(a, b) => (a ^ b).toByte}
    }
  }

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

    def padPKCS7(to: Int): IndexedSeq[Byte] = {
      self.padTo(to, (to - self.length).toByte)
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

    def padPKCS7(to: Int): String = {
      self.getBytes.toIndexedSeq.padPKCS7(to).map(_.toChar).mkString
    }
  }
}
