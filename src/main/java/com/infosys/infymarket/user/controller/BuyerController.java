package com.infosys.infymarket.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.infosys.infymarket.user.dto.BuyerDTO;
import com.infosys.infymarket.user.dto.LoginDTO;
import com.infosys.infymarket.user.dto.SellerDTO;
import com.infosys.infymarket.user.exception.InfyMarketException;
import com.infosys.infymarket.user.service.BuyerService;
import com.infosys.infymarket.user.service.SellerService;

@RestController
@CrossOrigin
@RequestMapping
public class BuyerController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	Environment environment;

	@Autowired
	BuyerService buyerservice;
	@Autowired
	SellerService sellerservice;

	@PostMapping(value = "/api/buyer/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createBuyer(@Valid @RequestBody BuyerDTO buyerDTO) throws InfyMarketException {
		try {
			String successMessage = environment.getProperty("API.INSERT_SUCCESS");
			logger.info("Registration request for buyer with data {}", buyerDTO);
			buyerservice.saveBuyer(buyerDTO);
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@GetMapping(value = "/api/buyers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BuyerDTO>> getAllBuyer() throws InfyMarketException {
		try {
			List<BuyerDTO> buyerDTOs = buyerservice.getAllBuyer();
			return new ResponseEntity<>(buyerDTOs, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@PostMapping(value = "/buyer/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO) throws InfyMarketException {
		try {
			buyerservice.login(loginDTO);
			logger.info("Login request for buyer {} with password {}", loginDTO.getEmail(), loginDTO.getPassword());
			String successMessage = environment.getProperty("API.LOGIN_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@PostMapping(value = "/api/seller/register", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createSeller(@Valid @RequestBody SellerDTO sellerDTO) throws InfyMarketException {
		try {
			String successMessage = environment.getProperty("API.INSERT_SUCCESS");
			logger.info("Registration request for seller with data {}", sellerDTO);
			sellerservice.saveSeller(sellerDTO);
			return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@GetMapping(value = "/api/sellers", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SellerDTO>> getAllSeller() throws InfyMarketException {
		try {
			List<SellerDTO> sellerDTOs = sellerservice.getAllSeller();
			return new ResponseEntity<>(sellerDTOs, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@PostMapping(value = "/seller/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> Login(@Valid @RequestBody LoginDTO loginDTO) throws InfyMarketException{
		try {
			sellerservice.login(loginDTO);
			logger.info("Login request for seller {} with password {}", loginDTO.getEmail(), loginDTO.getPassword());
			String successMessage = environment.getProperty("API.LOGIN_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@DeleteMapping(value = "/buyer/{buyerid}")
	public ResponseEntity<String> deleteBuyer(@PathVariable String buyerid) throws InfyMarketException {
		try {
			buyerservice.deleteBuyer(buyerid);
			String successMessage = environment.getProperty("API.DELETE_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}

	@DeleteMapping(value = "/seller/{sellerid}")
	public ResponseEntity<String> deleteSeller(@PathVariable String sellerid) throws InfyMarketException {
		try {
			sellerservice.deleteSeller(sellerid);
			String successMessage = environment.getProperty("API.DELETE_SUCCESS");
			return new ResponseEntity<>(successMessage, HttpStatus.OK);
		} catch (Exception exception) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(exception.getMessage()),
					exception);
		}
	}
}
