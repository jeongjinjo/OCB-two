package com.green.onezo.global.error;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException{

    private String message;

    public BizException(String message){
        this.message = message;
    }

}
