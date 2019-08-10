package com.gruporyc.restaurant.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SimpleResponse: Abstract object that manage simple response data from services
 * @author jmunoz
 * @since 31/07/2019
 * @version 1.0.0
 */
public class SimpleResponse {
    private boolean success = false;
    private String message;
    private String code = "N/A";

    /**
     * SimpleResponse: Elevated empty constructor of the class
     * @author jmunoz
     * @since 07/08/2019
     */
    public SimpleResponse(){
        super();
    }

    /**
     * SimpleResponse: Parametrized constructor of the class
     * @author jmunoz
     * @param success Boolean indicator for success transaction
     * @param message Support message for transaction response
     * @param code Response code if available
     * @since 10/08/2019
     */
    public SimpleResponse(boolean success, String message, String code){
        this.success = success;
        this.message = message;
        this.code = code;
    }

    /**
     * @return the indicator for success transaction
     */
    @JsonProperty("success")
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the indicator for success transaction
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return Support message for transaction response
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * @param message Support message for transaction response
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return Response code for transaction response
     */
    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    /**
     * @param code Response code for transaction response
     */
    public void setCode(String code) {
        this.code = code;
    }

}
