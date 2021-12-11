package toorla.typeAnalyzer.CompileErrorExceptions;

import toorla.CompileErrorException;

public class TypeNotValidException extends CompileErrorException{

        private String typeName;

        public TypeNotValidException(String typeName , int atLine , int atCol){
            super(atLine , atCol);
            this.typeName = typeName;
        }

        @Override
        public String toString() {
            return String.format("Error:Line:%d:Type %s is not a valid type", atLine , typeName );
        }
}

