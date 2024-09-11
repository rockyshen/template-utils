package com.rockyshen;

import com.rockyshen.result.Result;
import com.rockyshen.result.StatusCodeEnum;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Result<String> myResult = new Result<>();

        Result<Object> fail = myResult.fail(StatusCodeEnum.ERR_LOGIN);
        System.out.println(fail);
    }
}