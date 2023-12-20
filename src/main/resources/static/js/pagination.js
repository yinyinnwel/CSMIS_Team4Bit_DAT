const prev = document.querySelector('.prev');
const next = document.querySelector('.next');
const prevHider = document.querySelector('.prevHider');
const nextHider = document.querySelector('.nextHider');
const page_input = document.querySelector('.page_input');

const maxPage = document.querySelector('.maxPage');

const max = parseInt(maxPage.value);



backFunction(page_input.value);
nextFunction(page_input.value);

next.addEventListener('click', () => {
	var currentPage = parseInt(page_input.value);
	if(page_input.value < max) {
        page_input.value = currentPage+1;
    }
    nextFunction(currentPage);
});

prev.addEventListener('click', () => {
	var currentPage = parseInt(page_input.value);
    if(page_input.value > 1) {
        page_input.value = currentPage-1;
    }
    backFunction(currentPage);
});

function nextFunction(value){
	
	
    if(value == max) {
        nextHider.classList.add("active");
    }
    if(value > 1) {
        prevHider.classList.remove("active");
    }
    
    
}

function backFunction(value){
	
    if(value == 1) {
        prevHider.classList.add("active");
    }
    if(value < max) {
        nextHider.classList.remove("active");
    }
}