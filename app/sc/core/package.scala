package sc

package object core {

  def strProp( name: String, value : String ) : String = prop( name, value, true )

  def noStrProp( name: String, value : String ) : String = prop( name, value, false )

  private def prop( name: String, some : String, isString : Boolean  ) = {
      val value = if( isString ) "\"" + some + "\"" else some
      s"$name:$value"
  }


}
