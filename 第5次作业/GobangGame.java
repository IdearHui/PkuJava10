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
		String color = Chessman.BLACK.getChessman();
		int x_1=1,x_2=1,y_1=1,y_2=1,xy_l=1,xy_r=1,xy_ll=1,xy_rr=1;//八个方向
		for(int px=0;px<Chessboard.BOARD_SIZE-1;px++){
			for(int py=0;py<Chessboard.BOARD_SIZE-1;py++){
				while(board[px][py]!="十"){
					for(int i=px;i<WIN_COUNT-1+px
							&&i<Chessboard.BOARD_SIZE-1;i++){//x正方向
						if(board[i][py].equals(color)){
							x_1++;
							if(x_1==3){
								posX = i+1;
								posY = py;
								break;
							}
						}
					}
					for(int i=px;i<px-WIN_COUNT+1&&i>1;i++){//x负方向
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
							&&i<Chessboard.BOARD_SIZE-1;i++){//y正方向
						if(board[px][i].equals(color)){
							y_1++;
							if(y_1==3){
								posY = i+1;
								posX = px;
								break;
							}
						}
					}
					for(int i=py;i<py-WIN_COUNT+1&&i>1;i++){//y负方向
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
							&&i<Chessboard.BOARD_SIZE-1;i++){//xy主斜正方向
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
					for(int i=px;i<px-WIN_COUNT+1&&i>1;i++){//xy主斜负方向
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
					for(int i=px;i<px-WIN_COUNT+1&&i>1;i++){//xy副斜正方向
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
							&&i<Chessboard.BOARD_SIZE-1;i++){//xy副斜负方向
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
		while (board[posX][posY] != "十") {
			posX = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
			posY = (int) (Math.random() * (Chessboard.BOARD_SIZE - 1));
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
		//定义八个方向的棋子活度
		int countX_1 = 1,countX_2 = 1,//X正反方向
				countY_1 = 1,countY_2 = 1,//Y正反方向
				countL_1 = 1,countL_2 = 1,//2-4象限正反方向
				countR_1 = 1,countR_2 = 1;//1-3象限正反方向
		//判断X方向是否5子相连
		for(int i=1;i<WIN_COUNT;i++){
			if(posY+i > 22)//如果坐标超出边界，则不用继续找了
				break;
			if(board[posX][posY+i] == ico)//如果朝X增大方向有与该棋子同类型的，则计数加一
				countX_1 += 1;
			else
				break;//如果没有相同类型的，则不用继续找了
		}
		for(int i=1;i<WIN_COUNT;i++){
			if(posY-i < 0)//如果坐标超出边界，则不用继续找了
				break;
			if(board[posX][posY-i] == ico)//如果朝X减小方向有与该棋子同类型的，则计数加一
				countX_2 += 1;
			else
				break;//如果没有相同类型的，则不用继续找了
		}
		//判断Y方向是否5子相连
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
		//判断2/4象限对角线方向是否5子相连
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
		//判断1/3象限对角线方向是否5子相连
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
