package sc.core

import sc.FieldType.FieldType


trait ScNameTypeTrait[T <: ScNameTypeTrait[T] ] extends ScParts[T] {

  self : T =>

  val name : String
  val typeName : FieldType

  add( "name", name.toUpperCase)
  add( "type", typeName.toString )

}