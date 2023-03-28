const checkboxes = document.querySelectorAll('#00');
    checkboxes.forEach(checkbox => {
    	  checkbox.disabled = true;
    	  checkbox.style.backgroundColor = 'grey';
    	});