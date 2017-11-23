package sc.core

case class ListGrid( id : String, dataSource : RestDataSource ) extends ScObjectWithId[ListGrid] {

    val scClassName = "ListGrid"

    before( dataSource.toString )

    addNoStr( "dataSource", dataSource.id )

    def autoFetchData( auto : Boolean ) = add( "autoFetchData", auto.toString )

    def rowClick( funcBody : String ) = {
        addFunction(
            "rowClick",
            JsFunctionProp(
                funcBody,
                List( "record", "recordNum", "fieldNum" )
            )
        )
    }

}


