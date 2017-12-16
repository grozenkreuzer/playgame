package controllers

import javax.inject.Inject

import akka.stream.scaladsl.StreamConverters

import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc._
import sc._
import sc.core.StaticObjects._
import sc.core.isc.UnQuotedString
import sc.core._


class MainController @Inject
    (cc: ControllerComponents) extends AbstractController(cc) {

    def skinName = "Enterprise"

    def index = Action {
        Ok(views.html.index("Hello friends "))
    }

    def table( skinId : Int ) = Action {
        sc.skinName =  ScSkin( skinId ).toString
        Ok( views.html.table( sc.scriptCDATA( getTetsStructure) )  )
    }

    def getSmartResource(file : String ) = Action {

        val baseUrl = "toserve/isomorphic"

        val srcPath = file.split("/").toList 

        val fType =  srcPath.last.split('.').apply(1)

        val pathList =
            if( srcPath(1) == "some" ) srcPath.take( 1 ) ::: sc.skinName :: srcPath.last :: Nil
            else srcPath

        val wr = baseUrl :: pathList

        val pth = wr.mkString( "/", "/", "" )

        val url = this.getClass.getResource( pth )

        val dataContent = StreamConverters.fromInputStream( () => url.openStream() )

        val cntType =
            fType match {
                case "css" => "text/css"
                case "js" => "application/javascript"
                case _ => "application/octet-stream"
            }

        Ok.chunked( dataContent ).as( cntType )

    }

    def getTetsStructure =  {


        val dataSourceExample = RestDataSource(
            "infoDS",
            DataSourceField( "MESSAGE", FieldType.text ).title( "Сообщение" ) ::
              DataSourceField( "DATEFIELD", FieldType.datetime ).title( "Сообщение №2" ) ::
                DataSourceField( "MESSAGE2", FieldType.text ).title( "Сообщение №2" ) :: Nil
        )

        val optionDatasourceExample = RestDataSource(
            "skinsDS",
            DataSourceField( "ID", FieldType.integer ).title( "id" ) ::
                DataSourceField( "VALUE" ).title( "Значение" ) :: Nil
        ).addDsHandler(
            prms =>
                ScSkin.values.map( sk => Map( "ID" -> sk.id.toString, "VALUE" -> sk.toString ) ).toList
        )


        val aBtn  = Button( "testBtnA",  "Я кнопка во вкладке 1, проверь меня").width( "200" ).click( isc.say("Здравствуй дорогой товарищ") )
        val a2Btn = Button( "testBtnA2", "Я кнопка во вкладке 1, проверь меня").width( "200" ).click( isc.say("Здравствуй дорогой товарищ") )
        val a3Btn = Button( "testBtnA3", "Я кнопка во вкладке 1, проверь меня").width( "200" ).click( isc.say("Здравствуй дорогой товарищ") )

        val bTbn = Button( "testBtnB", "Я кнопка во вкладке 2, проверь меня" ).width( "200" ).click(  isc.say("Здравствуй дорогой товарищ") )

        val scVlo = VLayOut(
            "scVlo", aBtn :: a2Btn :: a3Btn ::  Nil
        ).defaultLayoutAlign( Align.center ).
            border( "1").
                membersMargin(15).
                    layoutTopMargin( 50 )


        val ts =
            TabSet( "testTS",
                List(
                    Tab( "Вкладка 1", scVlo ),
                    Tab( "Вкладка 2", bTbn )
                )
            )

        val lg =
            ListGrid(
                "infoLG",
                dataSourceExample.addDsHandler(
                    ( params : DsFetchParams ) => (1 to 20 ).toList.map(
                        index =>
                            // test data
                            dataSourceExample.fields.map(
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
            ).width( "550" ).rowClick( isc.say( UnQuotedString( "record.MESSAGE" ) ) ).autoFetchData(true)

        val b = Button( "testBtnInStk", "кнопка в секции" ).width( "200" ).click( isc.say("Здравствуй дорогой товарищ" ))

        val formForSection =
            DynamicForm( "sectionDF",
                List(
                    FormItem( "fieldOne", "простое текстовое поле" ),
                    FormItem( "fieldTwo", "простое текстовое поле" ).titleOrientation(TitleOrientation.top) ,
                    FormItem( "spaceItem", "" ).itemType( FormItemType.spacer ).height(20) ,
                    FormItem( "headerItem", "поле 2" ).itemType( FormItemType.header ).value("Выбор стиля интерфеса") ,
                    FormItem( "skinName", "выпадающий список" ).rowSpan( 2 ).itemType( FormItemType.select ).
                        optionDataSource( optionDatasourceExample ).defaultToFirstOption,
                    FormItem( "fieldBtn", "Изменить скин" ).
                        itemType( FormItemType.button ).click.handler(
                            """location.assign( location.protocol + '//' + location.host + location.pathname + '?skinId=' + sectionDF.getValue("SKINNAME") );"""
                        )
                )
            ).numCols(4).colWidths(List( 300,100,300,100 ))
           .cellBorder(1)


        val sectionStackExample =
            SectionStack(
                "mainSS",
                List(
                    SectionStackSection("secOne", "Секция №1", List( b,ts ) ).expanded ,
                    SectionStackSection("secTwo", "Секция №2", List( formForSection.border("1") ) ).expanded
                )
            ).visibilityMode( VisibilityMode.multiple ).defaultLayoutAlign("center")

        val buttonUnderTable =
            Button( "testBtn", "Я кнопка под таблицей, проверь меня").
                click( isc.say("Таблица большая :-)" ) ).
                    height("50").width("550"). wrap( true )

        val ssVlo = VLayOut( "mainVLO", List( lg, buttonUnderTable ) ).showEdges.defaultLayoutAlign("center")

        val mainHLO = HLayOut( "mainHLO", List( ssVlo, sectionStackExample ) )

        mainHLO.relativePosition.width( "95%" ).height("500")

        mainHLO.toString
    }



}
