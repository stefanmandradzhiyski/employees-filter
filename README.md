# employees-filter

Tech stack: Java 11, Spring, JavaScript, HTML, CSS, Gradle

Additional tools/libraries: Lombok, Mapstruct, Swagger, jQuery, PapaParse

Employees filter is a simple WEB application whose purpose is to find a pair of employees who have worked together for the longest time on common projects.
Users can select a TXT, CSV, or JSON file from their system and pass it to the server. The file regardless of the extension must contain fields/columns: 
- EmpID of type number, ProjectID of type number, DateFrom of type date, DateTo of type date.

A file structure example can be found in the "examples" directory.

How to setup:
- Import the project as Gradle one;
- Apply Java 11 SDK;
- Change Gradle build tool settings to build and run with IntelliJ IDEA;
- Run EmployeesFilterApplication;
- Use localhost:8080 in your browser and test the app.

API:
- api/v1/employees/retrieve-the-longest-cooperation

This endpoint expects a JSON object which contains a list of employees with a size between 2 and 256. Employee properties are empId, projectId, dateFrom and dateTo.
The only not required field is 'dateTo' - if its NULL the application is going to use today date. 
