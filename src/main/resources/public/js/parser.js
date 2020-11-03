$(document).ready(function() {

	//Handle 'Browse' button click and open file explorer
	$('#btn-browse').click(function() {
		$('#browser').click();
	});

	//Take the selected file, read the content and try to parse it to JSON
	$('#browser').change(function () {
	  if (this.files && this.files[0]) {
	  	var fileExtension = this.files[0].name.split(".").pop();

	    var reader = new FileReader();
	    reader.addEventListener("load", function (e) {
	      var fileContent = e.target.result;

	      var jsonObject = {}
	      if (fileExtension.toLowerCase() === "json") {
	      	jsonObject.employees = validParsedJSON(JSON.parse(fileContent).employees);
	      	sendSearchRequestToTheServer(jsonObject);
	      } else if (fileExtension.toLowerCase() === "txt" || fileExtension.toLowerCase() == "csv") {
	      	jsonObject.employees = validParsedCSV(Papa.parse(fileContent).data);
	      	sendSearchRequestToTheServer(jsonObject);
	      } else {
	      	console.log("Not supported file extension!");
	      }
	    });
	    
	    reader.readAsBinaryString(this.files[0]);
	  }   
	});

});

//Fully CSV parse
function validParsedCSV(listOfEmployees) {
	var employees = []

	listOfEmployees.forEach((element, index) => {
		if (index != 0) {
			insertValidEmployee(employees, element[0], element[1], element[2], element[3]);
		}
	});

	return employees;
}

//Fully JSON parse
function validParsedJSON(listOfEmployees) {
	var employees = []

	listOfEmployees.forEach(function(element) {
		insertValidEmployee(employees, element.empId, element.projectId, element.dateFrom, element.dateTo);
	});

	return employees;
}

//Insert employee in employees array
function insertValidEmployee(employees, empId, projectId, dateFrom, dateTo) {
	var inputEmpId = empId;
	var inputProjectId = projectId;
	var inputDateFrom = dateFrom;
	var inputDateTo = dateTo;
	
	inputEmpId = returnValueOrNull(inputEmpId);
	inputProjectId = returnValueOrNull(inputProjectId);
	inputDateFrom = returnValueOrNull(inputDateFrom);
	inputDateTo = returnValueOrNull(inputDateTo);

    var employee = {empId: inputEmpId, projectId: inputProjectId, dateFrom: inputDateFrom, dateTo: inputDateTo};
    employees.push(employee);
}

//Check for NULL values
function returnValueOrNull(inputValue) {
	var value = inputValue;
	value = value.trim();
	if (value.toUpperCase() === "NULL") {
		return null;
	} else {
		return value;
	}
}

//Send POST search request to the server, take response and populate the table
function sendSearchRequestToTheServer(jsonObject) {
	$.ajax({
		method: "POST",
		url: "http://localhost:8080/api/v1/employees/retrieve-the-longest-cooperation",
		accept: "application/json; charset=utf-8",
		contentType: "application/json; charset=utf-8",
        dataType: "JSON",
        data: JSON.stringify(jsonObject),
		success: function(response) {
            response.forEach(function(element) {
                $('#table').find('tbody').append("<tr><td>"
                    + element.firstEmployee.empId + "</td><td>"
                    + element.secondEmployee.empId + "</td><td>"
                    + element.projectId + "</td><td>"
                    + element.collaborationDuration + "</td></tr>"
                );
            });
		}
	});
}