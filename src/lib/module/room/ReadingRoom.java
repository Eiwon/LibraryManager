package lib.module.room;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lib.view.AlertDialog;

import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReadingRoom extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JPanel PanelBackground = new JPanel();
	private JPanel panelForMember;
	private RoomDAO dao = null;
	private ArrayList<SeatVO> list = new ArrayList<>();
	private ArrayList<JCheckBox> BList = new ArrayList<>();
	private ButtonGroup group = new ButtonGroup();
	private static Thread autoUpdate = null;
	private Boolean autoUpdateFlag = true;
	
	public ReadingRoom(String userId, String userAuth) {
		dao = RoomDAOImple.getInstance();
		autoUpdateFlag = true;
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(750, 300, 913, 669);
		getContentPane().setLayout(null);
		PanelBackground.setBounds(0, 0, 895, 580);
		PanelBackground.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(PanelBackground);
		PanelBackground.setLayout(null);
		
		panelForMember = new JPanel();
		panelForMember.setBounds(512, 579, 373, 41);
		getContentPane().add(panelForMember);
		panelForMember.setLayout(null);
		
		JButton btnSelect = new JButton("선택");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = getSelectedBox();
				if(selected >= 0 && list.get(selected).getState().equals(OracleRoomQuery.STATE_EMPTY)) {
					SeatVO alreadyOccupied = dao.checkSeatByUId(userId);
					if(alreadyOccupied == null) {
						occupySeat(selected, userId);
					}else {
						AlertDialog.printMsg("이미 " + alreadyOccupied.getSeatId() + "번 좌석을 사용중입니다.");
					}
				}
			}
		});
		btnSelect.setBounds(12, 10, 97, 23);
		panelForMember.add(btnSelect);
		
		JButton btnExtend = new JButton("연장");
		btnExtend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = getSelectedBox();
				if(selected >= 0 && list.get(selected).getState().equals(OracleRoomQuery.STATE_OCCUPIED)) {
					String selectedUId = list.get(selected).getUserId();
					if(selectedUId.equals(userId)) {
						extendSeat(selected);
					}else {
						AlertDialog.printMsg("다른 사용자의 좌석입니다.");
					}
				}
			}
		});
		btnExtend.setBounds(230, 10, 97, 23);
		panelForMember.add(btnExtend);
		
		JButton btnReturn = new JButton("반납");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = getSelectedBox();
				SeatVO selectedSeat = list.get(selected);
				
				if(selected >= 0 && selectedSeat.getState().equals(OracleRoomQuery.STATE_OCCUPIED)) {
					String selectedUId = selectedSeat.getUserId();
					if(selectedUId.equals(userId)) {
						emptySeat(selectedSeat.getSeatId());
					}else {
						AlertDialog.printMsg("다른 사용자의 좌석입니다.");
					}
				}
			}
		});
		btnReturn.setBounds(121, 10, 97, 23);
		panelForMember.add(btnReturn);
		
		JPanel panelForAdmin = new JPanel();
		if(!userAuth.equals("ADMIN")) {
			panelForAdmin.setVisible(false);
		}
		panelForAdmin.setLayout(null);
		panelForAdmin.setBounds(28, 579, 373, 41);
		getContentPane().add(panelForAdmin);
		
		JButton btnEdit = new JButton("편집");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				toEdit();
			}
		});
		btnEdit.setBounds(12, 10, 97, 23);
		panelForAdmin.add(btnEdit);
		
		JButton btnSave = new JButton("저장");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveChanged();
				dispose();
			}
		});
		btnSave.setBounds(122, 10, 97, 23);
		panelForAdmin.add(btnSave);
		
		JButton btnBack = new JButton("돌아가기");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setBounds(239, 10, 97, 23);
		panelForAdmin.add(btnBack);
		
		initSeat();
		reprintRemains();
		if(autoUpdate == null) {
			autoUpdate = new Thread(new Runnable() {

			@Override
			public void run() {
				while(autoUpdateFlag) {
					try {
						Thread.sleep(30000);
					} catch (InterruptedException e) {
						System.err.println(e.toString());
					}
					reprintRemains();
				}
			}
		});
			autoUpdate.start();
		}
	} // end ReadingRoom
	
	// DB로부터 좌석 위치값을 읽어와 좌석 객체 생성
	private void initSeat() {
		list = dao.selectAllSeat();
		for(SeatVO vo : list) {
			JCheckBox box = new JCheckBox();
			box.setBounds(vo.getX(), vo.getY(), 100, 100);
			
			BList.add(box);
			PanelBackground.add(box);
			group.add(box);
		}
	} // end initSeat
	
	// 좌석 리스트에서 선택된 좌석이 있는지 확인 후 그 인덱스 반환, 없다면 -1 반환
	private int getSelectedBox() {
		for(int i = 0; i < BList.size(); i++) {
			if(BList.get(i).isSelected()) {
				return i;
			}
		}
		return -1;
	} // end getSelectedBox
	
	// 유저가 좌석을 대여하면 실행, 좌석을 "OCCUPIED" 상태로 변경
	private void occupySeat(int selected, String userId) {
		SeatVO pick = list.get(selected);
		pick.setUserId(userId);
		pick.setStart(LocalDateTime.now());
		pick.setEnd(LocalDateTime.now().plusHours(4));
		pick.setState(OracleRoomQuery.STATE_OCCUPIED);
		
		if(dao.occupySeat(pick) == 1) {
			reprintRemains();
		}
	} // end occupySeat
	
	// 유저가 좌석을 반납하면 실행, 좌석을 "EMPTY" 상태로 변경
	private void emptySeat(String seatId) {
		if(dao.emptySeat(seatId) == 1) {
			reprintRemains();
		}
	} // end emptySeat
	
	// 유저가 좌석 대여 시간을 연장하면 실행
	private void extendSeat(int selected) {
		// 선택된 좌석의 남은 시간이 2시간 이하이면, 반납시간을 현재시간+4시간으로 변경
		SeatVO pick = list.get(selected);
		Duration d = Duration.between(LocalDateTime.now(), pick.getEnd());
		if(d.toMinutes() <= 120) {
			if(dao.extendSeat(pick.getSeatId()) == 1) {
				reprintRemains();
			}
		}else {
			AlertDialog.printMsg("아직 연장할 수 없습니다.");
		}
	} // end extendSeat

	// 모든 좌석의 색, 텍스트 최신화
	private void reprintRemains() {
		System.out.println("reprintRemains");
		list = dao.selectAllSeat();
		if(list == null) {
			System.out.println("저장된 좌석 정보 없음");
			return;
		}
		Duration d;
		for(int i = 0; i < list.size(); i++) {
			BList.get(i).setFont(new Font("굴림", Font.PLAIN, 11));
			if(list.get(i).getState().equals("OCCUPIED")) {
				d = Duration.between(LocalDateTime.now(), list.get(i).getEnd());
				BList.get(i).setText("No." + list.get(i).getSeatId() + "| "
									+ d.toHours() + " : " + (d.toMinutes() % 60));
				BList.get(i).setBackground(Color.red);
				if(d.isNegative()) {
					emptySeat(list.get(i).getSeatId());
				}
			}else {
				BList.get(i).setBackground(Color.green);
				BList.get(i).setText("No." + list.get(i).getSeatId());
			}
		}
		revalidate();
		repaint();
	} // end reprintRemains
	
	// 편집 모드로 변경, 
	private void toEdit() {
		autoUpdateFlag = false;
		panelForMember.setVisible(false);
		for(JCheckBox cb : BList) {
			cb.setEnabled(false);
			cb.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for(int i = 0; i < list.size(); i++) {
						if(list.get(i).getX() /100 == cb.getX()/100 && list.get(i).getY() /100 == cb.getY()/100) {
							list.remove(i);
							BList.remove(i);
							break;
						}
					}
					PanelBackground.remove(cb);
					revalidate();
					repaint();
				}
			});
		} // 기존의 모든 좌석을 체크 불가로 변경, 마우스 클릭시 제거되는 리스너 추가
		PanelBackground.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			int x = e.getX() /100 *100;
			int y = e.getY() /100 *100;
			JCheckBox box = new JCheckBox();
			SeatVO vo = new SeatVO((x / 100) + "." + (y / 100), x, y);
			list.add(vo);
			box.setBounds(x, y, 100, 100);
			box.setBackground(Color.GREEN);
			box.setEnabled(false);
			box.setText("No." + vo.getSeatId());
			box.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for(int i = 0; i < list.size(); i++) {
						if(list.get(i).getX() /100 == box.getX() /100 && list.get(i).getY() /100 == box.getY() /100) {
							list.remove(i);
							break;
						}
					}
					PanelBackground.remove(box);
					revalidate();
					repaint();
				}
			});
			PanelBackground.add(box);
			revalidate();
			repaint();
		}
		}); // 배경 패널에 마우스 클릭 리스너 추가 : 클릭 지점에 좌석(체크박스) 생성
	} // end toEdit
	
	private void saveChanged() {
		// 모든 seatId 값을 select
		// select한 seatId와 변경할 좌석 정보를 비교 
		// 이미 존재하는 seatId면 변경 x
		// 존재하지 않는 seatId면 insert
		// 테이블에만 있고 변경리스트에는 없는 seatId면 delete
		ArrayList<String> prevSeatId = dao.selectAllSeatId();
		if(list == null || prevSeatId == null)
			return;
		
		int lLen = list.size();
		for(int i = 0; i < lLen; i++) {
			int idx = prevSeatId.indexOf(list.get(0).getSeatId()); 
			// prevSeatId에 있는 값이면, 그 인덱스 반환, 없는 값이면 -1 반환
			
			if(idx == -1) { // 변경할 리스트에는 있는데 테이블에 없는 경우 (신규 좌석)
				dao.insertNewSeat(list.get(0));
			} else { // else 변경할 리스트와 테이블에 모두 있는 경우 (이미 존재하던 좌석)
				prevSeatId.remove(idx);
			}
			list.remove(0);
		}
		// 변경할 리스트에 없고 테이블에 있는 경우 (삭제된 좌석)
		for(String seatId : prevSeatId) {
			dao.deleteSeat(seatId);
		}
	} // end saveChanged
	
}
