package sc.core

case class Button( id : String, title : String, clickFunctionBody : String ) extends ScObjectWithId[Button] {

    override val scClassName = "Button"

    def wrap( set : Boolean ) = { add( "wrap", set.toString ) }

    add( "title", title )
    addFunction( "click", JsFunctionProp(  clickFunctionBody ) )



}

