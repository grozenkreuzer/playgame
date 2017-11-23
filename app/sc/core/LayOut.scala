package sc.core

trait LayOutTrait[T] extends ScObjectWithId[T] {

    sel : T =>

    val scClassName = ( if( isVertical ) "V" else "H" ) + "Layout"

    val isVertical : Boolean
    val members : List[ScObjectWithId[_]]

    def relativePosition = { add( "position", "relative" ) }

    add( "members", members )

}

case class HLayOut( id : String, members : List[ScObjectWithId[_]] ) extends LayOutTrait[HLayOut] {
    val isVertical = false
}

case class VLayOut( id : String, members : List[ScObjectWithId[_]] ) extends LayOutTrait[VLayOut] {
    val isVertical = true
}

