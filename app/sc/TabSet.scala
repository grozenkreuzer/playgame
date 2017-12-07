package sc

import sc.core.{ScObjectWithId, ScParts}

case class TabSet( id : String, tabs : List[Tab] ) extends ScObjectWithId[TabSet] {


    /************************************************************************/
    /********** init   ******************************/

    val scClassName = "TabSet"

    addNonEmpty( "tabs", tabs )

    before( tabs )

}

case class Tab( title: String, pane: ScObjectWithId[_] )  extends ScParts[Tab] {


    /************************************************************************/
    /********** init   ******************************/

    override def printBefore = false

    def startString = "{"

    def endString: String = "}"

    add( "title", title )

    add( "pane", pane )

}