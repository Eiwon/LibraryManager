package libManager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.BookVO;
import libManager.Controller.BookDAOImple;
import libManager.Controller.UserDAOImple;
import libManager.Controller.UserManagementService;
import libManager.Interface.OracleBookQuery;

import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckoutDialog extends JDialog {

	private static final int MAX_CHECK_OUT_TIME = 7;
	private final JPanel contentPanel = new JPanel();
	private JTextPane txtBookInfo;
	private JButton btnCheckout;
	private JButton btnReserve;
	private JButton btnExit;
	private BookDAOImple dao = null;
	private String returnDate;
	
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
		txtBookInfo.setBounds(32, 10, 461, 300);
		printBookInfo(vo);
		txtBookInfo.setEditable(false);
		txtBookInfo.setVisible(true);
		contentPanel.add(txtBookInfo);
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
			if(vo.getState().equals(OracleBookQuery.BOOK_STATE_OUT) && !userId.equals(UserManagementService.getUserId())) {
				// 책이 '대출 중' 상태이고 자신이 대출한 상태가 아닐 때에만 예약 버튼 활성화
				btnReserve.setEnabled(true);
			}else{
				btnReserve.setEnabled(false);
			}
			btnCheckout.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(UserManagementService.isBan()) {
						System.out.println("도서 대출 / 예약이 불가능한 상태입니다.");
						return;
					}
					
					String userId = UserManagementService.getUserId();
					String state = OracleBookQuery.BOOK_STATE_OUT;
					String outTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
					String returnTime 
					= LocalDateTime.now().plusDays(MAX_CHECK_OUT_TIME).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
						
					dao.insertCheckoutBook(vo.getBookId(), userId, state, outTime, returnTime);
					dao.updateByBookId(OracleBookQuery.BOOK_STATE_OUT, vo.getBookId());
					dispose();
				}
			});
				
			btnReserve.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				if(UserManagementService.isBan()) {
					System.out.println("도서 대출 / 예약이 불가능한 상태입니다.");
					return;
				}
				String userId = UserManagementService.getUserId();
				String state = OracleBookQuery.BOOK_STATE_RSV;
				LocalDateTime.parse(returnDate.replace(' ', 'T')).plusDays(7);
				String reserveTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				String reserveEndTime = LocalDateTime.parse(returnDate.replace(' ', 'T')).plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
				System.out.println(reserveTime + " ~ " + reserveEndTime);
					
				dao.insertCheckoutBook(vo.getBookId(), userId, state, reserveTime, reserveEndTime);
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
			contents =contents.concat("반납 예정일 : " + returnDate.substring(0, 10));
		}
		
		txtBookInfo.setText(contents);
	} // end printBookInfo
}
