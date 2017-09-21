import cats.{Cartesian, Functor}
import cats.data.OptionT
import cats.instances.list._
import cats.instances.option._

val list1 = List(1, 2, 3)
// list1: List[Int] = List(1, 2, 3)

val list2 = Functor[List].map(list1)(_ * 2)
// list2: List[Int] = List(2, 4, 6)

import cats.syntax.applicative._
val xs = Map("a" -> 1)

type ListOption[A] = OptionT[List, A]

val x = 42.pure[ListOption]
val y = 36.pure[ListOption]

val xxs  = for {
  x1 <- x
  y1 <- y
} yield x1 + y1
xxs.value

Cartesian[Option].product(Some(1), Some(2))
Cartesian[Option].product(Some(1), None)
//Cartesian.tuple2(Some(1), Some(20))

import cats.syntax.cartesian._

val z = Option(1) |@| Option(2)
z.tupled

val ys = for {
  n <- 1 to 22
} yield Option(n)

