package lib.module.room;

import java.time.LocalDateTime;

public class SeatVO {

	int seatId;
	int x, y;
	String userId;
	LocalDateTime start;
	LocalDateTime end;
	String state;
	
	public SeatVO( int seatId, int x, int y) {
		this.seatId = seatId;
		this.x = x;
		this.y = y;
		}
	
	public SeatVO(int seatId, int x, int y, String userId, LocalDateTime start, LocalDateTime end, String state) {
		this.seatId = seatId;
		this.x = x;
		this.y = y;
		this.userId = userId;
		this.start = start;
		this.end = end;
		this.state = state;
	}

	public int getSeatId() {
		return seatId;
	}

	public void setSeatId(int seatId) {
		this.seatId = seatId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	
	

}
