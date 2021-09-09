package com.infosys.infymarket.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosys.infymarket.user.dto.BuyerDTO;
import com.infosys.infymarket.user.dto.LoginDTO;
import com.infosys.infymarket.user.entity.Buyer;
import com.infosys.infymarket.user.exception.InfyMarketException;
import com.infosys.infymarket.user.repository.BuyerRepository;

@Service
@Transactional
public class BuyerService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BuyerRepository buyerrepo;
    
    

    public void saveBuyer(BuyerDTO buyerDTO) throws InfyMarketException{
        logger.info("Registration request for buyer with data {}",buyerDTO);
        Buyer buyer = buyerDTO.createBuyer();
        buyerrepo.save(buyer);
    }
	public List<BuyerDTO> getAllBuyer() throws InfyMarketException{

		Iterable<Buyer> buyers = buyerrepo.findAll();
		List<BuyerDTO> buyerDTOs = new ArrayList<>();

		buyers.forEach (buyer -> {
			BuyerDTO buyerDTO = BuyerDTO.valueOf(buyer);
			buyerDTOs.add(buyerDTO);
		});
		if(buyerDTOs.isEmpty())
			throw new InfyMarketException("Service.BUYERS_NOT_FOUND");
		logger.info("Buyer Details : {}", buyerDTOs);
		return buyerDTOs;
	}
	public boolean login(LoginDTO loginDTO) throws InfyMarketException{
		logger.info("Login request for buyer {} with password {}", loginDTO.getEmail(),loginDTO.getPassword());
		Buyer buy = buyerrepo.findByEmail(loginDTO.getEmail());
		if (buy != null && buy.getPassword().equals(loginDTO.getPassword())) {
			return true;
		} else {
			throw new InfyMarketException("Service.DETAILS_NOT_FOUND");
		}
	}
	public void deleteBuyer(String buyerid) throws InfyMarketException {
		Optional<Buyer> buyer = buyerrepo.findById(buyerid);
		buyer.orElseThrow(() -> new InfyMarketException("Service.BUYERS_NOT_FOUND"));
		buyerrepo.deleteById(buyerid);
	}

}
