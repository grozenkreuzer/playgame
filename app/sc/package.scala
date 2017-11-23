import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import play.twirl.api.Html
import sc.core._

package object sc {


  type RecordsData = List[Map[String, String]]

  implicit class RecordsDataString( in :  RecordsData){
      def toXmlRecords : String  =  in.map( m => makeRecord( m ) ).mkString
  }

  //---------------------------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------------------------

  def scriptCDATA(in:String) = Html( s"""<script type="text/javascript"> ${ CDATA( in )} </script>""" )

  def  CDATA(in:String)= "\n//<![CDATA[\n"+in+"\n//]]>\n"

  def escapeXmlSymbols( in : String ) = {
    in.trim.filterNot( _.isControl ).replace( "&","&amp;" ).
      replace(  "<","&lt;").
      replace(  ">","&gt;").
      /*replace(  "'","&apos;").
      replace(  "\"","&quot;").*/
      replace(  "\n"," ").
      replace(  "\t"," t").
      replace(  "\r","\\r")
  }

  def makeRecord(listNameValue : Iterable[(String, String)]) : String = {
    "<record>" + listNameValue.foldLeft("")((all, NameAndValue) =>
      all + "<" + NameAndValue._1.toUpperCase + ">" + escapeXmlSymbols( NameAndValue._2 ) + "</" + NameAndValue._1.toUpperCase + ">") + "</record>"
  }

  //---------------------------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------------------------

  def getTetsStructure = {

      val ds = RestDataSource(
          "infoDS",
          DataSourceField( "MESSAGE", FieldType.text ).title( "Сообщение" ) :: Nil
      )

      val lg =
          ListGrid(
              "infoLG",
               ds.addDsHandler(
                  ( params : DsFetchParams ) => List(
                    ds.fields.map( f => f.name -> "test value" ).toMap
                  )
              )
          ).width( "300" ).rowClick(
              "isc.say( record.MESSAGE  );"
          )

      val hlo =
          HLayOut(
              "mainHLO",
              VLayOut(
                  "mainVLO",
                  List(
                      lg.autoFetchData(true) ,
                      Button( "testBtn", "Я кнопка, проверь меня", " isc.say('Hello Moto'); " ).height("100").wrap(true)
                  )
              ) :: Nil
          )


      hlo.relativePosition.width( "95%" ).height("500")

      hlo.toString
  }

  case class DsFetchParams( name : String, start : Int, end : Int, componentId : String )

  object FieldType extends Enumeration{

    type FieldType = Value

    val text = Value("text")

  }

}
