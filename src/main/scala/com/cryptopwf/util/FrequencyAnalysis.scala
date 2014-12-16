package com.cryptopwf.util

object FrequencyAnalysis {
  val englishLetterFreq: Map[Char, Double] = Map(
    'E' -> .1270, 'T' -> .0906, 'A' -> .0817, 'O' -> .0751, 'I' -> .0697,
    'N' -> .0675, 'S' -> .0633, 'H' -> .0609, 'R' -> .0599, 'D' -> .0425,
    'L' -> .0403, 'C' -> .0278, 'U' -> .0276, 'M' -> .0241, 'W' -> .0236,
    'F' -> .0223, 'G' -> .0202, 'Y' -> .0197, 'P' -> .0193, 'B' -> .0129,
    'V' -> .0098, 'K' -> .0077, 'J' -> .0015, 'X' -> .0015, 'Q' -> .0010,
    'Z' -> .0007, ' ' -> .1300
  )

  def getLetterFrequency(text: String): Map[Char, Double] = {
    /* stolen from http://stackoverflow.com/questions/12105130/generating-a-frequency-map-for-a-string-in-scala */
    text.groupBy(_.toUpper).map{ t => (t._1, t._2.length.toDouble / text.length) }
  }

  def englishScore(text: String): Double = {
    val letterFreq = getLetterFrequency(text)

    text.map {
      case c => (englishLetterFreq.get(c.toUpper), letterFreq.get(c.toUpper))
    }.map {
      case (Some(a), Some(b)) => math.abs(a - b)
      case (Some(a), None) => a
      case (None, Some(b)) => 1
      case _ => 1
    }.sum
  }

  def mostEnglishText(candidates: collection.GenSeq[String]): String = {
    candidates.minBy(englishScore)
  }
}
