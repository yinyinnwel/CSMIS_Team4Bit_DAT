
//=====================================================================================
//====================Javascript for All Lunch List Page ==============================
//=====================================================================================

const searchBtn = document.querySelector('.searchBtn');

const optionCover = document.querySelector(".optionCover");

const startHiddenArr = Array.from(document.querySelectorAll("#start"));
const endHiddenArr = Array.from(document.querySelectorAll("#end"));

const startDate = document.querySelector('.start_datepicker');
const endDate = document.querySelector('.end_datepicker');

const confirmDateBtn = document.querySelector('.confirmDateBtn');

if(startDate.value == '' && endDate.value =='' ) {
	startDate.value = changeDateFormat(new Date());
	endDate.value = changeDateFormat(new Date());
	
	optionCover.classList.remove("show");
	
	setStart_EndDateValueToInputHidden(changeDateFormat(new Date()),changeDateFormat(new Date()));
}

startDate.addEventListener('change', (e) => {
    if(e.currentTarget.value != '' && endDate.value != ''){
		searchBtn.disabled = false;
        confirmDateBtn.disabled = false;
        checkStart_EndDate();
        optionCover.classList.remove("show");
    }else{
		searchBtn.disabled = true;
        confirmDateBtn.disabled = true;
        optionCover.classList.add("show");
    }
});

endDate.addEventListener('change', (e) => {
    if(e.currentTarget.value != '' && startDate.value != ''){
		searchBtn.disabled = false;
        confirmDateBtn.disabled = false;
        checkStart_EndDate();
        optionCover.classList.remove("show");
    }else{
		searchBtn.disabled = true;
        confirmDateBtn.disabled = true;
        optionCover.classList.add("show");
    }
});

function setStart_EndDateValueToInputHidden(start_date, end_date){
	for(let start of startHiddenArr){
		start.value = start_date;
	}
	for(let end of endHiddenArr){
		end.value = end_date;
	}
}

function checkStart_EndDate(){
	let confirm_startDate = new Date(startDate.value);
    let confirm_endDate = new Date(endDate.value);

    if(confirm_startDate > confirm_endDate){
        startDate.value = changeDateFormat(confirm_endDate);
        endDate.value = changeDateFormat(confirm_startDate);
        setStart_EndDateValueToInputHidden(changeDateFormat(confirm_endDate),changeDateFormat(confirm_startDate));
    }else{
		setStart_EndDateValueToInputHidden(changeDateFormat(confirm_startDate),changeDateFormat(confirm_endDate));
	}
    
    
}

function changeDateFormat(date) {
    let currentYear = date.getFullYear();
    let currentMonth = ("0" + (date.getMonth() + 1)).slice(-2);
    let currentDate = ("0" + date.getDate()).slice(-2);

    return (currentYear+"-"+currentMonth+"-"+currentDate);
}