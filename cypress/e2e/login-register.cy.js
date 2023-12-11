
describe("Demo", () => {

 
  it("toRegister", () => {
    cy.visit("http://localhost:5173/");

    cy.get('#toRegister').click();

  });

  it("Register", () => {
    cy.visit("http://localhost:5173/register");

    cy.get('#username').type('test');
    cy.get('#password').type('test');
    cy.get('#confirmPassword').type('test');
    cy.get('#continue').click();
    cy.get('#toLogin').click();

  });

  it("Preference", () => {
    cy.visit("http://localhost:5173/preference");

    //cy.get('#username').type('test');
    cy.get('#jobTitle').type('testJobTitle');
    cy.get('#jobTitleBtn').click();

    cy.get('#location').type('testLocation');
    cy.get('#locationBtn').click();

    cy.get('#company').type('testCompany');
    cy.get('#companyBtn').click();

    cy.get('#skills').type('testSkills');
    cy.get('#skillsBtn').click();

    cy.get('#continue').click();

  });

  it("Verification", () => {
    cy.visit("http://localhost:5173/verification");

    cy.get('#email').type('test@gmail.com');
    cy.get('#sendVerification').click();
    cy.get('#verificationCode').type('123456');
    cy.get('#continue').click();

  });



   it("loginToProfile", () => {
    cy.visit("http://localhost:5173/");

    cy.get("#username").type("test0");
    cy.get("#password").type("test0");

    cy.get("#login").click();
    cy.get("#profileBtn").click();
    cy.get("#toPersonalInfo").click();
    cy.get("#toPreference").click();
    
    cy.get("#toPersonalInfo").click();
    cy.get("#username").should("have.value", "test0");
    cy.get("#email").should("have.value", "b09902074@csie.ntu.edu.tw");
    cy.get("#edit-button").click();
    cy.get("#password").type("test0");
    cy.get("#confirm-password").type("test0");
    cy.get("#save-button").click();
    cy.get("#edit-button").click();
    cy.get("#cancel-button").click();

    cy.get("#toPreference").click();
    cy.get("#edit-button").click();
    cy.get("#skills").type(",B");
    cy.get("#desiredJobs").type(",C");
    cy.get("#desiredLocations").type(", D ");
    cy.get("#save-button").click();
    cy.get("#edit-button").click();
    cy.get("#cancel-button").click();


    cy.get("#homeBtn").click();

    cy.select("#select-level").select("0");

    //cy.get("div[class='items-center mx-24 px-10 mt-6']").type("Front-end Developer");
    //cy.get("").type("Software Engineer");
    //cy.get("#level").select("1");


  });


  // it("HomePage", () => {
  //   cy.visit("http://localhost:5173/home");

  //   cy.get('.searchJobTitle').type('Analysis');
    
  //  // cy.get('#small');

  // });

 




})