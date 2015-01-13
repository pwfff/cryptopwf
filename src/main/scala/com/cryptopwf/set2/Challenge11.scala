package com.cryptopwf.set2

import com.cryptopwf.encryption.EncryptionType
import com.cryptopwf.util.EncryptRandom
import com.cryptopwf.util.Helpers._

object Challenge11 {
  def detectEncryptionType(input: IndexedSeq[Byte]): EncryptionType.Value = {
    val grouped = input.grouped(16).toIndexedSeq
    if (grouped.distinct.length != grouped.length) EncryptionType.ECB else EncryptionType.CBC
  }
}
