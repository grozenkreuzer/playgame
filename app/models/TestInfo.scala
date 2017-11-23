package models

trait ScRecordTrait {

  def toXmlRecordStr : String =
    s"<record>${
      getFieldsAndValues.map( nv => "<" + nv._1 + ">" + nv._2 + "</" + nv._1 + ">" ).mkString
    }</record>"

  def getFieldsAndValues : Map[String, String]

}

case class TestInfo (message: String) extends  ScRecordTrait {
  def getFieldsAndValues : Map[String, String] = Map( "message" -> message )
}





