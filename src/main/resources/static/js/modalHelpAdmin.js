function openModal() {
	var modal = document.createElement("div");
	modal.setAttribute("class", "modal");
	document.body.appendChild(modal);

	var content = document.createElement("div");
	content.setAttribute("class", "content");
	content.innerHTML = `<h3>How I Can Help You?</h3>
						<button data-close-button onclick="closeModal()" style="position: absolute; top: 0; right: 0; margin-top: 23px; margin-right:30px;" ><i class="fa-solid fa-xmark"></i></button>
						<section class="container my-5" id="maincontent">
                          <section id="accordion">
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary border-top text-decoration-none" aria-controls="faq-17" aria-expanded="false" data-toggle="collapse" href="#faq-17" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Door Access File?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-17">
                              <div class="card card-body border-0 p-0">
                            	 <p><strong>In Door Access File</strong>  contains <b>Choose File Button</b>,<b>Date input field</b> and <b>Import Button</b>.<br> - Choose File button is to choose file that is .csv or .xlsx.<br> - Date inpute field is to choose the date but date must not be imported dates and upcoming dates.<br> - Import Button is to import chose file and selected date.</p>
							</div>
                      	
                              </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary border-top text-decoration-none" aria-controls="faq-18" aria-expanded="false" data-toggle="collapse" href="#faq-18" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Invoice?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-18">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Invoice</strong> contains <b>Create Voucher</b>,<b>Paid Voucher Lists </b>and <b>Details Invoice</b>.</p>
                                
                                <div class="accordion accordion-flush" id="accordionFlushExample">
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingOne">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
        Create Voucher
      </button>
    </h2>
    <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Create Voucher</b> contains create button date input field for payment date, dropdown list for catering service name , dropdown list for payment methods ,dropdown lists for cashier ,dropdown lists for receiver and dropdown lists for approver.<br> - Create Button can create voucher for last unpaid week.<br> - Payment Date input field for created date which cannnot be past date.<br> - Catering service name dropdown list input field is to choose the catering service name.<br> - Payment Method dropdown list input field is to choose the payment methods.<br> - Cashier dropdown list input field is to choose the cashier name.<br> - Receiver dropdown list input field is to choose the receiver name.<br> - Approver  dropdown list input field is to choose the approver name.</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
        Paid Voucher Lists
      </button>
    </h2>
    <div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><strong>In Paid Voucher Lists</strong> contains search input field.It can search everything u desire.</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingThree">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
        Details Invoice
      </button>
    </h2>
    <div id="flush-collapseThree" class="accordion-collapse collapse" aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><strong>In Details Invoice</strong> contains two search button.<br> - First search button is to search detail invoice by voucher number.<br> - Second search button is to search details invoice between two dates.</div>
    </div>
  </div>
</div>
                                
                              </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary border-top text-decoration-none" aria-controls="faq-19" aria-expanded="false" data-toggle="collapse" href="#faq-19" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Menu?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-19">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Menu</strong> contains <b>Choose File Button</b> and <b>Import Menu Button</b>.<br> - Choose File button is to choose the file that must be <b>.pdf</b>.<br> - Import Menu Button is to import the chose file.</p>
                              </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-20" aria-expanded="false" data-toggle="collapse" href="#faq-20" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Today Lunch Registered List?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-20">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Today Lunch Registered List</strong> contains <b>Search Button</b> and <b>Avoid Meat Lists</b>.<br> - Search Button can be serach today register list.<br> - Avoid Meat Lists shows avoid meat lists.</p>
                                </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-21" aria-expanded="false" data-toggle="collapse" href="#faq-21" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Register List?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-21">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Register List</strong> contain <b>Weekly</b> and <b>Monthly</b> Buttons.</p>
                                
                                <div class="accordion accordion-flush" id="accordionFlushExample">
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingOne">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
        Weekly
      </button>
    </h2>
    <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Weekly</b> contains Search Button and Update Button.<br> - Search Button can search everything u desire.<br> - Update Button is appear when u clicked humbuger button.When u click Update Button,modal pop up is appear and can update green(eat) or red(not eat) per week.</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
        Monthly
      </button>
    </h2>
    <div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Monthly</b> contains Search Button and Update Button.<br> - Search Button can search everything u desire.<br> - Update Button is appear when u clicked humbuger button.When u click Update Button,modal pop up is appear and can update green(eat) or red(not eat) per month.</div>
    </div>
  </div>
  </div>
                                
								</div>
                            </div>
                            
                             <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-22" aria-expanded="false" data-toggle="collapse" href="#faq-22" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Report List?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-22">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Report List </strong> contains <b>Yesterday</b> ,<b>Last Week</b> adnd <b>Last Month</b>.</p>
                                
                                <div class="accordion accordion-flush" id="accordionFlushExample">
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingOne">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
        Yesterday
      </button>
    </h2>
    <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Yesterday</b> contains Search Button and Update Button.<br> - Search Button can search everything u desire.<br> - Update Button is appear when u clicked humbuger button.When u click Update Button,modal pop up is appear and can update green(eat) or red(not eat) per week.</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
        Last Week
      </button>
    </h2>
    <div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Last Week</b> contains Search Button and Update Button.<br> - Search Button can search everything u desire.<br> - Update Button is appear when u clicked humbuger button.When u click Update Button,modal pop up is appear and can update green(eat) or red(not eat) per month.</div>
    </div>
  </div>
   <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingThree">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
        Last Month
      </button>
    </h2>
    <div id="flush-collapseThree" class="accordion-collapse collapse" aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Yesterday</b> contains Search Button and Update Button.<br> - Search Button can search everything u desire.<br> - Update Button is appear when u clicked humbuger button.When u click Update Button,modal pop up is appear and can update green(eat) or red(not eat) per week.</div>
    </div>
  </div>
 </div>
                                
								</div>
                            </div>
                            
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-23" aria-expanded="false" data-toggle="collapse" href="#faq-23" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Operator Level Dashboard?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-23">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Operator Level Dashboard</strong> admin can go to operator dashboard.</p>
								</div>
                            </div>
                            
                             <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-24" aria-expanded="false" data-toggle="collapse" href="#faq-24" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Staff Management?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-24">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Staff Management</strong> contains <b>Choose File Button</b> ,<b>Import Button</b> ,<b>Add New Staff Button</b> and <b>Search Button</b>.</p>
                                
                                <div class="accordion accordion-flush" id="accordionFlushExample">
    <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingOne">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
        Choose File Button
      </button>
    </h2>
    <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Choose File</b> choose .csv or .xlsx staff file</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
        Import Button
      </button>
    </h2>
    <div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Import Button</b> can import chose file.</div>
    </div>
  </div>
   <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingThree">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
        Add New Staff
      </button>
    </h2>
    <div id="flush-collapseThree" class="accordion-collapse collapse" aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Add New Staff</b> admin can add new single staff.</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingFour">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseFour" aria-expanded="false" aria-controls="flush-collapseFour">
        Search Button
      </button>
    </h2>
    <div id="flush-collapseFour" class="accordion-collapse collapse" aria-labelledby="flush-headingFour" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Search Button </b> can search everything u desire.</div>
    </div>
  </div>
 </div>
 </div>
                                
								</div>
                            </div>
                          
                          </section>
                        </section>`;
	modal.appendChild(content);
}

function closeModal() {
  var modal = document.querySelector(".modal");
  modal.parentNode.removeChild(modal);
}