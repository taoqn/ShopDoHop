package com.saledohop.controller;

import java.io.IOException;
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
import com.saledohop.model.catalog_info;
import com.saledohop.service.catalogService;
import com.saledohop.service.productService;

@Controller
public class CatalogController {

	@Autowired
	private catalogService cataService;
	
	@Autowired
	private productService proService;

	@RequestMapping(value = "/Catalog/{id}/Products", method = RequestMethod.GET)
	public ModelAndView CatalogListProduct(@PathVariable final int id) {
		Locale locale = LocaleContextHolder.getLocale();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("NameCatalog", cataService.findCatalog_InfoByID(id, locale.getLanguage()));
		modelAndView.addObject("ListProduct", proService.findListProduct_InfoByID_Catalog(cataService.findCatalogByID(id), locale.getLanguage()));
		modelAndView.setViewName("Catalog");
		return modelAndView;
	}

	@RequestMapping(value = "/Catalog/Image/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> ImageCatalog(@PathVariable final int id) {
		catalog cata = cataService.findCatalogByID(id);
		byte[] bytes = cata.getImg_catalog();
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG);
		return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/admin/Catalog/AddCatalog", method = RequestMethod.POST)
	public String addCatalog(@RequestParam(value = "nameENG", required = false) String nameENG,
			@RequestParam(value = "nameVIE", required = false) String nameVIE,
			@RequestParam(value = "descriptionENG", required = false) String descriptionENG,
			@RequestParam(value = "descriptionVIE", required = false) String descriptionVIE,
			@RequestParam(value = "image", required = false) MultipartFile file, Model model) throws IOException {

		if (file.isEmpty()) {
			return "redirect:/admin/Catalog/AddCatalog";
		}

		byte[] image = file.getBytes();
		catalog cata = new catalog();
		cata.setImg_catalog(image);

		catalog_info cata_info_ENG = new catalog_info();
		cata_info_ENG.setId_catalog(cata);
		cata_info_ENG.setName_catalog_info(nameENG);
		cata_info_ENG.setDescription_catalog_info(descriptionENG);
		cata_info_ENG.setLang_catalog_info(Lang_DB.en.name());

		catalog_info cata_info_VIE = new catalog_info();
		cata_info_VIE.setId_catalog(cata);
		cata_info_VIE.setName_catalog_info(nameVIE);
		cata_info_VIE.setDescription_catalog_info(descriptionVIE);
		cata_info_VIE.setLang_catalog_info(Lang_DB.vi.name());

		Set<catalog_info> table = new HashSet<catalog_info>();
		table.add(cata_info_ENG);
		table.add(cata_info_VIE);
		cata.setCatalog_info_table(table);

		cataService.save(cata);

		return "redirect:/admin/Catalog/ListCatalog";
	}

}
