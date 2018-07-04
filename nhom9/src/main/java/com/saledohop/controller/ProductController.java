package com.saledohop.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.saledohop.Lang_DB;
import com.saledohop.model.catalog;
import com.saledohop.model.product;
import com.saledohop.model.product_info;
import com.saledohop.service.catalogService;
import com.saledohop.service.productService;

@Controller
public class ProductController {

	@Autowired
	private catalogService cataService;
	
	@Autowired
	private productService proService;
	
	@RequestMapping(value = "/Product/{id}/Detail", method = RequestMethod.GET)
	public ModelAndView ProductDetail(@PathVariable final int id) {
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("ProductDetail", proService.findListProduct_InfoByID(id, locale.getLanguage()));
		modelAndView.setViewName("Product");
		proService.update_view_product(id);
		return modelAndView;
	}

	@RequestMapping(value = "/Product/Image/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> ImageProduct(@PathVariable final int id) {
		product pro = proService.findProductByID(id);
	    byte[] bytes = pro.getImg_product();
	    final HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG);
	    return new ResponseEntity<byte[]> (bytes, headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/User/Search", method = RequestMethod.GET)
	public ModelAndView SearchProduct(@RequestParam(value = "value", required = false) String value) {
		ModelAndView modelAndView = new ModelAndView();
		if(value == null){
			modelAndView.setViewName("redirect:/");
			return modelAndView;
		}
		Locale locale = LocaleContextHolder.getLocale();
		modelAndView.addObject("ListProductSearch", proService.findListProduct_InfoByValue(value, locale.getLanguage()));
		modelAndView.setViewName("Search");
		return modelAndView;
	}

	@RequestMapping(value = "/admin/Product/AddProduct", method = RequestMethod.POST)
	public String addProductNew(
			@RequestParam(value = "nameENG", required = false) String nameENG,
			@RequestParam(value = "nameVIE", required = false) String nameVIE,
			@RequestParam(value = "packedENG", required = false) String packedENG,
			@RequestParam(value = "packedVIE", required = false) String packedVIE,
			@RequestParam(value = "ingredientsENG", required = false) String ingredientsENG,
			@RequestParam(value = "ingredientsVIE", required = false) String ingredientsVIE,
			@RequestParam(value = "forUseENG", required = false) String forUseENG,
			@RequestParam(value = "forUseVIE", required = false) String forUseVIE,
			@RequestParam(value = "forPreserveENG", required = false) String forPreserveENG,
			@RequestParam(value = "forPreserveVIE", required = false) String forPreserveVIE,
			@RequestParam(value = "catalogList", required = false) int catalogList,
			@RequestParam(value = "priceProduct", required = false) double priceProduct,
			@RequestParam(value = "image", required = false) MultipartFile file,
			Model model) throws IOException {
		
		if (file.isEmpty()) {
			return "redirect:/admin/Product/AddProduct";
		}
		
		catalog cata = cataService.findCatalogByID(catalogList);
		byte[] image = file.getBytes();
		Date date = Calendar.getInstance().getTime();
		//DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		//String today = formatter.format(date);
		
		product pro = new product();
		pro.setId_catalog(cata);
		pro.setPrice_product(priceProduct);
		pro.setImg_product(image);
		pro.setCreated_product(date);
		pro.setView_product(0);
		pro.setLike_product(0);
		
		product_info pro_info_ENG = new product_info();
		pro_info_ENG.setId_product(pro);
		pro_info_ENG.setName_product_info(nameENG);
		pro_info_ENG.setPacked_product_info(packedENG);
		pro_info_ENG.setIngredients_product_info(ingredientsENG);
		pro_info_ENG.setFor_use_product_info(forUseENG);
		pro_info_ENG.setFor_preserve_product_info(forPreserveENG);
		pro_info_ENG.setLang_product_info(Lang_DB.en.name());
		
		product_info pro_info_VIE = new product_info();
		pro_info_VIE.setId_product(pro);
		pro_info_VIE.setName_product_info(nameVIE);
		pro_info_VIE.setPacked_product_info(packedVIE);
		pro_info_VIE.setIngredients_product_info(ingredientsVIE);
		pro_info_VIE.setFor_use_product_info(forUseVIE);
		pro_info_VIE.setFor_preserve_product_info(forPreserveVIE);
		pro_info_VIE.setLang_product_info(Lang_DB.vi.name());
		
		Set<product_info> table = new HashSet<product_info>();
		table.add(pro_info_ENG);
		table.add(pro_info_VIE);
		pro.setProduct_info_table(table);
		
		proService.save(pro);

		return "redirect:/admin/Product/AddProduct";
	}

}
