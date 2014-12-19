import com.cryptopwf.Challenge4
import org.scalatest._

import scala.io.Source

class Challenge4Spec extends FlatSpec with Matchers {
  "Challenge4" should "brute force decrypt something xored by one byte" in {
    val hexes = Source.fromURL(getClass.getResource("/challenge-data/4.txt")).getLines.toIndexedSeq

    val result = "Now that the party is jumping\n"

    Challenge4.decrypt(hexes) should be (result)
  }
}
