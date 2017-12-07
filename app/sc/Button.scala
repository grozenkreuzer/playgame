package sc

import sc.core.{JsFunctionNamed, ScObjectWithId}

case class Button( id : String, title : String) extends ScObjectWithId[Button] {

    /************************************************************************/
    /********** init   ******************************/

    override val scClassName = "Button"

    add( "title", title )


    /************************************************************************/
    /********** props   ******************************/

    def wrap( set : Boolean ): Button = { add( "wrap", set.toString ) }

    def wrap: Button = wrap( true )


    /************************************************************************/
    /********** functions  ******************************/

    val click = JsFunctionNamed( this, "click", Nil )

}

