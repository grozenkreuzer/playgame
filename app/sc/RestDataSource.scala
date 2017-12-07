package sc

import sc.core.ScObjectWithId

import scala.collection.mutable.ArrayBuffer

case class RestDataSource( id : String, fields : List[DataSourceField] ) extends ScObjectWithId[RestDataSource] {


    /************************************************************************/
    /********** init   ******************************/

    val scClassName = "RestDataSource"

    add( "fields", fields )

    add( "dataURL", "/ds" )

    RestDataSourceManager.registerDataSource( this )

    val handlers : ArrayBuffer[ DsFetchParams => RecordsData ] = ArrayBuffer.empty


    /************************************************************************/
    /********** handler for build data  ******************************/

    def addDsHandler( hndl : DsFetchParams => RecordsData ) = {
        handlers.append( hndl )
        this
    }

}

object RestDataSourceManager {

    private val dsList : ArrayBuffer[RestDataSource] = ArrayBuffer.empty

    private[sc] def registerDataSource( ds : RestDataSource  ){ dsList.append( ds )}

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
