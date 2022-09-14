package com.devsuperior.dscatalog.service.exception;

//Classe padrão para chamarmos quando existir a possibilidade de teu um erro notfound em uma classe
//A mensagem do construtor é personalizavel no método que chama essa classe
//A mensagem personalizada aparece no console log
//A mensagem apenas aparece para o "cliente" quando implementamos exception handler
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
