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
	 */
	public int[] computerDo() {
		String[][] board = chessboard.getBoard();
		//方向系数，分别为“/”、“\”、“-”、“|”
		int factor[] = {1,1,1,-1,1,0,0,1};
		//连续计数
		int count; 
		int posX = 0, posY = 0;
		boolean isDecided = false;
		String ico;
		outer:
		//棋盘扫描
		for (int x = 0; x < Chessboard.BOARD_SIZE; x++) {
			for (int y = 0; y < Chessboard.BOARD_SIZE; y++) {
				ico = board[x][y];
				if(ico == "十"){
					continue;
				}
				for (int i = 0; i < factor.length; i+=2) {
					count = 0;
					//根据factor指定的方向扫描
					for (int j = WIN_COUNT*-1; j < WIN_COUNT; j++) {
						int curX = x + factor[i]*j, curY = y + factor[i+1]*j;
						if(checkValid(curX,curY)){
							 if(board[curX][curY].equals(ico)){
								 count++;
							 }else{
								 count = 0;
							 }
							 //连够3子
							 if (count == WIN_COUNT - 2){
								//正向阻拦
								posX = curX + factor[i];
								posY = curY + factor[i+1];
								if(checkValid(posX,posY) && board[posX][posY] == "十"){
									isDecided = true;
									break outer;
								}
								//反向阻拦
								posX = curX - factor[i]*3;
								posY = curY - factor[i+1]*3;
								if(checkValid(posX,posY) && board[posX][posY] == "十"){
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
			} while (board[posX][posY] != "十");
		}
		
		int[] result = { posX, posY };
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
	public boolean isWon(int posX, int posY, String ico) {
		String[][] board = chessboard.getBoard();
		//方向系数，分别为“/”、“\”、“-”、“|”
		int factor[] = {1,1,1,-1,1,0,0,1};
		//连续计数
		int count; 
		for (int i = 0; i < factor.length; i+=2) {
			count = 0;
			//根据factor指定的方向扫描
			for (int j = WIN_COUNT*-1; j < WIN_COUNT; j++) {
				int newX = posX + factor[i]*j, newY = posY + factor[i+1]*j;
				if(checkValid(newX, newY)){
					 if(board[newX][newY].equals(ico)){
						 count++;
					 }else{
						 count = 0;
					 }
					 //连够5子
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
