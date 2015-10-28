 
public enum Chessman {
	BLACK("●"), WHITE("○");
	private String chessman;

	/**
	 * 私有构造器
	 */
	private Chessman(String chessman) {
		this.chessman = chessman;
	}

	/**
	 * @return String 黑棋或者白棋
	 */
	public String getChessman() {
		return this.chessman;
	}
}