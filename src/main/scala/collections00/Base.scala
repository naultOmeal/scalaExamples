package collections00

abstract class Base
// case objects allow you to make singleton objects
case object A extends Base
case object U extends Base
case object G extends Base
case object C extends Base

object Base{
  val fromInt: Int => Base = Array(A, U, G, C)
  val toInt: Base => Int = Map(A -> 0, U -> 1, G -> 2, C -> 3)
}