
describe("Demo", () => {

  it("login", () => {
    cy.visit("http://localhost:5173/");

    cy.get("#username").type("test");
    cy.get("#password").type("test");
    //cy.get("[name='password']").type("test");

    cy.get("#login").click();

  });

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

  it("HomePage", () => {
    cy.visit("http://localhost:5173/Home");

    cy.get('#default-search').type('testJobTitle');
    
   // cy.get('#small');

  });

  it("Profile", () => {
    cy.visit("http://localhost:5173/profile/personal_info");

    cy.get('#username');
    cy.get('#email');
    cy.get('#edit-button').click();
    cy.get('#cancel-button').click();
    cy.get('#edit-button').click();
    cy.get('#password').type('test');
    cy.get('#confirmPassword').type('test');
    cy.get('#save-button').click();

  });




})