package it.fides.timesheet.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.fides.timesheet.models.entities.BlackedTokenEntity;
import it.fides.timesheet.models.repositories.BlackedTokenRepository;

@Service
public class BlackedTokenService {
	
	@Autowired
	private BlackedTokenRepository blackListRepo;
	
	public List<BlackedTokenEntity> getAllBlackedTokens() {
		return blackListRepo.findAll();
	}
	
	public BlackedTokenEntity getBlackedToken(Long id) {
		return blackListRepo.findById(id).get();
	}
	
	public BlackedTokenEntity insertBlackedToken(BlackedTokenEntity blackToken) throws Exception {
		return blackListRepo.save(blackToken);
	}
	
	public void deleteBlackedToken(Long id) {
		blackListRepo.deleteById(id);
	}
}

