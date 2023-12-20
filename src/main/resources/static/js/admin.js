
const staff_info_btn_list = document.querySelectorAll('#staff_info_btn');
const staff_edit_btn_list = document.querySelectorAll('#staff_edit_btn');
const closePopupBtn = document.querySelector('.closePopupBtn');
const info_popup_container = document.querySelector('.info_popup_container');
const staff_detail_container = document.querySelector('.staff_detail_container');
/*const staff_info_update_container = document.querySelector('.staff_info_update_container');*/

/*const clear_all_field_btn = document.querySelector('.clear_all_field_btn');
const cancelInfoEditBtn = document.querySelector('.cancelInfoEditBtn');
const saveInfoEidtBtn = document.querySelector('.saveInfoEidtBtn');
const infoInput_list = document.querySelectorAll('#infoInput');*/

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


const editUserName = Array.from(document.querySelectorAll('#editUserName'));

/*const edit_status_option_Array = Array.from(document.querySelector('.status_dropdown').children);
const edit_role_option_Array = Array.from(document.querySelector('.role_dropdown').children);*/


for(var staff_info_btn of staff_info_btn_list) {
    staff_info_btn.addEventListener('click', (e) => {
        info_popup_container.classList.add('open');
        staff_detail_container.classList.add('open');
        
        let currentInfoId = e.currentTarget.parentElement.id;
		
		editUserName[0].innerText = username[currentInfoId].value;/*Edit Username*/

		infoTextArray[0].innerText = role[currentInfoId].value;
		infoTextArray[1].innerText = division[currentInfoId].value;
		infoTextArray[2].innerText = staffId[currentInfoId].value;
		infoTextArray[3].innerText = username[currentInfoId].value;
		infoTextArray[4].innerText = doorLog[currentInfoId].value;
		infoTextArray[5].innerText = department[currentInfoId].value;
		infoTextArray[6].innerText = team[currentInfoId].value;
		infoTextArray[7].innerText = email[currentInfoId].value;
		infoTextArray[8].innerText = user_status[currentInfoId].value;
		infoTextArray[9].innerText = createdAt[currentInfoId].value;
		infoTextArray[10].innerText = createdBy[currentInfoId].value;
		infoTextArray[11].innerText = updatedAt[currentInfoId].value;
		infoTextArray[12].innerText = updatedBy[currentInfoId].value;
    });
}

for(var staff_edit_btn of staff_edit_btn_list) {
    staff_edit_btn.addEventListener('click', () => {
        info_popup_container.classList.add('open');
        /*staff_info_update_container.classList.add('open');*/
    });
}


/*function clearAllStaffInfoInput() {
    for(var input of infoInput_list){
        input.value = '';
    }
}*/

closePopupBtn.addEventListener('click', () => {
    info_popup_container.classList.remove('open');
    (staff_detail_container.classList.contains('open')) && staff_detail_container.classList.remove('open');
    /*(staff_info_update_container.classList.contains('open')) && staff_info_update_container.classList.remove('open');*/
    /*clearAllStaffInfoInput();*/
});


/*cancelInfoEditBtn.addEventListener('click', () => {
    info_popup_container.classList.remove('open');
    (staff_info_update_container.classList.contains('open')) && staff_info_update_container.classList.remove('open');
    clearAllStaffInfoInput();
});

saveInfoEidtBtn.addEventListener('click', () => {
    info_popup_container.classList.remove('open');
    (staff_info_update_container.classList.contains('open')) && staff_info_update_container.classList.remove('open');
    clearAllStaffInfoInput();
});
*/
