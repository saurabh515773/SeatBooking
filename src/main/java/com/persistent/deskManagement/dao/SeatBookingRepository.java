package com.persistent.deskManagement.dao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.persistent.deskManagement.entity.SeatBooking;

@Repository
public interface SeatBookingRepository extends JpaRepository<SeatBooking, String> {

	@Query("SELECT s FROM SeatBooking s WHERE (s.seatNumber = ?1) AND (?2 BETWEEN s.bookedFrom and s.bookedTo OR ?3 BETWEEN s.bookedFrom and s.bookedTo)")
	Optional<List<SeatBooking>> findSeatAvailability(String seatNumber, LocalDateTime bookedFrom, LocalDateTime bookedTo);
	
	@Query("SELECT s FROM SeatBooking s WHERE s.employeeId = ?1 order by s.bookedFrom")
	Optional<List<SeatBooking>> findAllEmployeeBookedSeats(Integer employeeId);
	
	@Query("SELECT s FROM SeatBooking s "
			+ "WHERE s.bookedFrom BETWEEN ?1 and ?2 "
			+ "OR "
			+ "s.bookedTo BETWEEN ?1 and ?2")
	//@Query("SELECT s FROM SeatBooking s WHERE ?1 BETWEEN s.bookedFrom and s.bookedTo OR ?2 BETWEEN s.bookedFrom and s.bookedTo")
	Optional<List<SeatBooking>> findAllBookedSeats(LocalDateTime bookedFrom, LocalDateTime bookedTo);

}