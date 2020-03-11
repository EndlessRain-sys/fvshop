package com.hdw.fvshop.service;

import com.hdw.fvshop.dto.LocalAuthExecution;
import com.hdw.fvshop.entity.LocalAuth;
import com.hdw.fvshop.entity.PersonInfo;
import com.hdw.fvshop.exception.OperationException;

public interface LocalAuthService {
	/**
	 * 通过帐号和密码获取平台帐号信息
	 * 
	 * @param userName
	 * @return
	 */
	LocalAuth getLocalAuthByUsernameAndPwd(String userName, String password);

	/**
	 * 通过userId获取平台帐号信息
	 * 
	 * @param userId
	 * @return
	 */
	LocalAuth getLocalAuthByUserId(long userId);



	/**
	 * 修改平台帐号的登录密码
	 *
	 */
	LocalAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword)
			throws OperationException;

	int insertPersonInfo(PersonInfo personInfo);

	LocalAuthExecution register(PersonInfo personInfo, String userName, String password);
}
