//const confirmDatePicker = document.getElementById('confirmDatePicker');
const startDate = document.querySelector('.startDate');
const endDate = document.querySelector('.endDate');

const startDate_hidden = document.getElementById('startDate_hidden');
const endDate_hidden = document.getElementById('endDate_hidden');

const startDateLabel = document.getElementById('startDateLabel');
const endDateLabel = document.getElementById('endDateLabel');

const confirmDateBtn = document.getElementById('confirmDateBtn');

const totalAmount= document.getElementById("totalAmount");


/*paymentDateLabel.innerText = (changeDateFormat(new Date()));*/

if(startDate.value !="" && endDate.value!=""){
	confirmDateBtn.disabled = false;
}else{
	confirmDateBtn.disabled = true;
}

startDate.addEventListener('change', (e) => {
    if(e.currentTarget.value != '' && endDate.value != ''){
        confirmDateBtn.disabled = false;
    }else{
        confirmDateBtn.disabled = true;
    }
});

endDate.addEventListener('change', (e) => {
    if(e.currentTarget.value != '' && startDate.value != ''){
        confirmDateBtn.disabled = false;
    }else{
        confirmDateBtn.disabled = true;
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

//----------------- Checkout ---------------------------
const checkoutBtn = document.querySelector(".checkoutBtn");


const confirmationContainer = document.querySelector('.confirmationContainer');
const conf_form_container = document.querySelector('.conf_form_container');
const checkoutCancleBtn =document.querySelector('.checkoutCancleBtn');
const checkoutConfirmBtn = document.querySelector('.checkoutConfirmBtn');

checkoutBtn.addEventListener('click', () =>{
	confirmationContainer.classList.add("open");
});

checkoutCancleBtn.addEventListener('click', () =>{
	confirmationContainer.classList.remove("open");
});

checkoutConfirmBtn.addEventListener('click', () => {

		startDate.value = '';
		endDate.value = '';
		confirmDateBtn.disabled = true;
		checkoutBtn.disabled = true;

		startDateLabel.innerText = 'YYYY-MM-DD';
		endDateLabel.innerText = 'YYYY-MM-DD';

		let tbody = document.querySelector('tbody');
		let trList = tbody.querySelectorAll('tr');

		trList.forEach((tr) => {
			tbody.removeChild(tr);
		});

		totalAmount.innerText = "0.0";

		confirmationContainer.classList.remove("open");
	
	
});
