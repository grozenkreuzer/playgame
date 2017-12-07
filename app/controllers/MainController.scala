package controllers

import javax.inject.Inject

import play.api.mvc._
import sc.{Button, DataSourceField, DsFetchParams, DynamicForm, FormItem, HLayOut, ListGrid, RestDataSource, SectionStack, SectionStackSection, Tab, TabSet, VLayOut}
import sc.core.StaticObjects.{FieldType, FormItemType, ScSkin, VisibilityMode}
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

    def getTetsStructure = {

        val ds = RestDataSource(
            "infoDS",
            DataSourceField( "MESSAGE", FieldType.text ).title( "Сообщение" ) ::
                DataSourceField( "DATEFIELD", FieldType.datetime ).title( "Сообщение №2" ) ::
                DataSourceField( "MESSAGE2", FieldType.text ).title( "Сообщение №2" ) ::Nil
        )

        val dsOpt = RestDataSource(
            "skinsDS",
            DataSourceField( "ID", FieldType.integer ).title( "id" ) ::
                DataSourceField( "VALUE" ).title( "Значение" ) :: Nil
        ).addDsHandler(
            prms =>
                ScSkin.values.map( sk => Map( "ID" -> sk.id.toString, "VALUE" -> sk.toString ) ).toList
        )


        val aBtn = Button( "testBtnA", "Я кнопка во вкладке 1, проверь меня").width( "200" ).click( isc.say("Здравствуй дорогой товарищ") )

        val bTbn = Button( "testBtnB", "Я кнопка во вкладке 2, проверь меня" ).width( "200" ).click(  isc.say("Здравствуй дорогой товарищ") )

        val ts = TabSet( "testTS", List( Tab( "Вкладка 1", aBtn ), Tab( "Вкладка 2", bTbn ) ) )

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
            ).width( "550" ).rowClick( isc.say( UnQuotedString( "record.MESSAGE" ) ) )

        val b =
            Button(
                "testBtnInStk",
                "Я кнопка в стэке 1, проверь меня"
            ).width( "200" ).click( isc.say("Здравствуй дорогой товарищ" ))

        val formForSection =
            DynamicForm( "sectionDF",
                List(
                    FormItem( "fieldOne", "поле 1" ),
                    FormItem( "fieldTwo", "поле 2" ) ,
                    FormItem( "skinName", "Выбор стиля интерфейса" ).rowSpan( 2 ).itemType( FormItemType.select ).
                        optionDataSource( dsOpt ).defaultToFirstOption,
                    FormItem( "fieldBtn", "Изменить скин" ).
                        itemType( FormItemType.button ).click.handler(
                        //isc.say( UnQuotedString( "location.protocol + '//' + location.host + location.pathname" )  )
                        """location.assign( location.protocol + '//' + location.host + location.pathname + '?skinId=' + sectionDF.getValue("SKINNAME") );"""
                    )
                )
            ).numCols(4).cellBorder(1)

        val hlo =
            HLayOut(
                "mainHLO",
                VLayOut(
                    "mainVLO",
                    List(
                        lg.autoFetchData(true) ,
                        Button( "testBtn", "Я кнопка, проверь меня").click( isc.say("Hello Moto" ) ).
                            height("50").width("550"). wrap(true)//.alignCenter()
                    )
                ).showEdges.defaultLayoutAlign("center") ::
                    SectionStack(
                        "mainSS",
                        List(
                            SectionStackSection("secOne", "Секция №1", b :: ts :: Nil).expanded ,
                            SectionStackSection("secTwo", "Секция №2",
                                List(
                                    Button(
                                        "testBtn2",
                                        "Я кнопка в стэке 2, проверь меня"
                                    ).click(
                                        isc.say( "Hello Button" )
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

}
