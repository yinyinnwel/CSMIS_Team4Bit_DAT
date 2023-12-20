package com.dat.bit.csmis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dat.bit.csmis.entity.Restaurant;
import com.dat.bit.csmis.entity.Restaurant_Comment;
import com.dat.bit.csmis.entity.Staff;
import com.dat.bit.csmis.service.RestaurantCommentService;
import com.dat.bit.csmis.service.RestaurantService;
import com.dat.bit.csmis.service.StaffService;

@Controller
@RequestMapping("/DAT")
public class RestaurantController {
	
	@Autowired
	private StaffService staffService;
	
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private RestaurantCommentService restaurantCommentService;
	
	private boolean updatedStatus = false;
	private String noti_message="",updateMessage_class="notiMessageBox";
	
	public String RestaurantPage(String staffId,List<Restaurant> restaurants,Model model) {
		
		Staff staffData = staffService.getStaffByStaff_Id(staffId);
		String loginUsername = staffData.getName();
		
		
		model.addAttribute("loginUserName",loginUsername);
		model.addAttribute("restaurants",restaurants);
		model.addAttribute("restaurant",new Restaurant());//<= add Restaurant object to update or add
		model.addAttribute("updatedStatus",updatedStatus);
		model.addAttribute("updateMessage_class",updateMessage_class);
		model.addAttribute("updateMessage",noti_message);
		
		//reset value
		updatedStatus = false;
		updateMessage_class = "notiMessageBox";
		
		return "restaurant/restaurant";
	}
	
	
	@GetMapping("/restaurant")
	public String restaurant(Authentication authentication,Model model) {
		String staffId = authentication.getName();
		
		//---------- Get Restaurant List ----------------
		List<Restaurant> restaurants = restaurantService.getAllRestaurantList();
		
		model.addAttribute("infoPopupContainerClass","info_popup_container");
		model.addAttribute("retaurantReviewContainerClass","retaurant_review_container");
		return RestaurantPage(staffId,restaurants, model);
	}
	
	@PostMapping("/restaurant_update")
	public String restaurantAdd_Update(@ModelAttribute("restaurant")Restaurant restaurant,Authentication authentication,Model model) {
		String staffId = authentication.getName();
		
		try {
			if(restaurant.getId() == 0) {//<= if id equals to 0 ,call add New Restaurant function
				boolean status = restaurantService.saveResaturant(restaurant, staffId);
				
				if(status) {
				//you can't add this restaurant as active  
				//because there are some voucher that doesn't paid from other active restaurant
					updatedStatus = true;
				}else {
					updatedStatus = false;//add successfully
					//============ Show success noti dialog ===============
					
					updateMessage_class = "notiMessageBox success open";
					noti_message = "New restaurant is added successfully.";
				}

			}else {//<= call update Restaurant function
				boolean status = restaurantService.updateRestaurant(restaurant, staffId);
				
				if(status) {//you can't change because there are some voucher that doesn't paid
					updatedStatus = true;
				}else {//updated smoothly
					updatedStatus = false;
					//============ Show success noti dialog ===============
					
					updateMessage_class = "notiMessageBox success open";
					noti_message = "You have successfully updated.";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "redirect:/DAT/restaurant";
	}
	
	@GetMapping("/restaurant_search")
	public String restaurantSearch(@RequestParam("searchValue")String searchValue,Authentication authentication,Model model) {
		String staffId = authentication.getName();
		//---------- Get Searched Restaurant List ----------------
		List<Restaurant> search_restaurants = restaurantService.getAllRestaurantListBySearchName(searchValue);
		
		model.addAttribute("infoPopupContainerClass","info_popup_container");
		model.addAttribute("retaurantReviewContainerClass","retaurant_review_container");
		return RestaurantPage(staffId,search_restaurants, model);
		
	}
	
	@PostMapping("/restaurant_view_comment")
	public String veiwComment(@RequestParam("restaurantId") int restaurantId, Authentication authentication,
			Model model) {
		String staffId = authentication.getName();
		
		// ---------- Get Restaurant List ----------------
		List<Restaurant> restaurants = restaurantService.getAllRestaurantList();
		//----------- Get comments of selected Restaurant --------------
		Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
		List<Restaurant_Comment> comments = restaurantCommentService.getAllRestaurantComment_ByRestaurantId(restaurantId);

		model.addAttribute("infoPopupContainerClass","info_popup_container open");
		model.addAttribute("retaurantReviewContainerClass", "retaurant_review_container open");
		model.addAttribute("restaurantName",restaurant.getName());
		model.addAttribute("comments",comments);
		return RestaurantPage(staffId, restaurants, model);
	}

}
