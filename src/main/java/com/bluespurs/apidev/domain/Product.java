package com.bluespurs.apidev.domain;

import java.io.Serializable;

import lombok.Data;

/**
 * @author ABSOULI
 * 
 *         this class gathers the information related to the product
 *
 */

@Data
public class Product implements Serializable {

	private String productName;
	private Float price;
	private String currency;
	private String location;
}
