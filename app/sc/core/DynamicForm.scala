package sc.core

case class DynamicForm( id : String, fields: List[FormItem] ) extends ScObjectWithId[DynamicForm]  {

    override val scClassName = "DynamicForm"
    add("fields", fields)

    override def border(borderDescription: String): DynamicForm =
        addNoStr( "border", borderDescription )

    def cellBorder( size : String ) = add("cellBorder", size )
}


case class FormItem( name : String, title: String ) extends ScNamedTrait[FormItem] {

    def startString = "{"
    def endString: String = "}"

    add( "title", title )

}
