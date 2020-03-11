package com.hdw.fvshop.service.impl;

import java.util.Date;

import com.hdw.fvshop.entity.PersonInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hdw.fvshop.dao.LocalAuthDao;
import com.hdw.fvshop.dto.LocalAuthExecution;
import com.hdw.fvshop.entity.LocalAuth;
import com.hdw.fvshop.enums.LocalAuthStateEnum;
import com.hdw.fvshop.exception.OperationException;
import com.hdw.fvshop.service.LocalAuthService;

@Service
public class LocalAuthServiceImpl implements LocalAuthService {

	@Autowired
	private LocalAuthDao localAuthDao;

	@Override
	public LocalAuth getLocalAuthByUsernameAndPwd(String username, String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(username, password);
	}

	@Override
	public LocalAuth getLocalAuthByUserId(long userId) {
		return localAuthDao.queryLocalByUserId(userId);
	}


	@Override
	@Transactional
	public LocalAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword)
			throws OperationException {
		// 非空判断，判断传入的用户Id,帐号,新旧密码是否为空，新旧密码是否相同，若不满足条件则返回错误信息
		if (userId != null && userName != null && password != null && newPassword != null
				&& !password.equals(newPassword)) {
			try {
				// 更新密码
				int effectedNum = localAuthDao.updateLocalAuth(userId, userName, password,
						newPassword, new Date());
				// 判断更新是否成功
				if (effectedNum <= 0) {
					throw new OperationException("更新密码失败");
				}
				return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new OperationException("更新密码失败:" + e.toString());
			}
		} else {
			return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
		}
	}

	@Override
	public int insertPersonInfo(PersonInfo personInfo) {
		return localAuthDao.insertPersonInfo(personInfo);
	}

	@Override
	@Transactional
	public LocalAuthExecution register(PersonInfo personInfo, String userName, String password) {
		try {
			int effectedNum = localAuthDao.insertPersonInfo(personInfo);
			if (effectedNum <= 0) {
				throw new OperationException("注册失败");
			}
		} catch (Exception e) {
			throw new OperationException("注册失败" + e.toString());
		}
		try {
			if (userName != null && password != null && personInfo != null && personInfo.getUserId() != null) {
				// 创建LocalAuth对象并赋值
				LocalAuth localAuth = new LocalAuth();
				localAuth.setUsername(userName);
				localAuth.setPassword(password);
				localAuth.setPersonInfo(personInfo);
				int effectedNum = localAuthDao.insertLocalAuth(localAuth);
				if (effectedNum <= 0) {
					throw new OperationException("注册失败");
				}
				else {
					return new LocalAuthExecution(LocalAuthStateEnum.SUCCESS, localAuth);
				}
			} else {
				return new LocalAuthExecution(LocalAuthStateEnum.NULL_AUTH_INFO);
			}
		} catch (Exception e) {
			throw new OperationException("注册失败" + e.toString());
		}
	}
}
