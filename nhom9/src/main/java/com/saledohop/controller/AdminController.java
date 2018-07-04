package com.saledohop.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.saledohop.model.account;
import com.saledohop.service.accountService;
import com.saledohop.service.catalogService;
import com.saledohop.service.productService;
import com.saledohop.service.transactionService;

@Controller
public class AdminController {

	@Autowired
	private accountService accService;
	
	@Autowired
	private catalogService cataService;

	@Autowired
	private productService proService;
	
	@Autowired
	private transactionService tranService;

	@RequestMapping(value = { "/admin",
			"/admin/home",
			"/admin/Catalog/AddCatalog",
			"/admin/Catalog/ListCatalog",
			"/admin/Product/AddProduct",
			"/admin/Product/ListProduct",
			"/admin/Account/ListAccount",
			"/admin/Transaction/ListTransaction",
			"/admin/Transaction/AuthenticationTransaction"
	})
	public ModelAndView admin(HttpSession session, HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView modelAndView = new ModelAndView();
		if (session.getAttribute("accLoginSuccess") != null) {
			account acc = (account) session.getAttribute("accLoginSuccess");
			if (!acc.getRole().equals("USER")) {
				switch (request.getRequestURI()) {
					case "/admin/Catalog/ListCatalog":
						modelAndView.addObject("cata", cataService.getListCatalog());
						modelAndView.setViewName(request.getRequestURI());
						break;
					case "/admin/Product/AddProduct":
						modelAndView.addObject("catalogName", cataService.findCatalog_Info(locale.getLanguage()));
						modelAndView.setViewName(request.getRequestURI());
						break;
					case "/admin/Account/ListAccount":
						modelAndView.addObject("ListAccount", accService.findAll());
						modelAndView.setViewName(request.getRequestURI());
						break;
					case "/admin/Product/ListProduct":
						modelAndView.addObject("pro", proService.getListProduct());
						modelAndView.setViewName(request.getRequestURI());
						break;
					case "/admin/Transaction/ListTransaction":
						modelAndView.addObject("ListTransaction", tranService.findAll());
						modelAndView.setViewName(request.getRequestURI());
						break;
					case "/admin/Transaction/AuthenticationTransaction":
						modelAndView.addObject("ListTransaction", tranService.findAllbyDateFinish());
						modelAndView.setViewName(request.getRequestURI());
						break;
					default:
						modelAndView.setViewName(request.getRequestURI());
						break;
				}
			} else {
				modelAndView.setViewName("redirect:/");
			}
		} else {
			modelAndView.setViewName("redirect:/");
		}
		return modelAndView;
	}

}
