package sc.core

object isc {

    type RecordsData = List[Map[String, String]]

    type RPCParams = Map[String, String]

    sealed trait QuotedOrNotString {
        override def toString : String =
            this match {
                case q : QuotedString => "'" + q.text + "'"
                case unq : UnQuotedString => unq.text
            }
    }

    case class QuotedString( text : String ) extends QuotedOrNotString

    case class UnQuotedString( text : String ) extends QuotedOrNotString

    val defaultTitle = "{title:'Инфо'}"

    def say( message : String ) : String = say( QuotedString(message) )

    def say( message : QuotedOrNotString, dialogProps : String = defaultTitle ) : String = {
        val dPrps =
            if( dialogProps.isEmpty ) dialogProps
            else "," + dialogProps

        s"isc.say($message$dPrps);"
    }

}
