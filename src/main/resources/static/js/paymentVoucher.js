
const startDate = document.querySelector('.startDate');
const endDate = document.querySelector('.endDate');


const startDateLabel = document.getElementById('startDateLabel');
const endDateLabel = document.getElementById('endDateLabel');
const paymentDatePicker = document.querySelector('.paymentDatePicker');

const confirmDateBtn = document.getElementById('confirmDateBtn');

let selectedServiceOptionIndex = 0;
const serviceOption = document.querySelector(".serviceOption");

const receiverInput = document.getElementById("receiverInput");

//================== For Create invoice  ============================

const voucherStatusSelection = document.querySelector(".voucherStatusSelection");

const restaurantId = document.getElementById("restaurantId");
const paymentDate = document.getElementById("paymentDate");
const cashier = document.getElementById("cashier");
const receiver = document.getElementById("receiver");
const approver = document.getElementById("approver");
const paymentMethod = document.getElementById("paymentMethod");
const voucherstatus = document.getElementById("status");


//===================================================================


//paymentDateLabel.innerText = (changeDateFormat(new Date()));
paymentDatePicker.value = (changeDateFormat(new Date()));
paymentDate.value = (changeDateFormat(new Date()));


if(startDate.value != '' && endDate.value != ''){
	confirmDateBtn.disabled = false;
}else{
	confirmDateBtn.disabled = true;
}

//====================  Voucher Status ================================
voucherstatus.value = 1;
voucherStatusSelection.addEventListener("change",(e) => {
	voucherstatus.value =e.currentTarget.value;
});


//=====================================================================

startDate.addEventListener('change', (e) => {
    if(e.currentTarget.value != '' && endDate.value != ''){
        confirmDateBtn.disabled = false;
        compareStart_EndDate();
    }else{
        confirmDateBtn.disabled = true;
    }
});

endDate.addEventListener('change', (e) => {
    if(e.currentTarget.value != '' && startDate.value != ''){
        confirmDateBtn.disabled = false;
        compareStart_EndDate();
    }else{
        confirmDateBtn.disabled = true;
    }
});

function compareStart_EndDate(){
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

}

//==================== Choose restaurant Service ========================================
restaurantId.value = serviceOption.value;

for (let option of Array.from(serviceOption.children)) {
	if (option.selected) {
		selectedServiceOptionIndex = Array.from(serviceOption.children).indexOf(option);
		receiver.value = option.id;
		receiverInput.value = option.id;
	}
}

serviceOption.addEventListener("change", (e) => {
	
	for(let option of Array.from(serviceOption.children)){
		if(option.selected){
			receiver.value = option.id;
			receiverInput.value = option.id;
		}
	}

	console.log();
	
	restaurantId.value = e.currentTarget.value;
});

//==============================================================================

paymentDatePicker.addEventListener("change", (e)=>{
	
	let today = new Date();
	today.setHours(today.getHours());
	today.setMinutes(today.getMinutes());
	today.setSeconds(today.getSeconds());
	
	let date = new Date(e.currentTarget.value);
	date.setHours(today.getHours());
	date.setMinutes(today.getMinutes());
	date.setSeconds(today.getSeconds());
	
	if(date>=today || date.toDateString()==today.toDateString()){
		//====== set payment Date =====
		//console.log("yes"+e.currentTarget.value);
		paymentDate.value = e.currentTarget.value;
	}else{
		//console.log("no"+e.currentTarget.value)
		paymentDatePicker.value = changeDateFormat(today);
		//====== set payment Date =====
		paymentDate.value = changeDateFormat(today);
	}
	
	
	
})

//============================================================================


const infoArray = Array.from(document.querySelectorAll('#info'));

const addCashier = document.querySelector(".addCashier");
const cashier_approver_input = document.querySelector(".cashier_approver_input");
const optionBox = document.querySelector(".optionBox");

const cashier_approver_input1 = document.querySelector(".cashier_approver_input1");
const optionBox1 = document.querySelector(".optionBox1");


const app_cas_boxArray = Array.from(optionBox.children);
const app_cas_boxArray1 = Array.from(optionBox1.children);


// Add event listener to the document
document.addEventListener('click', function(event) {
  const isClickInside = cashier_approver_input.contains(event.target);
  const isClickInside1 = cashier_approver_input1.contains(event.target);

  // If the clicked element is outside the target element, do something
  if (!isClickInside) {
    // Do something, such as close a dropdown menu
    optionBox.classList.remove("show");
    
  }
  if (!isClickInside1) {
    // Do something, such as close a dropdown menu
    optionBox1.classList.remove("show");
    
  }
});

cashier_approver_input.addEventListener('focus', (event) => {
	
  optionBox.classList.add("show");
});
cashier_approver_input1.addEventListener('focus', (event) => {
	
  optionBox1.classList.add("show");
});


for(let app_cas of app_cas_boxArray){
	
	app_cas.addEventListener("click", (e) => {
		let name = e.currentTarget.children[0];
		
		if(e.target.className != 'edit'){
			cashier_approver_input.value = name.innerText;
			cashier.value = name.innerText;
		}
		
		
		optionBox.classList.remove("show");
	});
	
	
}

for(let app_cas of app_cas_boxArray1){
	
	app_cas.addEventListener("click", (e) => {
		let name = e.currentTarget.children[0];
	
		if(e.target.className != 'edit'){
			cashier_approver_input1.value = name.innerText;
			approver.value = name.innerText;
		}
		
		
		optionBox1.classList.remove("show");
	});
	
	
}



//================ Set Payment Method ================
const paymentMethodDropdown = document.querySelector('#paymentMethodDropdown');

paymentMethod.value = "cash";

paymentMethodDropdown.addEventListener("change", (e) => {
	paymentMethod.value = e.currentTarget.value;
});




function changeDateFormat(date) {
    let currentYear = date.getFullYear();
    let currentMonth = ("0" + (date.getMonth() + 1)).slice(-2);
    let currentDate = ("0" + date.getDate()).slice(-2);

    return (currentYear+"-"+currentMonth+"-"+currentDate);
}
//====================== Checkout action====================================

const checkoutBtn = document.querySelector('.checkoutBtn');


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

checkoutConfirmBtn.addEventListener('click',() => {
	
	
	if(cashier.value != "" && approver.value != "" && startDate.value != ""){
		conf_form_container.setAttribute("target", "_blank");
	}else{
		conf_form_container.setAttribute("target", "_self");
	}
	
	
	startDate.value = '';
	endDate.value = '';
	
	let voucherNo = document.getElementById("voucherNo");
	voucherNo.innerText = '- - -';
	
	startDateLabel.innerText = 'YYYY-MM-DD';
	endDateLabel.innerText = 'YYYY-MM-DD';


	let tbody = document.querySelector('tbody');
	let trList = tbody.querySelectorAll('tr');
	
	trList.forEach((tr) => {
		tbody.removeChild(tr);
	});
	
	let totalAmountSpan = document.getElementById("totalAmountSpan");
	totalAmountSpan.innerText = '0.0';
	
	let requiredArr = document.querySelectorAll('#required');
	
	requiredArr.forEach((required) => {
		required.innerHTML = '';
	})
	

	checkoutBtn.disabled = true;
	confirmDateBtn.disabled = true;
	confirmationContainer.classList.remove("open");
});

	
	
