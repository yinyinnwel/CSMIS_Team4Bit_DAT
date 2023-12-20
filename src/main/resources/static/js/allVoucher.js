
const startDate = document.querySelector('.startDate');
const endDate = document.querySelector('.endDate');

const confirmDateBtn = document.getElementById('confirmDateBtn');

const filterCover = document.querySelector(".filterCover");



if(startDate.value !="" && endDate.value!=""){
	confirmDateBtn.disabled = false;
	filterCover.classList.remove("show");
}else{
	confirmDateBtn.disabled = true;
	filterCover.classList.add("show");
}

startDate.addEventListener('change', (e) => {
    if(e.currentTarget.value != '' && endDate.value != ''){
        confirmDateBtn.disabled = false;
        filterCover.classList.remove("show");
    }else{
        confirmDateBtn.disabled = true;
        filterCover.classList.add("show");
    }
});

endDate.addEventListener('change', (e) => {
    if(e.currentTarget.value != '' && startDate.value != ''){
        confirmDateBtn.disabled = false;
        filterCover.classList.remove("show");
    }else{
        confirmDateBtn.disabled = true;
        filterCover.classList.add("show");
    }
});



confirmDateBtn.addEventListener('click', () => {
    let confirm_startDate = new Date(startDate.value);
    let confirm_endDate = new Date(endDate.value);

    if(confirm_startDate > confirm_endDate){
        startDate.value = changeDateFormat(confirm_endDate);
        endDate.value = changeDateFormat(confirm_startDate);
        startDateLabel.innerText = changeDateFormat(confirm_endDate);
        endDateLabel.innerText = changeDateFormat(confirm_startDate);
    }else{
        startDateLabel.innerText = changeDateFormat(confirm_startDate);
        endDateLabel.innerText = changeDateFormat(confirm_endDate);
    }

    //getAllDays_Mon_to_Fri(confirmDate);

});


function changeDateFormat(date) {
    let currentYear = date.getFullYear();
    let currentMonth = ("0" + (date.getMonth() + 1)).slice(-2);
    let currentDate = ("0" + date.getDate()).slice(-2);

    return (currentYear+"-"+currentMonth+"-"+currentDate);
}

//=========== Cost Show Function =========================

const costShowBtn = document.querySelector('.costShowBtn');


costShowBtn.addEventListener('click', () => {
	costShowBtn.classList.toggle("active");
	
	let costArr = Array.from(document.querySelectorAll('.cost'));
	
	costArr.forEach((cost) => {
		cost.classList.toggle("show");
	});
});

//=============== Amount Show Function ======================
const amountShowBtn = document.querySelector('.amountShowBtn');


amountShowBtn.addEventListener('click', () => {
	amountShowBtn.classList.toggle("active");
	
	let amountArr = Array.from(document.querySelectorAll('.amount'));
	
	amountArr.forEach((amount) => {
		amount.classList.toggle("show");
	});
});

const infoShowBtn= document.querySelector('.infoShowBtn');

infoShowBtn.addEventListener('click', () => {
	infoShowBtn.classList.toggle("active");
	
	let infoArr = Array.from(document.querySelectorAll('.info'));
	
	infoArr.forEach((info) => {
		info.classList.toggle("show");
	});
});

//=============== Edit POp UP function ===============================
const editStatusBtnArr = document.querySelectorAll('.editStatusBtn');
const closePopupBtn = document.querySelector('.closePopupBtn');

const info_popup_container = document.querySelector('.info_popup_container');

const desctiptionInputHidden = document.getElementById('desctiptionInputHidden');
const voucherNoInput = document.getElementById("voucherNoInput");
const voucherStatusDropdown = document.querySelector('.voucherStatusDropdown');

editStatusBtnArr.forEach((editBtn) => {
	
	editBtn.addEventListener('click', (e)=>{
		let parentTr = e.currentTarget.parentElement;
		let input1 = parentTr.children[0].value;
		
		let voucherCode = input1.substring(0,15);
		let status = parentTr.children[1].value;
		
		desctiptionInputHidden.value = voucherCode;
		voucherNoInput.innerText = voucherCode;
		voucherStatusDropdown.value = status;
		
		info_popup_container.classList.add('open');
	});
	
});

closePopupBtn.addEventListener('click', () =>{
	info_popup_container.classList.remove('open');
	
});

