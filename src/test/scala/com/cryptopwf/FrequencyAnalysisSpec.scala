import com.cryptopwf.util.FrequencyAnalysis
import org.scalatest._

class FrequencyAnalysisSpec extends FlatSpec with Matchers {
  "mostEnglishText" should "return the most English-like text" in {
    val correct = "Hi there, it's me!"
    val candidates = Vector(
      correct,
      "!@d2m!OIwid92!D@1kwlW!!((",
      "zzzzzzzzzzzzzzzzzzzz"
    )

    FrequencyAnalysis.mostEnglishText(candidates) should be (correct)
  }
}
