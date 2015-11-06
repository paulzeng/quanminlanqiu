package com.thomas.qmlq.bean;

import com.google.gson.Gson;

public class TrainVedio {
	private String title;
	private String vedioImg;
	private String vedioPath;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVedioImg() {
		return vedioImg;
	}
	public void setVedioImg(String vedioImg) {
		this.vedioImg = vedioImg;
	}
	public String getVedioPath() {
		return vedioPath;
	}
	public void setVedioPath(String vedioPath) {
		this.vedioPath = vedioPath;
	}
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
