package com.saledohop.controller;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.saledohop.model.account;
import com.saledohop.model.cart;
import com.saledohop.model.product;
import com.saledohop.model.product_info;
import com.saledohop.model.transaction;
import com.saledohop.model.order_account;
import com.saledohop.model.order_account_lang;
import com.saledohop.service.accountService;
import com.saledohop.service.cartService;
import com.saledohop.service.productService;
import com.saledohop.service.transactionService;

@Controller
public class UserController {

	@Autowired
	private accountService accService;

	@Autowired
	private transactionService tranService;

	@Autowired
	private productService proService;

	@Autowired
	private cartService caService;

	@RequestMapping(value = { "/User/Cart", "/User/Cart/Order", "/User/Profile" })
	public ModelAndView User(HttpSession session, HttpServletRequest request) {
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView modelAndView = new ModelAndView();
		if (session.getAttribute("accLoginSuccess") != null) {
			account a = (account) session.getAttribute("accLoginSuccess");
			account acc = accService.getOneByID(a.getId());
			switch (request.getRequestURI()) {
			case "/User/Cart":
				modelAndView.addObject("ListCart", accService.getListCardByLanguage(acc, locale.getLanguage()));
				modelAndView.addObject("TotalCart", accService.getTotalPriceListCart(acc));
				modelAndView.setViewName("Cart");
				break;
			case "/User/Cart/Order":
				if (accService.getListCardByLanguage(acc, locale.getLanguage()).isEmpty()) {
					modelAndView.setViewName("redirect:/User/Cart");
					return modelAndView;
				}
				modelAndView.addObject("ListCart", accService.getListCardByLanguage(acc, locale.getLanguage()));
				modelAndView.addObject("TotalCart", accService.getTotalPriceListCart(acc));
				modelAndView.setViewName("Order");
				break;
			case "/User/Profile":
				modelAndView.addObject("ListTransaction", tranService.findAllbyIdAccount(acc));
				modelAndView.setViewName("UserProfile");
				break;
			default:
				modelAndView.setViewName(request.getRequestURI());
				break;
			}
		} else {
			modelAndView.setViewName("redirect:/");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/User/Cart/Add", method = RequestMethod.POST)
	public String addProductToCart(HttpSession session, @RequestParam(value = "add", required = false) String add,
			@RequestParam(value = "like", required = false) String like,
			@RequestParam(value = "idProduct") int idProduct,
			@RequestParam(value = "numberProduct") int numberProduct) {

		if (session.getAttribute("accLoginSuccess") == null) {
			return "redirect:/login";
		}

		account a = (account) session.getAttribute("accLoginSuccess");
		account acc = accService.getOneByID(a.getId());

		if (!add.isEmpty()) {
			cart ca = new cart();
			product pro = proService.findProductByID(idProduct);
			ca.setId_account(acc);
			ca.setId_product(pro);
			ca.setNumber_cart(numberProduct);
			Set<cart> tb = acc.getCart_table();

			if (tb.isEmpty()) {
				tb.add(ca);
			} else {
				cart c = caService.containsProduct(tb, idProduct);
				if (c == null) {
					tb.add(ca);
				} else {
					c.setNumber_cart(numberProduct);
					tb.add(c);
				}
			}
			acc.setCart_table(tb);
			accService.save(acc);
			return "redirect:/User/Cart";
		}
		if (!like.isEmpty()) {
			return "redirect:/User/Cart";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/User/Cart/Delete", method = RequestMethod.GET)
	public String deleteProductToCart(HttpSession session, @RequestParam(value = "q") int idCart) {

		if (session.getAttribute("accLoginSuccess") == null) {
			return "redirect:/login";
		}

		if (caService.getOne(idCart) != null) {
			caService.deleteOne(idCart);
		}

		return "redirect:/User/Cart";
	}

	@RequestMapping(value = "/User/Cart/Save", method = RequestMethod.POST)
	public String saveProductToCart(HttpSession session, @RequestParam(value = "txtName") String txtName,
			@RequestParam(value = "txtEmail") String txtEmail, @RequestParam(value = "txtPhone") String txtPhone,
			@RequestParam(value = "txtAddress") String txtAddress,
			@RequestParam(value = "txtComment") String txtComment) {
		Locale locale = LocaleContextHolder.getLocale();
		if (session.getAttribute("accLoginSuccess") == null) {
			return "redirect:/login";
		}
		account a = (account) session.getAttribute("accLoginSuccess");
		account acc = accService.getOneByID(a.getId());
		if (accService.getListCardByLanguage(acc, locale.getLanguage()).isEmpty()) {
			return "redirect:/User/Cart";
		}
		transaction tran = new transaction();
		tran.setId_account(acc);
		tran.setAccount_name(txtName);
		tran.setAccount_email(txtEmail);
		tran.setAccount_phone(txtPhone);
		tran.setAccount_address(txtAddress);
		tran.setAmount_transaction((double) accService.getTotalPriceListCart(acc));
		tran.setComment_transaction(txtComment);
		tran.setDate_start_transaction(Calendar.getInstance().getTime());
		Set<order_account> tb_order = new HashSet<order_account>();
		Set<cart> tb_cart = acc.getCart_table();
		for (cart c : tb_cart) {
			order_account ord = new order_account();
			ord.setId_transaction(tran);
			ord.setProduct_price(c.getId_product().getPrice_product());
			ord.setProduct_number(c.getNumber_cart());
			Set<order_account_lang> tb_order_lang = new HashSet<order_account_lang>();
			for (product_info pro_info : c.getId_product().getProduct_info_table()) {
				order_account_lang ord_lang = new order_account_lang();
				ord_lang.setId_order(ord);
				ord_lang.setProduct_name(pro_info.getName_product_info());
				ord_lang.setLang(pro_info.getLang_product_info());
				tb_order_lang.add(ord_lang);
			}
			ord.setOrder_account_lang_table(tb_order_lang);
			tb_order.add(ord);
		}
		tran.setOrder_account_table(tb_order);
		Set<transaction> tb_tran = acc.getTransaction_table();
		tb_tran.add(tran);
		acc.setTransaction_table(tb_tran);
		acc.setCart_table(null);
		accService.save(acc);
		caService.deleteEntities(tb_cart);
		return "redirect:/User/Profile#2";
	}

	@RequestMapping(value = "/User/Transaction/Bill", method = RequestMethod.GET)
	public ModelAndView viewBillTransaction(HttpSession session, @RequestParam(value = "q") int idTransaction) {
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView modelAndView = new ModelAndView();
		if (session.getAttribute("accLoginSuccess") == null) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		if (tranService.getOne(idTransaction) == null) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		transaction tran = tranService.getOne(idTransaction);
		modelAndView.addObject("Transaction", tran);
		modelAndView.addObject("ListProduct", tranService.find_ListOrder_byID(tran, locale.getLanguage()));
		modelAndView.setViewName("Bill");
		return modelAndView;
	}

	@RequestMapping(value = "/admin/Transaction/DeleteTransaction", method = RequestMethod.GET)
	public String deleteTransaction(HttpSession session, @RequestParam(value = "q") int idTransaction) {
		if (session.getAttribute("accLoginSuccess") == null) {
			return "redirect:/login";
		}
		account a = (account) session.getAttribute("accLoginSuccess");
		account acc = accService.getOneByID(a.getId());
		if (acc.getRole().equals("USER")) {
			return "redirect:/login";
		}
		tranService.deleteOne(idTransaction);
		return "redirect:/admin/Transaction/AuthenticationTransaction";
	}

	@RequestMapping(value = "/admin/Transaction/AuthTransaction", method = RequestMethod.GET)
	public String authenticationTransaction(HttpSession session, @RequestParam(value = "q") int idTransaction) {
		if (session.getAttribute("accLoginSuccess") == null) {
			return "redirect:/login";
		}
		account a = (account) session.getAttribute("accLoginSuccess");
		account acc = accService.getOneByID(a.getId());
		if (acc.getRole().equals("USER")) {
			return "redirect:/login";
		}
		transaction tran = tranService.getOne(idTransaction);
		tran.setDate_finish_transaction(Calendar.getInstance().getTime());
		tranService.save(tran);
		return "redirect:/admin/Transaction/AuthenticationTransaction";
	}

	@RequestMapping(value = "/admin/Account/ListTransaction", method = RequestMethod.GET)
	public ModelAndView listTransactionbyAccount(HttpSession session, @RequestParam(value = "q") int idAccount) {
		ModelAndView modelAndView = new ModelAndView();
		account a = (account) session.getAttribute("accLoginSuccess");
		account acc = accService.getOneByID(a.getId());
		if (session.getAttribute("accLoginSuccess") == null) {
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		if (acc.getRole().equals("USER")) {
			modelAndView.setViewName("redirect:/login");
			return modelAndView;
		}
		modelAndView.addObject("ListTransaction", tranService.findAllbyIdAccount(accService.getOneByID(idAccount)));
		modelAndView.setViewName("/admin/Account/ListTransaction");
		return modelAndView;
	}

}
