package lib.module.room;

import java.util.ArrayList;

public interface RoomDAO {

	public int insertNewSeat(SeatVO seat);
	
	public ArrayList<SeatVO> selectAllSeat();
	
	public int occupySeat(SeatVO seat);
	
	public int emptySeat(int seatId);
	
	public int extendSeat(int seatId);
}
