package sc

import sc.core.{JsFunctionNamed, ScObjectTrait}
import sc.core.isc.{QuotedOrNotString, RPCParams}
import play.api.libs.json._

import scala.collection.mutable.ArrayBuffer
import scala.util.parsing.json.JSONObject

case class RPCManager(   params : Map[String, QuotedOrNotString],
                         actionURL : String,
                         method: String = "GET"
                     ) extends ScObjectTrait[RPCManager] {


    /************************************************************************/
    /********** init   ******************************/

    override val scClassName = "RPCManager"

    val defaultUrl = "rpcm"

    override def startString = s"isc.$scClassName.sendRequest({"

    val callBack = JsFunctionNamed(this, "callback" , List("rpcResponse", "data", "rpcRequest" ))

    if( params.nonEmpty )
        addNoStr( "params", params.map( v => v._1 + ":" + v._2.toString ).mkString("{", ",\n", "}") )

    add( "actionURL", defaultUrl + "/" + actionURL )

    add( "httpMethod", method )


    /************************************************************************/
    /********** props   ******************************/

    def willHandleError( b : Boolean  ) = addNoStr( "willHandleError", b )

    def params( arrayFromJs : String ) = add( "params", arrayFromJs )

    RPCManager.registerRPCManager( this )

    private[sc] var handler : Option[ RPCParams => Any ] = None


    /************************************************************************/
    /********** handler for build data  ******************************/

    def addRpcHandler(hndl : RPCParams => Any ) = {
        handler = Some( hndl )
        this
    }

}

object RPCManager {

    private val dsList : ArrayBuffer[RPCManager] = ArrayBuffer.empty

    private[sc] def registerRPCManager(ds : RPCManager  ){ dsList.append( ds )}

    def getRpcResult(url : String, params : RPCParams ) = {
        val ret =
            dsList.find( _.actionURL.equalsIgnoreCase( url ) ).
                flatMap( _.handler )

        ret.map( _( params ) ).getOrElse( "" )
    }

}