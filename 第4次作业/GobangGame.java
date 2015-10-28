import java.io.BufferedReader;
import java.io.InputStreamReader;

 
public class GobangGame {
	// ����ﵽӮ������������Ŀ
	private final int WIN_COUNT = 5;
	// �����û������X����
	private int posX = 0;
	// �����û������X����
	private int posY = 0;
	// ��������
	private Chessboard chessboard;

	/**
	 * �չ�����
	 */
	public GobangGame() {
	}

	/**
	 * ����������ʼ�����̺���������
	 * 
	 * @param chessboard
	 *            ������
	 */
	public GobangGame(Chessboard chessboard) {
		this.chessboard = chessboard;
	}

	/**
	 * ��������Ƿ�Ϸ���
	 * 
	 * @param inputStr
	 *            �ɿ���̨������ַ�����
	 * @return �ַ����Ϸ�����true,���򷵻�false��
	 */
	public boolean isValid(String inputStr) {
		// ���û�������ַ����Զ���(,)��Ϊ�ָ����ָ��������ַ���
		String[] posStrArr = inputStr.split(",");
		try {
			posX = Integer.parseInt(posStrArr[0]) - 1;
			posY = Integer.parseInt(posStrArr[1]) - 1;
		} catch (NumberFormatException e) {
			chessboard.printBoard();
			System.out.println("����(����,����)�ĸ�ʽ���룺");
			return false;
		}
		// ���������ֵ�Ƿ��ڷ�Χ֮��
		if (posX < 0 || posX >= Chessboard.BOARD_SIZE || posY < 0
				|| posY >= Chessboard.BOARD_SIZE) {
			chessboard.printBoard();
			System.out.println("X��Y����ֻ�ܴ��ڵ���1,��С�ڵ���" + Chessboard.BOARD_SIZE
					+ ",���������룺");
			return false;
		}
		// ��������λ���Ƿ��Ѿ�������
		String[][] board = chessboard.getBoard();
		if (board[posX][posY] != "ʮ") {
			chessboard.printBoard();
			System.out.println("��λ���Ѿ������ӣ����������룺");
			return false;
		}
		return true;
	}

	/**
	 * ��ʼ����
	 */
	public void start() throws Exception {
		// trueΪ��Ϸ����
		boolean isOver = false;
		chessboard.initBoard();
		chessboard.printBoard();
		// ��ȡ���̵�����
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputStr = null;
		// br.readLine:ÿ����������һ�����ݰ��س���������������ݱ�br��ȡ��
		while ((inputStr = br.readLine()) != null) {
			isOver = false;
			if (!isValid(inputStr)) {
				// ������Ϸ���Ҫ���������룬�ټ���
				continue;
			}
			// �Ѷ�Ӧ������Ԫ�ظ�Ϊ"��"
			String chessman = Chessman.BLACK.getChessman();
			chessboard.setBoard(posX, posY, chessman);
			// �ж��û��Ƿ�Ӯ��
			if (isWon(posX, posY, chessman)) {
				isOver = true;

			} else {
				// ��������ѡ��λ������
				int[] computerPosArr = computerDo();
				chessman = Chessman.WHITE.getChessman();
				chessboard.setBoard(computerPosArr[0], computerPosArr[1],
						chessman);
				// �жϼ�����Ƿ�Ӯ��
				if (isWon(computerPosArr[0], computerPosArr[1], chessman)) {
					isOver = true;
				}
			}
			// �������ʤ�ߣ�ѯ���û��Ƿ������Ϸ
			if (isOver) {
				// ������������³�ʼ�����̣�������Ϸ
				if (isReplay(chessman)) {
					chessboard.initBoard();
					chessboard.printBoard();
					continue;
				}
				// ������������˳�����
				break;
			}
			chessboard.printBoard();
			System.out.println("����������������꣬Ӧ��x,y�ĸ�ʽ���룺");
		}
	}

	/**
	 * �Ƿ����¿�ʼ���塣
	 * 
	 * @param chessman
	 *            "��"Ϊ�û���"��"Ϊ�������
	 * @return ��ʼ����true�����򷵻�false��
	 */
	public boolean isReplay(String chessman) throws Exception {
		chessboard.printBoard();
		String message = chessman.equals(Chessman.BLACK.getChessman()) ? "��ϲ������Ӯ�ˣ�"
				: "���ź��������ˣ�";
		System.out.println(message + "����һ�֣�(y/n)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		if (br.readLine().equals("y")) {
			// ��ʼ��һ��
			return true;
		}
		return false;

	}

	/**
	 * ������������
	 */
	public int[] computerDo() {
		String[][] board = chessboard.getBoard();
		//����ϵ�����ֱ�Ϊ��/������\������-������|��
		int factor[] = {1,1,1,-1,1,0,0,1};
		//��������
		int count; 
		int posX = 0, posY = 0;
		boolean isDecided = false;
		String ico;
		outer:
		//����ɨ��
		for (int x = 0; x < Chessboard.BOARD_SIZE; x++) {
			for (int y = 0; y < Chessboard.BOARD_SIZE; y++) {
				ico = board[x][y];
				if(ico == "ʮ"){
					continue;
				}
				for (int i = 0; i < factor.length; i+=2) {
					count = 0;
					//����factorָ���ķ���ɨ��
					for (int j = WIN_COUNT*-1; j < WIN_COUNT; j++) {
						int curX = x + factor[i]*j, curY = y + factor[i+1]*j;
						if(checkValid(curX,curY)){
							 if(board[curX][curY].equals(ico)){
								 count++;
							 }else{
								 count = 0;
							 }
							 //����3��
							 if (count == WIN_COUNT - 2){
								//��������
								posX = curX + factor[i];
								posY = curY + factor[i+1];
								if(checkValid(posX,posY) && board[posX][posY] == "ʮ"){
									isDecided = true;
									break outer;
								}
								//��������
								posX = curX - factor[i]*3;
								posY = curY - factor[i+1]*3;
								if(checkValid(posX,posY) && board[posX][posY] == "ʮ"){
									isDecided = true;
									break outer;
								}
							 }
						}
					}
				}
			}
		}
		if(!isDecided){
			do {
				posX = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
				posY = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
			} while (board[posX][posY] != "ʮ");
		}
		
		int[] result = { posX, posY };
		return result;
	}

	/**
	 * �ж���Ӯ
	 * 
	 * @param posX
	 *            ���ӵ�X���ꡣ
	 * @param posY
	 *            ���ӵ�Y����
	 * @param ico
	 *            ��������
	 * @return ��������������������һ��ֱ�ӣ������棬�����෴��
	 */
	public boolean isWon(int posX, int posY, String ico) {
		String[][] board = chessboard.getBoard();
		//����ϵ�����ֱ�Ϊ��/������\������-������|��
		int factor[] = {1,1,1,-1,1,0,0,1};
		//��������
		int count; 
		for (int i = 0; i < factor.length; i+=2) {
			count = 0;
			//����factorָ���ķ���ɨ��
			for (int j = WIN_COUNT*-1; j < WIN_COUNT; j++) {
				int newX = posX + factor[i]*j, newY = posY + factor[i+1]*j;
				if(checkValid(newX, newY)){
					 if(board[newX][newY].equals(ico)){
						 count++;
					 }else{
						 count = 0;
					 }
					 //����5��
					 if (count == WIN_COUNT){
						 return true;
					 }
				}
			}
		}
		return false;
	}

	private boolean checkValid(int posX, int posY){
		return posX >= 0 && posX < Chessboard.BOARD_SIZE && 
				posY >= 0 && posY < Chessboard.BOARD_SIZE;
	}
	
	public static void main(String[] args) throws Exception {

		GobangGame gb = new GobangGame(new Chessboard());
		gb.start();
	}
}
