var names = [ 'gender', 'name', 'age', "seatNo" ];
var seatLayoutPrevUniqueId;
var prevServiceNo;
var prevServiceNoTotSeats;
var oldSelectSeatsUniqueBtnObj

function getValues(serviceNo) {
	// alert("Welcome");
	var tableUniqueId = "passengerDetails-" + serviceNo;
	var table = document.getElementById(tableUniqueId);
	var rowSize = table.rows.length;
	// alert(rowSize+","+table);
	var myMap = {};
	myMap["passName"] = document.getElementById("passengerName-"+serviceNo).value;
	myMap["mobileNo"] = document.getElementById("mobileNo-"+serviceNo).value;
	myMap["emailId"] = document.getElementById("emailId-"+serviceNo).value;
	// alert(JSON.stringify(myMap));
	var ticketRowMap = new Map();
	var ticketMap = new Map();
	for (var row = 1; row < rowSize; row++) {
		var colCount = table.rows[row].cells.length;
		console.log(colCount);
		for (var cell = 0; cell < colCount; cell++) {

			if (cell != 3) {
				// alert(table.rows[row].cells.item(cell).firstChild.value);
				// ticketMap[names[cell]+""+row]=table.rows[row].cells.item(cell).firstChild.value;
				ticketMap[names[cell]] = table.rows[row].cells.item(cell).firstChild.value;
			} else {
				// alert(table.rows[row].cells[cell].innerHTML);
				ticketMap[names[cell]] = table.rows[row].cells[cell].innerHTML;
			}

			document.getElementById("dummy").value = "Test";
		}
		ticketRowMap.set(row, ticketMap);
	}
	myMap["ticketList"] = ticketRowMap;
	var jsonString = JSON.stringify(myMap);

	if (myMap.passName == "" || myMap.mobileNo == "" || myMap.emailId == "") {
		alert("Please enter passaenger details");
		return false;
	}
	for(var tickets of ticketRowMap.values()){
		if(tickets.name=="" || tickets.age=="" || tickets.gender=="Select"){
			alert("Please enter ticket details");
			return false;
		}
	}
	var seatIds= new Array();
	var count=1;
	for(var tickets of ticketRowMap.values()){
		var seatNo=document.getElementById("seats-"+count+"-"+serviceNo).value;
		seatIds.push(seatNo);
		count++;
	}
	
	var url = "http://10.188.31.125:2020/busBookingApp/verifySeatsBlocked/" + seatIds + "/"
	+ serviceNo;
	
	if (window.XMLHttpRequest) {
		request = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		request = new ActiveXObject("Microsoft.XMLHTTP");
	}
	

	try {
		request.onreadystatechange = getInfo;
		request.open("GET", url, false);
		request.send();
		var val = request.responseText;
		 //alert(val);
		if (val == "Blocked" ||  val == "Booked") {
			return false;
		}
		
	} catch (e) {
		alert("Unable to connect to server");
	}


	return true;
}

function loadSeats(seats, totalSeats, serviceNo) {
	// alert("loadSeats Seats ,totalSeats "+seats+","+totalSeats);
	if (seats === undefined || seats == "#")
		return;
	var str_array = seats.split("#");
	// alert(str_array.length);
	for (var i = 0; i < str_array.length; i++) {
		if (str_array[i] != "") {
			var btnId = document.getElementById(str_array[i]);
			// alert("welcome "+btnId+", "+str_array[i]);
			selectSeat(str_array[i], totalSeats, serviceNo);
		}
	}
}

var seats = 0;
var totalPrice = 0;
function selectSeat(id, totalSeats, serviceNo) {
	// var obj = JSON.parse(id);
	// alert("selectSeat id,total "+id+","+totalSeats)

	var btnUniqueId = id + "-" + serviceNo;
	var btnId = document.getElementById(btnUniqueId);
	// alert(btnId.className);
	if (btnId.className == "btnDGray" || btnId.className == "btnDBlocked"
			|| btnId.className == "btnDYellow")
		return;

	// alert("selectSeat"+id);
	// alert("selectSeat"+btnId.className);
	// alert("serviceNo" + serviceNo);
	var tableUniqueId = "passengerDetails-" + serviceNo;
	var priceUniqueId = "totalPrice-" + serviceNo;
	var genderUniqueId = "genderAppendDiv-" + serviceNo;
	var selectedSeatsUniqueId = "selectedSeats-" + serviceNo;
	var url;
	var table = document.getElementById(tableUniqueId);
	var ticketPrice = document.getElementById(priceUniqueId).value;
	if (btnId.className == "btnDAvl") {
		seats++;
		totalPrice = totalPrice + Number(ticketPrice);
		url = "http://10.188.31.125:2020/busBookingApp/selectSeats/" + id + "/"
				+ serviceNo;
		var rowSize = table.rows.length;
		if (rowSize > 5) {
			alert("Maximum 5 seats select");
			return;
		}
		// alert(rowSize);
		var row = table.insertRow(rowSize);
		// alert(btnId.title);
		var paramNames = [ "gender[]", "name[]", "age[]", "ticketId[]" ];
		var myDiv = document.getElementById(genderUniqueId);
		var array = [ "Select", "Male", "Female" ];
		var cell1 = row.insertCell(0);
		var genderCom = document.createElement("select");
		genderCom.id = "gender-"+serviceNo;
		myDiv.appendChild(genderCom);

		if (id > 1 && id < totalSeats) {
			if (btnId.title != "") {
				// First & Last row selected
				// 1,4,5,8,9,12,13,16,17,20,21,24,25,28,29,32,33,36,37,40,41,44,45,48
				// etc
				var startEndBtns = btnId.title;
				var isStartEnd = startEndBtns.indexOf("U");
				// alert("isStartEnd"+isStartEnd);
				var dec = Number(id) - 1 + "-" + serviceNo;
				var inc = Number(id) + 1 + "-" + serviceNo;

				var btnIdDec = document.getElementById(dec);
				var btnIdInc = document.getElementById(inc);
				if (isStartEnd > 0) {
					if (btnIdInc.className == "btnDYellow") {
						var option = document.createElement("option");
						option.value = array[2];
						option.text = array[2];
						genderCom.appendChild(option);
					} else {
						for (var i = 0; i < array.length; i++) {
							var option = document.createElement("option");
							option.value = array[i];
							option.text = array[i];
							genderCom.appendChild(option);
						}
					}
				} else {
					if (btnIdDec.className == "btnDYellow") {
						var option = document.createElement("option");
						option.value = array[2];
						option.text = array[2];
						genderCom.appendChild(option);
					} else {
						for (var i = 0; i < array.length; i++) {
							var option = document.createElement("option");
							option.value = array[i];
							option.text = array[i];
							genderCom.appendChild(option);
						}
					}
				}

			} else {
				// Middle two rows selected
				// 2,3,6,7,10,11,14,15,18,19,22,23,26,27,30,31,34,35,38,39,42,43,46,47
				// etc
				var dec = Number(id) - 1 + "-" + serviceNo;
				var inc = Number(id) + 1 + "-" + serviceNo;
				var btnIdDec = document.getElementById(dec);
				var btnIdInc = document.getElementById(inc);

				if ((btnIdInc.title != "" && btnIdInc.className == "btnDYellow")
						|| (btnIdDec.title != "" && btnIdDec.className == "btnDYellow")) {
					var option = document.createElement("option");
					option.value = array[2];
					option.text = array[2];
					genderCom.appendChild(option);
				} else {
					for (var i = 0; i < array.length; i++) {
						var option = document.createElement("option");
						option.value = array[i];
						option.text = array[i];
						genderCom.appendChild(option);
					}
				}

			}
		} else {
			if (id == 1) {
				// alert("Welcome to 1");
				var inc = Number(id) + 1 + "-" + serviceNo;
				var btnIdInc = document.getElementById(inc);
				if (btnIdInc.className == "btnDYellow") {
					var option = document.createElement("option");
					option.value = array[2];
					option.text = array[2];
					genderCom.appendChild(option);
				} else {
					for (var i = 0; i < array.length; i++) {
						var option = document.createElement("option");
						option.value = array[i];
						option.text = array[i];
						genderCom.appendChild(option);
					}
				}
			} else {
				// alert("Welcome to "+totalSeats);
				var dec = Number(id) - 1 + "-" + serviceNo;
				var btnIdDec = document.getElementById(dec);
				if (btnIdDec.className == "btnDYellow") {
					var option = document.createElement("option");
					option.value = array[2];
					option.text = array[2];
					genderCom.appendChild(option);
				} else {
					for (var i = 0; i < array.length; i++) {
						var option = document.createElement("option");
						option.value = array[i];
						option.text = array[i];
						genderCom.appendChild(option);
					}
				}
			}
		}

		genderCom.name = paramNames[0];
		cell1.appendChild(genderCom);

		var cell2 = row.insertCell(1);
		var passNameComp = document.createElement('input');
		passNameComp.name = paramNames[1];
		passNameComp.id="name-"+serviceNo;
		passNameComp.type = "text";
		passNameComp.placeholder = "Passenger Name"
		cell2.appendChild(passNameComp);

		var cell3 = row.insertCell(2);
		var ageComp = document.createElement('input');
		ageComp.name = paramNames[2];
		ageComp.id="age-"+serviceNo;
		ageComp.type = "number";
		ageComp.placeholder = "Age"
		cell3.appendChild(ageComp);

		var cell4 = row.insertCell(3);
		var seatNoComp = document.createElement('input');
		seatNoComp.id="seats-"+seats+"-"+serviceNo;
		seatNoComp.name = paramNames[3];
		seatNoComp.type = "text";
		seatNoComp.value = id;
		
		seatNoComp.hidden = true;
		cell4.style = "text-align:center;"
		cell4.innerHTML = id;
		cell4.appendChild(seatNoComp);

		btnId.className = "btnDGreen";

	} else {
		// btnId.style.backgroundColor = 'blue';

		seats--;
		totalPrice = totalPrice - Number(ticketPrice);
		url = "http://10.188.31.125:2020/busBookingApp/selectSeats/" + -id + "/"
				+ serviceNo;
		for (var row = 1; row < table.rows.length; row++) {
			var cells = table.rows.item(row).cells;
			// alert(id===cells[3].firstChild.data);
			if (id == cells[3].firstChild.data)
				table.deleteRow(row);
		}
		btnId.className = "btnDAvl";
	}
	if (seats < 0) {
		seats = 0;
	}
	if (totalPrice < 0) {
		totalPrice = 0;
	}
	btnId.style.color = 'white';
	document.getElementById(selectedSeatsUniqueId).innerHTML = seats;
	var totPriceUniqeId = "price-" + serviceNo;
	document.getElementById(totPriceUniqeId).innerHTML = totalPrice;

	if (window.XMLHttpRequest) {
		request = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		request = new ActiveXObject("Microsoft.XMLHTTP");
	}

	try {
		request.onreadystatechange = getInfo;
		request.open("GET", url, true);
		request.send();
	} catch (e) {
		alert("Unable to connect to server");
	}

}

function getInfo() {
	if (request.readyState == 4) {
		var val = request.responseText;
		// alert(val);
		if (val == "Blocked" || val == "Booked") {
			alert("Selected Seat has Already "+val+". Please select other seat. ");
			window.location.href = "http://10.188.31.125:2020/busBookingApp/home";
		} else if (val == "Expired") {
			alert("Slected seat time has expired.");
			window.location.href = "http://10.188.31.125:2020/busBookingApp/home";
		} else if (val == "Closed") {
			// alert("Closed successfully");
			// window.location.href = "http://10.188.31.125:2020/busBookingApp/home";
		}

	}
}

function countDownTime() {
	if (request.readyState == 4) {
		var val = request.responseText;
		// alert(val);
		var countDownTime = document.getElementById("countDownTime");
		countDownTime.innerHTML = "Page expire in : " + val;
		if (Number(val) <= 0) {
			alert("Slected seat time has expired.");
			window.location.href = "http://10.188.31.125:2020/busBookingApp/home";
		}
	}
}

function seatLayoutClose(seatLayout, serviceNo,totalSeats) {
	seats = 0;
	totalPrice = 0;
	// alert("close");
	var url = "http://10.188.31.125:2020/busBookingApp/unCheckServiceNoSeats/"
			+ serviceNo;
	if (window.XMLHttpRequest) {
		request = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		request = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	for(var i=1;i<=totalSeats;i++){
		var seatNoUniqueId  =i+"-"+serviceNo;
		var seatBtnUniqueObject = document.getElementById(seatNoUniqueId);
		if(seatBtnUniqueObject!=null && seatBtnUniqueObject.className=="btnDGreen"){
			// alert(seatBtnUniqueObject.className);
			seatBtnUniqueObject.click();
		}
	}

	try {
		request.onreadystatechange = getInfo;
		request.open("GET", url, true);
		request.send();
	} catch (e) {
		alert("Unable to connect to server");
	}
	var busSeatLayout = document.getElementById(seatLayout);
	busSeatLayout.style.display = "none";
	// busSeatLayout.style.height = "0%";
	// busSeatLayout.style.minHeight = "0%";
	document.getElementById("overlay-"+serviceNo).style.display = "none";
	var selectSeatsUniqueBtnObj = document.getElementById("selectSeatsBtn-"+serviceNo);
	selectSeatsUniqueBtnObj.disabled =false;
	selectSeatsUniqueBtnObj.className="btn-btn-primary";

}

function showBusSeatsLayout(seatLayout,serviceNo,totalSeats) {
	seats = 0;
	totalPrice = 0;
	if(typeof(showLayoutUniqueId)!="undefined"){
// alert(showLayoutUniqueId.id);
		showLayoutUniqueId.style.display = "none";
// showLayoutUniqueId.style.height = "0%";
		// showLayoutUniqueId.style.minHeight = "0%";
		// showLayoutUniqueId.style.position = "relative";
	}
	
	if(typeof(prevServiceNo)!="undefined" && typeof(prevServiceNoTotSeats)!="undefined"){
		for(var i=1;i<=prevServiceNoTotSeats;i++){
			var seatNoUniqueId  =i+"-"+prevServiceNo;
			var seatBtnUniqueObject = document.getElementById(seatNoUniqueId);
			if(seatBtnUniqueObject!=null && seatBtnUniqueObject.className=="btnDGreen"){
				// alert(seatBtnUniqueObject.className);
				seatBtnUniqueObject.click();
			}
		}
	}
	if(typeof(oldSelectSeatsUniqueBtnObj)!="undefined"){
		oldSelectSeatsUniqueBtnObj.disabled =false;
		oldSelectSeatsUniqueBtnObj.className="btn-btn-primary";
	}
	
	// alert(prevServiceNo+"-"+prevServiceNoTotSeats);
	var busSeatLayout = document.getElementById(seatLayout);
	busSeatLayout.style.display = "block";
	// busSeatLayout.style.height = "55%";
	// busSeatLayout.style.overflow="auto";
	// busSeatLayout.style.position = "relative";
	
	document.getElementById("overlay-"+serviceNo).style.display = "block";
	showLayoutUniqueId = busSeatLayout;
	prevServiceNo =serviceNo;
	prevServiceNoTotSeats =totalSeats;
	var selectSeatsUniqueBtnObj = document.getElementById("selectSeatsBtn-"+serviceNo);
	selectSeatsUniqueBtnObj.disabled =true;
	selectSeatsUniqueBtnObj.className="btn-btn-disabled";
	oldSelectSeatsUniqueBtnObj=selectSeatsUniqueBtnObj;
}

function hi() {
	alert("welcome");
}