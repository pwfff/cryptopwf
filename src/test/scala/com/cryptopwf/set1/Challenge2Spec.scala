import com.cryptopwf.set1.Challenge2
import org.scalatest._

class Challenge2Spec extends FlatSpec with Matchers {
  "Challenge2" should "xor two hex strings" in {
    val hex1 = "1c0111001f010100061a024b53535009181c"
    val hex2 = "686974207468652062756c6c277320657965"

    val result = "746865206b696420646f6e277420706c6179"

    Challenge2.xorhex(hex1, hex2) should be (result)
  }
}
