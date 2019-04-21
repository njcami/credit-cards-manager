package com.manager.cards.controller;

import com.manager.cards.model.Card;
import com.manager.cards.security.IAuthenticationFacade;
import com.manager.cards.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class specifically for Card related calls.
 */
@Controller
public class CardController {

	@Autowired
	private CardService cardService;

	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@ModelAttribute("card")
	public Card formBackingObject() {
		return new Card();
	}

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	@ResponseBody
	public String currentLoggedInUserName() {
		return authenticationFacade.getAuthentication().getName();
	}

	@GetMapping("/cards/cards")
	public String userForm(Model model) {
		addCards(model);
		return "addCard";
	}

	@PostMapping("/cards/addCard")
	public String saveOrUpdateCard(@ModelAttribute("card") @Valid Card card, BindingResult result, Model model) {
		if (result.hasErrors()) {
			addCards(model);
			return "addCard";
		} else if (card.getId() == null) {
			cardService.save(card, currentLoggedInUserName());
		} else {
			cardService.update(card, currentLoggedInUserName());
		}
		return "redirect:/cards/cards";
	}

	@GetMapping("/deleteCard")
	public String userForm(@RequestParam Long id, Model model) {
		cardService.delete(id, currentLoggedInUserName());
		addCards(model);
		return "addCard";
	}

	@GetMapping("/editCard")
	public String userEditForm(@RequestParam Long id, Model model) {
		Card card = cardService.find(id, currentLoggedInUserName());
		if (card != null) {
			model.addAttribute("card", card);
		}
		return "addCard";
	}

	private void addCards(Model model) {
		List<Card> cards = cardService.listCardsForLoggedInUser(currentLoggedInUserName());
		if (!CollectionUtils.isEmpty(cards)) {
			model.addAttribute("cards", cards);
		}
	}
}
