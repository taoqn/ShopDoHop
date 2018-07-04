package com.saledohop.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.saledohop.model.account;
import com.saledohop.service.accountService;
import com.saledohop.service.catalogService;
import com.saledohop.service.productService;

@Controller
public class AccountController {

	@Autowired
	private accountService accService;

	@Autowired
	private catalogService cataService;
	
	@Autowired
	private productService proService;

	@RequestMapping(value = { "/", "/index" })
	public ModelAndView index() {
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView modelAndView = new ModelAndView();
		Pageable limit = new PageRequest(0,20);
		modelAndView.addObject("Product_Info", proService.find_ListProduct_Info_sort_page(locale.getLanguage(), limit));
		modelAndView.addObject("Catalog_Info_Table", cataService.findCatalog_Info(locale.getLanguage()));
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@RequestMapping(value = { "/login" })
	public ModelAndView login(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if (session.getAttribute("accLoginSuccess") == null) {
			modelAndView.setViewName("login");
		} else {
			modelAndView.setViewName("redirect:/");
		}
		return modelAndView;
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public ModelAndView loginAndLogon(HttpServletRequest request, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		String email = request.getParameter("user");
		String pass = request.getParameter("pass");
		account Exists = accService.Email(email);
		if (Exists != null) {
			if (!accService.checkAccount(pass, Exists)) {
				modelAndView.setViewName("redirect:/login?error=true");
			} else {
				session.setAttribute("accLoginSuccess", Exists);
				if (Exists.getRole().equals("USER"))
					modelAndView.setViewName("redirect:/");
				else
					modelAndView.setViewName("redirect:/admin/home");
			}
		} else {
			modelAndView.setViewName("redirect:/login?error=true");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/registration")
	public ModelAndView registration(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if (session.getAttribute("accLoginSuccess") == null) {
			account acc = new account();
			modelAndView.addObject("acc", acc);
			modelAndView.setViewName("registration");
		} else {
			modelAndView.setViewName("redirect:/");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid account acc, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		account Exists = accService.Email(acc.getEmail());
		if (Exists != null) {
			bindingResult.rejectValue("email", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			accService.save(acc, "USER");
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("acc", new account());
			modelAndView.setViewName("registration");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/User/ChangePassword", method = RequestMethod.POST)
	public String saveProductToCart(HttpSession session, @RequestParam(value = "oldPassword") String oldPassword,
			@RequestParam(value = "newPassword") String newPassword,
			@RequestParam(value = "confirmPassword") String confirmPassword) {

		if (oldPassword.equals("") || newPassword.equals("") || confirmPassword.equals("")) {
			return "redirect:/User/Profile#3";
		}

		if (session.getAttribute("accLoginSuccess") == null) {
			return "redirect:/login";
		}

		account a = (account) session.getAttribute("accLoginSuccess");
		account acc = accService.getOneByID(a.getId());

		if (!accService.checkAccount(oldPassword, acc)) {
			return "redirect:/User/Profile?old=true#3";
		}

		if (!newPassword.equals(confirmPassword)) {
			return "redirect:/User/Profile?confirm=true#3";
		}

		acc.setPassword(newPassword);
		accService.save(acc, acc.getRole());

		return "redirect:/User/Profile#3";
	}
	
	@RequestMapping(value = "/admin/Account/DeleteAccount", method = RequestMethod.GET)
	public String deleteTransaction(HttpSession session, @RequestParam(value = "q") int idAccount) {
		if (session.getAttribute("accLoginSuccess") == null) {
			return "redirect:/login";
		}
		account a = (account) session.getAttribute("accLoginSuccess");
		account acc = accService.getOneByID(a.getId());
		if (acc.getRole().equals("USER")) {
			return "redirect:/login";
		}
		if (acc.getId() == idAccount) {
			return "redirect:/admin/Account/ListAccount";
		}
		accService.deleteOne(idAccount);
		return "redirect:/admin/Account/ListAccount";
	}
	
}
