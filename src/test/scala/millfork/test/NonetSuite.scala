package millfork.test

import millfork.test.emu.{EmuBenchmarkRun, EmuUnoptimizedRun}
import org.scalatest.{FunSuite, Matchers}

/**
  * @author Karol Stasiak
  */
class NonetSuite extends FunSuite with Matchers {

  test("Nonet operations") {
    val m = EmuUnoptimizedRun(
      """
        | array output [3] @$c000
        | array input = [5,6,7]
        | void main () {
        |   word a
        |   a = $110
        |   output[1] = a >>>> 1
        |   output[2] = a >>>> 2
        | }
        | void copyEntry(byte index) {
        |   output[index] = input[index]
        | }
      """.stripMargin)
      m.readByte(0xc001) should equal(0x88)
      m.readByte(0xc002) should equal(0x44)
  }

  test("Nonet left shift") {
    EmuBenchmarkRun(
      """
        | word output0 @$c000
        | word output1 @$c002
        | word output2 @$c004
        | word output3 @$c006
        | void main () {
        |   byte a
        |   a = 3
        |   output0 = nonet(a << 1)
        |   output1 = nonet(a << 2)
        |   output2 = nonet(a << 6)
        |   output3 = nonet(a << 7)
        | }
      """.stripMargin) { m =>
      m.readWord(0xc000) should equal(0x06)
      m.readWord(0xc002) should equal(0x0C)
      m.readWord(0xc004) should equal(0xC0)
      m.readWord(0xc006) should equal(0x180)
    }
  }

  test("Nonet left shift 2") {
    EmuBenchmarkRun(
      """
        | word output0 @$c000
        | word output1 @$c002
        | word output2 @$c004
        | word output3 @$c006
        | void main () {
        |   byte a
        |   a = 3
        |   output0 = nonet(a << 1)
        |   output1 = nonet(a << 2)
        |   output2 = nonet(a << 6)
        |   output3 = nonet(a << 7)
        | }
      """.stripMargin) { m =>
      m.readWord(0xc000) should equal(0x06)
      m.readWord(0xc002) should equal(0x0C)
      m.readWord(0xc004) should equal(0xC0)
      m.readWord(0xc006) should equal(0x180)
    }
  }
}
