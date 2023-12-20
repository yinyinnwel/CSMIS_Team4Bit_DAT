const addAvoidMeatBtn = document.querySelector('.addAvoidMeatBtn');

const info_popup_container  =document.querySelector('.info_popup_container');
const closePopupBtn = document.querySelector('.closePopupBtn');

const add_edit_avoidMeatLabel = document.getElementById('add_edit_avoidMeatLabel');

const avoidMeatIdHidden = document.getElementById('avoidMeatIdHidden');
const avoidMeatInput = document.getElementById('avoidMeatInput');
const avoidMeatStatusDropdown = document.querySelector('.avoidMeatStatusDropdown');

const avoidMeat_edit_btnArr = document.querySelectorAll("#avoidMeat_edit_btn");
//************ Check not to add or update duplicate avoid meat ******* */
let tempAvoidMeatForEdit = '';

const avoidMeatSubmitBtn = document.getElementById('avoidMeatSubmitBtn');

const duplicate_avoidMeat = document.querySelector('.duplicate_avoidMeat');

const avoidMeatListHidden = document.querySelector('.avoidMeatListHidden').value;
let rawString = avoidMeatListHidden.substring(1,avoidMeatListHidden.length-1);
let avoidMeatListArray = rawString.split(",");
avoidMeatListArray = avoidMeatListArray.map(item => item.toLowerCase().trim());


avoidMeatInput.addEventListener('input', (e)=>{
	
	let inputData = (e.currentTarget.value).toLowerCase();
	
	if (add_edit_avoidMeatLabel.innerText == 'Add New Avoid Meat') {//Add new Avoid meat

		if (avoidMeatListArray.includes(inputData)) {
			//if contain
			avoidMeatSubmitBtn.disabled = true;
			duplicate_avoidMeat.classList.add('show');
		} else {
			//if not contain
			avoidMeatSubmitBtn.disabled = false;
			duplicate_avoidMeat.classList.remove('show');
		}
	}else{//Edit current avoid meat
		let avoidMeatListArr_without_currentEditMeat = avoidMeatListArray.slice(); // Create a copy of the original array;
		const index = avoidMeatListArray.indexOf(tempAvoidMeatForEdit.toLowerCase());
		if (index > -1) {
			avoidMeatListArr_without_currentEditMeat.splice(index,1);
		}
		
		
		if (avoidMeatListArr_without_currentEditMeat.includes(inputData)) {
			//if contain
			avoidMeatSubmitBtn.disabled = true;
			duplicate_avoidMeat.classList.add('show');
		} else {
			//if not contain
			avoidMeatSubmitBtn.disabled = false;
			duplicate_avoidMeat.classList.remove('show');
		}
	}
	
	
	
})

//**********************************************************************
addAvoidMeatBtn.addEventListener('click', () => {
	add_edit_avoidMeatLabel.innerText = "Add New Avoid Meat";
	info_popup_container.classList.add('open');
	avoidMeatIdHidden.value = -1;
	avoidMeatInput.value = '';
	avoidMeatStatusDropdown.value = 1;
});

avoidMeat_edit_btnArr.forEach((avoidMeat_edit_btn) => {
	avoidMeat_edit_btn.addEventListener('click', (e) => {
		add_edit_avoidMeatLabel.innerText = "Edit Avoid Meat";
		
		
		let parentTr = e.currentTarget.parentElement;
		let avoidMeat_Id = parentTr.children[0].value;
		let avoidMeat_Name = parentTr.children[1].value;
		let avoidMeat_status = parentTr.children[2].value;
		
		tempAvoidMeatForEdit = avoidMeat_Name;
		
		avoidMeatIdHidden.value = avoidMeat_Id;
		avoidMeatInput.value = avoidMeat_Name;
		avoidMeatStatusDropdown.value = avoidMeat_status;
		
		info_popup_container.classList.add('open');
	
	});
});


closePopupBtn.addEventListener('click', () => {
	info_popup_container.classList.remove('open');
	duplicate_avoidMeat.classList.remove('show');
});