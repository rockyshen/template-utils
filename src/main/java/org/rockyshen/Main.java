package org.rockyshen;

import org.rockyshen.result.Result;
import org.rockyshen.result.StatusCodeEnum;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Result<String> myResult = new Result<>();

        Result<Object> fail = myResult.fail(StatusCodeEnum.ERR_LOGIN);
        System.out.println(fail);
    }
}