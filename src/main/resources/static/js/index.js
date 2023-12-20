const index_popup_container = document.querySelector('.index_popup_container');
const mail_compose_container = document.querySelector('.mail_compose_container');


const lunch_menu = document.querySelector('.menu');
const mail = document.querySelector('.mail');
const closeMailBoxBtn = document.querySelector('.closeMailBoxBtn');
const sendMail_btn = document.querySelector('.sendMail_btn');
const closePopupBtn = document.querySelector('.closePopupBtn');

const mail_subject = document.getElementById('mail_subject');
const mail_description = document.getElementById('mail_description');


//========================= Lunch Menu Function ======================

const pdf_container_Arr = document.querySelectorAll('.pdf_container');

pdf_container_Arr.forEach((pdf_container) => {
	let embed = pdf_container.firstElementChild;
	let src = embed.src;
	let checkSrc = src.substring(28,33);
	
	if(checkSrc=='null'){
		pdf_container.classList.add('close');
	}else{
		pdf_container.classList.remove('close');
	}
})

/*========================= Send Comment Function ========================*/
const addReviewBtn = document.querySelector('.addReviewBtn');
const restaurant_comment_container = document.querySelector('.restaurant_comment_container');
const closeCommentBoxBtn = document.querySelector('.closeCommentBoxBtn');

const comment_description = document.getElementById("comment_description");
const letterCount = document.getElementById("letterCount");

addReviewBtn.addEventListener('click', () => {
	restaurant_comment_container.classList.toggle('open');
	mail.classList.remove('change');
	mail_compose_container.classList.remove('open');
});

closeCommentBoxBtn.addEventListener('click', () => {
	restaurant_comment_container.classList.remove('open');
});

comment_description.value = "";
comment_description.addEventListener("input", (e) => {
	
	letterCount.innerText = e.target.value.length;
})

/**========================================================================= */
lunch_menu.addEventListener('click', () => {
    index_popup_container.classList.add('open');
});

closePopupBtn.addEventListener('click', () => {
    index_popup_container.classList.remove('open');
});

try {
	mail.addEventListener('click', () => {
		mail.classList.toggle('change');
		mail_compose_container.classList.toggle('open');
		restaurant_comment_container.classList.remove('open');
	});
} catch {

}

closeMailBoxBtn.addEventListener('click', () => {
    afterSendMailFunction();
});

mail_subject.addEventListener('input', (e) => {
    var input = e.currentTarget.value;
    if(input != '' && mail_description.value != '') {
        sendMail_btn.disabled = false;
    }else{
        sendMail_btn.disabled = true;
    }
});

mail_description.addEventListener('input', (e) => {
    var input = e.currentTarget.value;
    if(input != '' && mail_subject.value != '') {
        sendMail_btn.disabled = false;
    }else{
        sendMail_btn.disabled = true;
    }
});

sendMail_btn.addEventListener('click', () => {
    afterSendMailFunction();
});



function afterSendMailFunction() {
    mail.classList.remove('change');
    mail_compose_container.classList.remove('open');
}

//================ Graph JS ======================================
const showDepartmentNameRadioArr = Array.from(document.querySelectorAll('#showDepartmentNameRadio'));

showDepartmentNameRadioArr.forEach((showDepartmentNameRadio) => {
	showDepartmentNameRadio.addEventListener('mouseenter', (e) => {
		let divParent = e.currentTarget.parentElement;
		let span = divParent.children[3];
		span.classList.add("show");
		
	});
	
	showDepartmentNameRadio.addEventListener('mouseout', (e) => {
		let divParent = e.currentTarget.parentElement;
		let span = divParent.children[3];
		span.classList.remove("show");
	});
});
