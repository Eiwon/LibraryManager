package lib.module.room;
import java.awt.BorderLayout;
import java.awt.CheckboxGroup;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lib.controller.UserManager;
import lib.view.AlertDialog;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ReadingRoom extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private RoomDAOImple dao = null;
	int id = 0;
	ArrayList<SeatVO> list = new ArrayList<>();
	ArrayList<JCheckBox> BList = new ArrayList<>();
	ButtonGroup group = new ButtonGroup();
	public ReadingRoom() {
		dao = RoomDAOImple.getInstance();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 913, 669);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 895, 580);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(512, 579, 373, 41);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnSelect = new JButton("선택");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = getSelectedBox();
				if(selected >= 0 && list.get(selected).getState().equals(OracleRoomQuery.STATE_EMPTY)) {
					occupySeat(selected);
				}
			}
		});
		btnSelect.setBounds(12, 10, 97, 23);
		panel_1.add(btnSelect);
		
		JButton btnExtend = new JButton("연장");
		btnExtend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = getSelectedBox();
				if(selected >= 0 && list.get(selected).getState().equals(OracleRoomQuery.STATE_OCCUPIED)) {
					String selectedUId = list.get(selected).getUserId();
					if(selectedUId.equals(UserManager.getUserId())) {
						extendSeat(selected);
					}else {
						new AlertDialog("다른 사용자의 좌석입니다.");
					}
				}
			}
		});
		btnExtend.setBounds(230, 10, 97, 23);
		panel_1.add(btnExtend);
		
		JButton btnReturn = new JButton("반납");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = getSelectedBox();
				if(selected >= 0 && list.get(selected).getState().equals(OracleRoomQuery.STATE_OCCUPIED)) {
					String selectedUId = list.get(selected).getUserId();
					if(selectedUId.equals(UserManager.getUserId())) {
						emptySeat(list.get(selected).getSeatId());
					}else {
						new AlertDialog("다른 사용자의 좌석입니다.");
					}
				}
			}
		});
		btnReturn.setBounds(121, 10, 97, 23);
		panel_1.add(btnReturn);
		
		initSeat();
		reprintRemains();
		
		Thread autoUpdate = new Thread(new Runnable() {

			@Override
			public void run() {
				while(true) {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					System.err.println(e.toString());
				}
				reprintRemains();
				}
			}
		});
		autoUpdate.start();
//		contentPanel.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				int x = e.getX() /100 *100;
//				int y = e.getY() /100 *100;
//				JPanel panel = new JPanel();
//				SeatUnit su = new SeatUnit(id++, x, y);
//				list.add(su);
//				panel.setBounds(x, y, 100, 100);
//				panel.setBackground(Color.GREEN);
//				panel.addMouseListener(new MouseAdapter() {
//					@Override
//					public void mouseClicked(MouseEvent e) {
//						// remove code
//					}
//				});
//				contentPanel.add(panel);
//				JCheckBox chckbxNewCheckBox = new JCheckBox();
//				panel.add(chckbxNewCheckBox);
//				dao.insertNewSeat(su);
//				revalidate();
//				repaint();
//			}
//		});
		
	}
	private void initSeat() {
		list = dao.selectAllSeat();
		for(SeatVO vo : list) {
			JCheckBox box = new JCheckBox();
			box.setBounds(vo.x, vo.y, 100, 100);
			
			BList.add(box);
			contentPanel.add(box);
			group.add(box);
		}
	}
	
	private int getSelectedBox() {
		for(int i = 0; i < BList.size(); i++) {
			if(BList.get(i).isSelected()) {
				return i;
			}
		}
		return -1;
	}
	
	private void occupySeat(int selected) {
		SeatVO pick = list.get(selected);
		pick.setUserId(UserManager.getUserId());
		pick.setStart(LocalDateTime.now());
		pick.setEnd(LocalDateTime.now().plusHours(4));
		pick.setState(OracleRoomQuery.STATE_OCCUPIED);
		
		if(dao.occupySeat(pick) == 1) {
			BList.get(selected).setBackground(Color.red);
			reprintRemains();
		}
	}
	
	private void emptySeat(int seatId) {
		if(dao.emptySeat(seatId) == 1) {
			reprintRemains();
		}
	}
	
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
	}
	
	
	private void reprintRemains() {
		System.out.println("reprintRemains");
		list = dao.selectAllSeat();
		Duration d;
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).state.equals("OCCUPIED")) {
				d = Duration.between(LocalDateTime.now(), list.get(i).getEnd());
				BList.get(i).setText(d.toHours() + " : " + (d.toMinutes() % 60));
				BList.get(i).setBackground(Color.red);
				if(d.isNegative()) {
					emptySeat(list.get(i).seatId);
				}
			}else {
				BList.get(i).setBackground(Color.green);
				BList.get(i).setText("");
			}
			
		}
		revalidate();
		repaint();
	}
	
}
