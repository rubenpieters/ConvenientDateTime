package be.rubenpieters.util

import scala.annotation.tailrec

/**
  * Created by ruben on 2/10/16.
  */
object Intersperse {
  // from scalaz: https://github.com/scalaz/scalaz/blob/972ffe1a1bb49c26a38da01509263d1192484524/core/src/main/scala/scalaz/std/List.scala
  /** Intersperse the element `a` between each adjacent pair of elements in `as` */
  final def intersperse[A](as: List[A], a: A): List[A] = {
    @tailrec
    def intersperse0(accum: List[A], rest: List[A]): List[A] = rest match {
      case Nil      => accum
      case x :: Nil => x :: accum
      case h :: t   => intersperse0(a :: h :: accum, t)
    }
    intersperse0(Nil, as).reverse
  }
}
