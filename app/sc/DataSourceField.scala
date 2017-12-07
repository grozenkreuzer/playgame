package sc

import sc.core.ScNameTypeTrait
import sc.core.StaticObjects.FieldType
import sc.core.StaticObjects.FieldType.FieldType

case class DataSourceField( name : String, typeName : FieldType = FieldType.text  ) extends ScNameTypeTrait[DataSourceField] {

    /************************************************************************/
    /********** functions  ******************************/

    def title( value :String ) = add( "title", value )

}
