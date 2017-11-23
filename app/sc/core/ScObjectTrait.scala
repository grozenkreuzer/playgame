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
    def endString = "});" + new2Line


    /******************* standart Canvas Attributes ****************************/

    def autoDraw( auto : Boolean ) = { addNoStr( "autoDraw", auto.toString ) }
    def alignCenter() = add( "align", "center" )

    def width( w : String ) = add( "width", w )
    def height( h : String ) = add( "height", h )

    /***************************************************************************/


}

