import com.cryptopwf.Challenge3
import org.scalatest._

class Challenge3Spec extends FlatSpec with Matchers {
  "Challenge3" should "brute force decrypt something xored by one byte" in {
    val hex = "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736"
    val result = "Cooking MC's like a pound of bacon"

    Challenge3.decrypt(hex) should be (result)
  }
}
