package controllers

import javax.inject.Inject
import sc._
import play.api.mvc.{AbstractController, ControllerComponents}
import sc.DsFetchParams
import sc.core.RestDataSourceManager

class ScController @Inject
(cc: ControllerComponents)
  extends AbstractController(cc) {


    def ResponseForGet(startRow: Int, endRow: Int, fetchedRows: Int, data: String) = {
        val totalRows = if ((endRow - startRow) <= fetchedRows) endRow + 2 else startRow + fetchedRows
        val r = "<response><status>0</status><startRow>" + startRow.toString + "</startRow><endRow>" + (startRow + fetchedRows - 1).toString + "</endRow><totalRows>" + totalRows.toString + "</totalRows><data>" + data.mkString + "</data></response>"
        r
    }

    def processDsRequest =  Action{
        implicit request =>

            val startRow = request.getQueryString("_startRow").get.toInt
            val endRow = request.getQueryString("_endRow").get.toInt

            val data =
                RestDataSourceManager.getDsHandlers(
                    DsFetchParams(
                        request.getQueryString("_dataSource").get,
                        startRow,
                        endRow,
                        request.getQueryString("_componentId").get
                    )
                )

            Ok(
                ResponseForGet(
                    startRow, endRow,
                    data.size, data.toXmlRecords
                )
            )
    }

}
