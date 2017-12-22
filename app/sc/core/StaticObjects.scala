package sc.core

object StaticObjects {

    object VisibilityMode extends Enumeration {
        type VisibilityMode = Value

        val mutex : VisibilityMode       = Value("mutex")
        val multiple : VisibilityMode    = Value("multiple")
    }

    object FormItemType extends Enumeration {
        type FormItemType = Value

        val button      = Value("button")
        val select      = Value("select")
        val header      = Value("header")
        val spacer      = Value("spacer")
        val textArea    = Value("textArea")
        val radioGroup  = Value("radioGroup")

    }

    object TitleOrientation extends Enumeration{
        type TitleOrientation = Value

        val left        = Value("left")
        val right       = Value("right")
        val top         = Value("top")
    }

    object Align extends Enumeration{
        type Align = Value

        val center      = Value("center")
        val left        = Value("left")
        val right       = Value("right")
        val top         = Value("top")
        val bottom      = Value( "bottom" )

    }

    object FieldType extends Enumeration{
        type FieldType = Value

        val text        = Value("text")
        val datetime    = Value("datetime")
        val integer     = Value("integer")

    }

    object ScSkin extends Enumeration{
        type ScSkin     = Value

        val Enterprise  = Value("Enterprise")
        val BlackOps    = Value("BlackOps")
        val Cupertino   = Value("Cupertino")
        val EnterpriseBlue = Value("EnterpriseBlue")
        val fleet       = Value("fleet")
        val Graphite    = Value("Graphite")
        val SilverWave  = Value("SilverWave")
        val Simplicity  = Value("Simplicity")
        val SmartClient = Value("SmartClient")
        val TreeFrog    = Value("TreeFrog")
    }

    object HTTPMethod extends Enumeration{
        type HTTPMethod = Value

        val POST        = Value("POST")
        val GET         = Value("GET")
    }

}
