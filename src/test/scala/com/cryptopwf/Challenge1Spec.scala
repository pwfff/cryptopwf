import com.cryptopwf.Challenge1
import org.scalatest._

class Challenge1Spec extends FlatSpec with Matchers {
  "Challenge1" should "convert hex strings to base64 encoded bytes" in {
    val hex = "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
    val base64 = "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"

    Challenge1.hex2base64(hex) should be (base64)
  }
}
