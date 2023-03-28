const selectedMeats = document.getElementById('selectedMeats');
let meatArr = [];

function displayData() {
			
			
          var datalist = document.getElementById("datalist");
          var selectedOption = datalist.value;
          var displayDiv = document.getElementById("display");
          var options = document.querySelectorAll('#options option');
          var selectedOptionIndex = -1;
          
          // Find the index of the selected option
          for (var i = 0; i < options.length; i++) {
            if (options[i].value === selectedOption) {
              selectedOptionIndex = i;
              break;
            }
          }
          
          if (selectedOptionIndex !== -1) {
            // Remove the selected option from the datalist
            options[selectedOptionIndex].remove();
            
            // Display the selected option
            displayDiv.innerHTML += "<p>" + selectedOption + " <button type='button' onclick='removeData(this)'><i class='fa-solid fa-xmark'></i></button></p>";
            datalist.value = "";
            
            meatArr.push(selectedOption);
            createMeatListString(meatArr);
          }
        }
        
        function removeData(button) {
          var dataToRemove = button.parentNode;
          var optionValue = dataToRemove.firstChild.nodeValue.trim();
          var options = document.getElementById("options");
          var newOption = document.createElement("option");
          newOption.value = optionValue;
          
          
          meatArr.splice(meatArr.indexOf(optionValue),1)
           createMeatListString(meatArr);
          
          // Remove the selected option from the display
          dataToRemove.parentNode.removeChild(dataToRemove);
          
          // Insert the removed option back into the datalist in ascending order
          var inserted = false;
          for (var i = 0; i < options.options.length; i++) {
            if (optionValue.localeCompare(options.options[i].value) < 0) {
              options.insertBefore(newOption, options.options[i]);
              inserted = true;
              break;
            }
          }
          
          if (!inserted) {
            options.appendChild(newOption);
          }
        }
        
        
        function createMeatListString(meatArr){
			selectedMeats.value = "";
			
			for(let meat of meatArr) {
				selectedMeats.value += meat+",";
			}
		}