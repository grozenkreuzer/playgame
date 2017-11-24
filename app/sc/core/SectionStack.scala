package sc.core

import sc.core.StaticObjects.VisibilityMode.VisibilityMode

case class SectionStackSection( name : String, title: String, items:List[ ScObjectWithId[_]] ) extends ScNamedTrait[SectionStackSection] {

    override def printBefore = false
    
    def startString = "{"
    def endString: String = "}"

    def expanded = add( "expanded", "true" )

    add( "title", title )
    addNonEmpty( "items", items )

}

case class SectionStack( id : String, sections : List[SectionStackSection] ) extends LayOutTrait[SectionStack] {

    override val scClassName = "SectionStack"

    def isVertical = true

    def members : List[ScObjectWithId[_]] = Nil

    def visibilityMode( mode : VisibilityMode ) = add("visibilityMode", mode.toString )

    before( sections )

    addNonEmpty( "sections", sections )

}