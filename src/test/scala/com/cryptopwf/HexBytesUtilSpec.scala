import com.cryptopwf.util.HexBytesUtil
import org.scalatest._

class HexBytesUtilSpec extends FlatSpec with Matchers {
  "hammingDistance" should "calculate hamming distance between two strings" in {
    val a = "this is a test".map(_.toByte)
    val b = "wokka wokka!!!".map(_.toByte)

    HexBytesUtil.hammingDistance(a, b) should be (37)
  }
}
