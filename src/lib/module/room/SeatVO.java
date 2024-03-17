package lib.module.room;

import java.time.LocalDateTime;

public class SeatVO {

	private String seatId;
	private int x, y;
	private String userId;
	private LocalDateTime start;
	private LocalDateTime end;
	private String state;
	
	public SeatVO(String seatId, int x, int y) {
		this.seatId = seatId;
		this.x = x;
		this.y = y;
		}
	
	public SeatVO(String seatId, int x, int y, String userId, LocalDateTime start, LocalDateTime end, String state) {
		this.seatId = seatId;
		this.x = x;
		this.y = y;
		this.userId = userId;
		this.start = start;
		this.end = end;
		this.state = state;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
