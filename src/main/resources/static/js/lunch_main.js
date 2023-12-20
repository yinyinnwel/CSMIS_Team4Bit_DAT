const editCostBtn = document.querySelector('.editCostBtn');
const closePopup = document.querySelector('.closePopup');
const saveEditCostBtn = document.querySelector('.saveEditCostBtn');
const popup_container = document.querySelector('.popup_container');


const companyCostHidden = document.getElementById("companyCostHidden");
const staffCostHidden = document.getElementById("staffCostHidden");

const companyCost = document.getElementById("companyCost");
const staffCost = document.getElementById("staffCost");


/*============================================================================================== */

try {
	editCostBtn.addEventListener('click', () => {
		companyCost.value = companyCostHidden.value;
		staffCost.value = staffCostHidden.value;
		(!popup_container.classList.contains('open')) && popup_container.classList.add('open');
	});

} catch {

}
closePopup.addEventListener('click', () => {
    (popup_container.classList.contains('open')) && popup_container.classList.remove('open');
});

saveEditCostBtn.addEventListener('click',() => {
    (popup_container.classList.contains('open')) && popup_container.classList.remove('open');
});





companyCost.addEventListener('input', (e) => {
	if(e.target.value == ''){
		companyCost.value = 0;
	}else{
		companyCost.value = parseInt(e.target.value);
	}
});

staffCost.addEventListener('input', (e) => {
	if(e.target.value == ''){
		staffCost.value = 0;
	}else{
		staffCost.value = parseInt(e.target.value);
	}
});