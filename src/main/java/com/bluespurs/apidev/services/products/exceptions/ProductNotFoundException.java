package com.bluespurs.apidev.services.products.exceptions;

import com.bluespurs.apidev.domain.exceptions.NotFoundException;

/**
 * @author ABSOULI
 * exception handling
 *
 */
public class ProductNotFoundException extends NotFoundException {

	public ProductNotFoundException(String ProductName) {
		super("Could not find products info for " + ProductName);
	}

}
