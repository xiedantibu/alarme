package com.huewu.alarme.service.network.request;

import java.net.URL;

import com.huewu.alarme.model.UserInfo;
import com.huewu.alarme.service.network.JsonRequest;

public class DeleteUserRequest extends JsonRequest<UserInfo>{

	private static final String URL = "http://ghfal.herokuapp.com/user/";

	public DeleteUserRequest(UserInfo user) {
		super(Method.DELETE, URL + user.uid);
	}

}//end of class
