package com.gruporyc.restaurant.order.controller;

import com.gruporyc.restaurant.order.dto.OrderDTO;
import com.gruporyc.restaurant.order.dto.SimpleResponse;
import com.gruporyc.restaurant.order.services.OrderServiceManager;
import com.gruporyc.restaurant.order.utilities.TextsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

/**
 * ProxyEndpointController: Proxy controllers to manage all interactions from order applications
 * to BE platform
 * @author jmunoz
 * @since 10/08/2019
 * @version 1.0.0
 */
@RestController
@RequestMapping("/v1")
public class ProxyEndpointController {

    @Autowired
    private TextsHelper textsHelper;

    @Autowired
    private OrderServiceManager orderService;

    @RequestMapping("/")
    public String index() {
        return "Restaurant order microservice working properly!";
    }

    /**
     * createOrder: Method to create a new order into platform
     * @author jmunoz
     * @since 10/08/2019
     * @param newOrder New OrderDTO to be created
     * @see ResponseEntity <Object>
     */
    @RequestMapping(value = "orders", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> createOrder(@Validated @RequestBody OrderDTO newOrder) {

        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(newOrder));
        } catch (HttpClientErrorException ex) {
            responseEntity = setErrorResponse(ex);
        }
        return responseEntity;
    }

    /**
     * getOrdersByStatus: Method to get a list of orders by status
     * @author jmunoz
     * @since 10/08/2019
     * @see ResponseEntity <Object>
     */
    @RequestMapping(value = "orders/active", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getOrdersByStatus() {

        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(orderService.getActiveOrders());
        } catch (HttpClientErrorException ex) {
            responseEntity = setErrorResponse(ex);
        }
        return responseEntity;
    }

    /**
     * createOrder: Method to update given order by id
     * @author jmunoz
     * @since 10/08/2019
     * @param orderId Order universal identifier
     * @param payload Request payload - status to be updated
     * @see ResponseEntity <Object>
     */
    @RequestMapping(value = "orders/{orderId}/status", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable("orderId") String orderId,
                                                    @RequestBody ModelMap payload) {

        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(
                    orderService.updateOrderStatus(orderId, payload.get("status").toString()));
        } catch (HttpClientErrorException ex) {
            responseEntity = setErrorResponse(ex);
        }
        return responseEntity;
    }

    private ResponseEntity<Object> setErrorResponse(HttpClientErrorException ex) {
        String message = "";
        HttpStatus status;
        switch (ex.getStatusCode().value()) {
            case 404:
                message = textsHelper.getTranslation("api.error.notFound");
                status = HttpStatus.NOT_FOUND;
                break;
            case 401:
                textsHelper.getTranslation("api.error.accessDenied");
                status = HttpStatus.UNAUTHORIZED;
                break;
            case 400:
                textsHelper.getTranslation("api.error.badRequest");
                status = HttpStatus.BAD_REQUEST;
                break;
            case 409:
                message = textsHelper.getTranslation("api.error.alreadyExist");
                status = HttpStatus.NOT_ACCEPTABLE;
                break;
            case 500:
                textsHelper.getTranslation("api.error.internalServer");
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
            case 503:
                textsHelper.getTranslation("api.error.serviceUnavailable");
                status = HttpStatus.SERVICE_UNAVAILABLE;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                textsHelper.getTranslation("api.error.unknownError");
        }
        return  ResponseEntity.status(status)
                .body(new SimpleResponse(false, message, String.valueOf(ex.getStatusCode().value())));

    }
}