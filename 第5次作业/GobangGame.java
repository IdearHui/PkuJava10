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
		String color = Chessman.BLACK.getChessman();
		int x_1=1,x_2=1,y_1=1,y_2=1,xy_l=1,xy_r=1,xy_ll=1,xy_rr=1;//�˸�����
		for(int px=0;px<Chessboard.BOARD_SIZE-1;px++){
			for(int py=0;py<Chessboard.BOARD_SIZE-1;py++){
				while(board[px][py]!="ʮ"){
					for(int i=px;i<WIN_COUNT-1+px
							&&i<Chessboard.BOARD_SIZE-1;i++){//x������
						if(board[i][py].equals(color)){
							x_1++;
							if(x_1==3){
								posX = i+1;
								posY = py;
								break;
							}
						}
					}
					for(int i=px;i<px-WIN_COUNT+1&&i>1;i++){//x������
						if(board[i][py].equals(color)){
							x_2++;
							if(x_2==3){
								posX = i-1;
								posY = py;
								break;
							}
						}
					}
					for(int i=py;i<WIN_COUNT-1+py
							&&i<Chessboard.BOARD_SIZE-1;i++){//y������
						if(board[px][i].equals(color)){
							y_1++;
							if(y_1==3){
								posY = i+1;
								posX = px;
								break;
							}
						}
					}
					for(int i=py;i<py-WIN_COUNT+1&&i>1;i++){//y������
						if(board[px][i].equals(color)){
							y_2++;
							if(y_2==3){
								posY = i-1;
								posX = px;
								break;
							}
						}
					}
					for(int i=px;i<px+WIN_COUNT-1
							&&i<Chessboard.BOARD_SIZE-1;i++){//xy��б������
						for(int j=py;j<WIN_COUNT-1+py
								&&j<Chessboard.BOARD_SIZE-1;j++){
							if(board[i][j].equals(color)){
								xy_l++;
								if(xy_l==3){
									posX = i+1;
									posY = j+1;
									break;
								}
							}
						}
					}
					for(int i=px;i<px-WIN_COUNT+1&&i>1;i++){//xy��б������
						for(int j=py;j<py-WIN_COUNT+1&&j>1;j++){
							if(board[i][j].equals(color)){
								xy_l++;
								if(xy_l==3){
									posX = i-1;
									posY = j-1;
									break;
								}
							}
						}
					}
					for(int i=px;i<px-WIN_COUNT+1&&i>1;i++){//xy��б������
						for(int j=py;j<WIN_COUNT-1+py
								&&j<Chessboard.BOARD_SIZE-1;j++){
							if(board[i][j].equals(color)){
								xy_l++;
								if(xy_l==3){
									posX = i-1;
									posY = j+1;
									break;
								}
							}
						}
					}
					for(int i=px;i<px+WIN_COUNT+1
							&&i<Chessboard.BOARD_SIZE-1;i++){//xy��б������
						for(int j=py;j<py-WIN_COUNT+1&&j>1;j++){
							if(board[i][j].equals(color)){
								xy_l++;
								if(xy_l==3){
									posX = i+1;
									posY = j-1;
									break;
								}
							}
						}
					}
				}
			}
		}
		while (board[posX][posY] != "ʮ") {
			posX = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
			posY = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
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
		//����˸���������ӻ��
		int countX_1 = 1,countX_2 = 1,//X��������
				countY_1 = 1,countY_2 = 1,//Y��������
				countL_1 = 1,countL_2 = 1,//2-4������������
				countR_1 = 1,countR_2 = 1;//1-3������������
		//�ж�X�����Ƿ�5������
		for(int i=1;i<WIN_COUNT;i++){
			if(posY+i > 22)//������곬���߽磬���ü�������
				break;
			if(board[posX][posY+i] == ico)//�����X���������������ͬ���͵ģ��������һ
				countX_1 += 1;
			else
				break;//���û����ͬ���͵ģ����ü�������
		}
		for(int i=1;i<WIN_COUNT;i++){
			if(posY-i < 0)//������곬���߽磬���ü�������
				break;
			if(board[posX][posY-i] == ico)//�����X��С�������������ͬ���͵ģ��������һ
				countX_2 += 1;
			else
				break;//���û����ͬ���͵ģ����ü�������
		}
		//�ж�Y�����Ƿ�5������
		for(int i=1;i<WIN_COUNT;i++){
			if(posX+i > 22)
				break;
			if(board[posX+i][posY] == ico)
				countY_1 += 1;
			else
				break;
		}
		for(int i=1;i<WIN_COUNT;i++){
			if(posX-i < 0)
				break;
			if(board[posX-i][posY] == ico)
				countY_2 += 1;
			else
				break;
		}
		//�ж�2/4���޶Խ��߷����Ƿ�5������
		for(int i=1;i<WIN_COUNT;i++){
			if((posX+i > 22) || (posY+i > 22))
				break;
			if(board[posX+i][posY+i] == ico)
				countL_1 += 1;
			else
				break;
		}
		for(int i=1;i<WIN_COUNT;i++){
			if((posX-i < 0) || (posY-i < 0))
				break;
			if(board[posX-i][posY-i] == ico)
				countL_2 += 1;
			else
				break;
		}
		//�ж�1/3���޶Խ��߷����Ƿ�5������
		for(int i=1;i<WIN_COUNT;i++){
			if((posX+i > 22) || (posY-i < 0))
				break;
			if(board[posX+i][posY-i] == ico)
				countR_1 += 1;
			else
				break;
		}
		for(int i=1;i<WIN_COUNT;i++){
			if((posX-i < 0)||(posY+i > 22))
				break;
			if(board[posX-i][posY+i] == ico)
				countR_2 += 1;
			else
				break;
		}
		if((countX_1+countX_2-1)>4 || (countY_1+countY_2-1)>4
				|| (countL_1+countL_2-1)>4 || (countR_1+countR_2-1)>4)
			return true;
		else
			return false;
	}

	private boolean checkValid(int posX, int posY){
		return posX >= 0 && posX < Chessboard.BOARD_SIZE && 
				posY >= 0 && posY < Chessboard.BOARD_SIZE;
	}
	
	public static void main(String[] args) throws Exception {

		GobangGame1 gb = new GobangGame1(new Chessboard());
		gb.start();
	}
}
