# Counseling project: Java-REST backend with double frontend -> Angular 7 SPA dashboard, and a Primefaces 7 dashboard, built into a single runnable JAR file

This project was an experiment.  
**To integrate all kinds of technologies and build the fully functional whole system into a single runnable JAR file, that can demonstrate, these technologies can indeed work together flawlessly**.  
The experiment was successful.    
  
The project was built around a fictional Counseling business concept, called Szabo Counseling. 
  
As this project **never intended to be handled by a real customer**, therefore  
**not all validations implemented, and not all exceptions handled appropriately**,  
and also while trying to make the application as nice as possible within the time boundaries,  
**the design was not the focus of this project either**.    
  
While checking whether the telephone number has a letter in it, or to align everything pixel-wise are also have their fair share of challanges, the integration and configuration tasks offered much more satisfaction for me :o) 

The main page's initial HTML/CSS/JS skeleton were downloaded as a free source from:  
https://www.free-css.com/free-css-templates/page215/doctor  
This template then got a complete redesign as well as an Angular Router to work as an SPA.  
  
For both logged-in dashboards, a skeleton _ menu _ HTML/CSS/JS used, downloaded from a free HTML template provider site.  
  
  
Then it too got a complete redesign, with Angular Router to work as an SPA, and with Angular Material and custom HTML/CSS/JS **on the Patient dashboard side**.  
The **Employee dashboard side** got redesigned as well, with custom HTML/CSS/JS, with Primefaces 7 and JSF templating technology.  
  
Tests only written for the DAOs.
 

# Used technologies:  
**Backend:**  
  - Java 8
  - Spring 5
  - Spring Security 5
  - Hibernate 5 with jpamodelgen
  - h2 1.4, in-memory database
  - Jersey 2, JAX-RS reference implementation
  - Jackson 2, JSON provider
  - Jetty 9, in-memory web server
  - itextpdf 5, for PDF building and generation  
  
**Frontend:**
  - Angular 7.0.6, for the Patient dashboard
  - Typescript 3.1.6, for the Patient dashboard
  - RXJS 6.3.3, for the Patient dashboard
  - Angular Material, for the Patient dashboard
  - JSF 2, the Mojarra reference implementation, for the Employee dashboard
  - Primefaces 7, JSF component library, for the Employee dashboard
  - HTML, CSS, Javascript (jQuery), Bootstrap
  
**Misc:**
  - Maven build tool
  - NPM  6.4.1 package manager  
  - Node.js 11.2.0, integrated web server for Angular
  - Angular CLI 7.0.6, for Angular command-line
  - maven-shade-plugin to build the JAR
  - exec-maven-plugin to run the Angular build as a part of the Frontend build  
  - JUnit
  - GIT
    
   **IDEs:**
   - Eclipse, for the Backend and the Employee dashboard
   - Visual Studio Code, for the Patient dashboard and the Main site
  
    

# Available functionalities
- Able to login/logout to the system, both as Patient and as Employee/Counselor TO/FROM their respective dashboard.
- Protect the different pages and services available.
- As an Employee, be able to register both Patients and Employees. 
    - For a Patient, be able to assign a Counselor.
    - For a Counselor, be able to assign a list of Patients, as well as a Manager, if needed.
- As an Employee be able to issue and archive Bookings/Appointments.
    - When booking an appointment, be able to see if the choosen user, for that specific day has any other bookings.
    - For all bookings, if they happened in the past, the respective Employee is required to submit a report, 
      which is a short summary of what happened during the meeting. 
        - The generated PDF report will default contain:
          - All sections designed and alligned.
          - The details of the booking. 
          - The participants and their details and response to the appointment.
          - At the header, the fictional company's logo and name.
          - At the footer, again, the company name, AND the "actual page/all page" counter.
          - And of course, the summary, written by the Employee.
     - The report then can be downloaded by the respective Employee.
     
- All Users be able to Accept or Reject bookings, NO_RESPONSE being as the default response.
- All Users receive invitations real time.
- All accepted invitations highlight the selected day in the calendar for both parties.
- Be able to shutdown the running in-memory web server from the webpage. 

**Tested & works on:**  
 - Chrome Version 74.0.3729.169 (Official Build) (64-bit)  
 - Microsoft EdgeHTML 17.17134  
 - Firefox Version 67.0.1 (32-bit)
  
 # To run the project:   
   
 Make sure to have the above mentioned versions AND a full Angular development environment.  
 Make sure to run `NPM update`.  
 Make sure to also install Angular Material from NPM.  
 Make sure to set the correct path in the `/Frontend/src/main/resources/angular_build.bat` file!    
 
 Build the Backend with `mvn clean install`, then the Frontend, with appropriate `angular_build.bat` setup.  
 It should also build the Angular part as well in to the Frontend/Resources folder,  
 that Maven will package together to a JAR file in the target folder with the rest of the compiled sources.  
   
     
The JAR will be named `Frontend-0.0.1-SNAPSHOT-shaded.jar` by default.
