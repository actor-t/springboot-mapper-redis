package com.open.boot.common.enumset;

public enum ColorEnum {
	RED("红色", 1), GREEN("绿色", 2), BLANK("白色", 3), YELLO("黄色", 4);

	// ordinal和name属性系统自带
	// 枚举属性
	private String name;
	private int index;

	// 构造方法
	private ColorEnum(String name, int index) {
		this.name = name;
		this.index = index;
	}

	// 覆盖方法
	@Override
	public String toString() {
		return this.index + "_" + this.name;
	}

	public static ColorEnum getByName(String name) {
		for (ColorEnum colorEnum : ColorEnum.values()) {
			if (colorEnum.getName().equals(name)) {
				return colorEnum;
			}
		}
		return null;
	}

	public String getName() {
		return this.name;
	}
}
