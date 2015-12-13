
public class Chessboard {
	// 定义一个二维数组来充当棋盘
	private String[][] board;
	// 定义棋盘的大小
	public static final int BOARD_SIZE = 22;
	private int lastX = -1, lastY = -1;//记录棋局中当前最后一步下棋的位置
	/**
	 * 初始化棋盘
	 * 
	 * @return void
	 */
	public void initBoard() {
		// 初始化棋盘数组
		board = new String[BOARD_SIZE][BOARD_SIZE];
		// 把每个元素赋值为“十”，用于控制台输出棋盘
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = "十";
			}
		}
	}
	
	public void test() {
		Object[][] array = new Object[10][10];
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				array[i][j] = new Object();
			}
		}
	}

	/**
	 * 在控制台输出棋盘的方法
	 */
	public void printBoard() {
		// 打印每个数组元素
			for (int i=0; i < BOARD_SIZE; i+=2)
		{
			System.out.printf("  %02d",i+1);	// 第一行显示列号序列
		}
		System.out.println();
		for (int i = 0; i < BOARD_SIZE; i++) {
			System.out.printf("%02d", i+1);		// 首列显示行号
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (i == lastX && j == lastY)
				{
					System.out.print("◎");		// 突出显示最后下棋位置的棋子
				}else
				{
				// 打印后不换行
				System.out.print(board[i][j]);
				}
			}
			// 每打印完一行数组元素就换行一次
			System.out.print("\n");
		}
		System.out.println("请输入您下棋的坐标，应以x,y的格式输入：");
	}

	/**
	 * 给棋盘位置赋值
	 * 
	 * @param posX
	 *            X坐标
	 * @param posY
	 *            Y坐标
	 * @param chessman
	 *            棋子
	 */
	public void setBoard(int posX, int posY, String chessman) {
		lastX = posX;
		lastY = posY;
		this.board[posX][posY] = chessman;
	}

	/**
	 * 返回棋盘
	 * 
	 * @return 返回棋盘
	 */
	public String[][] getBoard() {
		return this.board;
	}
}