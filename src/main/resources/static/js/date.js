
const holidayArraysString = document.getElementById("holidayArrays").value;

let newHolidayArrayString = holidayArraysString.substring(1,holidayArraysString.length-1);

let holidayArray = newHolidayArrayString.split(",");

for(let day of holidayArray){
	holidayArray[holidayArray.indexOf(day)] = day.trim();
}


//==========================
const lunch_registeration_date_label = document.getElementById("lunch_registeration_date_label");
const calender = document.querySelector(".calender");
const current_next_option = document.getElementById("current_next_option");

const calendarChangeDiv = document.querySelector(".calendarChangeDiv");
const calendarChangeBtn = document.getElementById("calendarChangeBtn");

if(new Date().getDate() >= 27){
	calendarChangeDiv.classList.add('show');
}else{
	calendarChangeDiv.classList.remove('show');
}



let date;
//let date = new Date("2023-03-28");
if(current_next_option.value == 'current'){
	/*date = new Date("2023-03-25");*/
	
	date = new Date();
	calendarChangeBtn.innerText = "Go to Next Month";
}else{
	let d = new Date();
	let year = d.getFullYear();
	let month = d.getMonth() + 1;

	if (month < 12) {
		month += 1;
	} else {
		month = 1;
		year += 1;
	}

	date = new Date(year+"-"+(month < 10 ? '0'+month : month)+"-01");
	calendarChangeBtn.innerText = "Back to Current Month";
}



//=========== Create lunch registration calendar when u open lunch registration page ===========================

let hour = 14;
let minute = 0;

let time = new Date();
time.setHours(14);
time.setMinutes(0);
time.setSeconds(0);

Create_Calendar_Function(date,1);


let cc = 0;

if (date.toLocaleDateString() == new Date().toLocaleDateString()) {
	//if current time is under 2pm
	if (new Date() < time) {
		const checktwoPM = setInterval(() => {
			//console.log(cc++);
//====if today is friday of current week and time is 2pm and 0 minute,close lunch registeration for next week
			if (new Date().getDay() == 5 && new Date().getHours() == hour && new Date().getMinutes() == minute) {
				clearInterval(checktwoPM);
			//== Create new Calender ==================================
				calender.removeChild(document.querySelector('tbody'));
				Create_Calendar_Function(date, 2);
			//=========================================================
				let rawString = rawStringElement.value;
				let registeredString = rawString.slice(0, rawString.length - 1);
				let registeredBitArray = registeredString.split(",");
				let lunchCancelIndexArray = [];
				for (let i = 0; i < registeredBitArray.length; i++) {
					if (registeredBitArray[i] == '0') {
						lunchCancelIndexArray.push(i);
					}
				}

				

				let newCheckArr = Array.from(document.querySelectorAll('.checkBox'));

				for (let index of lunchCancelIndexArray) {
					let input = newCheckArr[index];
					let parentTD = input.parentElement;
					let label = parentTD.children[1];

					input.checked = false;
					label.classList.remove("checked");
				}
				
			}

		}, 1000);
	}

}







function Create_Calendar_Function(date, logicNum) {
	
	

	let currentYear = date.getFullYear();
	let currentMonth = date.getMonth();
	
	let realCurrentMonth  =(currentMonth+1);
	let realCurrentDate = date.getDate();
	let nowDate = currentYear+"-"+(realCurrentMonth<10 ? '0'+realCurrentMonth : realCurrentMonth)+"-"+(realCurrentDate < 10 ? '0'+realCurrentDate : realCurrentDate);
	
	let monthName = date.toLocaleString('en-US', {month: 'long'});
	
	lunch_registeration_date_label.innerText = monthName + " " + currentYear;
	
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
	
	for (let pDay of previousDayArray) {
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
	}
	
	for (let cDay of currentDayArray) {
	  let m = currentMonth + 1;
	  let d = cDay;
	
	  let dateString =
	    currentYear + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
	
	  currentDateArray.push(dateString);
	}
	
	for (let nDay of nextDayArray) {
	  let m = currentMonth + 2;
	  let d = nDay;
	
	  let dateString =
	    currentYear + "-" + (m < 10 ? "0" + m : m) + "-" + (d < 10 ? "0" + d : d);
	
	  nextDateArray.push(dateString);
	}
	
	// console.log(previousDateArray);
	// console.log(currentDateArray);
	// console.log(nextDateArray);
	
	let allDateArray = previousDateArray.concat(currentDateArray, nextDateArray);
	
	//console.log(allDateArray);
	
	let numberOfWeek = allDayArray.length / 7;
	
	//console.log(numberOfWeek);
	
	
	//------- Create Calender Table ====================
	const tbody = document.createElement("tbody");
	tbody.setAttribute("class","tbody");
	
	let start = 0;
	let end = 7;
	let count = 1;
	let checkCount = 1;
	let coverIndex = 0;
	
	if (date.toLocaleDateString() == new Date().toLocaleDateString()) {
		coverIndex = Math.floor((allDateArray.indexOf(nowDate) / 7)) + logicNum;
		
		//======= if today is Firday and time is over 2pm ,disable registration for next week
		if (new Date().getDay() == 5 && new Date() >= time) {
				coverIndex = Math.floor((allDateArray.indexOf(nowDate) / 7)) + 2;
		}
		
	}

	
	/*console.log(coverIndex);*/
	
	for (let i = 1; i <= numberOfWeek; i++) {
	  var tr = document.createElement("tr");
	  var th0 = document.createElement("th");
	  th0.textContent = "Week" + i;
	
	  //--------------------------------------
	  var td1 = document.createElement("td");
	  var td2 = document.createElement("td");
	  var td3 = document.createElement("td");
	  var td4 = document.createElement("td");
	  var td5 = document.createElement("td");
	  var td6 = document.createElement("td");
	  var td7 = document.createElement("td");
	
	  for (let j = start; j < end; j++) {
	    if (j == start) {//<=Sunday
	      td1.textContent = allDayArray[j];
	      holidayArray.includes(allDateArray[j])
	            ? td1.setAttribute("class", "holiday")
	            : td1.setAttribute("class", "disable");
	    } 
	    else if (j == end - 1) {//Saturday
	      td7.textContent = allDayArray[j];
	      holidayArray.includes(allDateArray[j])
	            ? td7.setAttribute("class", "holiday")
	            : td7.setAttribute("class", "disable");
	    } else {
	      //Mon -  Fri
	      let input = document.createElement("input");
	      let label = document.createElement("label");
	      let span = document.createElement("span");
	
	      if(i <= coverIndex){
	        span.setAttribute("class","dayCover open");
	      }else{
	        span.setAttribute("class","dayCover");
	      }
	      
	
	      input.setAttribute("type", "checkbox");
	      input.setAttribute("class", "checkBox");
	      input.setAttribute("id", "check" + checkCount);
	      input.value = allDayArray[j];
	      input.checked = true;
	
	      label.setAttribute("for", "check" + checkCount);
	      label.setAttribute("class", "dayLable checked");
	      label.textContent = allDayArray[j];
	
	      switch (j % 7) {
	        case 1:
	          {
	            holidayArray.includes(allDateArray[j])
	              ? td2.setAttribute("class", "holiday")
	              : td2.setAttribute("class", "disable");
	
	            if(!previousDateArray.includes(allDateArray[j]) && !nextDateArray.includes(allDateArray[j])){
	                td2.appendChild(input);
	                td2.appendChild(label);
	                td2.appendChild(span);
	            }else{
	                td2.textContent = allDayArray[j];
	            }
	
	          }
	          break;
	        case 2:
	          {
	            holidayArray.includes(allDateArray[j])
	            ? td3.setAttribute("class", "holiday")
	            : td3.setAttribute("class", "disable");
	            if(!previousDateArray.includes(allDateArray[j]) && !nextDateArray.includes(allDateArray[j])){
	                td3.appendChild(input);
	                td3.appendChild(label);
	                td3.appendChild(span);
	            }else{
	                td3.textContent = allDayArray[j];
	            }
	          }
	          break;
	        case 3:
	          {
	            holidayArray.includes(allDateArray[j])
	            ? td4.setAttribute("class", "holiday")
	            : td4.setAttribute("class", "disable");
	            if(!previousDateArray.includes(allDateArray[j]) && !nextDateArray.includes(allDateArray[j])){
	                td4.appendChild(input);
	                td4.appendChild(label);
	                td4.appendChild(span);
	            }else{
	                td4.textContent = allDayArray[j];
	            }
	          }
	          break;
	        case 4:
	          {
	            holidayArray.includes(allDateArray[j])
	            ? td5.setAttribute("class", "holiday")
	            : td5.setAttribute("class", "disable");
	            if(!previousDateArray.includes(allDateArray[j]) && !nextDateArray.includes(allDateArray[j])){
	                td5.appendChild(input);
	                td5.appendChild(label);
	                td5.appendChild(span);
	            }else{
	                td5.textContent = allDayArray[j];
	            }
	          }
	          break;
	        case 5:
	          {
	            holidayArray.includes(allDateArray[j])
	            ? td6.setAttribute("class", "holiday")
	            : td6.setAttribute("class", "disable");
	            if(!previousDateArray.includes(allDateArray[j]) && !nextDateArray.includes(allDateArray[j])){
	                td6.appendChild(input);
	                td6.appendChild(label);
	                td6.appendChild(span);
	            }else{
	                td6.textContent = allDayArray[j];
	            }
	          }
	          break;
	        default: break;
	      }
	      checkCount++;
	    }
	  }
	
	  tr.appendChild(th0);
	  tr.appendChild(td1);
	  tr.appendChild(td2);
	  tr.appendChild(td3);
	  tr.appendChild(td4);
	  tr.appendChild(td5);
	  tr.appendChild(td6);
	  tr.appendChild(td7);
	
	  tbody.appendChild(tr);
	
	  count++;
	  start = end;
	  end = count * 7;
	}
	
	calender.appendChild(tbody);
	
		
}
