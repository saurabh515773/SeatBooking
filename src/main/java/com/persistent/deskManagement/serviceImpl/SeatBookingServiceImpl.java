package com.persistent.deskManagement.serviceImpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistent.deskManagement.dao.SeatBookingRepository;
import com.persistent.deskManagement.entity.SeatBooking;
import com.persistent.deskManagement.model.EnumHelper.StatusEnum;
import com.persistent.deskManagement.service.SeatBookingService;

@Service("SeatBookingServiceImpl")
public class SeatBookingServiceImpl implements SeatBookingService {

	@Autowired
	SeatBookingRepository seatBookingRepository;
	
	@Override
	public Boolean findSeatAvailability(String seatNumber, LocalDateTime bookedFrom, LocalDateTime bookedTo) {
		
		Optional<SeatBooking> seat = seatBookingRepository.findSeatAvailability(seatNumber, bookedFrom, bookedTo);
		if(seat.isPresent()) {
			return false;
		}
		return true;
	}

	@Override
	public SeatBooking seatBooking(String seatNumber, LocalDateTime bookedFrom, LocalDateTime bookedTo, String employeeId) {
		SeatBooking seatBookingObj = new SeatBooking();
		seatBookingObj.setBookingId("1");
		seatBookingObj.setSeatNumber(seatNumber);
		seatBookingObj.setStatus(StatusEnum.BOOKED);
		seatBookingObj.setBookedFrom(bookedFrom);
		seatBookingObj.setBookedTo(bookedTo);
		seatBookingObj.setIsTimeElapsed(false);
		seatBookingObj.setEmployeeId(employeeId);
		seatBookingObj = seatBookingRepository.save(seatBookingObj);
		return seatBookingObj;
	}

}
