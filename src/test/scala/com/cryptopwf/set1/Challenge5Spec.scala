import com.cryptopwf.set1.Challenge5
import org.scalatest._

import com.cryptopwf.util.Helpers._

class Challenge5Spec extends FlatSpec with Matchers {
  "Challenge5" should "encrypt plaintext with repeating-key XOR" in {
    val plaintext = "Burning 'em, if you ain't quick and nimble\nI go crazy when I hear a cymbal"
    val key = "ICE"

    val result = "0b3637272a2b2e63622c2e69692a23693a2a3c6324202d623d63343c2a26226324272765272a282b2f20430a652e2c652a3124333a653e2b2027630c692b20283165286326302e27282f"

    Challenge5.encrypt(plaintext, key).toHex should be (result)
  }
}
