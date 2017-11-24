import akka.http.scaladsl.model.HttpHeader.ParsingResult.Ok
import play.twirl.api.Html
import sc.core.StaticObjects.{FieldType, VisibilityMode}
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
          DataSourceField( "MESSAGE", FieldType.text ).title( "Сообщение" ) ::
              DataSourceField( "DATEFIELD", FieldType.datetime ).title( "Сообщение №2" ) ::
              DataSourceField( "MESSAGE2", FieldType.text ).title( "Сообщение №2" ) ::Nil
      )

      val lg =
          ListGrid(
              "infoLG",
               ds.addDsHandler(
                  ( params : DsFetchParams ) => (1 to 20 ).toList.map(
                       index =>
                           // test data
                           ds.fields.map(
                               f =>
                                   f.name -> (
                                       if( f.name == "DATEFIELD" ){
                                           "2017-01-" +index.toString.reverse.padTo(2, '0').reverse
                                       } else
                                            ( index + " Запись в таблица поля " + f.name )
                                   )
                           ).toMap
                  )
              )
          ).width( "550" ).rowClick(
              "isc.say( record.MESSAGE );"
          )

      val b =
          Button(
              "testBtn3",
              "Я кнопка в стэке 1, проверь меня",
              " isc.say('Здравствуй дорогой товарищ');"
          ).width( "200" )

      val formForSection =
          DynamicForm( "sectionDF",
              List( FormItem( "fieldOne", "поле 1" ), FormItem( "fieldTwo", "поле 2" ) ) )

      val hlo =
          HLayOut(
              "mainHLO",
              VLayOut(
                  "mainVLO",
                  List(
                      lg.autoFetchData(true) ,
                      Button( "testBtn", "Я кнопка, проверь меня", " isc.say('Hello Moto'); " ).
                          height("50").width("400"). wrap(true)//.alignCenter()
                  )
              ).showEdges.defaultLayoutAlign("center") ::
               SectionStack(
                  "mainSS",
                   List(
                       SectionStackSection("secOne", "Секция №1", b :: Nil).expanded ,
                       SectionStackSection("secTwo", "Секция №2",
                           List(
                               Button(
                                   "testBtn2",
                                   "Я кнопка в стэке 2, проверь меня",
                                   " isc.say('Hello Button');"
                               ).width( "200" ),
                               formForSection.border("1")
                           )
                       ).expanded
                   )
               ).visibilityMode( VisibilityMode.multiple ).defaultLayoutAlign("center") :: Nil
          )//.showEdges


      hlo.relativePosition.width( "95%" ).height("500")

      hlo.toString
  }

  case class DsFetchParams( name : String, start : Int, end : Int, componentId : String )



}
