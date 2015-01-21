import com.cryptopwf.set2.Challenge9
import org.scalatest._

class Challenge9Spec extends FlatSpec with Matchers {
  "Challenge9" should "convert hex strings to base64 encoded bytes" in {
    val input = "YELLOW SUBMARINE"
    val result = "YELLOW SUBMARINE\u0004\u0004\u0004\u0004"

    Challenge9.padTo(input, 20) should be (result)
  }
}
