//===================== Help =============================

const help_icon = document.getElementById('help_icon');

const helpPopupContainer = document.querySelector('.helpPopupContainer');
const closeHelpPopupBtn = document.querySelector('.closeHelpPopupBtn');


help_icon.addEventListener('click', ()=>{
	helpPopupContainer.classList.add('open');
});

closeHelpPopupBtn.addEventListener('click', ()=>{
	helpPopupContainer.classList.remove('open');
});
//========================================================================


const searchReplace_container = document.querySelector('.searchReplace_container');
const searchReplace_paper_container = document.querySelector('.searchReplace_paper_container');


const filter_dropdown = document.querySelector('.filter_dropdown');
const filterByOptionsArray = Array.from(filter_dropdown.children);
const filter_paper_dropdown = document.querySelector('.filter_paper_dropdown');
const filterStatusOptionsArray = Array.from(filter_paper_dropdown.children);

const selectedFilterBy = document.getElementById("selectedFilterBy");
const selectedStatus = document.getElementById("selectedStatus");

const inputFile = document.getElementById('inputFile');
const saveFileBtn = document.getElementById('saveFileBtn');

const infoText_list = document.querySelectorAll('.infoText');
const infoTextArray = Array.from(infoText_list);

const role_doc = document.querySelectorAll("#role");
const role = Array.from(role_doc);
const division_doc = document.querySelectorAll("#division");
const division = Array.from(division_doc);
const staffId_doc = document.querySelectorAll("#staffId");
const staffId = Array.from(staffId_doc);
const username_doc = document.querySelectorAll("#name");
const username = Array.from(username_doc);
const doorLog_doc = document.querySelectorAll("#doofLog");
const doorLog = Array.from(doorLog_doc);
const department_doc = document.querySelectorAll("#department");
const department = Array.from(department_doc);
const team_doc = document.querySelectorAll("#team");
const team = Array.from(team_doc);
const email_doc = document.querySelectorAll("#email");
const email = Array.from(email_doc);
const user_status_doc = document.querySelectorAll("#status");
const user_status = Array.from(user_status_doc);
const createdAt_doc = document.querySelectorAll("#createdAt");
const createdAt = Array.from(createdAt_doc);
const createdBy_doc = document.querySelectorAll("#createdBy");
const createdBy = Array.from(createdBy_doc);
const updatedAt_doc = document.querySelectorAll("#updatedAt");
const updatedAt = Array.from(updatedAt_doc);
const updatedBy_doc = document.querySelectorAll("#updatedBy");
const updatedBy = Array.from(updatedBy_doc);
const status_doc = document.querySelectorAll("#statusData");
const statusArr = Array.from(status_doc);

const staff_info_btn_list = document.querySelectorAll('#staff_info_btn');
const staff_edit_btn_list = document.querySelectorAll('#staff_edit_btn');
const closePopupBtn = document.querySelector('.closePopupBtn');
closePopupBtn.style.display = "flex";
const info_popup_container = document.querySelector('.info_popup_container');
const staff_detail_container = document.querySelector('.staff_detail_container');
const staff_info_update_container = document.querySelector('.staff_info_update_container');

const editUserName = Array.from(document.querySelectorAll('#editUserName'));

const edit_status_option_Array = Array.from(document.querySelector('.status_dropdown').children);
const edit_role_option_Array = Array.from(document.querySelector('.role_dropdown').children);

const clear_all_field_btn = document.querySelector('.clear_all_field_btn');
const cancelInfoEditBtn = document.querySelector('.cancelInfoEditBtn');
const saveInfoEidtBtn = document.querySelector('.saveInfoEidtBtn');
const infoInput_list = document.querySelectorAll('#infoInput');
const infoInputArray = Array.from(infoInput_list);

const main_searchInput = document.querySelector('.main_searchInput');



switch (selectedFilterBy.value) {
  case "staffId":{
	  filterByOptionsArray[0].selected = true;
	 	main_searchInput.removeAttribute('list');
  }break;
  case "username":{
	  filterByOptionsArray[1].selected = true;
	  main_searchInput.removeAttribute('list');
  }break;
  case "department":{
	  filterByOptionsArray[2].selected = true;
	  main_searchInput.setAttribute('list','department_list');
  }break;
  case "team":{
	  filterByOptionsArray[3].selected = true;
	  main_searchInput.setAttribute('list','team_list');
  }break;
  case "status":
    {
        filterByOptionsArray[4].selected = true;
        searchReplace_container.classList.add('close');
        searchReplace_paper_container.classList.remove('close');
        filter_paper_dropdown.value = 'active';
        
        if(selectedStatus.value=="active"){
            filterStatusOptionsArray[0].selected = true;
            filterStatusOptionsArray[1].selected = false;
        }else{
			filterStatusOptionsArray[0].selected = false;
            filterStatusOptionsArray[1].selected = true;
        }
        
    }
    break;

  default:
    break;
}




filter_dropdown.addEventListener('change', (e) => {
	var currentValue = e.currentTarget.value;

	if (currentValue == 'status') {
		searchReplace_container.classList.add('close');
		searchReplace_paper_container.classList.remove('close');
		filter_paper_dropdown.value = 'active';
	} else {
		searchReplace_container.classList.remove('close');
		searchReplace_paper_container.classList.add('close');
		
		switch (currentValue) {
		case "staffId": {
			main_searchInput.removeAttribute('list');
		} break;
		case "username": {
			main_searchInput.removeAttribute('list');
		} break;
		case "department": {
			main_searchInput.setAttribute('list', 'department_list');
		} break;
		case "team": {
			main_searchInput.setAttribute('list', 'team_list');
		} break;
		
		default:
			break;
		}
	}
	
	
});

/*-============= UPload File ================================= */
inputFile.addEventListener('change', (e) => {

	if (e.currentTarget.files.length == 0) {
		saveFileBtn.disabled = true;
	} else {
		saveFileBtn.disabled = false;
	}
});

const loading_popup_container = document.querySelector('.loading_popup_container');

saveFileBtn.addEventListener('click',() => {
	loading_popup_container.classList.add('open');
});

/*==============================================================*/

for (var staff_info_btn of staff_info_btn_list) {
	staff_info_btn.addEventListener('click', (e) => {
		info_popup_container.classList.add('open');
		staff_detail_container.classList.add('open');

		let currentInfoId = e.currentTarget.parentElement.id;
		
		editUserName[0].innerText = username[currentInfoId].value;/*Edit Username*/

		infoTextArray[0].innerText = role[currentInfoId].value;
		infoTextArray[1].innerText = division[currentInfoId].value=='' ? '-----' : division[currentInfoId].value;
		infoTextArray[2].innerText = staffId[currentInfoId].value;
		infoTextArray[3].innerText = username[currentInfoId].value;
		infoTextArray[4].innerText = doorLog[currentInfoId].value=='' ? '-----' : doorLog[currentInfoId].value;
		infoTextArray[5].innerText = department[currentInfoId].value=='' ? '-----' : department[currentInfoId].value;
		infoTextArray[6].innerText = team[currentInfoId].value=='' ? '-----' : team[currentInfoId].value;
		infoTextArray[7].innerText = email[currentInfoId].value=='' ? '-----' : email[currentInfoId].value;
		infoTextArray[8].innerText = user_status[currentInfoId].value;
		infoTextArray[9].innerText = createdAt[currentInfoId].value=='' ? '-----' : createdAt[currentInfoId].value;
		infoTextArray[10].innerText = createdBy[currentInfoId].value=='' ? '-----' : createdBy[currentInfoId].value;
		infoTextArray[11].innerText = updatedAt[currentInfoId].value=='' ? '-----' : updatedAt[currentInfoId].value;
		infoTextArray[12].innerText = updatedBy[currentInfoId].value=='' ? '-----' : updatedBy[currentInfoId].value;
	});
}

for (var staff_edit_btn of staff_edit_btn_list) {
	staff_edit_btn.addEventListener('click', (e) => {
		info_popup_container.classList.add('open');
		staff_info_update_container.classList.add('open');
		
		let currentInfoId = e.currentTarget.parentElement.id;
		
		editUserName[1].innerText = username[currentInfoId].value;/*Edit Username*/
		
		infoInputArray[0].value = division[currentInfoId].value;/*Division*/
		infoInputArray[1].value = staffId[currentInfoId].value;/*staffId*/
		infoInputArray[2].value = doorLog[currentInfoId].value;/*doorlogNo*/
		infoInputArray[3].value = department[currentInfoId].value;/*department*/
		infoInputArray[4].value = team[currentInfoId].value;/*team*/
		infoInputArray[5].value = email[currentInfoId].value;/*email*/
		
		
		if(statusArr[currentInfoId].value == '1'){
			edit_status_option_Array[0].selected = true;
			edit_status_option_Array[1].selected = false;
		}else{
			edit_status_option_Array[1].selected = true;
			edit_status_option_Array[0].selected = false;
		}
		
		if(role[currentInfoId].value == 'ROLE_EMPLOYEE'){
			edit_role_option_Array[0].selected = true;
			edit_role_option_Array[1].selected = false;
		}else{
			edit_role_option_Array[0].selected = false;
			edit_role_option_Array[1].selected = true;
		}
		
	});
}



closePopupBtn.addEventListener('click', () => {
	info_popup_container.classList.remove('open');
	(staff_detail_container.classList.contains('open')) && staff_detail_container.classList.remove('open');
	(staff_info_update_container.classList.contains('open')) && staff_info_update_container.classList.remove('open');
	loading_container.classList.remove('open');
	
});


saveInfoEidtBtn.addEventListener('click', () => {
	info_popup_container.classList.remove('open');
	(staff_info_update_container.classList.contains('open')) && staff_info_update_container.classList.remove('open');
});




