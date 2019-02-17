package com.bluespurs.apidev.services.products;

import com.bluespurs.apidev.domain.Product;

/**
 * @author ABSOULI this service serves to handel products ...
 *
 */
public interface ProductService {

	/**
	 * this service compile the best price of an product from products returned by
	 * third-party applications BestBuy & Walmart
	 **/
	Product getLowestCurrentAvailablePrice(String productName);

}
