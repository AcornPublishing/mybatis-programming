package org.mybatis.presentation;

import java.util.List;

import javax.annotation.Resource;

import org.mybatis.domain.Shop;
import org.mybatis.service.ShopService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/shop")
public class ShopController {
	@Resource
	private ShopService shopService;

	/* 가게 목록 조회 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(Shop shop) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/shop/list");

		// 서비스 객체 호출
		List<Shop> listShop = this.shopService.find(shop);
		modelAndView.addObject("listShop", listShop);

		return modelAndView;
	}

	/* 가게 등록 화면 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addForm() throws Exception {
		return "/shop/add";
	}

	/* 가게 등록 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(Shop shop) throws Exception {
		RedirectView redirectView = new RedirectView("/shop/list");
		redirectView.setExposeModelAttributes(false);

		// 서비스 객체 호출
		this.shopService.add(shop);

		return new ModelAndView(redirectView);
	}

	/* 가게 조회 */
	@RequestMapping(value = "/view/{shopNo}", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable String shopNo) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/shop/view");

		// 서비스 객체 호출
		Shop shop = this.shopService.view(shopNo);
		modelAndView.addObject("shop", shop);

		return modelAndView;
	}

	/* 가게 수정 조회 */
	@RequestMapping(value = "/edit/{shopNo}", method = RequestMethod.GET)
	public ModelAndView editForm(@PathVariable String shopNo) throws Exception {
		ModelAndView modelAndView = new ModelAndView("/shop/edit");

		// 서비스 객체 호출
		Shop shop = this.shopService.view(shopNo);
		modelAndView.addObject("shop", shop);

		return modelAndView;
	}

	/* 가게 수정 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ModelAndView edit(Shop shop) throws Exception {
		RedirectView redirectView = new RedirectView("/shop/list");
		redirectView.setExposeModelAttributes(false);

		// 서비스 객체 호출
		this.shopService.edit(shop);

		return new ModelAndView(redirectView);
	}

	/* 가게 삭제 */
	@RequestMapping(value = "/remove/{shopNo}", method = RequestMethod.GET)
	public ModelAndView remove(@PathVariable String shopNo) throws Exception {
		RedirectView redirectView = new RedirectView("/shop/list");
		redirectView.setExposeModelAttributes(false);

		// 서비스 객체 호출
		this.shopService.remove(shopNo);

		return new ModelAndView(redirectView);
	}
}
