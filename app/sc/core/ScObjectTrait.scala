package sc.core

trait ScObjectWithId[T] extends ScObjectTrait[T] {

    self : T =>

    val id : String

    add( "ID", id )

}

trait ScObjectTrait[T] extends ScParts[T] {

    self : T =>

    val scClassName : String

    def startString = s"isc.$scClassName.create({"

    def endString = "});" + _new2Line


    /******************* standart Canvas Attributes ****************************/

    def autoDraw( auto : Boolean ) = { addNoStr( "autoDraw", auto.toString ) }

    def alignCenter() = add( "align", "center" )

    def border( borderDescription : String ) = add( "border", borderDescription )

    def showEdges( show: Boolean):T = addNoStr("showEdges", show.toString )

    def showEdges: T = showEdges( true )

    def width( w : String ) = add( "width", w )

    def height( h : String ) = add( "height", h )

    def relativePosition = { add( "position", "relative" ) }

    /***************************************************************************/


}


