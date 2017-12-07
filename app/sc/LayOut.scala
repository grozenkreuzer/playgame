package sc

import sc.core.ScObjectWithId

trait LayOutTrait[T] extends ScObjectWithId[T] {

    sel : T =>

    /************************************************************************/
    /********** init   ******************************/

    def isVertical : Boolean

    val scClassName = ( if( isVertical  ) "V" else "H" ) + "Layout"

    def members : List[ScObjectWithId[_]]

    addNonEmpty( "members", members )


    /************************************************************************/
    /********** props   ******************************/

    def relativePosition = { add( "position", "relative" ) }

    def defaultLayoutAlign( align : String ) = add("defaultLayoutAlign", align)



}

case class HLayOut( id : String, members : List[ScObjectWithId[_]] ) extends LayOutTrait[HLayOut] {
    def isVertical = false
}

case class VLayOut( id : String, members : List[ScObjectWithId[_]] ) extends LayOutTrait[VLayOut] {
    def isVertical = true
}


