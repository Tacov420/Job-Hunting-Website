describe('progress test', () => {

  it("login", () => {
    cy.visit("http://localhost:5173/");

    cy.get("#username").type("test0");
    cy.get("#password").type("test0");
    //cy.get("[name='password']").type("test");

    cy.get("#login").click();
    //cy.visit("http://localhost:5173/home");
    //cy.get('#default-search').type('test');

  });

  it("View Progress List", () => {
    cy.visit("http://localhost:5173/progress_tracking");
    cy.get("table[class='w-full text-lg text-base/loose text-left text-gray-500 ']").should("exist");

  });

  
  it("Add Progress", () => {
    cy.visit("http://localhost:5173/progress_tracking");
    cy.get('#addJob').click();
    cy.get('#NewJobDialog').should("exist");
    cy.get("#companyInput").should('exist');
    cy.get("#jobtitleInput").should('exist');
    cy.get("#stageInput").should('exist');
    cy.get("#DateInput").should('exist');
    cy.get("button[type='submit']").should('exist');

  });

  
  it("Edit Progress-Add Stage", () => {
    cy.visit("http://localhost:5173/progress_tracking");
    cy.get('#addJob').click();
    cy.get("#companyInput").type("麥當勞");
    cy.get("#jobtitleInput").type("MA");
    cy.get("#stageInput").type("一面");
    cy.get("#DateInput").type("2023-12-11");
    cy.get('#select').select("2");
    cy.get("button[type='submit']").should('exist').click();
    cy.get("tr[id='麥當勞']").should('exist');
    cy.get('#addJob').click();
    cy.get("#companyInput").type("肯德基");
    cy.get('#cancel').click();
    cy.get("tr[id='肯德基']").should('not.exist');
  });

   
  it("Edit Progress-Edit Stage", () => {
    cy.visit("http://localhost:5173/progress_tracking");
    cy.get("tr[id='Line']").should('exist').click();
    cy.visit("http://localhost:5173/progress_tracking/0");
    cy.get("div[class='items-center mx-24 px-10 mt-6']").should('exist');
    //cy.get("#newStatus").should('exist').click();
    cy.get("#0").should('exist').click();
    cy.get('#cancel').click();
  });


  it("Edit Progress-Delete Progress", () => {
    cy.visit("http://localhost:5173/progress_tracking");
    cy.get("button[id='delete-Line']").should('exist').click();
    cy.get("tr[id='Line']").should('not.exist');
  });


})