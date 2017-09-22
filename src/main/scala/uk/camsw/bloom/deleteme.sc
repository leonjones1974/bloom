import uk.camsw.bloom.{Contains, No, Possibly}
import cats.syntax.applicative._
import cats.syntax.functor._
import uk.camsw.bloom.instances.contains._

val x = 1.pure[Contains]
x.map(_ * 2)
val y: Contains[Int] = Possibly(1)
y.map(_ * 23)
val z: Contains[Int] = No
z.map(_ * 23)

Option.empty[Int].map(_ * 36)

//val xs = List(Option(10), None)
//
//val ys = for {
//  x <- xs if x.isDefined
//} yield x

//val ys = List(Possibly(10), No: Contains[Int])
//val yys = for {
//  y <- Possibly(10)
//
//} yield y
import cats.syntax.option._
none




