package lib.view;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import lib.Interface.OracleBookQuery;
import lib.controller.BookDAOImple;
import lib.controller.ImageManager;
import lib.controller.UserManager;
import lib.model.BookVO;

import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import javax.swing.JLabel;

public class CheckoutDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static final int MAX_CHECK_OUT_TIME = 7;
	private final JPanel contentPanel = new JPanel();
	private JTextPane txtBookInfo;
	private JButton btnCheckout;
	private JButton btnReserve;
	private JButton btnExit;
	private BookDAOImple dao = null;
	private LocalDateTime returnDate;
	
	public CheckoutDialog(BookVO vo) {
		dao = BookDAOImple.getInstance();
		String userId = dao.selectByBookState(vo.getBookId(), vo.getState());
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
		setBounds(100, 100, 525, 411);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 512, 339);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		
		txtBookInfo = new JTextPane();
		txtBookInfo.setBounds(187, 10, 302, 300);
		printBookInfo(vo);
		txtBookInfo.setEditable(false);
		txtBookInfo.setVisible(true);
		contentPanel.add(txtBookInfo);
		
		JLabel lblImg = new JLabel("");
		lblImg.setBounds(26, 10, 160, 211);
		lblImg.setIcon(ImageManager.getInstance().getImageToIcon(vo.getImg(), 144, 218));
		
		contentPanel.add(lblImg);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 339, 495, 33);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			btnCheckout = new JButton("대출하기");
			btnReserve = new JButton("예약하기");
			btnExit = new JButton("취소");
			if(vo.getState().equals(OracleBookQuery.BOOK_STATE_SET)) {
				// 책이 '대여가능' 상태면 대출버튼 활성화
				btnCheckout.setEnabled(true);
			}else {
				btnCheckout.setEnabled(false);
			}
			if(vo.getState().equals(OracleBookQuery.BOOK_STATE_OUT) && !userId.equals(UserManager.getUserId())) {
				// 책이 '대출 중' 상태이고 자신이 대출한 상태가 아닐 때에만 예약 버튼 활성화
				btnReserve.setEnabled(true);
			}else{
				btnReserve.setEnabled(false);
			}
			btnCheckout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(UserManager.isBan()) {
						new AlertDialog("도서 대출 / 예약이 불가능한 상태입니다.");
						return;
					}
					
					String userId = UserManager.getUserId();
					String state = OracleBookQuery.BOOK_STATE_OUT;
					LocalDateTime outTime = LocalDateTime.now();
					LocalDateTime returnTime 
					= outTime.plusDays(MAX_CHECK_OUT_TIME);
						
					dao.insertCheckoutBook(vo.getBookId(), userId, state, outTime, returnTime);
					dao.updateByBookId(OracleBookQuery.BOOK_STATE_OUT, vo.getBookId());
					dispose();
				}
			});
				
			btnReserve.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				if(UserManager.isBan()) {
					new AlertDialog("도서 대출 / 예약이 불가능한 상태입니다.");
					return;
				}
				String userId = UserManager.getUserId();
				String state = OracleBookQuery.BOOK_STATE_RSV;
				LocalDateTime reserveDate = LocalDateTime.now();
				LocalDateTime reserveEndDate = returnDate.plusDays(7);
					
				dao.insertCheckoutBook(vo.getBookId(), userId, state, reserveDate, reserveEndDate);
				dao.updateByBookId(OracleBookQuery.BOOK_STATE_RSV, vo.getBookId());
				}
			});
			btnExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			buttonPane.add(btnReserve);
			buttonPane.add(btnCheckout);
			buttonPane.add(btnExit);
		}
		
	} // end CheckoutDialog
	
	private void printBookInfo(BookVO vo) {
		String contents = "";
		contents = contents.concat("제목 : " + vo.getName() + "\n");
		contents = contents.concat("도서 코드 : " + vo.getBookId() + "\n");
		contents = contents.concat("저자 : " + vo.getWriter() + "\n");
		contents = contents.concat("장르 : " + vo.getCategory() + "\n");
		contents = contents.concat("상태 : " + vo.getState() + "\n");
		
		if(vo.getState().equals(OracleBookQuery.BOOK_STATE_OUT) || vo.getState().equals(OracleBookQuery.BOOK_STATE_RSV)) {
			returnDate = BookDAOImple.getInstance().selectCheckinDate(vo.getBookId());
			contents =contents.concat("반납 예정일 : " + returnDate.toString().substring(0, 10));
		}
		
		txtBookInfo.setText(contents);
	} // end printBookInfo
}
