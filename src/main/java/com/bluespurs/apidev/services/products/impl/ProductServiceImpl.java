package com.bluespurs.apidev.services.products.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bluespurs.apidev.domain.Product;
import com.bluespurs.apidev.services.products.ProductService;
import com.bluespurs.apidev.services.products.exceptions.ProductNotFoundException;
import com.bluespurs.apidev.services.products.exceptions.TechnicalException;
import com.bluespurs.apidev.utils.RequestService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author ABSOULI class for implementing products business logic
 */

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

	@Value("${bestbuy.rest.base.url}")
	public String baseUrlBestBuy;

	@Value("${bestbuy.api.key}")
	public String bestBuyApiKey;

	@Value("${walmart.rest.base.url}")
	public String baseUrlWalmart;

	@Value("${walmart.api.key}")
	public String walmartApiKey;

	private static String URI_PRODUCTS = "/products";

	final String productSearchBestBuyUrl = baseUrlBestBuy + URI_PRODUCTS + "?name={productName}&apiKey={apiKey}";

	final String productSearchWalmartUrl = baseUrlWalmart + URI_PRODUCTS + "?name={productName}&apiKey={apiKey}";

	@Override
	public Product getLowestCurrentAvailablePrice(String productName) {

		/** here we put our business logic **/

		// do first call to BestBuy service, the walmart service call

		RequestService requestService = new RequestService(productName);
		log.debug("getLowestCurrentAvailablePrice call  BestBuy : {} ", productName);

		List<Product> allproducts = new ArrayList<Product>();

		try {

			allproducts.addAll(requestService.getListProductsByName(productSearchBestBuyUrl, bestBuyApiKey));

		} catch (Exception e) {
			log.error(e.getMessage());

			if (e instanceof TechnicalException) {
				log.error(" first call failed to BestBuy api, proceed to call the walmart api ...{}", e.getMessage());

				try {

					// second call (walmart) if the first failed
					allproducts.addAll(requestService.getListProductsByName(productSearchWalmartUrl, walmartApiKey));

				} catch (Exception e1) {
					log.error(e.getMessage());
					if (e instanceof TechnicalException) {
						log.error(" second call to walmart api failed too ...{}", e.getMessage());
					}
				}
			}
		}

		try {
			// second call if the first succeded without exception
			allproducts.addAll(requestService.getListProductsByName(productSearchWalmartUrl, walmartApiKey));
		} catch (Exception e) {
			log.error(e.getMessage());
			if (e instanceof TechnicalException) {
				log.error(" second call to walmart api failed too ...{}", e.getMessage());
			}
		}

		// once we have finished recovering the products, we procced to determine the
		// one how have the best prise !!

		return this.getBestPriceProduct(allproducts);
	}

	/**
	 * determine the best price product
	 * 
	 * @param allproducts list of products
	 * @return
	 */
	Product getBestPriceProduct(List<Product> allproducts) {

		return allproducts.stream().min(Comparator.comparing(Product::getPrice))
				.orElseThrow(NoSuchElementException::new);
	}

}
