package sc

import sc.core.ScObjectWithId
import sc.core.StaticObjects.Align.Align

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

    def defaultLayoutAlign( align : Align ):T = defaultLayoutAlign( align.toString )

    def defaultLayoutAlign( align : String ):T = add("defaultLayoutAlign", align)

    def membersMargin( int : Int ) = addNoStr( "membersMargin", int )

    def layoutMargin( int : Int ) = addNoStr( "layoutMargin", int )

    def layoutBottomMargin ( int : Int ) = addNoStr( "layoutBottomMargin", int )

    def layoutLeftMargin ( int : Int ) = addNoStr( "layoutLeftMargin", int )

    def layoutRightMargin ( int : Int ) = addNoStr( "layoutRightMargin", int )

    def layoutTopMargin  ( int : Int ) = addNoStr( "layoutTopMargin", int )




}

case class HLayOut( id : String, members : List[ScObjectWithId[_]] ) extends LayOutTrait[HLayOut] {
    def isVertical = false
}

case class VLayOut( id : String, members : List[ScObjectWithId[_]] ) extends LayOutTrait[VLayOut] {
    def isVertical = true
}


