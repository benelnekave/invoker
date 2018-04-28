package com.ben.test.invoker.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller recives a customer and invoke an http request to the springdata_h2_poc microservice whice saves the 
 * data to an inmemmory db.
 * This controller sends a one line request where usually in java you need much more than that
 * @author belnekave
 *
 */
@RestController
public class InvokerController {

	@RequestMapping(method = RequestMethod.POST, value = "/insert_customer", produces = MediaType.APPLICATION_JSON)
	public HttpResponse insert_customer(@RequestBody String customer) {

		HttpResponse response = null;
		try {
			response = Request.Post("http://localhost:8070/register").bodyString(customer, ContentType.APPLICATION_JSON)
					.execute().returnResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;

	}

	@ExceptionHandler
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {

		response.sendError(HttpStatus.BAD_REQUEST.value());

	}

}
