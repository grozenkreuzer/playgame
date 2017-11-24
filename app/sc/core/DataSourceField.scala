package sc.core

import sc.core.StaticObjects.FieldType.FieldType

case class DataSourceField( name : String, typeName : FieldType  ) extends ScNameTypeTrait[DataSourceField] {

  def startString = "{"
  def endString: String = "}"

  def title( value :String ) = add( "title", value )

}
