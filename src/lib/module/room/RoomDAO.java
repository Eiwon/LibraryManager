package lib.module.room;

import java.util.ArrayList;

public interface RoomDAO {

	public ArrayList<String> selectAllSeatId();
	
	public int insertNewSeat(SeatVO seat);
	
	public int deleteSeat(String seatId);
	
	public ArrayList<SeatVO> selectAllSeat();
	
	public int occupySeat(SeatVO seat);
	
	public int emptySeat(String seatId);
	
	public int extendSeat(String seatId);
	
	public SeatVO checkSeatByUId(String userId);
	
}
