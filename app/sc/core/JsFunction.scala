package sc.core

case class JsFunctionNamed[T <: ScParts[T]](owner : T, name : String, parameters : List[String] = Nil ) extends JsFunction[T]

case class JsFunctionProp[T <: ScParts[T]](owner : T, parameters : List[String] = Nil ) extends JsFunction[T] { val name = "" }

trait JsFunction[T <: ScParts[T]] {

  val owner : T

  def apply( funcBody : String ) = handler( funcBody )

  def handler( funcBody : String ) = {
      owner.addNoStr( name, getFullString( funcBody ) )
      owner
  }

  val parameters : List[String]

  val name : String

  def getFullString( body : String ) : String =
      s"""function(${parameters.mkString( "," )}){
         |     $body
         |}""".stripMargin
}
