package sims.chareyron.petanque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import sims.chareyron.petanque.model.SousTournoi;

@Controller
public class TournoiWsController {
	@Autowired
	private SimpMessagingTemplate template;

	public void updateTournoi(SousTournoi aSousTournoi) {
		this.template.convertAndSend("/topic/updateTournoi", aSousTournoi);
	}
}
