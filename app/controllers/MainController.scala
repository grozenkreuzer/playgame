package controllers

import javax.inject.Inject

import models.{TestInfo}
import play.api.mvc.Action
import play.api.data._
import play.api.i18n._
import play.api.mvc._


class MainController @Inject
    (cc: ControllerComponents) extends AbstractController(cc) {

    def index = Action {
        Ok(views.html.index("Hello friends "))
    }

    def table = Action {
        Ok( views.html.table( sc.scriptCDATA( sc.getTetsStructure) )  )
    }

    def getTestInfoList = {
        val ret =
            List(
                TestInfo("one"),
                TestInfo("two"),
                TestInfo("three")
            )
        ret
    }
}
