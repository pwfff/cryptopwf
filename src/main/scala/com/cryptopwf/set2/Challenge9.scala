package com.cryptopwf.set2

import com.cryptopwf.util.Helpers._

object Challenge9 {
  def padTo(input: String, to: Int): String = {
    input.padPKCS7(to)
  }
}
