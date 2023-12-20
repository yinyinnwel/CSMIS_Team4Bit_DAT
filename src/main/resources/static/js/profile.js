const passwordInput = document.querySelector('.passwordInput');
const passwordEditBtn = document.querySelector('.passwordEditBtn');


const email_noti = document.getElementById("email_noti");
const email_noti_checkbox = document.getElementById("email_noti_checkbox");

const avoidMeatArray = Array.from(document.querySelectorAll('.avoidMeat'));
const avoidMeatList = document.getElementById("avoidMeatList");


const changePasswordMagic_container = document.querySelector(".changePasswordMagic_container");

const old_password_input = document.getElementById("old_password_input");
const new_password_input = document.getElementById("new_password_input");
const confirm_password_input = document.getElementById("confirm_password_input");

const changePasswordBtn = document.querySelector(".changePasswordBtn");

const closePopupBtn = document.querySelector('.closePopupBtn');
const info_popup_container = document.querySelector('.info_popup_container');

const cover = document.querySelector(".cover");





passwordEditBtn.addEventListener("click",() => {
    info_popup_container.classList.add('open');
});


new_password_input.addEventListener("input", (e) => {
    if(e.target.value == confirm_password_input.value && (e.target.value!='' && confirm_password_input.value!='')){
        changePasswordBtn.disabled = false;
    }else{
        changePasswordBtn.disabled = true;
    }
});

confirm_password_input.addEventListener("input", (e) => {
    if(e.target.value == new_password_input.value && (e.target.value!='' && new_password_input.value!='')){
        changePasswordBtn.disabled = false;
    }else{
        changePasswordBtn.disabled = true;
    }
});

changePasswordBtn.addEventListener("click", () => {
    changePasswordMagic_container.classList.remove("show");
});


closePopupBtn.addEventListener('click', () => {
    info_popup_container.classList.remove('open');
    old_password_input.value = "";
    cover.classList.remove("close");
    document.getElementById("wrongPass_text").innerText = "";
    new_password_input.value = "";
    confirm_password_input.value = "";
});








/*-------- set Checked to avoid meat checkbox from user avoid Meat list------- */
const av_DataofUser = (avoidMeatList.value).split(',');

for(let am_checkbox of avoidMeatArray){
	if(av_DataofUser.includes(am_checkbox.value)){
		am_checkbox.checked = true;
	}
}

/*------- Check Email noti is ON/Off--------- */
if(email_noti.value == 1) {/*<== email noti is on*/
	email_noti_checkbox.checked = true;
}else{
	email_noti_checkbox.checked = false;
}

/*---------------------------------------------- */
email_noti_checkbox.addEventListener("change",(e) => {
	if(e.target.checked) {
		email_noti.value = 1;
	}else{
		email_noti.value = 0;
	}
});


/*------------- Avoid Meat checkbox click Function-------- */

for(let avoidMeat of avoidMeatArray){
	avoidMeat.addEventListener("change",()=>{
		avoidMeatList.value = '';/*<= Clear avoidmeatList to create new list */
		for(let av_meat of avoidMeatArray){
			if(av_meat.checked){
				avoidMeatList.value += (av_meat.value+",");
			}
		}
		/*------ remove , that is at the end of value */
		avoidMeatList.value = (avoidMeatList.value).slice(0,avoidMeatList.value.length-1);
	})
}


