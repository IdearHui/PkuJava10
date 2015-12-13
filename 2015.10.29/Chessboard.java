
public class Chessboard {
	// ����һ����ά�������䵱����
	private String[][] board;
	// �������̵Ĵ�С
	public static final int BOARD_SIZE = 22;
	private int lastX = -1, lastY = -1;//��¼����е�ǰ���һ�������λ��
	/**
	 * ��ʼ������
	 * 
	 * @return void
	 */
	public void initBoard() {
		// ��ʼ����������
		board = new String[BOARD_SIZE][BOARD_SIZE];
		// ��ÿ��Ԫ�ظ�ֵΪ��ʮ�������ڿ���̨�������
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				board[i][j] = "ʮ";
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
	 * �ڿ���̨������̵ķ���
	 */
	public void printBoard() {
		// ��ӡÿ������Ԫ��
			for (int i=0; i < BOARD_SIZE; i+=2)
		{
			System.out.printf("  %02d",i+1);	// ��һ����ʾ�к�����
		}
		System.out.println();
		for (int i = 0; i < BOARD_SIZE; i++) {
			System.out.printf("%02d", i+1);		// ������ʾ�к�
			for (int j = 0; j < BOARD_SIZE; j++) {
				if (i == lastX && j == lastY)
				{
					System.out.print("��");		// ͻ����ʾ�������λ�õ�����
				}else
				{
				// ��ӡ�󲻻���
				System.out.print(board[i][j]);
				}
			}
			// ÿ��ӡ��һ������Ԫ�ؾͻ���һ��
			System.out.print("\n");
		}
		System.out.println("����������������꣬Ӧ��x,y�ĸ�ʽ���룺");
	}

	/**
	 * ������λ�ø�ֵ
	 * 
	 * @param posX
	 *            X����
	 * @param posY
	 *            Y����
	 * @param chessman
	 *            ����
	 */
	public void setBoard(int posX, int posY, String chessman) {
		lastX = posX;
		lastY = posY;
		this.board[posX][posY] = chessman;
	}

	/**
	 * ��������
	 * 
	 * @return ��������
	 */
	public String[][] getBoard() {
		return this.board;
	}
}