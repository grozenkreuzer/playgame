package sc.core

case class JsFunctionNamed( name : String, body :String, parameters : List[String] = Nil ) extends JsFunction

case class JsFunctionProp( body :String, parameters : List[String] = Nil ) extends JsFunction { val name = "" }

trait JsFunction {

  val body : String

  val parameters : List[String]

  val name : String

  override def toString() : String =
    if( name.isEmpty )
      s"""function(${parameters.mkString( "," )}){
         |     $body
         |}""".stripMargin
    else
      s"""function $name(${parameters.mkString( "," )}){
         |     $body
         |}""".stripMargin
}
