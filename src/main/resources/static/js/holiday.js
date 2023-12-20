//===================== Help =============================

const help_icon = document.getElementById('help_icon');

const helpPopupContainer = document.querySelector('.helpPopupContainer');
const closePopupBtn = document.querySelector('.closePopupBtn');

help_icon.addEventListener('click', ()=>{
	helpPopupContainer.classList.add('open');
});

closePopupBtn.addEventListener('click', ()=>{
	helpPopupContainer.classList.remove('open');
});


//====================== Loading Page Popup ================
const loading_popup_container = document.querySelector('.loading_popup_container');

const inputFile = document.getElementById("inputFile");
const saveFileBtn = document.querySelector("#saveFileBtn");
saveFileBtn.addEventListener("click",() => {
	if(inputFile.value!=""){
		loading_popup_container.classList.add('open');
	}
	
});





