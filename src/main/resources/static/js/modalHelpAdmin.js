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
                                  What about  Admin Dashboard?
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
        What about Menu?
      </button>
    </h2>
    <div id="collapseOne" class="accordion-collapse collapse show" aria-labelledby="headingOne" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Menu</strong> admin can add menu list from restaurant.
      </div>
    </div>
  </div>
  
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingTwo">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
        What about Registerd List?
      </button>
    </h2>
    <div id="collapseTwo" class="accordion-collapse collapse" aria-labelledby="headingTwo" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Registered List</strong> admin can watch details of registered list.
      </div>
    </div>
  </div>
  
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingThree">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
        What about Report List?
      </button>
    </h2>
    <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Report List</strong>. admin can watch operator's report.
      </div>
    </div>
  </div>
  
   <div class="accordion-item">
    <h2 class="accordion-header" id="headingFour">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
        What about Staff List?
      </button>
    </h2>
    <div id="collapseFour" class="accordion-collapse collapse" aria-labelledby="headingFour" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Staff List</strong> admin can watch staff list.
      </div>
    </div>
  </div>
  
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingFive">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
        What about Staff List?
      </button>
    </h2>
    <div id="collapseFive" class="accordion-collapse collapse" aria-labelledby="headingFive" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Staff List</strong> admin can watch staff list.
      </div>
    </div>
  </div>
  
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingSix">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
        What about Invoice?
      </button>
    </h2>
    <div id="collapseSix" class="accordion-collapse collapse" aria-labelledby="headingSix" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Invoice </strong> admin can watch details of invoice.
      </div>
    </div>
  </div>
  
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingSeven">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseSeven" aria-expanded="false" aria-controls="collapseSeven">
        What about Master Data Setup?
      </button>
    </h2>
    <div id="collapseSeven" class="accordion-collapse collapse" aria-labelledby="headingSeven" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Master Data Setup</strong> admin can add master data setup and aslo update.
      </div>
    </div>
  </div>
  
  <div class="accordion-item">
    <h2 class="accordion-header" id="headingEight">
      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseEight" aria-expanded="false" aria-controls="collapseEight">
        What about Email?
      </button>
    </h2>
    <div id="collapseEight" class="accordion-collapse collapse" aria-labelledby="headingEight" data-bs-parent="#accordionExample">
      <div class="accordion-body">
        <strong>In Email</strong> admin can watch staffs who get email notification.
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
                                <p><strong>In Menu</strong> admin can add menu list from restaurant.</p>
                              </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary border-top text-decoration-none" aria-controls="faq-19" aria-expanded="false" data-toggle="collapse" href="#faq-19" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Registered List?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-19">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Registered List</strong> admin can watch details of registered list.</p>
                              </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-20" aria-expanded="false" data-toggle="collapse" href="#faq-20" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Report List?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-20">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Report List</strong>. admin can watch operator's report.</p>
                                </div>
                            </div>
                      
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-21" aria-expanded="false" data-toggle="collapse" href="#faq-21" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Staff List?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-21">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Staff List</strong> admin can watch staff list.</p>
								</div>
                            </div>
                            
                             <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-22" aria-expanded="false" data-toggle="collapse" href="#faq-22" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Invoice?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-22">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Invoice </strong> admin can watch details of invoice.</p>
								</div>
                            </div>
                            
                            <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-23" aria-expanded="false" data-toggle="collapse" href="#faq-23" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Master Data Setup?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-23">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Master Data Setup</strong> admin can add master data setup and aslo update.</p>
								</div>
                            </div>
                            
                             <a class="py-3 d-block h-100 w-100 position-relative z-index-1 pr-1 text-secondary  border-top text-decoration-none" aria-controls="faq-24" aria-expanded="false" data-toggle="collapse" href="#faq-24" role="button">
                              <div class="position-relative">
                                <h2 class="h4 m-0 pr-3">
                                  What about Email?
                                </h2>
                                <div class="position-absolute top-0 right-0 h-100 d-flex align-items-center">
                                  <i class="fa fa-plus"></i>
                                </div>
                              </div>
                            </a>
                            <div class="collapse" id="faq-24">
                              <div class="card card-body border-0 p-0">
                                <p><strong>In Email</strong> admin can watch staffs who get email notification.</p>
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
