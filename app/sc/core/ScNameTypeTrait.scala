package sc.core

import sc.core.StaticObjects.FieldType.FieldType


trait ScNameTypeTrait[T <: ScNameTypeTrait[T] ] extends ScNamedTrait[T] {

    self : T =>

    val typeName : FieldType

    add( "type", typeName.toString )

}

trait ScNamedTrait[T <: ScNamedTrait[T] ] extends ScParts[T] {

    self : T =>

    /********** init   ******************************/
    def startString = "{"

    def endString: String = "}"

    val name : String

    add( "name", name.toUpperCase)

}