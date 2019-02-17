package com.bluespurs.apidev.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bluespurs.apidev.domain.Product;
import com.bluespurs.apidev.services.products.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/product")
@RestController
public class ProductController extends AbstractController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/search")
	public ResponseEntity<Product> getLowestCurrentAvailablePrice(@RequestParam("name") final String productName) {

		/** here we put our web logic **/
		log.debug("search lower price for products {}", productName);
		
		Product bestPriceProduct = this.productService.getLowestCurrentAvailablePrice(productName);

		return ResponseEntity.ok(bestPriceProduct);

	}

}
