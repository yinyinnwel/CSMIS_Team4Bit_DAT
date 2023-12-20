const addRestaurantBtn = document.querySelector('.addRestaurantBtn')
const restaurant_review_btn_list = document.querySelectorAll("#restaurant_review_btn");
const restaurant_info_btn_list = document.querySelectorAll('#restaurant_info_btn');
const staff_edit_btn_list = document.querySelectorAll('#restaurant_edit_btn');
const staff_info_edit_label = document.getElementById('staff_info_edit_label');

const closePopupBtn = document.querySelector('.closePopupBtn');
const info_popup_container = document.querySelector('.info_popup_container');
const retaurant_review_container = document.querySelector('.retaurant_review_container');
const restaurant_detail_container = document.querySelector('.restaurant_detail_container');
const restaurant_info_update_container = document.querySelector('.restaurant_info_update_container');

const clear_all_field_btn = document.querySelector('.clear_all_field_btn');
const cancelInfoEditBtn = document.querySelector('.cancelInfoEditBtn');
const saveInfoEidtBtn = document.querySelector('.saveInfoEidtBtn');
const infoInput_list = document.querySelectorAll('#infoInput');

/*=======================  Restaurant Info ================================ */
const idArray = Array.from(document.querySelectorAll("#rs_id"));
const nameArray = Array.from(document.querySelectorAll("#name"));
const addressArray = Array.from(document.querySelectorAll("#address"));
const phone_noArray = Array.from(document.querySelectorAll("#phone_no"));
const emailArray = Array.from(document.querySelectorAll("#email"));
const statusArray = Array.from(document.querySelectorAll("#status"));
const createdAtArray = Array.from(document.querySelectorAll("#createdAt"));
const createdByArray = Array.from(document.querySelectorAll("#createdBy"));
const updatedAtArray = Array.from(document.querySelectorAll("#updatedAt"));
const updatedByArray = Array.from(document.querySelectorAll("#updatedBy"));
const receiverArray = Array.from(document.querySelectorAll("#receiver"));

const infoTextArray = Array.from(document.querySelectorAll('.infoText'));

const editRestaurantName = document.getElementById("editRestaurantName");

/*========================== Restaurant Edit or add ==================================== */

const edit_status_option_Array = Array.from(document.querySelector('.status_dropdown').children);

const infoInputArray = Array.from(infoInput_list);

/*=========================== Radio Status ======================================= */

const radio_statusArray = Array.from(document.querySelectorAll("#radio_status"));

for(let radio of radio_statusArray){
	if(radio.value == 1){
		let tdElement = radio.parentNode;
		let radioCheckBox = tdElement.children[1];
		radioCheckBox.classList.add("active");
	}
}

/*======================================================================================= */


for(var restaurant_review_btn of restaurant_review_btn_list) {
	restaurant_review_btn.addEventListener('click', () => {
		info_popup_container.classList.add('open');

	});
}


for(var restaurant_info_btn of restaurant_info_btn_list) {
    restaurant_info_btn.addEventListener('click', (e) => {
        info_popup_container.classList.add('open');
        restaurant_detail_container.classList.add('open');
        
        let currentInfoId = e.currentTarget.parentElement.id;
        
        editRestaurantName.innerText = nameArray[currentInfoId].value;
        
        infoTextArray[0].innerText = nameArray[currentInfoId].value;
		infoTextArray[1].innerText = addressArray[currentInfoId].value;
		infoTextArray[2].innerText = emailArray[currentInfoId].value;
		infoTextArray[3].innerText = phone_noArray[currentInfoId].value;
		infoTextArray[4].innerText = receiverArray[currentInfoId].value;
		infoTextArray[5].innerText = statusArray[currentInfoId].value;
		infoTextArray[6].innerText = createdAtArray[currentInfoId].value;
		infoTextArray[7].innerText = createdByArray[currentInfoId].value;
		infoTextArray[8].innerText = updatedAtArray[currentInfoId].value;
		infoTextArray[9].innerText = updatedByArray[currentInfoId].value;
    });
}

for(var staff_edit_btn of staff_edit_btn_list) {
    staff_edit_btn.addEventListener('click', (e) => {
        staff_info_edit_label.innerText = 'Edit Restaurant';
        info_popup_container.classList.add('open');
        restaurant_info_update_container.classList.add('open');
        clear_all_field_btn.classList.add("remove");
        
        let currentInfoId = e.currentTarget.parentElement.id;
        
        infoInputArray[0].value = parseInt(idArray[currentInfoId].value);/*ID*/
        infoInputArray[1].value = nameArray[currentInfoId].value;/*Name*/
       /* infoInputArray[1].readOnly = true;*/
		infoInputArray[2].value = addressArray[currentInfoId].value;/*Address*/
		infoInputArray[3].value = emailArray[currentInfoId].value;/*Email*/
		infoInputArray[4].value = phone_noArray[currentInfoId].value;/*Phone no*/
		infoInputArray[5].value = receiverArray[currentInfoId].value;/*receiver*/
    
        if(statusArray[currentInfoId].value == 'Active'){
			edit_status_option_Array[0].selected = true;
			edit_status_option_Array[1].selected = false;
		}else{
			edit_status_option_Array[1].selected = true;
			edit_status_option_Array[0].selected = false;
		}
    });
}

addRestaurantBtn.addEventListener('click', (e) => {
    staff_info_edit_label.innerText = 'Add Restaurant';
    info_popup_container.classList.add('open');
    restaurant_info_update_container.classList.add('open');
    clear_all_field_btn.classList.remove("remove");
});


function clearAllStaffInfoInput() {
    for(var input of infoInput_list){
        input.value = '';
    }
    /*infoInputArray[1].readOnly = false;*/
}

closePopupBtn.addEventListener('click', (e) => {
    info_popup_container.classList.remove('open');
    (retaurant_review_container.classList.contains('open')) && retaurant_review_container.classList.remove('open');
    (restaurant_detail_container.classList.contains('open')) && restaurant_detail_container.classList.remove('open');
    (restaurant_info_update_container.classList.contains('open')) && restaurant_info_update_container.classList.remove('open');
    clearAllStaffInfoInput();
});

/*clear_all_field_btn.addEventListener('click', () => {
    clearAllStaffInfoInput();
});*/

cancelInfoEditBtn.addEventListener('click', () => {
    info_popup_container.classList.remove('open');
    (restaurant_detail_container.classList.contains('open')) && restaurant_detail_container.classList.remove('open');
    (restaurant_info_update_container.classList.contains('open')) && restaurant_info_update_container.classList.remove('open');
    clearAllStaffInfoInput();
});


