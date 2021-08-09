package fr.mycommerce.transverse;

public enum ActionType {
	DEFAULT(0), CREATE(1), UPDATE(2);

	private int mode;

	private ActionType(int mode) {
		this.mode = mode;
	}

	public int getmode() {
		return this.mode;
	}

}
