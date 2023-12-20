
const checkBoxs= document.querySelectorAll('.checkBox');
const checkBoxList =  Array.from(checkBoxs);

const dayLables = document.querySelectorAll('.dayLable');
const dayLable_List = Array.from(dayLables);


let rawStringElement = document.getElementById("rawString");

const refreshRegisterationTableBtn = document.querySelector('.refreshRegisterationTableBtn');

const saveLunchRegisterationBtn = document.querySelector('.saveLunchRegisterationBtn');

for(var dayLabel of dayLable_List) {
    dayLabel.addEventListener('click', (e) => {
        var label = e.currentTarget;
        label.classList.toggle('checked');
        // var check = checkBoxList[dayLable_List.indexOf(label)];
       
    });
}


var string = '';
checkBoxList.forEach((check) => {
	check.addEventListener('change',() => {
		string = '';
		for (var check of checkBoxList) {
			if (check.checked && !check.parentNode.classList.contains('holiday')) {
				string = string.concat('1,');
			}
			if (!check.checked && !check.parentNode.classList.contains('holiday')) {
				string = string.concat('0,');
			}
			if (check.parentNode.classList.contains('holiday')) {
				string = string.concat('#,');

			}
		}
		rawStringElement.value = string;
	});
})


refreshRegisterationTableBtn.addEventListener('click',() => {
    for(var check of checkBoxList) {
        if(!check.parentNode.classList.contains('holiday') && !check.parentNode.children[2].classList.contains('open')){
            var label = dayLable_List[checkBoxList.indexOf(check)];
            check.checked = true;
            label.classList.add('checked');
        }
    }
    string = '';
	for (var check of checkBoxList) {
		if (check.checked && !check.parentNode.classList.contains('holiday')) {
			string = string.concat('1,');
		}
		if (!check.checked && !check.parentNode.classList.contains('holiday')) {
			string = string.concat('0,');
		}
		if (check.parentNode.classList.contains('holiday')) {
			string = string.concat('#,');

		}
	}
	rawStringElement.value = string;

});


/*=============== Lunch Cancel Function =================== */



lunchCancelFunction();



function lunchCancelFunction() {
	
let rawString = rawStringElement.value;
let registeredString = rawString.slice(0, rawString.length - 1);
let registeredBitArray = registeredString.split(",");
let lunchCancelIndexArray = [];

	if (rawString == "") {
		for (var check of checkBoxList) {
			let td = check.parentElement;
			let label = td.children[1];
			let span = td.children[2];
			if (span.classList.contains('open')) {
				check.checked = false;
				label.classList.remove("checked");
			}
		}
	}


	for (let i = 0; i < registeredBitArray.length; i++) {
		if (registeredBitArray[i] == '0') {
			lunchCancelIndexArray.push(i);
		}
	}


	for (let index of lunchCancelIndexArray) {
		let input = checkBoxList[index];
		let parentTD = input.parentElement;
		let label = parentTD.children[1];

		input.checked = false;
		label.classList.remove("checked");
	}

}



/*========================================================= */
let string1 = '';
for (var check of checkBoxList) {
	if (check.checked && !check.parentNode.classList.contains('holiday')) {
		string1 = string1.concat('1,');
	}
	if (!check.checked && !check.parentNode.classList.contains('holiday')) {
		string1 = string1.concat('0,');
	}
	if (check.parentNode.classList.contains('holiday')) {
		string1 = string1.concat('#,');

	}
}
rawStringElement.value = string1;




/*========== Avoid Meat list Funciton ================= */

const avoidMeatArray = Array.from(document.querySelectorAll('.avoidMeat'));
const avoidMeatList = document.getElementById("avoidMeatList");

/*-------- set Checked to avoid meat checkbox from user avoid Meat list------- */
const av_DataofUser = (avoidMeatList.value).split(',');

for(let am_checkbox of avoidMeatArray){
	if(av_DataofUser.includes(am_checkbox.value)){
		am_checkbox.checked = true;
	}
}

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


saveLunchRegisterationBtn.addEventListener('click', () => {
	

    
});






