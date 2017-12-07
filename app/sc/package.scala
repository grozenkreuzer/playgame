import play.twirl.api.Html

package object sc {

    type RecordsData = List[Map[String, String]]

    implicit class RecordsDataString( in :  RecordsData){ def toXmlRecords : String  =  in.map( m => makeRecord( m ) ).mkString }

    var skinName : String = "Enterprise"

    def scriptCDATA( in: String ) = Html( s"""<script type="text/javascript"> ${ CDATA( in )} </script>""" )

    def  CDATA(in:String)= "\n//<![CDATA[\n"+in+"\n//]]>\n"

    def escapeXmlSymbols( in : String ) = {
        in.trim.filterNot( _.isControl ).replace( "&","&amp;" ).
          replace(  "<","&lt;").replace(  ">","&gt;").replace(  "\n"," ").replace(  "\t"," t").replace(  "\r","\\r")
    }

    def makeRecord(listNameValue : Iterable[(String, String)]) : String = {
        "<record>" + listNameValue.foldLeft("")((all, NameAndValue) =>
          all + "<" + NameAndValue._1.toUpperCase + ">" + escapeXmlSymbols( NameAndValue._2 ) + "</" + NameAndValue._1.toUpperCase + ">") + "</record>"
    }

    def ResponseForGet(startRow: Int, endRow: Int, fetchedRows: Int, data: String) = {
        val totalRows = if ((endRow - startRow) <= fetchedRows) endRow + 2 else startRow + fetchedRows
        val r = "<response><status>0</status><startRow>" + startRow.toString + "</startRow><endRow>" + (startRow + fetchedRows - 1).toString + "</endRow><totalRows>" + totalRows.toString + "</totalRows><data>" + data.mkString + "</data></response>"
        r
    }

    def getResponse( startRow : Int, endRow : Int, dataSourceId : String, componentId : String ) = {

        val data =
            RestDataSourceManager.getDsHandlers(
                DsFetchParams(
                    dataSourceId, startRow, endRow,componentId
                )
            )

        ResponseForGet( startRow, endRow, data.size, data.toXmlRecords )
    }

    case class DsFetchParams( name : String, start : Int, end : Int, componentId : String )

}
