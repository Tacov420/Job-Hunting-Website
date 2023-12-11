describe('company test', () => {

    it("login", () => {
      cy.visit("http://localhost:5173/");
  
      cy.get("#username").type("test0");
      cy.get("#password").type("test0");
      //cy.get("[name='password']").type("test");
  
      cy.get("#login").click();
      //cy.visit("http://localhost:5173/home");
      //cy.get('#default-search').type('test');
  
    });


    it("View Company List", () => {
        cy.visit("http://localhost:5173/company_tracking");
        cy.get("#dropdown").should('exist').click();
        cy.get("h2[class='text-xl font-bold text-gray-500 py-3']").should('exist');
    });
    
    it("Search Company-View Result", () => {
        cy.visit("http://localhost:5173/company_tracking");
        cy.get("#dropdown").should('exist').click();
        cy.get("input[id='searchInput']").type('AS');
        cy.get("#ASML").should('exist');
    });
    
    
    it("Search Company-Add Company", () => {
        cy.visit("http://localhost:5173/company_tracking");
        cy.get("#dropdown").should('exist').click();
        cy.get("#ASML").should('exist').click();
    
    });

    it("Search Company-Delete Company", () => {
        
    });
/*
    it("Search Company-Email Notification Setting", () => {
    
    });
    
    it("Add Company From Job Searching", () => {
    
    });*/
})