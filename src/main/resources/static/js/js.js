//----------------- Check internet Connection online or offline -------------
const connectionContainer = document.querySelector('.connectionContainer');
const connection_status = document.getElementById('connection_status');

if(!navigator.onLine){
	connection_status.innerText = 'No Connection';
	connectionContainer.classList.remove('online');
	connectionContainer.classList.add('offline');
}

window.addEventListener('online', ()=>{
	connection_status.innerText = 'Back Online';
	connectionContainer.classList.remove('offline');
	connectionContainer.classList.add('online');
})
window.addEventListener('offline', ()=>{
	connection_status.innerText = 'No Connection';
	connectionContainer.classList.remove('online');
	connectionContainer.classList.add('offline');
})




const drawerContainer = document.querySelector(".drawer-container");
const drawerToggle = document.querySelector(".toggle_icon");
const nav = document.querySelector(".nav_container");
const profile = document.querySelector(".navi_profile_container");
const center = document.querySelector(".center_container");

const contents = document.querySelectorAll('.content');

drawerToggle.addEventListener("click", function () {
  drawerContainer.classList.toggle("close");
  nav.classList.toggle("close");
  center.classList.toggle("width")
});

profile.addEventListener("click", () => {
  profile.classList.toggle("open");
}); 


for(var content of contents) {
  content.addEventListener('click', (e) => {
    e.currentTarget.classList.add('selected');
    for(var c of contents) {
      (e.currentTarget != c) && c.classList.remove('selected');
    }
  });
}


// window.addEventListener("click", (e) => {
//   try {
//     var option1 = e.target.classList.contains("navi_profile_container");
//     var option2 = e.target.parentNode.classList.contains("navi_profile_container");

//     if (!option1 || !option2) {
//       if (profile.classList.contains("open")) {
//         profile.classList.remove("open");
//       } else {
//       }
//     }
//   } catch (error) {}
//   //console.log(e.target.parentNode.classList.contains('navi_profile_container'));
// });
