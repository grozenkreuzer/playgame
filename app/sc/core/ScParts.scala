package sc.core

import scala.collection.mutable.ArrayBuffer

trait ScParts[EndType]{

  
  self : EndType =>

  def startString : String

  def endString : String

  def addFunction( funcName :String, fnc : JsFunctionProp ): EndType ={
      addNoStr( funcName, fnc.toString() )
      self
  }

  def add( name : String, value : String ) : EndType = add( name, value,  true )

  def addNoStr( name : String, value : String ) : EndType = add( name, value, false )

  def addNonEmpty ( name : String, values : List[Any], isString : Boolean = false ) : EndType = {

      if( values.nonEmpty )
          add( name, values, isString )

      this
  }

  def add( name : String, values : List[Any], isString : Boolean = false ) : EndType = {

       val prop =
            if( values.head.isInstanceOf[ ScObjectWithId[_] ] ){

                val vList : List[ScObjectWithId[_]] = values.asInstanceOf[ List[ScObjectWithId[_]]]
                before( vList.mkString( _newLine ) )
                vList.map( _.id ).mkString( "," )

            } else values.map( _2space + _).mkString( _newLine , "," + _newLine, _newLine  )


      add( noStrProp( name, toArray( prop ) ) )
      
  }

  def add( name : String, strValue : String, isString : Boolean ) : EndType = {
      if (isString) add(strProp(name, strValue))
      else add(noStrProp(name, strValue))
  }

  def add( str : String ) : EndType = { addPart(  str ); self }

  def printBefore : Boolean = true

  def before( innerElements : List[ScParts[_]] ):Unit =
      innerElements.foreach( el => before( el.beforeScript.mkString ))

  def before( part : String ):Unit =  beforeScript.append( part )

  private def toArray( in : String  )  = "[ " + in + _space + "]"

  private def addPart( part : String ) = { parts = part :: parts; self }

  private var parts : List[ String ] = Nil

  private val beforeScript : ArrayBuffer[String] = ArrayBuffer.empty[String]

  private def getAllPartString ={
      parts.map( _space + _ ).mkString( "," + _newLine )
  }

  
  // ----------------- format text -----------------------------------

  protected val butify : Boolean = true

  def _newLine : String = if( butify ) "\n" else ""

  def new2Line : String = _newLine + _newLine

  def _space : String = if( butify ) "  " else ""

  def _2space : String = _space + _space

  // ----------------- / format text -----------------------------------


  override def toString : String = {

      val beforeStr =
        if( !printBefore || beforeScript.isEmpty ) ""
        else  beforeScript.mkString //(/*"\n", "\n", "\n"*/)

        beforeStr +
        startString + _newLine +
        getAllPartString + _newLine +
        endString
  }




}