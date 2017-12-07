package sc

import sc.core.{JsFunctionNamed, ScObjectWithId}

case class ListGrid( id : String, dataSource : RestDataSource ) extends ScObjectWithId[ListGrid] {

    /************************************************************************/
    /********** init   ******************************/

    val scClassName = "ListGrid"

    before( dataSource.toString )

    addNoStr( "dataSource", dataSource.id )


    /************************************************************************/
    /********** props   ******************************/

    def autoFetchData( auto : Boolean ) = add( "autoFetchData", auto.toString )


    /************************************************************************/
    /********** functions  ******************************/

    val rowClick = JsFunctionNamed( this, "rowClick", List( "record", "recordNum", "fieldNum" ) )

}


