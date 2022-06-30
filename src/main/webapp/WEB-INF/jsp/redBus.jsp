<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel='stylesheet'
	href='https://use.fontawesome.com/releases/v5.5.0/css/all.css'
	integrity='sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU'
	crossorigin='anonymous' />

<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet" />

<link href="css/greenBus.css" rel="stylesheet" />
<script src="js/greenBus.js">
	</script>

</head>
<%-- onload="loadSeats(${busHeaderDetails.selectedSeats},${busDetailsUi.value.serviceNo})" --%>
<body>
	<!-- <body onload="welcome()"> -->
	<div class="sticky">
		<div class="closeBtn" style="text-align: right;" title="Close">X</div>
		<span style="text-decoration: underline; color: red;">Error
			Panel</span>
		<div>I will stick to the screen when you reach my scroll
			position</div>
		<div class="${message.className}">${message.messageName}</div>

	</div>

	<c:choose>
		<c:when test="${!empty busDetailsUiMap}">
			<c:forEach items="${busDetailsUiMap}" var="busDetailsUi">
				<div id="busHeader-${busDetailsUi.value.serviceNo}" >
					<div class="row">
						<div class="col1">
							<div class="srvceNO">${busDetailsUi.value.serviceNo}</div>
							<p>${busDetailsUi.value.boardingPoint}-
								${busDetailsUi.value.droppingPoint}</p>
						</div>
						<div class="col2">
							<span class="StrtTm curHand tooltipstered" id="bPtsDiv_Forward1">${busDetailsUi.value.betweenTime}</span>
							<p>Duration: ${busDetailsUi.value.durationHrs} hrs</p>
						</div>
						<div class="col3">
							<div class="col3-left">
								<span> ${busDetailsUi.value.busDescription} </span>
								<p>Via: CTR, KOLAR. ${busDetailsUi.value.busServiceType}</p>
							</div>
							<div class="col3-right">
								<p>Depot: ALIPIRI</p>
								<div class="restStops"></div>
								<img
									src="//static.abhibus.com/ap_tg/ap/oprs-web/_assets/images/new/icon-vehicleTrack.png"
									title="GPS with Vehicle Tracking enabled." width="20px"
									height="20px" data-cycle-fx="tileBlind"
									data-cycle-tile-count="12">
							</div>
						</div>
						<div class="col4">
							<span class="availCs" availcs=${busHeaderDetails.seatsAvailable}>
								${busDetailsUi.value.seatsAvailable} Seats </span>
							<p>Window 19</p>
						</div>
						<div class="col5">
							<span class="TickRate rupeeIco" tickrate="229">
								${busDetailsUi.value.price} </span> <input type="button"
								class="btn-btn-primary" name="SrvcSelectBtnForward"
								id="selectSeatsBtn-${busDetailsUi.value.serviceNo}"
								value="Select Seats"
								onclick="showBusSeatsLayout('seatLayout-${busDetailsUi.value.serviceNo}',${busDetailsUi.value.serviceNo},${busDetailsUi.value.totalSeats})">
						</div>
					</div>

					<div id="seatLayout-${busDetailsUi.value.serviceNo}"
						style="display: none; width: 100%;">
						<div id="overlay-${busDetailsUi.value.serviceNo}"
							style="border: 1px solid #ccc;">
							<table align="center">
								<tbody>
									<tr>
										<td width="18%"><div class="success"
												style="font-size: 18px;">Click on seat to
												select/deselect seat</div></td>
										<td width="10%" align="right"><div class="closeBtn"
												title="Close"
												onclick="seatLayoutClose('seatLayout-${busDetailsUi.value.serviceNo}',${busDetailsUi.value.serviceNo},${busDetailsUi.value.totalSeats})">X</div></td>
									</tr>
								</tbody>
							</table>
							<table style="padding-left: 220px;">
								<tr>
									<td>
										<div class="seatsSteerCS">
											<table style="padding-left: 20px;">
												<tr>
													<c:if
														test="${busDetailsUi.value.busServiceType ne 'Passanger'}">
														<td style="padding-bottom: 150px;">
															<div>
																<img src="images/bus-str.png" />
															</div>
														</td>
													</c:if>
													<c:if
														test="${busDetailsUi.value.busServiceType eq 'Passanger'}">
														<td style="padding-bottom: 220px;">
															<div>
																<img src="images/bus-str.png" />
															</div>
														</td>
													</c:if>
													<td><table align="left" style="padding-left: 20px;">
															<c:if
																test="${busDetailsUi.value.busServiceType eq 'Sleeper'}">
																<c:forEach items="${busDetailsUi.value.viewBusSetasMap}"
																	var="map" varStatus="loop">
																	<c:if test="${loop.index == 2}">
																		<tr style="height: 30px;">
																			<br />
																		</tr>
																	</c:if>
																	<tr>
																		<c:forEach items="${map.value}" var="ticketList">
																			<td><button
																					class="${ticketList.btnStyleClsName}"
																					onclick="selectSeat(${ticketList.ticketId},${ticketList.totalSeats},${busDetailsUi.value.serviceNo})"
																					title="${ticketList.ticketTitleWindow}"
																					id="${ticketList.ticketId}-${busDetailsUi.value.serviceNo}">
																					${ticketList.ticketId} <i class="material-icons"
																						style="-webkit-transform: scaleX(-1); transform: scaleX(-1); border: none;">airline_seat_recline_extra</i>
																				</button></td>
																		</c:forEach>
																	</tr>
																</c:forEach>
															</c:if>
															<c:if
																test="${busDetailsUi.value.busServiceType ne 'Sleeper'}">
																<c:forEach items="${busDetailsUi.value.viewBusSetasMap}"
																	var="map" varStatus="loop">
																	<tr>
																		<c:forEach items="${map.value}" var="ticketList">
																			<c:if test="${ticketList.ticketId >=1}">
																				<td><button
																						class="${ticketList.btnStyleClsName}"
																						onclick="selectSeat(${ticketList.ticketId},${ticketList.totalSeats},${busDetailsUi.value.serviceNo})"
																						title="${ticketList.ticketTitleWindow}"
																						id="${ticketList.ticketId}-${busDetailsUi.value.serviceNo}">
																						${ticketList.ticketId} <i class="material-icons"
																							style="-webkit-transform: scaleX(-1); transform: scaleX(-1); border: none;">airline_seat_recline_extra</i>
																					</button></td>
																			</c:if>
																			<c:if test="${ticketList.ticketId ==-1}">
																				<td></td>
																			</c:if>
																		</c:forEach>
																	</tr>
																</c:forEach>
															</c:if>
														</table></td>
												</tr>
												<tr>
													<td style="padding-bottom: 20px;">
														<div></div>
													</td>
													<td>
														<table align="center" style="display: block;">
															<tr>
																<td><i class="material-icons btnGray"
																	style="-webkit-transform: scaleX(-1); transform: scaleX(-1);">airline_seat_recline_extra</i>
																	<span>Booked </span></td>
																<td><i class="material-icons btnGreen"
																	style="-webkit-transform: scaleX(-1); transform: scaleX(-1);">airline_seat_recline_extra</i>
																	<span>Selected </span></td>
																<td><i class="material-icons btnBlue"
																	style="-webkit-transform: scaleX(-1); transform: scaleX(-1);">airline_seat_recline_extra</i>
																	<span>Available </span></td>
																<td><i class="material-icons btnBllocked"
																	style="-webkit-transform: scaleX(-1); transform: scaleX(-1);">airline_seat_recline_extra</i>
																	<span>Blocked </span></td>
																<td><i class="material-icons btnYellow"
																	style="-webkit-transform: scaleX(-1); transform: scaleX(-1);">airline_seat_recline_extra</i>
																	<span>Female </span></td>
															</tr>
															<tr>
																<td></td>
																<td><span>No. Of Seats Selected :</span>
																	<div id="selectedSeats-${busDetailsUi.value.serviceNo}"></div></td>
																<td></td>
																<td></td>
																<td><span>Total Price :</span>
																	<div id="price-${busDetailsUi.value.serviceNo}"></div></td>
															</tr>
														</table>
													</td>

												</tr>
											</table>
									</td>
									<td>
										<fieldset style="border: 1px solid #ccc;">
											<legend>Passenger Details: </legend>
											<form method="post"
												action="/busBookingApp/displaySelectedSeats/${busDetailsUi.value.serviceNo}"
												onSubmit="return getValues(${busDetailsUi.value.serviceNo})">
												<table>
													<tr>
														<td>Primary Passenger Name</td>
														<td><input class="form-control" type="text"
															name="passengerName"
															id="passengerName-${busDetailsUi.value.serviceNo}"
															placeholder="Primary Passenger Name"></td>
													</tr>
													<tr>
														<td>Mobile No.</td>
														<td><input class="form-control" type="text"
															name="mobileNo"
															id="mobileNo-${busDetailsUi.value.serviceNo}"
															placeholder="Mobile No."></td>
													</tr>
													<tr>
														<td>Email Id</td>
														<td><input class="form-control" type="text"
															name="emailId"
															id="emailId-${busDetailsUi.value.serviceNo}"
															placeholder="Email Id"></td>

													</tr>
													<tr>

														<table style="border: 1px solid #ccc;"
															id="passengerDetails-${busDetailsUi.value.serviceNo}">
															<thead>
																<tr>
																	<th style="width: 75px; background-color: #33cc33;">Gender</th>

																	<th style="width: 174px; background-color: #33cc33;">Name</th>
																	<th style="width: 174px; background-color: #33cc33;">Age
																	</th>
																	<th style="width: 70px; background-color: #33cc33;">SeatNo.</th>
																</tr>
															</thead>

															<div id="genderAppendDiv-${busDetailsUi.value.serviceNo}"></div>

														</table>
													</tr>
													<input type="hidden" id="dummy" name="dummy" />
													<input type="hidden"
														id="totalPrice-${busDetailsUi.value.serviceNo}"
														name="totalPrice" value="${busDetailsUi.value.price}" />
													<input type="hidden" name="serviceNo"
														value="${busHeaderDetails.serviceNo}">
													<tr>
														<td><input type="submit" value="Continue"></td>
													</tr>
												</table>
											</form>
										</fieldset>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</c:forEach>
		</c:when>
	</c:choose>
</body>


</html>
