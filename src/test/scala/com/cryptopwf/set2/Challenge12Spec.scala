import com.cryptopwf.set2.Challenge12
import org.scalatest._

import com.cryptopwf.encryption._
import com.cryptopwf.util.EncryptRandom
import com.cryptopwf.util.Helpers._

import scala.io.Source

class Challenge12Spec extends FlatSpec with Matchers {
  "Challenge12" should "find common prefix length" in {
    val empty = Vector[Byte]()
    val foo = Vector[Byte](1, 2, 3)
    val bar = Vector[Byte](1, 2, 3, 4)

    Challenge12.commonPrefixLength(empty, empty) should be (0)
    Challenge12.commonPrefixLength(empty, foo) should be (0)
    Challenge12.commonPrefixLength(foo, empty) should be (0)
    Challenge12.commonPrefixLength(foo, bar) should be (3)
    Challenge12.commonPrefixLength(bar, foo) should be (3)
  }

  "Challenge12" should "detect the ECB keysize" in {
    val key = "7D811C375882F28C7D811C375882F28C".asHex
    val unknownPlaintext = """|Um9sbGluJyBpbiBteSA1LjAKV2l0aCBteSByYWctdG9wIGRvd24gc28gbXkg
                              |aGFpciBjYW4gYmxvdwpUaGUgZ2lybGllcyBvbiBzdGFuZGJ5IHdhdmluZyBq
                              |dXN0IHRvIHNheSBoaQpEaWQgeW91IHN0b3A/IE5vLCBJIGp1c3QgZHJvdmUg
                              |YnkK""".stripMargin.asBase64
    val answer = """|Rollin' in my 5.0
                    |With my rag-top down so my hair can blow
                    |The girlies on standby waving just to say hi
                    |Did you stop? No, I just drove by
                    |""".stripMargin + "\01"

    val oracle = Challenge12.encryptionOracle(_: IndexedSeq[Byte], unknownPlaintext, key)

    Challenge12.ecbDecryption(oracle) should be (answer)
  }
}
