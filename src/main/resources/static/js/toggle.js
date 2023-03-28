// JavaScript code to handle the toggle switch event
		var toggleSwitch = document.getElementById("toggle-switch");
		
		toggleSwitch.addEventListener('change', function() {
			var isChecked = this.checked;
			//console.log("Toggle switch is " + (isChecked ? "on" : "off"));
			// Send HTTP request to server to update database
			// ...
			var xhttp = new XMLHttpRequest();
			xhttp.open("GET", "/toggle-switch?isChecked=" + isChecked, true);
			xhttp.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");
			xhttp.send("isChecked=" + isChecked); 
		});
		
		