package com.wzrd.m.contacts;

import java.io.Serializable;

public class SortModel extends Contact implements Serializable{

	/** 显示数据拼音的首字母 */
	public String sortLetters;

	public SortToken sortToken = new SortToken();

	public SortModel(String name, String number, String sortKey) {
		super(name, number, sortKey);
	}

	@Override
	public String toString() {
		return "SortModel [sortLetters=" + sortLetters + ", sortToken=" + sortToken.toString() + "]";
	}
}
