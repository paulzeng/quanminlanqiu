package com.thomas.qmlq.bean;

import com.google.gson.Gson;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * 帖子评论
 * 
 * @author thomas
 * 
 */
public class Comment extends BmobObject {

	private String content;// 评论内容

	private User user;// 评论的用户，Pointer类型，一对一关系 
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
 

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
