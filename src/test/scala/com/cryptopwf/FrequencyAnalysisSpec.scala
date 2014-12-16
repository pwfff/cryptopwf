import com.cryptopwf.util.FrequencyAnalysis
import org.scalatest._

class FrequencyAnalysisSpec extends FlatSpec with Matchers {
  "getLetterFrequency" should "map characters to their frequency in a text" in {
    val text = "sssuuppppp"
    val frequencies = Map('S' -> .3, 'U' -> .2, 'P' -> .5)

    FrequencyAnalysis.getLetterFrequency(text) should be (frequencies)
  }
  "mostEnglishText" should "return the most English-like text" in {
    val correct = "Hi there, it's me!"
    val candidates = Array(
      correct,
      "!@d2m!OIwid92!D@1kwlW!!((",
      "zzzzzzzzzzzzzzzzzzzz"
    )

    FrequencyAnalysis.mostEnglishText(candidates) should be (correct)
  }
}
