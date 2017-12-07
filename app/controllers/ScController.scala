package controllers

import javax.inject.Inject
import sc._
import play.api.mvc.{AbstractController, ControllerComponents}

class ScController @Inject
(cc: ControllerComponents)
  extends AbstractController(cc) {


    def processDsRequest =  Action{
        implicit request =>

            val startRow = request.getQueryString("_startRow").get.toInt
            val endRow = request.getQueryString("_endRow").get.toInt

            Ok(
                getResponse(
                    startRow, endRow,
                    request.getQueryString("_dataSource").get,
                    request.getQueryString("_componentId").get
                )
            )
    }

}
