package sc.core

trait LayOutTrait[T] extends ScObjectWithId[T] {

    sel : T =>

    def isVertical : Boolean

    val scClassName = ( if( isVertical  ) "V" else "H" ) + "Layout"

    def members : List[ScObjectWithId[_]]

    def relativePosition = { add( "position", "relative" ) }

    def defaultLayoutAlign( align : String ) = add("defaultLayoutAlign", align)

    addNonEmpty( "members", members )

}

case class HLayOut( id : String, members : List[ScObjectWithId[_]] ) extends LayOutTrait[HLayOut] {
    def isVertical = false
}

case class VLayOut( id : String, members : List[ScObjectWithId[_]] ) extends LayOutTrait[VLayOut] {
    def isVertical = true
}


