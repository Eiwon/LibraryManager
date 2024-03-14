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
	private final JPanel contentPanel = new JPanel();
	private JPanel panelForMember;
	private RoomDAOImple dao = null;
	private ArrayList<SeatVO> list = new ArrayList<>();
	private ArrayList<JCheckBox> BList = new ArrayList<>();
	private ButtonGroup group = new ButtonGroup();
	private static Thread autoUpdate = null;
	private Boolean autoUpdateFlag = true;
	private String userId;
	
	public ReadingRoom(String userId, String userAuth) {
		this.userId = userId;
		dao = RoomDAOImple.getInstance();
		autoUpdateFlag = true;
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 913, 669);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 895, 580);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
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
						occupySeat(selected);
					}else {
						new AlertDialog("이미 " + alreadyOccupied.getSeatId() + "번 좌석을 사용중입니다.");
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
						new AlertDialog("다른 사용자의 좌석입니다.");
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
				if(selected >= 0 && list.get(selected).getState().equals(OracleRoomQuery.STATE_OCCUPIED)) {
					String selectedUId = list.get(selected).getUserId();
					if(selectedUId.equals(userId)) {
						emptySeat(list.get(selected).getSeatId());
					}else {
						new AlertDialog("다른 사용자의 좌석입니다.");
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
	}
	private void initSeat() {
		list = dao.selectAllSeat();
		for(SeatVO vo : list) {
			JCheckBox box = new JCheckBox();
			box.setBounds(vo.getX(), vo.getY(), 100, 100);
			
			BList.add(box);
			contentPanel.add(box);
			group.add(box);
		}
	} // end initSeat
	
	private int getSelectedBox() {
		for(int i = 0; i < BList.size(); i++) {
			if(BList.get(i).isSelected()) {
				return i;
			}
		}
		return -1;
	} // end getSelectedBox
	
	private void occupySeat(int selected) {
		SeatVO pick = list.get(selected);
		pick.setUserId(userId);
		pick.setStart(LocalDateTime.now());
		pick.setEnd(LocalDateTime.now().plusHours(4));
		pick.setState(OracleRoomQuery.STATE_OCCUPIED);
		
		if(dao.occupySeat(pick) == 1) {
			BList.get(selected).setBackground(Color.red);
			reprintRemains();
		}
	} // end occupySeat
	
	private void emptySeat(String seatId) {
		if(dao.emptySeat(seatId) == 1) {
			reprintRemains();
		}
	} // end emptySeat
	
	private void extendSeat(int selected) {
		// 선택된 좌석의 남은 시간이 2시간 이하이면, 반납시간을 현재시간+4시간으로 변경
		SeatVO pick = list.get(selected);
		Duration d = Duration.between(LocalDateTime.now(), pick.getEnd());
		if(d.toMinutes() <= 120) {
			if(dao.extendSeat(pick.getSeatId()) == 1) {
				reprintRemains();
			}
		}else {
			new AlertDialog("아직 연장할 수 없습니다.");
		}
	} // end extendSeat

	
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
					contentPanel.remove(cb);
					revalidate();
					repaint();
				}
			});
		}
		contentPanel.addMouseListener(new MouseAdapter() {
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
							BList.remove(i);
							break;
						}
					}
					contentPanel.remove(box);
					revalidate();
					repaint();
				}
			});
			contentPanel.add(box);
			revalidate();
			repaint();
		}
		});
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
		for(int i = 0; i < list.size(); i++)
			System.out.print(list.get(i).getSeatId() + " ");
		System.out.println();
		int lLen = list.size();
		for(int i = 0; i < lLen; i++) {
			int idx = prevSeatId.indexOf(list.get(0).getSeatId());
			if(idx == -1) { // 변경리스트에는 있는데 테이블에 없는 경우
				dao.insertNewSeat(list.get(0));
			} else { // else 변경리스트와 테이블에 모두 있는 경우
				prevSeatId.remove(idx);
			}
			list.remove(0);
		}
		// 변경리스트에 없고 테이블에 있는 경우
		for(String seatId : prevSeatId) {
			dao.deleteSeat(seatId);
		}
		
	} // end saveChanged
}
