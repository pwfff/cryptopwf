import com.cryptopwf.set2.Challenge11
import org.scalatest._

import com.cryptopwf.util.EncryptRandom
import com.cryptopwf.encryption.EncryptionType._

import scala.io.Source

class Challenge11Spec extends FlatSpec with Matchers {
  "Challenge11" should "detect ECB" in {
    val ECBEncrypted = EncryptRandom.encryptRandom(("a" * 512).getBytes, ECB)._1
    Challenge11.detectEncryptionType(ECBEncrypted) should be (ECB)

    val CBCEncrypted = EncryptRandom.encryptRandom(("a" * 512).getBytes, CBC)._1
    Challenge11.detectEncryptionType(CBCEncrypted) should be (CBC)
  }
}
