/*
 * stolen from b0lt on IRC because he's smarter than me
 */
package com.cryptopwf.util

object FrequencyAnalysis {
  val englishLetterFreq: Vector[Double] = Vector(0.0817, 0.0129, 0.0278, 0.0425, 0.127, 0.0223, 0.0202, 0.0609, 0.0697, 0.0015, 0.0077, 0.0403, 0.0241, 0.0675, 0.0751, 0.0193, 0.001, 0.0599, 0.0633, 0.0906, 0.0276, 0.0098, 0.0236, 0.0015, 0.0197, 0.0007,
0.13, 0.0)

  def charIndex(char: Char): Int = {
    if (char == ' ') {
      26
    } else {
      val c = char.toUpper - 'A'
      if (c < 0 || c > 25) {
        27
      } else {
        c
      }
    }
  }

  def englishScore(text: String): Double = {
    var score = 0.0;

    val stringCount = new Array[Int](28)
    text.foreach { c =>
      stringCount(charIndex(c)) += 1
      if (c < 0 || c > 'z') score += 10
    }

    val stringFreq = stringCount map { _.asInstanceOf[Double] / text.length() }

    for (i <- 0 to 27) {
      score += Math.abs(englishLetterFreq(i) - stringFreq(i))
    }

    score
  }

  def mostEnglishText(candidates: collection.GenSeq[String]): String = {
    candidates.minBy(englishScore)
  }
}
