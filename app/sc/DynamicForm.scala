package sc

import sc.core.StaticObjects.FormItemType.FormItemType
import sc.core.{JsFunctionNamed, ScNamedTrait, ScObjectWithId}

case class DynamicForm( id : String, fields: List[FormItem] ) extends ScObjectWithId[DynamicForm]  {


    /************************************************************************/
    /********** init   ******************************/

    override val scClassName = "DynamicForm"

    add("fields", fields)

    before( fields )

    override def border(borderDescription: String): DynamicForm =
        addNoStr( "border", borderDescription )


    /************************************************************************/
    /********** props   ******************************/

    def cellBorder( size : Int ) = add("cellBorder", size.toString )

    def cellBorder( size : String ) = add("cellBorder", size )

    def numCols( size : Int ) = addNoStr( "numCols", size )

}


case class FormItem( name : String, title: String ) extends ScNamedTrait[FormItem] {


    /************************************************************************/
    /********** init   ******************************/

    override def printBefore = false

    add( "title", title )


    /************************************************************************/
    /********** props   ******************************/

    def colSpan( colNum : Int ) = addNoStr( "colSpan", colNum )

    def rowSpan( colNum : Int ) = addNoStr( "rowSpan", colNum )

    def defaultToFirstOption = add( "defaultToFirstOption ", "true" )

    def defaultToFirstOption( yes : Boolean ) = add( "defaultToFirstOption ", yes.toString )

    def defaultValue( in : Any ) = addNoStr( "defaultValue", in )

    def defaultValue( in : String ) = add( "defaultValue", in )

    def itemType( tp : FormItemType ) = add( "type", tp.toString )

    def value( in : Any ) = addNoStr( "value", in )

    def value( in : String ) = add( "value", in )

    def optionDataSource( dataSource : RestDataSource, valueField : String = "ID", displayField : String = "VALUE") = {

        add( "displayField", displayField )
        add( "valueField", valueField )

        before( dataSource.toString )
        addNoStr( "optionDataSource", dataSource.id )
    }


    /************************************************************************/
    /********** functions  ******************************/

    val click = JsFunctionNamed( this, "click" , List( "form", "item" ))

}
