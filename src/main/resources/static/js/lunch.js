//===================== Help =============================


const help_icon = document.getElementById('help_icon');

const helpPopupContainer = document.querySelector('.helpPopupContainer');
const closeHelpPopupBtn = document.querySelector('.closeHelpPopupBtn');


help_icon.addEventListener('click', ()=>{
	helpPopupContainer.classList.add('open');
});

closeHelpPopupBtn.addEventListener('click', ()=>{
	helpPopupContainer.classList.remove('open');
});

/*=========== Open Loading Popup while inserting data ===================== */
const inputFile = document.getElementById('inputFile');
const saveFileBtn = document.getElementById("saveFileBtn");
const loading_popup_container = document.querySelector('.loading_popup_container');

saveFileBtn.addEventListener('click',() => {
	if(inputFile.value != ''){
		loading_popup_container.classList.add('open');
	}
	
});


