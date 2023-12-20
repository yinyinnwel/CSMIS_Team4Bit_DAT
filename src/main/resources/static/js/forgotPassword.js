const email_input = document.getElementById("email_input");

const countdownShowHidden = document.getElementById("countdownShowHidden");

const matchOTPBtn = document.getElementById("matchOTPBtn");
const otpInput = document.getElementById("otpInput");
const coverPassword_container = document.querySelector(".coverPassword_container");

const otpTempHidden = document.getElementById("otpTempHidden");

const sendOTP_cover = document.querySelector(".sendOTP_cover");

const invalidStaffId = document.getElementById("invalidStaffId");

var otp = parseInt(otpTempHidden.value)-146905;



/*========== If provided Staffid is invalid---------- */

if (invalidStaffId.innerText == "Invalid Staff Id!") {
	otpInput.value = otp;
	otpInput.disabled = true;
	matchOTPBtn.disabled = true;
	coverPassword_container.classList.add("close");
	email_input.readOnly = true;
	sendOTP_cover.classList.add("show");
	otpTempHidden.value = "There is  no otp!What are u looking for?";
}

/*----- Check Email is valid or not ------------ */

function isValidEmail(email) {
  // Regular expression for email validation
  var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  
  // Test the email against the regular expression and return true if it matches
  return emailRegex.test(email);
}

email_input.addEventListener("input", (e) => {
	if (isValidEmail(e.target.value)) {
		sendOTP_cover.classList.remove("show");
		/*console.log("Email is valid");*/
	} else {
		sendOTP_cover.classList.add("show");
		/*console.log("Email is not valid");*/
	}
});


/* ------------- Send OTP Btn click Function -----------------*/

const sendOTP_btn = document.getElementById("sendOTP_btn");

if(countdownShowHidden.value == 'yes'){
	countDownFunction();
}


/* ---------count down function---------- */
function countDownFunction() {
  let countdowntime = 15;

  sendOTP_cover.classList.add("show");

  const countdownElement = document.querySelector(".countdownLabel");

  countdownElement.classList.add("show");

  const countdownInterval = setInterval(() => {
    countdowntime--;

    countdownElement.innerHTML = "Count down "+countdowntime+"s";
    
	  matchOTPBtn.addEventListener("click", () => {
		  if (otpInput.value == otp) {
			  otpInput.disabled = true;
			  matchOTPBtn.disabled = true;
			  coverPassword_container.classList.add("close");
			  email_input.readOnly = true;
			  //--- Clear interval
			  clearInterval(countdownInterval);
			  countdownElement.classList.remove("show");
			  sendOTP_cover.classList.add("show");
			  otpTempHidden.value = "There is  no otp!What are u looking for?";
		  }
	  });

    if (countdowntime == -1) {
      clearInterval(countdownInterval);
      countdownElement.classList.remove("show");
      sendOTP_cover.classList.remove("show");
    }
  }, 1000);
}

/* ----------- Match OTP Function -------------------- */

matchOTPBtn.addEventListener("click", () => {
  if (otpInput.value == otp) {
    otpInput.disabled = true;
    matchOTPBtn.disabled = true;
    coverPassword_container.classList.add("close");
    email_input.readOnly = true;
    sendOTP_cover.classList.add("show");
    otpTempHidden.value = "There is  no otp!What are u looking for?";
  }
});


/* ---------- new password and confirm password match Function */
const staffId = document.getElementById("staffId");
const new_password = document.getElementById("new_password");
const confirm_password = document.getElementById("confirm_password");

const changePasswordBtn = document.getElementById("changePasswordBtn");

staffId.addEventListener("input",(e) => {
    if(e.target.value !='' && (new_password.value==confirm_password.value) && (confirm_password.value!='' && new_password.value!='')){
        changePasswordBtn.disabled = false;
    }else{
        changePasswordBtn.disabled = true;
    }
});

new_password.addEventListener("input",(e) => {
    if(e.target.value == confirm_password.value && (e.target.value!='' && confirm_password.value!='' && staffId.value!='')){
        changePasswordBtn.disabled = false;
    }else{
        changePasswordBtn.disabled = true;
    }
});

confirm_password.addEventListener("input",(e) => {
    if(e.target.value == new_password.value && (e.target.value!='' && new_password.value!='' && staffId.value!='')){
        changePasswordBtn.disabled = false;
    }else{
        changePasswordBtn.disabled = true;
    }
});