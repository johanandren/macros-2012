// first REPL sample from the talk
// requires Scala 2.10-RC1
// Run in Scala 2.10 REPL by issuing ":load reflection.scala"

// import the runtime universe instance (scala.reflect.api.JavaUniverse)
import scala.reflect.runtime.universe._

// get Type instance for some types
typeOf[Int]
typeOf[List[Int]]

// note that a Type is not equal to a Class
typeOf[Int => Boolean]

// capture the type, also note that there is an implicit parameter 
// to typeOf[T] that is an instance of TypeTag[T]
val t = typeOf[List[Int]]

// list all member symbols
t.members

// extract the member symbol for the method head
// there are two set of names, TermName and TypeName, 
// a string could be ambigous so therefore we need to
// use newTermName to get a TermName for "head"
val head = t.member(newTermName("head"))

// will give us the type with unbound type parameter A
head.typeSignature

// with a context we get the correct signature
head.typeSignatureIn(t)

// lets perform a reflective call to head on an instance,
// for that we need a mirror
import scala.reflect.runtime.currentMirror

// and an instance
val l = List(1,2,3)

// to access the method mirror we need to go through the instance
// and then use a MethodSymbol identifying for the method we want
val instanceMirror = currentMirror.reflect(l)
val methodMirror = instanceMirror.reflectMethod(head.asMethod)

// call apply() on the method mirror
methodMirror()
