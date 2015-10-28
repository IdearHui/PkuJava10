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
	 * 
	 * http://zjh776.iteye.com/blog/1979748
	 * �ο���΢�����������㷨˼·��������ȫ�Լ�ʵ��
	 */
	// ��������ÿ��λ�õķ���ֵ(Խ���м����ֵԽ��)
	private static int[][] score1 = 
	{
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
		{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
		{ 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0},
		{ 0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 8, 7, 6, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,10,9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,10,9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8, 8, 8, 7, 6, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 3, 2, 1, 0},
		{ 0, 1, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 1, 0},
		{ 0, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 0},
		{ 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
	};
	// �Ӻ���ǰ�ֱ��ǳ��������ġ����ġ�������������������߶������ġ������������ķ���ֵ
	private static int[] score2 = {-5, -5, -5, 3, 5, 50, 200, 500, 10000, 100000};
	public int[] computerDo() 
	{
		// ���������
//		int posX = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
//		int posY = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
//		String[][] board = chessboard.getBoard();
//		while (board[posX][posY] != "ʮ") {
//			posX = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
//			posY = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
//		}
//		int[] result = { posX, posY };
//		return result;
		// ��������ݹ�ֵ��������
		int pi = 0;
		int pj = 0;
		int posX = 0;
		int posY = 0;
		int flag = 0;	//���10-1�ֱ�����������ġ����ġ�������������������߶������ġ�����������
		int score = 0;	//��¼������ڸõ�����ķ���
		String ico = Chessman.WHITE.getChessman();
		String[][] board = chessboard.getBoard();
		
		for(int i = 0; i <= Chessboard.BOARD_SIZE - 1; i++)
		{
			for(int j = 0; j <= Chessboard.BOARD_SIZE - 1; j++)
			{
				if(board[i][j] != "ʮ")
				{
					continue;
				}
				flag = 0;
				// "-"
				// ����
				if((j + 1 <= Chessboard.BOARD_SIZE - 1
				   && j + 2 <= Chessboard.BOARD_SIZE - 1
				   && j + 3 <= Chessboard.BOARD_SIZE - 1
				   && j + 4 <= Chessboard.BOARD_SIZE - 1
				   && j + 5 <= Chessboard.BOARD_SIZE - 1
				   && board[i][j + 1] == ico
				   && board[i][j + 2] == ico
				   && board[i][j + 3] == ico
				   && board[i][j + 4] == ico
				   && board[i][j + 5] == ico)	//011111
				   ||
				   (j - 1 >= 0
				   && j - 2 >= 0
				   && j - 3 >= 0
				   && j - 4 >= 0
				   && j - 5 >= 0
				   && board[i][j - 1] == ico
				   && board[i][j - 2] == ico
				   && board[i][j - 3] == ico
				   && board[i][j - 4] == ico
				   && board[i][j - 5] == ico))  //111110
				{
					if(flag < 10)
					{
						flag = 10;
						pi = i;
						pj = j;
					}
				}
				// ����
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == "ʮ")	//011110(��ߵ���)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == "ʮ"))	//011110(�ұߵ���)
				{
					if(flag < 9)
					{
						flag = 9;
						pi = i;
						pj = j;
					}
				}
				// ����
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011112
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211110
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico) //11011
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >=0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j - 1] == ico)	//10111
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j + 1] == ico)) //11101
					
				{
					if(flag < 8)
					{
						flag = 8;
						pi = i;
						pj = j;
					}
				}
				// ����
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == "ʮ"
						&& board[i][j - 1] == "ʮ")	//001110(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == "ʮ"
						&& board[i][j + 1] == "ʮ") //011100(�����ڶ�����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "ʮ"
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ")	//010110(������������)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "ʮ"
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ") //011010(������������)
						)
				{
					if(flag < 7)
					{
						flag = 7;
						pi = i;
						pj = j;
					}
				}
				// ����
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001112(������һ����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211100(������һ����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "ʮ")	//001112(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "ʮ") //211100(�����ڶ�����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ"
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010112(������һ����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ"
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211010(������һ����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ")	//010112(������������)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ") //211010(������������)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "ʮ"
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011012(������һ����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "ʮ"
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210110(������һ����)
					    ||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "ʮ"
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman())	//011012(�������ĸ���)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "ʮ"
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman()) //210110(�������ĸ���)
					    ||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == "ʮ"
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011102(������һ����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == "ʮ"
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //201110(������һ����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == "ʮ"
						&& board[i][j + 1] == Chessman.BLACK.getChessman())	//011102(�����������)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == "ʮ"
						&& board[i][j - 1] == Chessman.BLACK.getChessman()) //201110(�����������)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j - 1] == ico)	//10011(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j + 1] == ico) //11001(�����ڶ�����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == ico)	//10011(������������)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == ico) //11001(������������)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ"
						&& board[i][j + 3] == ico
						&& board[i][j - 1] == ico)	//10101(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ"
						&& board[i][j - 3] == ico
						&& board[i][j + 1] == ico) //10101(�����ڶ�����)
						)
				{
					if(flag < 6)
					{
						flag = 6;
						pi = i;
						pj = j;
					}
				}
				// ���
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "ʮ"
						&& board[i][j - 1] == "ʮ")	//00110(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "ʮ"
						&& board[i][j + 1] == "ʮ") //01100(�����ڶ�����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "ʮ"
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ")	//010010(������������)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "ʮ"
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ") //010010(������������)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ"
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ")	//01010(������������)
						)
				{
					if(flag < 5)
					{
						flag = 5;
						pi = i;
						pj = j;
					}					
				}
				// �߶�
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == "ʮ"
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//000112(������һ����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == "ʮ"
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211000(������һ����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "ʮ")	//000112(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "ʮ") //211000(�����ڶ�����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == "ʮ")	//000112(������������)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == "ʮ") //211000(������������)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "ʮ"
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001012(������һ����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "ʮ"
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210100(������һ����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ"
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "ʮ")	//001012(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ"
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "ʮ") //210100(�����ڶ�����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ"
						&& board[i][j - 3] == "ʮ")	//001012(�������ĸ���)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ"
						&& board[i][j + 3] == "ʮ") //210100(�������ĸ���)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ"
						&& board[i][j + 3] == "ʮ"
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010012(������һ����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ"
						&& board[i][j - 3] == "ʮ"
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210010(������һ����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ")	//010012(������������)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ") //210010(������������)						
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "ʮ")	//010012(�������ĸ���)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "ʮ") //210010(�������ĸ���)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == "ʮ"
						&& board[i][j + 3] == ico
						&& board[i][j - 1] == ico)	//10001(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == "ʮ"
						&& board[i][j - 3] == ico
						&& board[i][j + 1] == ico) //10001(�����ڶ�����)
					    ||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == ico
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == ico)	//10001(������������)						
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
					    && j + 5 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ"
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == "ʮ"
						&& board[i][j + 5] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2010102(�����ڶ�����)
					    ||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ"
					    && board[i][j - 3] == ico
					    && board[i][j - 4] == "ʮ"
						&& board[i][j - 5] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2010102(�����ڶ�����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
					    && j - 2 >= 0
						&& j - 3 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "ʮ"
						&& board[i][j + 3] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "ʮ"
						&& board[i][j - 3] == Chessman.BLACK.getChessman()) //2010102(�������ĸ���)						
						||
					    (j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
					    && j + 5 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "ʮ"
						&& board[i][j + 4] == "ʮ"
						&& board[i][j + 5] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2011002(�����ڶ�����)
					    ||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
					    && board[i][j - 3] == "ʮ"
					    && board[i][j - 4] == "ʮ"
						&& board[i][j - 5] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2001102(�����ڶ�����)
						||
					    (j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
					    && j - 3 >= 0
						&& j - 4 >= 0
						&& board[i][j + 1] == "ʮ"
						&& board[i][j + 2] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "ʮ"
						&& board[i][j - 4] == Chessman.BLACK.getChessman()) //2011002(�����������)
					    ||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == Chessman.BLACK.getChessman()
					    && board[i][j + 1] == ico
					    && board[i][j + 2] == ico
						&& board[i][j + 3] == "ʮ"
						&& board[i][j + 4] == Chessman.BLACK.getChessman())	//2001102(�����������)						
						||
					    (j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
					    && j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j + 1] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "ʮ"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == "ʮ"
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //2011002(������������)
					    ||
						(j - 1 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "ʮ"
					    && board[i][j + 2] == ico
					    && board[i][j + 3] == ico
						&& board[i][j + 4] == "ʮ"
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//2001102(������������)
						)
				{
					if(flag < 4)
					{
						flag = 4;
						pi = i;
						pj = j;
					}
				}
				// ����
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == Chessman.BLACK.getChessman())	//201112(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(�����ڶ�����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman())	//210112(������������)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 >= 0
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman()) //211012(������������)
						)
				{
					if(flag < 3)
					{
						flag = 3;
						pi = i;
						pj = j;
					}
				}
				// ����
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == Chessman.BLACK.getChessman())	//20112(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(�����ڶ�����)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman())	//21012(������������)
						)
				{
					if(flag < 2)
					{
						flag = 2;
						pi = i;
						pj = j;
					}
				}
				// ����
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == Chessman.BLACK.getChessman())	//2012(�����ڶ�����)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == Chessman.BLACK.getChessman()) //2102(�����ڶ�����)
						)
				{
					if(flag < 1)
					{
						flag = 1;
						pi = i;
						pj = j;
					}
				}
				//"|"
				if((i + 1 <= Chessboard.BOARD_SIZE - 1
						   && i + 2 <= Chessboard.BOARD_SIZE - 1
						   && i + 3 <= Chessboard.BOARD_SIZE - 1
						   && i + 4 <= Chessboard.BOARD_SIZE - 1
						   && i + 5 <= Chessboard.BOARD_SIZE - 1
						   && board[i + 1][j] == ico
						   && board[i + 2][j] == ico
						   && board[i + 3][j] == ico
						   && board[i + 4][j] == ico
						   && board[i + 5][j] == ico)	//011111
						   ||
						   (i - 1 >= 0
						   && i - 2 >= 0
						   && i - 3 >= 0
						   && i - 4 >= 0
						   && i - 5 >= 0
						   && board[i - 1][j] == ico
						   && board[i - 1][j] == ico
						   && board[i - 1][j] == ico
						   && board[i - 1][j] == ico
						   && board[i - 1][j] == ico))  //111110
						{
							if(flag < 10)
							{
								flag = 10;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == "ʮ")	//011110(��ߵ���)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == "ʮ"))	//011110(�ұߵ���)
						{
							if(flag < 9)
							{
								flag = 9;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//011112
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //211110
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i - 1][j] == ico
								&& board[i - 1][j] == ico) //11011
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >=0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i - 1][j] == ico)	//10111
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i + 1][j] == ico)) //11101
							
						{
							if(flag < 8)
							{
								flag = 8;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == "ʮ"
								&& board[i - 1][j] == "ʮ")	//001110(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == "ʮ"
								&& board[i + 1][j] == "ʮ") //011100(�����ڶ�����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "ʮ"
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ")	//010110(������������)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "ʮ"
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ") //011010(������������)
								)
						{
							if(flag < 7)
							{
								flag = 7;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//001112(������һ����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //211100(������һ����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "ʮ")	//001112(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "ʮ") //211100(�����ڶ�����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ"
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//010112(������һ����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ"
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //211010(������һ����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ")	//010112(������������)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ") //211010(������������)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "ʮ"
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//011012(������һ����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "ʮ"
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //210110(������һ����)
							    ||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "ʮ"
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman())	//011012(�������ĸ���)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "ʮ"
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman()) //210110(�������ĸ���)
							    ||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == "ʮ"
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//011102(������һ����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == "ʮ"
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //201110(������һ����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == "ʮ"
								&& board[i + 1][j] == Chessman.BLACK.getChessman())	//011102(�����������)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == "ʮ"
								&& board[i - 1][j] == Chessman.BLACK.getChessman()) //201110(�����������)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i - 1][j] == ico)	//10011(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i + 1][j] == ico) //11001(�����ڶ�����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == ico)	//10011(������������)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == ico) //11001(������������)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ"
								&& board[i + 3][j] == ico
								&& board[i - 1][j] == ico)	//10101(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ"
								&& board[i - 3][j] == ico
								&& board[i + 1][j] == ico) //10101(�����ڶ�����)
								)
						{
							if(flag < 6)
							{
								flag = 6;
								pi = i;
								pj = j;
							}
						}
						// ���
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "ʮ"
								&& board[i - 1][j] == "ʮ")	//00110(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "ʮ"
								&& board[i + 1][j] == "ʮ") //01100(�����ڶ�����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "ʮ"
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ")	//010010(������������)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "ʮ"
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ") //010010(������������)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ"
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ")	//01010(������������)
								)
						{
							if(flag < 5)
							{
								flag = 5;
								pi = i;
								pj = j;
							}					
						}
						// �߶�
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == "ʮ"
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//000112(������һ����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == "ʮ"
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //211000(������һ����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "ʮ")	//000112(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "ʮ") //211000(�����ڶ�����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "ʮ"
								&& board[i - 1][j] == "ʮ")	//000112(������������)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == "ʮ") //211000(������������)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "ʮ"
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//001012(������һ����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "ʮ"
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //210100(������һ����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ"
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "ʮ")	//001012(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ"
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "ʮ") //210100(�����ڶ�����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ"
								&& board[i - 3][j] == "ʮ")	//001012(�������ĸ���)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ"
								&& board[i + 3][j] == "ʮ") //210100(�������ĸ���)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ"
								&& board[i + 3][j] == "ʮ"
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//010012(������һ����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ"
								&& board[i - 3][j] == "ʮ"
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //210010(������һ����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ")	//010012(������������)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ") //210010(������������)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "ʮ")	//010012(�������ĸ���)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "ʮ") //210010(�������ĸ���)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == "ʮ"
								&& board[i + 3][j] == ico
								&& board[i - 1][j] == ico)	//10001(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == "ʮ"
								&& board[i - 3][j] == ico
								&& board[i + 1][j] == ico) //10001(�����ڶ�����)
							    ||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == ico
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == ico)	//10001(������������)						
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
							    && i + 5 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ"
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == "ʮ"
								&& board[i + 5][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == Chessman.BLACK.getChessman()) //2010102(�����ڶ�����)
							    ||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ"
							    && board[i - 3][j] == ico
							    && board[i - 4][j] == "ʮ"
								&& board[i - 5][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == Chessman.BLACK.getChessman())	//2010102(�����ڶ�����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
							    && i - 2 >= 0
								&& i - 3 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "ʮ"
								&& board[i + 3][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "ʮ"
								&& board[i - 3][j] == Chessman.BLACK.getChessman()) //2010102(�������ĸ���)						
								||
							    (i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
							    && i + 5 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "ʮ"
								&& board[i + 4][j] == "ʮ"
								&& board[i + 5][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == Chessman.BLACK.getChessman()) //2011002(�����ڶ�����)
							    ||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
							    && board[i - 3][j] == "ʮ"
							    && board[i - 4][j] == "ʮ"
								&& board[i - 5][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == Chessman.BLACK.getChessman())	//2001102(�����ڶ�����)
								||
							    (i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
							    && i - 3 >= 0
								&& i - 4 >= 0
								&& board[i + 1][j] == "ʮ"
								&& board[i + 2][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "ʮ"
								&& board[i - 4][j] == Chessman.BLACK.getChessman()) //2011002(�����������)
							    ||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == Chessman.BLACK.getChessman()
							    && board[i + 1][j] == ico
							    && board[i + 2][j] == ico
								&& board[i + 3][j] == "ʮ"
								&& board[i + 4][j] == Chessman.BLACK.getChessman())	//2001102(�����������)
								||
							    (i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
							    && i - 4 >= 0
								&& i - 5 >= 0
								&& board[i + 1][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "ʮ"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == "ʮ"
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //2011002(������������)
							    ||
								(i - 1 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "ʮ"
							    && board[i + 2][j] == ico
							    && board[i + 3][j] == ico
								&& board[i + 4][j] == "ʮ"
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//2001102(������������)
								)
						{
							if(flag < 4)
							{
								flag = 4;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == Chessman.BLACK.getChessman())	//201112(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == Chessman.BLACK.getChessman()) //211102(�����ڶ�����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman())	//210112(������������)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 >= 0
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman()) //211012(������������)
								
								)
						{
							if(flag < 3)
							{
								flag = 3;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == Chessman.BLACK.getChessman())	//20112(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == Chessman.BLACK.getChessman()) //211102(�����ڶ�����)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman())	//21012(������������)
								)
						{
							if(flag < 2)
							{
								flag = 2;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == Chessman.BLACK.getChessman())	//2012(�����ڶ�����)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == Chessman.BLACK.getChessman()) //2102(�����ڶ�����)
								)
						{
							if(flag < 1)
							{
								flag = 1;
								pi = i;
								pj = j;
							}
						}
				//���´����i,j����Ҫ��д
				//"/"
				if((j + 1 <= Chessboard.BOARD_SIZE - 1
						   && j + 2 <= Chessboard.BOARD_SIZE - 1
						   && j + 3 <= Chessboard.BOARD_SIZE - 1
						   && j + 4 <= Chessboard.BOARD_SIZE - 1
						   && j + 5 <= Chessboard.BOARD_SIZE - 1
						   && board[i][j + 1] == ico
						   && board[i][j + 2] == ico
						   && board[i][j + 3] == ico
						   && board[i][j + 4] == ico
						   && board[i][j + 5] == ico)	//011111
						   ||
						   (j - 1 >= 0
						   && j - 2 >= 0
						   && j - 3 >= 0
						   && j - 4 >= 0
						   && j - 5 >= 0
						   && board[i][j - 1] == ico
						   && board[i][j - 2] == ico
						   && board[i][j - 3] == ico
						   && board[i][j - 4] == ico
						   && board[i][j - 5] == ico))  //111110
						{
							if(flag < 10)
							{
								flag = 10;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == "ʮ")	//011110(��ߵ���)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == "ʮ"))	//011110(�ұߵ���)
						{
							if(flag < 9)
							{
								flag = 9;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011112
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211110
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico) //11011
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >=0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10111
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico)) //11101
							
						{
							if(flag < 8)
							{
								flag = 8;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "ʮ"
								&& board[i][j - 1] == "ʮ")	//001110(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "ʮ"
								&& board[i][j + 1] == "ʮ") //011100(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ")	//010110(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ") //011010(������������)
								)
						{
							if(flag < 7)
							{
								flag = 7;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001112(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211100(������һ����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ")	//001112(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ") //211100(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010112(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211010(������һ����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ")	//010112(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ") //211010(������������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011012(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210110(������һ����)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman())	//011012(�������ĸ���)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()) //210110(�������ĸ���)
							    ||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "ʮ"
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011102(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "ʮ"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //201110(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "ʮ"
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//011102(�����������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "ʮ"
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //201110(�����������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10011(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //11001(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico)	//10011(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico) //11001(������������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10101(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //10101(�����ڶ�����)
								)
						{
							if(flag < 6)
							{
								flag = 6;
								pi = i;
								pj = j;
							}
						}
						// ���
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j - 1] == "ʮ")	//00110(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j + 1] == "ʮ") //01100(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ")	//010010(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ") //010010(������������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ")	//01010(������������)
								)
						{
							if(flag < 5)
							{
								flag = 5;
								pi = i;
								pj = j;
							}					
						}
						// �߶�
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//000112(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211000(������һ����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ")	//000112(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ") //211000(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == "ʮ")	//000112(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == "ʮ") //211000(������������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001012(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210100(������һ����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ")	//001012(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ") //210100(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == "ʮ")	//001012(�������ĸ���)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == "ʮ") //210100(�������ĸ���)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == "ʮ"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010012(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == "ʮ"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210010(������һ����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ")	//010012(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ") //210010(������������)
								
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ")	//010012(�������ĸ���)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ") //210010(�������ĸ���)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10001(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //10001(�����ڶ�����)
							    ||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico)	//10001(������������)						
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
							    && j + 5 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "ʮ"
								&& board[i][j + 5] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2010102(�����ڶ�����)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
							    && board[i][j - 3] == ico
							    && board[i][j - 4] == "ʮ"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2010102(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
							    && j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == Chessman.BLACK.getChessman()) //2010102(�������ĸ���)						
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
							    && j + 5 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j + 4] == "ʮ"
								&& board[i][j + 5] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2011002(�����ڶ�����)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
							    && board[i][j - 3] == "ʮ"
							    && board[i][j - 4] == "ʮ"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2001102(�����ڶ�����)
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
							    && j - 3 >= 0
								&& j - 4 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j - 4] == Chessman.BLACK.getChessman()) //2011002(�����������)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
							    && board[i][j + 1] == ico
							    && board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j + 4] == Chessman.BLACK.getChessman())	//2001102(�����������)
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
							    && j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j + 1] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "ʮ"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //2011002(������������)
							    ||
								(j - 1 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ"
							    && board[i][j + 2] == ico
							    && board[i][j + 3] == ico
								&& board[i][j + 4] == "ʮ"
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//2001102(������������)
								)
						{
							if(flag < 4)
							{
								flag = 4;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//201112(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman())	//210112(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 >= 0
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()) //211012(������������)
								)
						{
							if(flag < 3)
							{
								flag = 3;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//20112(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman())	//21012(������������)
								)
						{
							if(flag < 2)
							{
								flag = 2;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//2012(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //2102(�����ڶ�����)
								)
						{
							if(flag < 1)
							{
								flag = 1;
								pi = i;
								pj = j;
							}
						}
				
				
				//"\"
				if((j + 1 <= Chessboard.BOARD_SIZE - 1
						   && j + 2 <= Chessboard.BOARD_SIZE - 1
						   && j + 3 <= Chessboard.BOARD_SIZE - 1
						   && j + 4 <= Chessboard.BOARD_SIZE - 1
						   && j + 5 <= Chessboard.BOARD_SIZE - 1
						   && board[i][j + 1] == ico
						   && board[i][j + 2] == ico
						   && board[i][j + 3] == ico
						   && board[i][j + 4] == ico
						   && board[i][j + 5] == ico)	//011111
						   ||
						   (j - 1 >= 0
						   && j - 2 >= 0
						   && j - 3 >= 0
						   && j - 4 >= 0
						   && j - 5 >= 0
						   && board[i][j - 1] == ico
						   && board[i][j - 2] == ico
						   && board[i][j - 3] == ico
						   && board[i][j - 4] == ico
						   && board[i][j - 5] == ico))  //111110
						{
							if(flag < 10)
							{
								flag = 10;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == "ʮ")	//011110(��ߵ���)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == "ʮ"))	//011110(�ұߵ���)
						{
							if(flag < 9)
							{
								flag = 9;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011112
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211110
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico) //11011
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >=0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10111
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico)) //11101
							
						{
							if(flag < 8)
							{
								flag = 8;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "ʮ"
								&& board[i][j - 1] == "ʮ")	//001110(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "ʮ"
								&& board[i][j + 1] == "ʮ") //011100(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ")	//010110(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ") //011010(������������)
								)
						{
							if(flag < 7)
							{
								flag = 7;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001112(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211100(������һ����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ")	//001112(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ") //211100(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010112(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211010(������һ����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ")	//010112(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ") //211010(������������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011012(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210110(������һ����)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman())	//011012(�������ĸ���)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()) //210110(�������ĸ���)
							    ||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "ʮ"
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011102(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "ʮ"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //201110(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "ʮ"
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//011102(�����������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "ʮ"
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //201110(�����������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10011(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //11001(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico)	//10011(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico) //11001(������������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10101(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //10101(�����ڶ�����)
								)
						{
							if(flag < 6)
							{
								flag = 6;
								pi = i;
								pj = j;
							}
						}
						// ���
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j - 1] == "ʮ")	//00110(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j + 1] == "ʮ") //01100(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ")	//010010(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ") //010010(������������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ")	//01010(������������)
								)
						{
							if(flag < 5)
							{
								flag = 5;
								pi = i;
								pj = j;
							}					
						}
						// �߶�
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//000112(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211000(������һ����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ")	//000112(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ") //211000(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == "ʮ")	//000112(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == "ʮ") //211000(������������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001012(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210100(������һ����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ")	//001012(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ") //210100(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == "ʮ")	//001012(�������ĸ���)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == "ʮ") //210100(�������ĸ���)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == "ʮ"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010012(������һ����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == "ʮ"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210010(������һ����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ")	//010012(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ") //210010(������������)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ")	//010012(�������ĸ���)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ") //210010(�������ĸ���)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10001(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //10001(�����ڶ�����)
							    ||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == ico
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico)	//10001(������������)						
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
							    && j + 5 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "ʮ"
								&& board[i][j + 5] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2010102(�����ڶ�����)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
							    && board[i][j - 3] == ico
							    && board[i][j - 4] == "ʮ"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2010102(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
							    && j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "ʮ"
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "ʮ"
								&& board[i][j - 3] == Chessman.BLACK.getChessman()) //2010102(�������ĸ���)						
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
							    && j + 5 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j + 4] == "ʮ"
								&& board[i][j + 5] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2011002(�����ڶ�����)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
							    && board[i][j - 3] == "ʮ"
							    && board[i][j - 4] == "ʮ"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2001102(�����ڶ�����)
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
							    && j - 3 >= 0
								&& j - 4 >= 0
								&& board[i][j + 1] == "ʮ"
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "ʮ"
								&& board[i][j - 4] == Chessman.BLACK.getChessman()) //2011002(�����������)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
							    && board[i][j + 1] == ico
							    && board[i][j + 2] == ico
								&& board[i][j + 3] == "ʮ"
								&& board[i][j + 4] == Chessman.BLACK.getChessman())	//2001102(�����������)
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
							    && j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j + 1] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "ʮ"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "ʮ"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //2011002(������������)
							    ||
								(j - 1 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "ʮ"
							    && board[i][j + 2] == ico
							    && board[i][j + 3] == ico
								&& board[i][j + 4] == "ʮ"
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//2001102(������������)
								)
						{
							if(flag < 4)
							{
								flag = 4;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//201112(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman())	//210112(������������)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 >= 0
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()) //211012(������������)
								
								)
						{
							if(flag < 3)
							{
								flag = 3;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//20112(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(�����ڶ�����)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman())	//21012(������������)
								)
						{
							if(flag < 2)
							{
								flag = 2;
								pi = i;
								pj = j;
							}
						}
						// ����
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//2012(�����ڶ�����)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //2102(�����ڶ�����)
								)
						{
							if(flag < 1)
							{
								flag = 1;
								pi = i;
								pj = j;
							}
						}
				if(flag == 0 && score < score1[i][j])
				{
					score = score1[i][j];
					posX = pi;
					posY = pj;
				}
				else if(flag > 0 && score < score1[i][j] + score2[flag - 1])
				{
					score = score1[i][j] + score2[flag - 1];
					posX = pi;
					posY = pj;
				}
			}
		}
		// �����㷨������С��������-�¼�֦
		int[] result = {posX, posY};
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
	public boolean isWon(int posX, int posY, String ico) 
	{
		String[][] board = chessboard.getBoard();
		int tmpX = posX, tmpY = posY, cnt = 1;
		// "-"
		tmpY = posY;
		cnt = 1;
		while(tmpY - 1 >= 0 && board[tmpX][tmpY - 1] == ico)
		{
			cnt++;
			if(cnt >= 5)
			{
				return true;
			}
			tmpY--;
		}
		tmpY = posY;
		while(tmpY + 1 <= Chessboard.BOARD_SIZE - 1 && board[tmpX][tmpY + 1] == ico)
		{
			cnt++;
			if(cnt >= 5)
			{
				return true;
			}
			tmpY++;
		}
		// "|"		
		tmpY = posY;
		while(tmpX - 1 >= 0 && board[tmpX - 1][tmpY] == ico)
		{
			cnt++;
			if(cnt >= 5)
			{
				return true;
			}
			tmpX--;
		}
		tmpX = posX;
		while(tmpX + 1 <= Chessboard.BOARD_SIZE - 1 && board[tmpX + 1][tmpY] == ico)
		{
			cnt++;
			if(cnt >= 5)
			{
				return true;
			}
			tmpX++;
		}
		// "/"
		tmpX = posX;
		cnt = 1;
		while(tmpX + 1 <= Chessboard.BOARD_SIZE - 1 && tmpY - 1 >= 0 && board[tmpX + 1][tmpY - 1] == ico)
		{
			cnt++;
			if(cnt >= 5)
			{
				return true;
			}
			tmpX++;
			tmpY--;
		}
		tmpX = posX;
		tmpY = posY;
		while(tmpX - 1 >= 0 && tmpY + 1 <= Chessboard.BOARD_SIZE - 1 && board[tmpX - 1][tmpY + 1] == ico)
		{
			cnt++;
			if(cnt >= 5)
			{
				return true;
			}
			tmpX--;
			tmpY++;
		}
		// "\"
		tmpX = posX;
		tmpY = posY;
		cnt = 1;
		while(tmpX - 1 >= 0 && tmpY - 1 >= 0 && board[tmpX - 1][tmpY - 1] == ico)
		{
			cnt++;
			if(cnt >= 5)
			{
				return true;
			}
			tmpX--;
			tmpY--;
		}
		tmpX = posX;
		tmpY = posY;
		while(tmpX + 1 <= Chessboard.BOARD_SIZE - 1 && tmpY + 1 <= Chessboard.BOARD_SIZE - 1 && board[tmpX + 1][tmpY + 1] == ico)
		{
			cnt++;
			if(cnt >= 5)
			{
				return true;
			}
			tmpX++;
			tmpY++;
		}
		return false;
	}

	public static void main(String[] args) throws Exception {

		GobangGame gb = new GobangGame(new Chessboard());
		gb.start();
	}
}
