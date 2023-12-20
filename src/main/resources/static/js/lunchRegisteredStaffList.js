const monthPicker = document.querySelector(".monthPicker");

const filter_dropdown = document.querySelector(".filter_dropdown");

const confirmMonthBtn = document.getElementById("confirmMonthBtn");

const infoTextArray = Array.from(document.querySelectorAll('.infoText'));

const staff_info_btn_list = document.querySelectorAll("#staff_info_btn");
const closePopupBtn = document.querySelector(".closePopupBtn");
const info_popup_container = document.querySelector(".info_popup_container");
const staff_detail_container = document.querySelector(".staff_detail_container");
const staff_info_update_container = document.querySelector(".staff_info_update_container");

const tmpMonthPickerValue = document.getElementById("tmpMonthPickerValue");

const calender = document.querySelector(".calender");

if(monthPicker.value == ''){
	setCurrentMonth_Year_to_monthPicker();
}

monthPicker.addEventListener("change", (e) => {
	tmpMonthPickerValue.value = e.target.value;
})


function setCurrentMonth_Year_to_monthPicker() {
  var now = new Date();
  let currentYear = now.getFullYear();
  let currentMonth = ("0" + (now.getMonth() + 1)).slice(-2);

  monthPicker.value = currentYear + "-" + currentMonth;
}

confirmMonthBtn.addEventListener("click", () => {
 console.log( monthPicker.value)
});


for (var staff_info_btn of staff_info_btn_list) {
  staff_info_btn.addEventListener("click", (e) => {
	let td = e.currentTarget.parentElement;

	let role = td.children[0];
	let staffId = td.children[1];
	let name = td.children[2];
	let registerId = td.children[3];
	let registered_date = td.children[4];
	let register_for = td.children[5];
	let updated_date = td.children[6];
	let rawString = td.children[7];
	
	infoTextArray[0].innerText = role.value;
	infoTextArray[1].innerText = staffId.value;
	infoTextArray[2].innerText = name.value;
	infoTextArray[3].innerText = registered_date.value;
	infoTextArray[4].innerText = (updated_date.value=='null' ? '- - - - -' : updated_date.value);
	
	calender.removeChild(document.querySelector('.tbody'));
	Create_Calendar_Function(new Date((register_for.value).substring(0,11)),rawString.value);
	  
    info_popup_container.classList.add("open");
    staff_detail_container.classList.add("open");
  });
}

closePopupBtn.addEventListener("click", (e) => {
  info_popup_container.classList.remove("open");
  staff_detail_container.classList.contains("open") && staff_detail_container.classList.remove("open");
});








function Create_Calendar_Function(date,rawString) {

	let currentYear = date.getFullYear();
	let currentMonth = date.getMonth();
	
	let realCurrentMonth  =(currentMonth+1);
	let realCurrentDate = date.getDate();
	let nowDate = currentYear+"-"+(realCurrentMonth<10 ? '0'+realCurrentMonth : realCurrentMonth)+"-"+(realCurrentDate < 10 ? '0'+realCurrentDate : realCurrentDate);
	
	let monthName = date.toLocaleString('en-US', {month: 'long'});
	
	let fistdayOfCurrentMonth = new Date(currentYear, currentMonth, 1);
	let lastdayOfCurrentMonth = new Date(currentYear, currentMonth + 1, 0);
	let lastdayOfPreviousMonth = new Date(currentYear, currentMonth, 0);
	
	//------ this is current -------------------------
	let getDayOfFirst = fistdayOfCurrentMonth.getDay(); //1,2,..(Mon.Thu,..)
	let getDayOfEnd = lastdayOfCurrentMonth.getDay(); //1,2,..
	
	let getDateOfFirst = fistdayOfCurrentMonth.getDate(); //12.13,...
	let getDateOfEnd = lastdayOfCurrentMonth.getDate(); //12.13,...
	//--------This is previoud-------------------------
	let getDayOfEnd_P = lastdayOfPreviousMonth.getDay();
	let getDateOfEnd_P = lastdayOfPreviousMonth.getDate();
	
	// console.log(getDayOfFirst);
	// console.log(getDateOfEnd_P);
	//====  Arrays ==================
	let previousDayArray = [];
	let currentDayArray = [];
	let nextDayArray = [];
	
	let previousDateArray = [];
	let currentDateArray = [];
	let nextDateArray = [];
	
	function getAllDaysOfCurrentMonthFunction() {
	  for (let i = getDateOfFirst; i <= getDateOfEnd; i++) {
	    currentDayArray.push(i);
	  }
	}
	
	switch (getDayOfFirst) {
	  case 0:
	    {//Sunday=> need 0 day
	      getAllDaysOfCurrentMonthFunction();
	    }
	    break;
	  case 1:
	    {//Monday => need 1 day
	      for (let i = getDateOfEnd_P; i < getDateOfEnd_P + 1; i++) {
	        previousDayArray.push(i);
	      }
	      getAllDaysOfCurrentMonthFunction();
	    }
	    break;
	  case 2:
	    { //Tuesday=> need 2 day
	      for (let i = getDateOfEnd_P - 1; i < getDateOfEnd_P - 1 + 2; i++) {
	        previousDayArray.push(i);
	      }
	    getAllDaysOfCurrentMonthFunction();
	    }
	    break;
	  case 3:
	    {//Wednesday=> need 3 day
	      for (let i = getDateOfEnd_P - 2; i < getDateOfEnd_P - 2 + 3; i++) {
	        previousDayArray.push(i);
	      }
	      getAllDaysOfCurrentMonthFunction();
	    }
	    break;
	  case 4:
	    {//thursday=> need 4 day
	      for (let i = getDateOfEnd_P - 3; i < getDateOfEnd_P - 3 + 4; i++) {
	        previousDayArray.push(i);
	      }
	      getAllDaysOfCurrentMonthFunction();
	    }
	    break;
	  case 5:
	    {//Friday=> need 5 day
	      for (let i = getDateOfEnd_P - 4; i < getDateOfEnd_P - 4 + 5; i++) {
	        previousDayArray.push(i);
	      }
	      getAllDaysOfCurrentMonthFunction();
	    }
	    break;
	  case 6:
	    {//Saturday=> need 6 day
	      for (let i = getDateOfEnd_P - 5; i < getDateOfEnd_P - 5 + 6; i++) {
	        previousDayArray.push(i);
	      }
	      getAllDaysOfCurrentMonthFunction();
	    }
	    break;
	
	  default:
	    break;
	}
	
	switch (getDayOfEnd) {
	  case 0:
	    {//Sunday=> need 6 day
	      for (let i = 1; i <= 6; i++) {
	        nextDayArray.push(i);
	      }
	    }
	    break;
	  case 1:
	    {//Monday => need 5 day
	      for (let i = 1; i <= 5; i++) {
	        nextDayArray.push(i);
	      }
	    }
	    break;
	  case 2:
	    {//Tuesday=> need 4 day
	      for (let i = 1; i <= 4; i++) {
	        nextDayArray.push(i);
	      }
	    }
	    break;
	  case 3:
	    {//Wednesday=> need 3 day
	      for (let i = 1; i <= 3; i++) {
	        nextDayArray.push(i);
	      }
	    }
	    break;
	  case 4:
	    {//thursday=> need 2 day
	      for (let i = 1; i <= 2; i++) {
	        nextDayArray.push(i);
	      }
	    }
	    break;
	  case 5:
	    {//Friday=> need 1 day
	      for (let i = 1; i <= 1; i++) {
	        nextDayArray.push(i);
	      }
	    }
	    break;
	  case 6:
	    {//Saturday=> need 0 day
	      //No need ,Hehe!
	    }
	    break;
	
	  default:
	    break;
	}
	
	// console.log(previousDayArray);
	// console.log(currentDayArray);
	// console.log(nextDayArray);
	
	let allDayArray = previousDayArray.concat(currentDayArray, nextDayArray);
	
	//console.log(allDayArray);
	
	/*for (let pDay of previousDayArray) {
	  let y = currentYear;
	  let m = currentMonth;
	  if (currentMonth == 0) {
	    y = y - 1;
	    m = 12;
	  }
	  let d = pDay;
	
	  let dateString =
	    y + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
	
	  previousDateArray.push(dateString);
	}*/
	
	/*for (let cDay of currentDayArray) {
	  let m = currentMonth + 1;
	  let d = cDay;
	
	  let dateString =
	    currentYear + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
	
	  currentDateArray.push(dateString);
	}*/
	
	/*for (let nDay of nextDayArray) {
	  let m = currentMonth + 2;
	  let d = nDay;
	
	  let dateString =
	    currentYear + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
	
	  nextDateArray.push(dateString);
	}*/
	
	// console.log(previousDateArray);
	// console.log(currentDateArray);
	// console.log(nextDateArray);
	
	//let allDateArray = previousDateArray.concat(currentDateArray, nextDateArray);
	
	let numberOfWeek = allDayArray.length / 7;
	
	
	//------- Create Calender Table ====================
	const tbody = document.createElement("tbody");
	tbody.setAttribute("class","tbody")
	
	let start = 0;
	let end = 7;
	let count = 1;
	let start1 = 0;
	let end1 = 7;
	let count1 = 1;
	let checkCount = 0;
	//let coverIndex =  Math.floor((allDateArray.indexOf(nowDate)/7))+1;
	
	let rawArray = (rawString.substring(0,rawString.length-1)).split(",");
	console.log(rawArray)
	
	let tdArray = [];
	
	for (let j = 1; j <= allDayArray.length; j++) {
		var td = document.createElement("td");
		td.setAttribute("class", "");
		td.textContent = allDayArray[j-1];

		

		//console.log(start1 + " : " +end1)
		if (j > start1+1 && j < end1) {
			if (j > previousDayArray.length && j <= allDayArray.length - nextDayArray.length) {
				switch(rawArray[checkCount]){
					case '1' : td.setAttribute("class", "yes");break;
					case '0' : td.setAttribute("class", "no");break;
					case '#' : td.setAttribute("class", "holiday");break;
 				}
				checkCount++;
			}
		}
		
		tdArray.push(td);
		
		
		if(j%7 == 0){
			//console.log(count1);
			count1++;
			start1 = end1;
	  		end1 = count1 * 7;
		}

	}
	
	
	for (let i = 1; i <= numberOfWeek; i++) {
	  var tr = document.createElement("tr");
	  var th0 = document.createElement("th");
	  th0.textContent = "Week" + i;
	  
	  tr.appendChild(th0);
	  
	   for (let j = start; j < end; j++) {
		   if(j == start) {//<=Sunday
			   tr.appendChild(tdArray[start]);
		   }else if(j == end - 1){//Saturday
			   tr.appendChild(tdArray[end-1]);
		   }else{
			   tr.appendChild(tdArray[j]);
		   }
	   }
	   
	  tbody.appendChild(tr);
	  
	  count++;
	  start = end;
	  end = count * 7;
	}
	
	
	
	
	
	calender.appendChild(tbody);
	
		
}

