package com.bluespurs.apidev.presentation.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.MediaType;

@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AbstractController {

	// here I can add other common behavior

}
