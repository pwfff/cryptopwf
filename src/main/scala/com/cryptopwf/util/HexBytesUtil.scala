/*
 * Ripped from https://gist.github.com/tmyymmt/3727124 and modified
 */
package com.cryptopwf.util

object HexBytesUtil {
  def hex2bytes(hex: String): Array[Byte] = {
    hex.replaceAll("[^0-9A-Fa-f]", "").sliding(2, 2).toArray.map(Integer.parseInt(_, 16).toByte)
  }

  def bytes2hex(bytes: collection.GenSeq[Byte]): String = {
    bytes.map("%02x".format(_)).mkString
  }
}
