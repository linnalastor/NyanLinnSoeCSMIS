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
                                  What about Operator Dashboard?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-17">
                              <div class="card card-body border-0 p-0">
                            	
<div class="accordion" id="accordionExample">
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingOne">
      <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
        How Menu Works?
      </button>
    </h2>
    <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Menu</strong> you can watch current week's menu lists from restuarant.
      </div>
    </div>
  </div>
  
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
        How Lunch Plan Works?
      </button>
    </h2>
    <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Lunch Plan</strong> you can update days (monthly) that you don't want to eat lunch.You also can update days (per week) that you dont' want to eat.
      </div>
    </div>
  </div>
  
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingThree">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
        Report
      </button>
    </h2>
    <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Report</strong>.
      </div>
    </div>
  </div>
  
   <div class="accordion-item">
    <h2 class="accordion-header" id="headingFour">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseThree">
        Account Status
      </button>
    </h2>
    <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Account Status</strong> include two main function Description and Update Password.In Description,you can select your avoid meats.Update Password is created for if u want to change new password.
      </div>
    </div>
  </div>
</div>
                      	
                              </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary border-top text-decoration-none" aria-controls="faq-18" aria-expanded="false" data-toggle="collapse" href="#faq-18" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Menu?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-18">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Menu</strong> you can watch current week's menu lists from restuarant.</p>
                              </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary border-top text-decoration-none" aria-controls="faq-19" aria-expanded="false" data-toggle="collapse" href="#faq-19" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Lunch Plan?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-19">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Lunch Plan</strong> you can update days (monthly) that you don't want to eat lunch.You also can update days (per week) that you dont' want to eat.</p>
                              </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-20" aria-expanded="false" data-toggle="collapse" href="#faq-20" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Report?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-20">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Report</strong>.</p>
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
                                <p><strong>In Account Status</strong> include two main function Description and Update Password.In Description,you can select your avoid meats.Update Password is created for if u want to change new password.</p>
								</div>
                            </div>
                      
                            <button data-close-button class="bottom btn btn-primary" onclick="closeModal()" style="margin-right:30px;">Close</button>

                          
                          </section>
                        </section>`;
	modal.appendChild(content);
}

function closeModal() {
  var modal = document.querySelector(".modal");
  modal.parentNode.removeChild(modal);
}
