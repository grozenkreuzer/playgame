package sc.core

import sc.core.StaticObjects.FieldType.FieldType


trait ScNameTypeTrait[T <: ScNameTypeTrait[T] ] extends ScNamedTrait[T] {

    self : T =>

    val typeName : FieldType

    add( "type", typeName.toString )

}

trait ScNamedTrait[T <: ScNamedTrait[T] ] extends PropsTrait[T]{

    self : T =>

    /********** init   ******************************/

    val name : String

    add( "name", name.toUpperCase)

}

case class Props( propList : Map[String, String] ) extends PropsTrait[Props] {
    propList.foreach(
        p => add( p._1, p._2 )
    )
}

trait PropsTrait[T <: PropsTrait[T] ] extends ScParts[T] {

    self : T =>

    /********** init   ******************************/

    def startString = "{"

    def endString: String = "}"

}