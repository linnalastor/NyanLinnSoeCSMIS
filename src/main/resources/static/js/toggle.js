let toggle = document.querySelector(".toggle");
let text = document.querySelector(".text");
const myHiddenParam = document.getElementById("myHiddenParam");



/*------------ Check status again when refresh form----- */

if(myHiddenParam.value == 1){
	console.log("on")
	toggle.classList.add("active");
	text.innerHTML = "Get Notification ON";
}else{
	console.log("off")
	toggle.classList.remove("active");
	text.innerHTML = "Get Notification OFF";
}

/*========================================================== */

function myFunction(){
    toggle.classList.toggle("active");

    if(toggle.classList.contains("active")){
        text.innerHTML = "Get Notification ON";
       myHiddenParam.value = 1;
       
		
    }else{
        text.innerHTML = "Get Notification OFF";
         myHiddenParam.value = 0;
    }
}


