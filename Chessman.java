 
public enum Chessman {
	BLACK("��"), WHITE("��");
	private String chessman;

	/**
	 * ˽�й�����
	 */
	private Chessman(String chessman) {
		this.chessman = chessman;
	}

	/**
	 * @return String ������߰���
	 */
	public String getChessman() {
		return this.chessman;
	}
}