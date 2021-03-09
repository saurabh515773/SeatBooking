package com.persistent.deskManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.persistent.deskManagement.entity.SeatBooking;

public interface SeatBookingService {

	
	public Boolean findSeatAvailability (String seatNumber, LocalDateTime bookedFrom , LocalDateTime bookedTo);
	
	public SeatBooking seatBooking(String seatNumber, LocalDateTime bookedFrom , LocalDateTime bookedTo, Integer employeeId);
	
	public Optional<List<SeatBooking>> allEmployeeBookedSeats(Integer employeeId, LocalDateTime bookedFrom , LocalDateTime bookedTo);
	
	public ArrayList<String> allBookedSeats(LocalDateTime bookedFrom , LocalDateTime bookedTo);
	
	public Optional<SeatBooking> occupySeat(String bookingId);
	
	public Optional<SeatBooking> returnSeat(String bookingId);

}
