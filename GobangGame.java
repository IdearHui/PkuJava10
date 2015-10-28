import java.io.BufferedReader;
import java.io.InputStreamReader;

 
public class GobangGame {
	// 定义达到赢条件的棋子数目
	private final int WIN_COUNT = 5;
	// 定义用户输入的X坐标
	private int posX = 0;
	// 定义用户输入的X坐标
	private int posY = 0;
	// 定义棋盘
	private Chessboard chessboard;

	/**
	 * 空构造器
	 */
	public GobangGame() {
	}

	/**
	 * 构造器，初始化棋盘和棋子属性
	 * 
	 * @param chessboard
	 *            棋盘类
	 */
	public GobangGame(Chessboard chessboard) {
		this.chessboard = chessboard;
	}

	/**
	 * 检查输入是否合法。
	 * 
	 * @param inputStr
	 *            由控制台输入的字符串。
	 * @return 字符串合法返回true,反则返回false。
	 */
	public boolean isValid(String inputStr) {
		// 将用户输入的字符串以逗号(,)作为分隔，分隔成两个字符串
		String[] posStrArr = inputStr.split(",");
		try {
			posX = Integer.parseInt(posStrArr[0]) - 1;
			posY = Integer.parseInt(posStrArr[1]) - 1;
		} catch (NumberFormatException e) {
			chessboard.printBoard();
			System.out.println("请以(数字,数字)的格式输入：");
			return false;
		}
		// 检查输入数值是否在范围之内
		if (posX < 0 || posX >= Chessboard.BOARD_SIZE || posY < 0
				|| posY >= Chessboard.BOARD_SIZE) {
			chessboard.printBoard();
			System.out.println("X与Y坐标只能大于等于1,与小于等于" + Chessboard.BOARD_SIZE
					+ ",请重新输入：");
			return false;
		}
		// 检查输入的位置是否已经有棋子
		String[][] board = chessboard.getBoard();
		if (board[posX][posY] != "十") {
			chessboard.printBoard();
			System.out.println("此位置已经有棋子，请重新输入：");
			return false;
		}
		return true;
	}

	/**
	 * 开始下棋
	 */
	public void start() throws Exception {
		// true为游戏结束
		boolean isOver = false;
		chessboard.initBoard();
		chessboard.printBoard();
		// 获取键盘的输入
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputStr = null;
		// br.readLine:每当键盘输入一行内容按回车键，则输入的内容被br读取到
		while ((inputStr = br.readLine()) != null) {
			isOver = false;
			if (!isValid(inputStr)) {
				// 如果不合法，要求重新输入，再继续
				continue;
			}
			// 把对应的数组元素赋为"●"
			String chessman = Chessman.BLACK.getChessman();
			chessboard.setBoard(posX, posY, chessman);
			// 判断用户是否赢了
			if (isWon(posX, posY, chessman)) {
				isOver = true;

			} else {
				// 计算机随机选择位置坐标
				int[] computerPosArr = computerDo();
				chessman = Chessman.WHITE.getChessman();
				chessboard.setBoard(computerPosArr[0], computerPosArr[1],
						chessman);
				// 判断计算机是否赢了
				if (isWon(computerPosArr[0], computerPosArr[1], chessman)) {
					isOver = true;
				}
			}
			// 如果产生胜者，询问用户是否继续游戏
			if (isOver) {
				// 如果继续，重新初始化棋盘，继续游戏
				if (isReplay(chessman)) {
					chessboard.initBoard();
					chessboard.printBoard();
					continue;
				}
				// 如果不继续，退出程序
				break;
			}
			chessboard.printBoard();
			System.out.println("请输入您下棋的坐标，应以x,y的格式输入：");
		}
	}

	/**
	 * 是否重新开始下棋。
	 * 
	 * @param chessman
	 *            "●"为用户，"○"为计算机。
	 * @return 开始返回true，反则返回false。
	 */
	public boolean isReplay(String chessman) throws Exception {
		chessboard.printBoard();
		String message = chessman.equals(Chessman.BLACK.getChessman()) ? "恭喜您，您赢了，"
				: "很遗憾，您输了，";
		System.out.println(message + "再下一局？(y/n)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		if (br.readLine().equals("y")) {
			// 开始新一局
			return true;
		}
		return false;

	}

	/**
	 * 计算机随机下棋
	 * 
	 * http://zjh776.iteye.com/blog/1979748
	 * 参考此微博的五子棋算法思路，代码完全自己实现
	 */
	// 棋盘上面每个位置的分数值(越往中间分数值越高)
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
	// 从后往前分别是长连、活四、冲四、活三、眠三、活二、眠二、死四、死三、死二的分数值
	private static int[] score2 = {-5, -5, -5, 3, 5, 50, 200, 500, 10000, 100000};
	public int[] computerDo() 
	{
		// 计算机下棋
//		int posX = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
//		int posY = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
//		String[][] board = chessboard.getBoard();
//		while (board[posX][posY] != "十") {
//			posX = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
//			posY = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
//		}
//		int[] result = { posX, posY };
//		return result;
		// 计算机根据估值函数下棋
		int pi = 0;
		int pj = 0;
		int posX = 0;
		int posY = 0;
		int flag = 0;	//标记10-1分别代表长连、活四、冲四、活三、眠三、活二、眠二、死四、死三、死二
		int score = 0;	//记录计算机在该点下棋的分数
		String ico = Chessman.WHITE.getChessman();
		String[][] board = chessboard.getBoard();
		
		for(int i = 0; i <= Chessboard.BOARD_SIZE - 1; i++)
		{
			for(int j = 0; j <= Chessboard.BOARD_SIZE - 1; j++)
			{
				if(board[i][j] != "十")
				{
					continue;
				}
				flag = 0;
				// "-"
				// 长连
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
				// 活四
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == "十")	//011110(左边的零)
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
						&& board[i][j - 5] == "十"))	//011110(右边的零)
				{
					if(flag < 9)
					{
						flag = 9;
						pi = i;
						pj = j;
					}
				}
				// 冲四
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
				// 活三
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == "十"
						&& board[i][j - 1] == "十")	//001110(正数第二个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == "十"
						&& board[i][j + 1] == "十") //011100(倒数第二个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "十"
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十")	//010110(正数第三个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "十"
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十") //011010(倒数第三个零)
						)
				{
					if(flag < 7)
					{
						flag = 7;
						pi = i;
						pj = j;
					}
				}
				// 眠三
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001112(正数第一个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211100(倒数第一个零)
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
						&& board[i][j - 1] == "十")	//001112(正数第二个零)
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
						&& board[i][j + 1] == "十") //211100(倒数第二个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十"
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010112(正数第一个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十"
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211010(倒数第一个零)
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
						&& board[i][j - 2] == "十")	//010112(正数第三个零)
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
						&& board[i][j + 2] == "十") //211010(倒数第三个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "十"
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011012(正数第一个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "十"
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210110(倒数第一个零)
					    ||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "十"
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman())	//011012(正数第四个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "十"
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman()) //210110(倒数第四个零)
					    ||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == "十"
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011102(正数第一个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == "十"
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //201110(倒数第一个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == "十"
						&& board[i][j + 1] == Chessman.BLACK.getChessman())	//011102(正数第五个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == "十"
						&& board[i][j - 1] == Chessman.BLACK.getChessman()) //201110(倒数第五个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j - 1] == ico)	//10011(正数第二个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j + 1] == ico) //11001(倒数第二个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == ico)	//10011(正数第三个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == ico) //11001(倒数第三个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十"
						&& board[i][j + 3] == ico
						&& board[i][j - 1] == ico)	//10101(正数第二个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十"
						&& board[i][j - 3] == ico
						&& board[i][j + 1] == ico) //10101(倒数第二个零)
						)
				{
					if(flag < 6)
					{
						flag = 6;
						pi = i;
						pj = j;
					}
				}
				// 活二
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "十"
						&& board[i][j - 1] == "十")	//00110(正数第二个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "十"
						&& board[i][j + 1] == "十") //01100(倒数第二个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "十"
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十")	//010010(正数第三个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "十"
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十") //010010(倒数第三个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十"
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十")	//01010(正数第三个零)
						)
				{
					if(flag < 5)
					{
						flag = 5;
						pi = i;
						pj = j;
					}					
				}
				// 眠二
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == "十"
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//000112(正数第一个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == "十"
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211000(倒数第一个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "十")	//000112(正数第二个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "十") //211000(倒数第二个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == "十")	//000112(正数第三个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == "十") //211000(倒数第三个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "十"
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001012(正数第一个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "十"
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210100(倒数第一个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十"
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "十")	//001012(正数第二个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十"
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "十") //210100(倒数第二个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十"
						&& board[i][j - 3] == "十")	//001012(正数第四个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十"
						&& board[i][j + 3] == "十") //210100(倒数第四个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十"
						&& board[i][j + 3] == "十"
						&& board[i][j + 4] == ico
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010012(正数第一个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十"
						&& board[i][j - 3] == "十"
						&& board[i][j - 4] == ico
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210010(倒数第一个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十")	//010012(正数第三个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十") //210010(倒数第三个零)						
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "十")	//010012(正数第四个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "十") //210010(倒数第四个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == "十"
						&& board[i][j + 3] == ico
						&& board[i][j - 1] == ico)	//10001(正数第二个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == "十"
						&& board[i][j - 3] == ico
						&& board[i][j + 1] == ico) //10001(倒数第二个零)
					    ||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == ico
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == ico)	//10001(正数第三个零)						
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
					    && j + 5 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十"
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == "十"
						&& board[i][j + 5] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2010102(正数第二个零)
					    ||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十"
					    && board[i][j - 3] == ico
					    && board[i][j - 4] == "十"
						&& board[i][j - 5] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2010102(倒数第二个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
					    && j - 2 >= 0
						&& j - 3 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == "十"
						&& board[i][j + 3] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == "十"
						&& board[i][j - 3] == Chessman.BLACK.getChessman()) //2010102(正数第四个零)						
						||
					    (j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
					    && j + 5 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == "十"
						&& board[i][j + 4] == "十"
						&& board[i][j + 5] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2011002(正数第二个零)
					    ||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j - 4 >= 0
						&& j - 5 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
					    && board[i][j - 3] == "十"
					    && board[i][j - 4] == "十"
						&& board[i][j - 5] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2001102(倒数第二个零)
						||
					    (j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
					    && j - 3 >= 0
						&& j - 4 >= 0
						&& board[i][j + 1] == "十"
						&& board[i][j + 2] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == "十"
						&& board[i][j - 4] == Chessman.BLACK.getChessman()) //2011002(正数第五个零)
					    ||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == Chessman.BLACK.getChessman()
					    && board[i][j + 1] == ico
					    && board[i][j + 2] == ico
						&& board[i][j + 3] == "十"
						&& board[i][j + 4] == Chessman.BLACK.getChessman())	//2001102(倒数第五个零)						
						||
					    (j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
					    && j - 4 >= 0
						&& j - 5 >= 0
						&& board[i][j + 1] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == "十"
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == ico
						&& board[i][j - 4] == "十"
						&& board[i][j - 5] == Chessman.BLACK.getChessman()) //2011002(正数第六个零)
					    ||
						(j - 1 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j + 5 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == "十"
					    && board[i][j + 2] == ico
					    && board[i][j + 3] == ico
						&& board[i][j + 4] == "十"
						&& board[i][j + 5] == Chessman.BLACK.getChessman())	//2001102(倒数第六个零)
						)
				{
					if(flag < 4)
					{
						flag = 4;
						pi = i;
						pj = j;
					}
				}
				// 死四
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j + 4 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == ico
						&& board[i][j + 4] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == Chessman.BLACK.getChessman())	//201112(正数第二个零)
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
						&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(倒数第二个零)
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
						&& board[i][j - 2] == Chessman.BLACK.getChessman())	//210112(正数第三个零)
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
						&& board[i][j + 2] == Chessman.BLACK.getChessman()) //211012(倒数第三个零)
						)
				{
					if(flag < 3)
					{
						flag = 3;
						pi = i;
						pj = j;
					}
				}
				// 死三
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j + 3 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == ico
						&& board[i][j + 3] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == Chessman.BLACK.getChessman())	//20112(正数第二个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j - 3 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == ico
						&& board[i][j - 3] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(倒数第二个零)
						||
						(j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& j - 2 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman())	//21012(正数第三个零)
						)
				{
					if(flag < 2)
					{
						flag = 2;
						pi = i;
						pj = j;
					}
				}
				// 死二
				else if((j + 1 <= Chessboard.BOARD_SIZE - 1
						&& j + 2 <= Chessboard.BOARD_SIZE - 1
						&& j - 1 >= 0
						&& board[i][j + 1] == ico
						&& board[i][j + 2] == Chessman.BLACK.getChessman()
						&& board[i][j - 1] == Chessman.BLACK.getChessman())	//2012(正数第二个零)
						||
						(j - 1 >= 0
						&& j - 2 >= 0
						&& j + 1 <= Chessboard.BOARD_SIZE - 1
						&& board[i][j - 1] == ico
						&& board[i][j - 2] == Chessman.BLACK.getChessman()
						&& board[i][j + 1] == Chessman.BLACK.getChessman()) //2102(倒数第二个零)
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
						// 活四
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == "十")	//011110(左边的零)
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
								&& board[i - 5][j] == "十"))	//011110(右边的零)
						{
							if(flag < 9)
							{
								flag = 9;
								pi = i;
								pj = j;
							}
						}
						// 冲四
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
						// 活三
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == "十"
								&& board[i - 1][j] == "十")	//001110(正数第二个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == "十"
								&& board[i + 1][j] == "十") //011100(倒数第二个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "十"
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十")	//010110(正数第三个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "十"
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十") //011010(倒数第三个零)
								)
						{
							if(flag < 7)
							{
								flag = 7;
								pi = i;
								pj = j;
							}
						}
						// 眠三
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//001112(正数第一个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //211100(倒数第一个零)
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
								&& board[i - 1][j] == "十")	//001112(正数第二个零)
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
								&& board[i + 1][j] == "十") //211100(倒数第二个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十"
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//010112(正数第一个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十"
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //211010(倒数第一个零)
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
								&& board[i - 2][j] == "十")	//010112(正数第三个零)
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
								&& board[i + 2][j] == "十") //211010(倒数第三个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "十"
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//011012(正数第一个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "十"
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //210110(倒数第一个零)
							    ||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "十"
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman())	//011012(正数第四个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "十"
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman()) //210110(倒数第四个零)
							    ||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == "十"
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//011102(正数第一个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == "十"
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //201110(倒数第一个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == "十"
								&& board[i + 1][j] == Chessman.BLACK.getChessman())	//011102(正数第五个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == "十"
								&& board[i - 1][j] == Chessman.BLACK.getChessman()) //201110(倒数第五个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i - 1][j] == ico)	//10011(正数第二个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i + 1][j] == ico) //11001(倒数第二个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == ico)	//10011(正数第三个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == ico) //11001(倒数第三个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十"
								&& board[i + 3][j] == ico
								&& board[i - 1][j] == ico)	//10101(正数第二个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十"
								&& board[i - 3][j] == ico
								&& board[i + 1][j] == ico) //10101(倒数第二个零)
								)
						{
							if(flag < 6)
							{
								flag = 6;
								pi = i;
								pj = j;
							}
						}
						// 活二
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "十"
								&& board[i - 1][j] == "十")	//00110(正数第二个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "十"
								&& board[i + 1][j] == "十") //01100(倒数第二个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "十"
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十")	//010010(正数第三个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "十"
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十") //010010(倒数第三个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十"
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十")	//01010(正数第三个零)
								)
						{
							if(flag < 5)
							{
								flag = 5;
								pi = i;
								pj = j;
							}					
						}
						// 眠二
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == "十"
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//000112(正数第一个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == "十"
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //211000(倒数第一个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "十")	//000112(正数第二个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "十") //211000(倒数第二个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "十"
								&& board[i - 1][j] == "十")	//000112(正数第三个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == "十") //211000(倒数第三个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "十"
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//001012(正数第一个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "十"
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //210100(倒数第一个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十"
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "十")	//001012(正数第二个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十"
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "十") //210100(倒数第二个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十"
								&& board[i - 3][j] == "十")	//001012(正数第四个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十"
								&& board[i + 3][j] == "十") //210100(倒数第四个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十"
								&& board[i + 3][j] == "十"
								&& board[i + 4][j] == ico
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//010012(正数第一个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十"
								&& board[i - 3][j] == "十"
								&& board[i - 4][j] == ico
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //210010(倒数第一个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十")	//010012(正数第三个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十") //210010(倒数第三个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "十")	//010012(正数第四个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "十") //210010(倒数第四个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == "十"
								&& board[i + 3][j] == ico
								&& board[i - 1][j] == ico)	//10001(正数第二个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == "十"
								&& board[i - 3][j] == ico
								&& board[i + 1][j] == ico) //10001(倒数第二个零)
							    ||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == ico
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == ico)	//10001(正数第三个零)						
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
							    && i + 5 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十"
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == "十"
								&& board[i + 5][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == Chessman.BLACK.getChessman()) //2010102(正数第二个零)
							    ||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十"
							    && board[i - 3][j] == ico
							    && board[i - 4][j] == "十"
								&& board[i - 5][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == Chessman.BLACK.getChessman())	//2010102(倒数第二个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
							    && i - 2 >= 0
								&& i - 3 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == "十"
								&& board[i + 3][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == "十"
								&& board[i - 3][j] == Chessman.BLACK.getChessman()) //2010102(正数第四个零)						
								||
							    (i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
							    && i + 5 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == "十"
								&& board[i + 4][j] == "十"
								&& board[i + 5][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == Chessman.BLACK.getChessman()) //2011002(正数第二个零)
							    ||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i - 4 >= 0
								&& i - 5 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
							    && board[i - 3][j] == "十"
							    && board[i - 4][j] == "十"
								&& board[i - 5][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == Chessman.BLACK.getChessman())	//2001102(倒数第二个零)
								||
							    (i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
							    && i - 3 >= 0
								&& i - 4 >= 0
								&& board[i + 1][j] == "十"
								&& board[i + 2][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == "十"
								&& board[i - 4][j] == Chessman.BLACK.getChessman()) //2011002(正数第五个零)
							    ||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == Chessman.BLACK.getChessman()
							    && board[i + 1][j] == ico
							    && board[i + 2][j] == ico
								&& board[i + 3][j] == "十"
								&& board[i + 4][j] == Chessman.BLACK.getChessman())	//2001102(倒数第五个零)
								||
							    (i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
							    && i - 4 >= 0
								&& i - 5 >= 0
								&& board[i + 1][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == "十"
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == ico
								&& board[i - 4][j] == "十"
								&& board[i - 5][j] == Chessman.BLACK.getChessman()) //2011002(正数第六个零)
							    ||
								(i - 1 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == "十"
							    && board[i + 2][j] == ico
							    && board[i + 3][j] == ico
								&& board[i + 4][j] == "十"
								&& board[i + 5][j] == Chessman.BLACK.getChessman())	//2001102(倒数第六个零)
								)
						{
							if(flag < 4)
							{
								flag = 4;
								pi = i;
								pj = j;
							}
						}
						// 死四
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i + 4 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == ico
								&& board[i + 4][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == Chessman.BLACK.getChessman())	//201112(正数第二个零)
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
								&& board[i + 1][j] == Chessman.BLACK.getChessman()) //211102(倒数第二个零)
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
								&& board[i - 2][j] == Chessman.BLACK.getChessman())	//210112(正数第三个零)
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
								&& board[i + 2][j] == Chessman.BLACK.getChessman()) //211012(倒数第三个零)
								
								)
						{
							if(flag < 3)
							{
								flag = 3;
								pi = i;
								pj = j;
							}
						}
						// 死三
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i + 3 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == ico
								&& board[i + 3][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == Chessman.BLACK.getChessman())	//20112(正数第二个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i - 3 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == ico
								&& board[i - 3][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == Chessman.BLACK.getChessman()) //211102(倒数第二个零)
								||
								(i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& i - 2 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman())	//21012(正数第三个零)
								)
						{
							if(flag < 2)
							{
								flag = 2;
								pi = i;
								pj = j;
							}
						}
						// 死二
						else if((i + 1 <= Chessboard.BOARD_SIZE - 1
								&& i + 2 <= Chessboard.BOARD_SIZE - 1
								&& i - 1 >= 0
								&& board[i + 1][j] == ico
								&& board[i + 2][j] == Chessman.BLACK.getChessman()
								&& board[i - 1][j] == Chessman.BLACK.getChessman())	//2012(正数第二个零)
								||
								(i - 1 >= 0
								&& i - 2 >= 0
								&& i + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i - 1][j] == ico
								&& board[i - 2][j] == Chessman.BLACK.getChessman()
								&& board[i + 1][j] == Chessman.BLACK.getChessman()) //2102(倒数第二个零)
								)
						{
							if(flag < 1)
							{
								flag = 1;
								pi = i;
								pj = j;
							}
						}
				//以下代码的i,j还需要改写
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
						// 活四
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == "十")	//011110(左边的零)
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
								&& board[i][j - 5] == "十"))	//011110(右边的零)
						{
							if(flag < 9)
							{
								flag = 9;
								pi = i;
								pj = j;
							}
						}
						// 冲四
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
						// 活三
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "十"
								&& board[i][j - 1] == "十")	//001110(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "十"
								&& board[i][j + 1] == "十") //011100(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十")	//010110(正数第三个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十") //011010(倒数第三个零)
								)
						{
							if(flag < 7)
							{
								flag = 7;
								pi = i;
								pj = j;
							}
						}
						// 眠三
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001112(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211100(倒数第一个零)
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
								&& board[i][j - 1] == "十")	//001112(正数第二个零)
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
								&& board[i][j + 1] == "十") //211100(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010112(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211010(倒数第一个零)
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
								&& board[i][j - 2] == "十")	//010112(正数第三个零)
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
								&& board[i][j + 2] == "十") //211010(倒数第三个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011012(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210110(倒数第一个零)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman())	//011012(正数第四个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()) //210110(倒数第四个零)
							    ||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "十"
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011102(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "十"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //201110(倒数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "十"
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//011102(正数第五个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "十"
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //201110(倒数第五个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10011(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //11001(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico)	//10011(正数第三个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico) //11001(倒数第三个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10101(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //10101(倒数第二个零)
								)
						{
							if(flag < 6)
							{
								flag = 6;
								pi = i;
								pj = j;
							}
						}
						// 活二
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j - 1] == "十")	//00110(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j + 1] == "十") //01100(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十")	//010010(正数第三个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十") //010010(倒数第三个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十")	//01010(正数第三个零)
								)
						{
							if(flag < 5)
							{
								flag = 5;
								pi = i;
								pj = j;
							}					
						}
						// 眠二
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//000112(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211000(倒数第一个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "十")	//000112(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "十") //211000(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == "十")	//000112(正数第三个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == "十") //211000(倒数第三个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001012(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210100(倒数第一个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "十")	//001012(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "十") //210100(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == "十")	//001012(正数第四个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == "十") //210100(倒数第四个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == "十"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010012(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == "十"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210010(倒数第一个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十")	//010012(正数第三个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十") //210010(倒数第三个零)
								
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十")	//010012(正数第四个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十") //210010(倒数第四个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10001(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //10001(倒数第二个零)
							    ||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico)	//10001(正数第三个零)						
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
							    && j + 5 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "十"
								&& board[i][j + 5] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2010102(正数第二个零)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
							    && board[i][j - 3] == ico
							    && board[i][j - 4] == "十"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2010102(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
							    && j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == Chessman.BLACK.getChessman()) //2010102(正数第四个零)						
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
							    && j + 5 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j + 4] == "十"
								&& board[i][j + 5] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2011002(正数第二个零)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
							    && board[i][j - 3] == "十"
							    && board[i][j - 4] == "十"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2001102(倒数第二个零)
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
							    && j - 3 >= 0
								&& j - 4 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j - 4] == Chessman.BLACK.getChessman()) //2011002(正数第五个零)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
							    && board[i][j + 1] == ico
							    && board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j + 4] == Chessman.BLACK.getChessman())	//2001102(倒数第五个零)
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
							    && j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j + 1] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "十"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //2011002(正数第六个零)
							    ||
								(j - 1 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "十"
							    && board[i][j + 2] == ico
							    && board[i][j + 3] == ico
								&& board[i][j + 4] == "十"
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//2001102(倒数第六个零)
								)
						{
							if(flag < 4)
							{
								flag = 4;
								pi = i;
								pj = j;
							}
						}
						// 死四
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//201112(正数第二个零)
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
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(倒数第二个零)
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
								&& board[i][j - 2] == Chessman.BLACK.getChessman())	//210112(正数第三个零)
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
								&& board[i][j + 2] == Chessman.BLACK.getChessman()) //211012(倒数第三个零)
								)
						{
							if(flag < 3)
							{
								flag = 3;
								pi = i;
								pj = j;
							}
						}
						// 死三
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//20112(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman())	//21012(正数第三个零)
								)
						{
							if(flag < 2)
							{
								flag = 2;
								pi = i;
								pj = j;
							}
						}
						// 死二
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//2012(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //2102(倒数第二个零)
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
						// 活四
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == "十")	//011110(左边的零)
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
								&& board[i][j - 5] == "十"))	//011110(右边的零)
						{
							if(flag < 9)
							{
								flag = 9;
								pi = i;
								pj = j;
							}
						}
						// 冲四
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
						// 活三
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "十"
								&& board[i][j - 1] == "十")	//001110(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "十"
								&& board[i][j + 1] == "十") //011100(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十")	//010110(正数第三个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十") //011010(倒数第三个零)
								)
						{
							if(flag < 7)
							{
								flag = 7;
								pi = i;
								pj = j;
							}
						}
						// 眠三
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001112(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211100(倒数第一个零)
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
								&& board[i][j - 1] == "十")	//001112(正数第二个零)
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
								&& board[i][j + 1] == "十") //211100(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010112(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211010(倒数第一个零)
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
								&& board[i][j - 2] == "十")	//010112(正数第三个零)
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
								&& board[i][j + 2] == "十") //211010(倒数第三个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011012(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210110(倒数第一个零)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman())	//011012(正数第四个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()) //210110(倒数第四个零)
							    ||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "十"
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//011102(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "十"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //201110(倒数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "十"
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//011102(正数第五个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "十"
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //201110(倒数第五个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10011(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //11001(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico)	//10011(正数第三个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico) //11001(倒数第三个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10101(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //10101(倒数第二个零)
								)
						{
							if(flag < 6)
							{
								flag = 6;
								pi = i;
								pj = j;
							}
						}
						// 活二
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j - 1] == "十")	//00110(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j + 1] == "十") //01100(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十")	//010010(正数第三个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十") //010010(倒数第三个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十")	//01010(正数第三个零)
								)
						{
							if(flag < 5)
							{
								flag = 5;
								pi = i;
								pj = j;
							}					
						}
						// 眠二
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//000112(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //211000(倒数第一个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "十")	//000112(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "十") //211000(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == "十")	//000112(正数第三个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == "十") //211000(倒数第三个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//001012(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210100(倒数第一个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "十")	//001012(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "十") //210100(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == "十")	//001012(正数第四个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == "十") //210100(倒数第四个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == "十"
								&& board[i][j + 4] == ico
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//010012(正数第一个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == "十"
								&& board[i][j - 4] == ico
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //210010(倒数第一个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十")	//010012(正数第三个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十") //210010(倒数第三个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十")	//010012(正数第四个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十") //210010(倒数第四个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j - 1] == ico)	//10001(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == ico
								&& board[i][j + 1] == ico) //10001(倒数第二个零)
							    ||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == ico
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico)	//10001(正数第三个零)						
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
							    && j + 5 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == "十"
								&& board[i][j + 5] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2010102(正数第二个零)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
							    && board[i][j - 3] == ico
							    && board[i][j - 4] == "十"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2010102(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
							    && j - 2 >= 0
								&& j - 3 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == "十"
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == "十"
								&& board[i][j - 3] == Chessman.BLACK.getChessman()) //2010102(正数第四个零)						
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
							    && j + 5 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j + 4] == "十"
								&& board[i][j + 5] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman()) //2011002(正数第二个零)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j - 4 >= 0
								&& j - 5 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
							    && board[i][j - 3] == "十"
							    && board[i][j - 4] == "十"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman())	//2001102(倒数第二个零)
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
							    && j - 3 >= 0
								&& j - 4 >= 0
								&& board[i][j + 1] == "十"
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == "十"
								&& board[i][j - 4] == Chessman.BLACK.getChessman()) //2011002(正数第五个零)
							    ||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
							    && board[i][j + 1] == ico
							    && board[i][j + 2] == ico
								&& board[i][j + 3] == "十"
								&& board[i][j + 4] == Chessman.BLACK.getChessman())	//2001102(倒数第五个零)
								||
							    (j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
							    && j - 4 >= 0
								&& j - 5 >= 0
								&& board[i][j + 1] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == "十"
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == ico
								&& board[i][j - 4] == "十"
								&& board[i][j - 5] == Chessman.BLACK.getChessman()) //2011002(正数第六个零)
							    ||
								(j - 1 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j + 5 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == "十"
							    && board[i][j + 2] == ico
							    && board[i][j + 3] == ico
								&& board[i][j + 4] == "十"
								&& board[i][j + 5] == Chessman.BLACK.getChessman())	//2001102(倒数第六个零)
								)
						{
							if(flag < 4)
							{
								flag = 4;
								pi = i;
								pj = j;
							}
						}
						// 死四
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j + 4 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == ico
								&& board[i][j + 4] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//201112(正数第二个零)
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
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(倒数第二个零)
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
								&& board[i][j - 2] == Chessman.BLACK.getChessman())	//210112(正数第三个零)
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
								&& board[i][j + 2] == Chessman.BLACK.getChessman()) //211012(倒数第三个零)
								
								)
						{
							if(flag < 3)
							{
								flag = 3;
								pi = i;
								pj = j;
							}
						}
						// 死三
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j + 3 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == ico
								&& board[i][j + 3] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//20112(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j - 3 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == ico
								&& board[i][j - 3] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //211102(倒数第二个零)
								||
								(j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& j - 2 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman())	//21012(正数第三个零)
								)
						{
							if(flag < 2)
							{
								flag = 2;
								pi = i;
								pj = j;
							}
						}
						// 死二
						else if((j + 1 <= Chessboard.BOARD_SIZE - 1
								&& j + 2 <= Chessboard.BOARD_SIZE - 1
								&& j - 1 >= 0
								&& board[i][j + 1] == ico
								&& board[i][j + 2] == Chessman.BLACK.getChessman()
								&& board[i][j - 1] == Chessman.BLACK.getChessman())	//2012(正数第二个零)
								||
								(j - 1 >= 0
								&& j - 2 >= 0
								&& j + 1 <= Chessboard.BOARD_SIZE - 1
								&& board[i][j - 1] == ico
								&& board[i][j - 2] == Chessman.BLACK.getChessman()
								&& board[i][j + 1] == Chessman.BLACK.getChessman()) //2102(倒数第二个零)
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
		// 搜索算法：极大极小搜索、α-β剪枝
		int[] result = {posX, posY};
		return result;
	}

	/**
	 * 判断输赢
	 * 
	 * @param posX
	 *            棋子的X坐标。
	 * @param posY
	 *            棋子的Y坐标
	 * @param ico
	 *            棋子类型
	 * @return 如果有五颗相邻棋子连成一条直接，返回真，否则相反。
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
