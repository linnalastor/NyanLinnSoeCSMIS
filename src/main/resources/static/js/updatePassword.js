const newPassword = document.getElementById('newPassword');
const confirmPassword= document.getElementById('confirmPassword');

const updatePasswordBtn = document.querySelector('.updatePasswordBtn');

newPassword.addEventListener('input', (e) => {
	if(e.currentTarget.value != "" && (e.currentTarget.value == confirmPassword.value)){
		updatePasswordBtn.disabled = false;
		console.log("match");
	}else{
		console.log("not match");
		updatePasswordBtn.disabled = true;
	}
});

confirmPassword.addEventListener('input', (e) => {
	if(e.currentTarget.value != "" && (e.currentTarget.value == newPassword.value)){
		console.log("match");
		updatePasswordBtn.disabled = false;
	}else{
		console.log("not match");
		updatePasswordBtn.disabled = true;
	}
});