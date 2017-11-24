package sc.core

object StaticObjects {


    object VisibilityMode extends Enumeration {
        type VisibilityMode = Value
        val mutex = Value("mutex")
        val multiple = Value("multiple")
    }

    object FieldType extends Enumeration{

        type FieldType = Value

        val text = Value("text")
        val datetime = Value("datetime")

    }

}
