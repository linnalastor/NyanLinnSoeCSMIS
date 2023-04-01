const endDate= document.getElementById("endDate");
const lastDate= document.getElementById("lastDate");

const createInvoiceButton = document.getElementById("createInvoiceButton");
console.log(endDate.value,lastDate.value);

let eDate=new Date(endDate.value);
eDate.setHours(1);
eDate.setMinutes(1);
eDate.setSeconds(1);

let lDate=new Date(lastDate.value);
lDate.setHours(1);
lDate.setMinutes(1);
lDate.setSeconds(1);
console.log(eDate>lDate);

if(eDate  <= lDate){
	createInvoiceButton.disabled = false;
}else{
	createInvoiceButton.disabled = true;
}
