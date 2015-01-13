package com.cryptopwf.set2

import com.cryptopwf.encryption.EncryptionType
import com.cryptopwf.util.EncryptRandom
import com.cryptopwf.util.Helpers._

object Challenge11 {
  def detectEncryptionType(input: Array[Byte]): EncryptionType.Value = {
    // calling toHex as a gross fix for array equality checking
    val grouped = input.grouped(16).toIndexedSeq.map(_.toIndexedSeq.toHex)
    if (grouped.distinct.length != grouped.length) EncryptionType.ECB else EncryptionType.CBC
  }
}
