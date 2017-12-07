package sc.core

import scala.collection.mutable.ArrayBuffer

trait ScParts[EndType]{

  self : EndType =>


    /********** init   ******************************/
    def startString : String

    def endString : String


    /********** add props methods ******************************/
    def add( name : String, value : String ) : EndType = add( name, value,  true )

    def add( name : String, value : ScObjectWithId[_] ) : EndType = {
      before( value.toString )
      addNoStr( name, value.id)
  }

    def addNoStr( name : String, value : Any ) : EndType = add( name, value.toString, false )

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


    /********** build before string ******************************/
    // если печать объекта передана выше по структуре, то самом объекет печать отключаем переопределением этого метода
    def printBefore : Boolean = true

    def before( innerElements : List[ScParts[_]] ):Unit = innerElements.foreach( el => before( el.beforeScript.mkString ))

    def before( part : String ):Unit =  beforeScript.append( part )

    private def strProp( name: String, value : String ) : String = prop( name, value, true )

    private def noStrProp( name: String, value : String ) : String = prop( name, value, false )

    private def prop( name: String, some : String, isString : Boolean  ) = {
        val value = if( isString ) "\"" + some + "\"" else some
        s"$name:$value"
    }

    private def toArray( in : String  )  = "[ " + in + _space + "]"

    private def addPart( part : String ) = { parts = part :: parts; self }

    private var parts : List[ String ] = Nil

    private val beforeScript : ArrayBuffer[String] = ArrayBuffer.empty[String]

    private def getAllPartString = parts.map( _space + _ ).mkString( "," + _newLine )


    /********** format script   ******************************/
    protected val butify : Boolean = true

    def _newLine : String = if( butify ) "\n" else ""

    def _new2Line : String = _newLine + _newLine

    def _space : String = if( butify ) "  " else ""

    def _2space : String = _space + _space


    /********** build finish script ******************************/
    override def toString : String = {

        (
            if( !printBefore || beforeScript.isEmpty ) ""
            else  beforeScript.mkString
        ) +
        startString + _newLine +
        getAllPartString + _newLine +
        endString
    }




}