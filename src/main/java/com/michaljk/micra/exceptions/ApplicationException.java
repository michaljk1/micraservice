package com.michaljk.micra.exceptions;

public class ApplicationException extends Exception {

    public ApplicationException(String errorMessage){
        super(errorMessage);
    }

    @Override
    public String toString(){
        return getMessage();
    }

}
