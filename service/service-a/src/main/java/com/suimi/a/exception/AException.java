/*
 * Copyright (c) 2013-2015, 成都中联信通科技股份有限公司
 * Created by lichengcai on 2017-05-22.
 */
package com.suimi.a.exception;

public class AException extends RuntimeException {
    private static final long serialVersionUID = 2230554814008431249L;

    private ErrorMsg errorMsg;

    public AException(ErrorMsg errorMsg) {
        super(errorMsg.getMsg());
        this.errorMsg = errorMsg;
    }

    public AException(ErrorMsg errorMsg, Throwable cause) {
        super(errorMsg.getMsg(), cause);
        this.errorMsg = errorMsg;
    }

    public ErrorMsg getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(ErrorMsg errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class ErrorMsg{
        private String code;

        private String msg;

        public ErrorMsg(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }

}
