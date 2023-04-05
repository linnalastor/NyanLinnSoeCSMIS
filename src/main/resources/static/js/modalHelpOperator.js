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
                                  What about Menu?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-17">
                              <div class="card card-body border-0 p-0">	
								<p><strong>In Menu</strong> you can watch current week menu lists and next week menu lists from restuarant.</p>
                              </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary border-top text-decoration-none" aria-controls="faq-18" aria-expanded="false" data-toggle="collapse" href="#faq-18" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Lunch Plan?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-18">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Lunch Plan</strong> contains <b>Monthly Button</b> and <b>Weekly Button</b>.</p>
                                
                                <div class="accordion accordion-flush" id="accordionFlushExample">
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingOne">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
        Monthly 
      </button>
    </h2>
    <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Monthly</b> contains update button.When u clicked update button,modal pop up will appear and u can select eat(green) or not eat(eat) per month.</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
        Weekly
      </button>
    </h2>
    <div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Weekly</b> contains update button.When u clicked update button,modal pop up will appear and u can select eat(green) or not eat(eat) per week.</div>
    </div>
  </div>
 </div>
                                
                              </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary border-top text-decoration-none" aria-controls="faq-19" aria-expanded="false" data-toggle="collapse" href="#faq-19" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Report?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-19">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Report</strong> contains <b>Yesterday</b> ,<b>Last Week</b> and <b>Last Month</b> buttons.</p>
                                
                                <div class="accordion accordion-flush" id="accordionFlushExample">
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingOne">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
        Yesterday
      </button>
    </h2>
    <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body">Coming...</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
       Last Week
      </button>
    </h2>
    <div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body">Coming..</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingThree">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
        Last Month
      </button>
    </h2>
    <div id="flush-collapseThree" class="accordion-collapse collapse" aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body">Coming...</div>
    </div>
  </div>
</div>
                                
                              </div>
                            </div>
                          
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-21" aria-expanded="false" data-toggle="collapse" href="#faq-21" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Account Status?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-21">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Account Status</strong> contains <b>Email Notification Button</b> ,<b>Avoid Meat List Button</b> and <b>Update Password Button.</b></p>
                                
                                <div class="accordion accordion-flush" id="accordionFlushExample">
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingOne">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseOne" aria-expanded="false" aria-controls="flush-collapseOne">
        Email Notification Button
      </button>
    </h2>
    <div id="flush-collapseOne" class="accordion-collapse collapse" aria-labelledby="flush-headingOne" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>Email Notification Button</b> , u can ON or OFF toggle switch button.If u are ON notifications can received weekly remainder or Announcement from DAT Admin.</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseTwo" aria-expanded="false" aria-controls="flush-collapseTwo">
       Avoid Meat List
      </button>
    </h2>
    <div id="flush-collapseTwo" class="accordion-collapse collapse" aria-labelledby="flush-headingTwo" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>Avoid Meat List</b> contains update button.If u have avoid meat,u can click update button.When u click the update button,available meats from restaurant is show in text field and select u want to avoid meat and click right arrow icon and Save changes.</div>
    </div>
  </div>
  <div class="accordion-item">
    <h2 class="accordion-header" id="flush-headingThree">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#flush-collapseThree" aria-expanded="false" aria-controls="flush-collapseThree">
        Update Password
      </button>
    </h2>
    <div id="flush-collapseThree" class="accordion-collapse collapse" aria-labelledby="flush-headingThree" data-bs-parent="#accordionFlushExample">
      <div class="accordion-body"><b>In Update Password</b> ,contains <b>Old Password</b>, <b>New Password</b> and <b>Confirm New Password</b>.Firstly u will fill your old password.If your old password is wrong u can't change new password.After correct your old password,u can fill your new password and confirm new password.If your new password and confirm new password is not match,u can't click update password.Please do step by steps</div>
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
