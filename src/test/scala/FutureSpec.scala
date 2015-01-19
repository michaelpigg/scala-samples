import org.scalatest.{ WordSpec, Matchers}
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class FuturesSpec extends WordSpec with Matchers {
  "Await.result" should {

    def delayingFuture(value: String): Future[String] = future {
      Thread.sleep(5000)
      value
    }
    "allow blocking, even if it's not a good idea" in {
      println("before Await")
      val result = Await.result(delayingFuture("badbusiness"), 10 seconds)
      println(s"result $result")
      println("after Await")
      result shouldBe "badbusiness"
    }
    "throw an exception if future does not return within timeout" in {
      intercept[TimeoutException] {
        Await.result(delayingFuture("toolong"), 1 seconds)
      }
    }
  }
}
