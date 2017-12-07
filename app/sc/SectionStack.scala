package sc

import sc.core.StaticObjects.VisibilityMode.VisibilityMode
import sc.core.{ScNamedTrait, ScObjectWithId}

case class SectionStack( id : String, sections : List[SectionStackSection] ) extends LayOutTrait[SectionStack] {


    /************************************************************************/
    /********** init   ******************************/

    override val scClassName = "SectionStack"

    def isVertical = true

    def members : List[ScObjectWithId[_]] = Nil

    before( sections )

    addNonEmpty( "sections", sections )


    /************************************************************************/
    /********** props   ******************************/

    def visibilityMode( mode : VisibilityMode ) = add("visibilityMode", mode.toString )

}


case class SectionStackSection( name : String, title: String, items:List[ ScObjectWithId[_]] ) extends ScNamedTrait[SectionStackSection] {

    /************************************************************************/
    /********** init   ******************************/
    override def printBefore = false

    add( "title", title )

    addNonEmpty( "items", items )


    /************************************************************************/
    /********** props   ******************************/

    def expanded = add( "expanded", "true" )

}