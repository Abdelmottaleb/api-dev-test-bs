package com.bluespurs.apidev.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.bluespurs.apidev.domain.Product;
import com.bluespurs.apidev.services.products.exceptions.ProductNotFoundException;
import com.bluespurs.apidev.services.products.exceptions.TechnicalException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ABSOULI this utility for external API calls
 */
@Slf4j
public class RequestService {

	private String productName;

	@Resource(name = "RestTemplate")
	RestTemplate devApiRestTemplate;

	/**
	 * constructor of class RequestService
	 * 
	 * @param productName : the name of product to search
	 */
	public RequestService(String productName) {
		this.productName = productName;
	}

	public List<Product> getListProductsByName(String url, String apiKey) throws TechnicalException, ProductNotFoundException {

		log.debug("search products list from for Best Buy for : {} ", productName);

		Product[] products = null;
		try {
			products = devApiRestTemplate.getForObject(url, Product[].class, productName, apiKey);

			if (null == products || products.length == 0) {
				throw new ProductNotFoundException(productName);
			}
		} catch (RestClientException e) {

			throw new TechnicalException(e);

		}
		
		return Arrays.asList(products);

	}

}
