package com.persistent.deskManagement.service;

import java.time.LocalDateTime;

import com.persistent.deskManagement.entity.SeatBooking;

public interface SeatBookingService {

	
	public Boolean findSeatAvailability (String seatNumber, LocalDateTime bookedFrom , LocalDateTime bookedTo);
	
	public SeatBooking seatBooking(String seatNumber, LocalDateTime bookedFrom , LocalDateTime bookedTo, String employeeId);
}
