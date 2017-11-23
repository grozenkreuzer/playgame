package sc.core

import sc.{DsFetchParams, RecordsData}

import scala.collection.mutable.ArrayBuffer

case class RestDataSource( id : String, fields : List[DataSourceField] ) extends ScObjectWithId[RestDataSource] {

    val scClassName = "RestDataSource"

    add( "fields", fields )

    add( "dataURL", "/ds" )

    RestDataSourceManager.registerDataSource( this )

    val handlers : ArrayBuffer[ DsFetchParams => RecordsData ] = ArrayBuffer.empty

    def addDsHandler( hndl : DsFetchParams => RecordsData ) = {
        handlers.append( hndl )
        this
    }

}

object RestDataSourceManager {

    val dsList : ArrayBuffer[RestDataSource] = ArrayBuffer.empty

    def registerDataSource( ds : RestDataSource  ): Unit = {
        dsList.append( ds )
    }

    def getDsHandlers( params : DsFetchParams ) = {
        val ret =
            dsList.find( _.id.equalsIgnoreCase( params.name ) ).
                flatMap(
                    ds =>
                        ds.handlers.headOption.map(
                            h => h.apply( params )
                        )
                )

        ret.getOrElse( Nil )
    }

}
