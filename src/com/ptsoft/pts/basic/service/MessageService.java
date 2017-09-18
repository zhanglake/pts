package com.ptsoft.pts.basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ptsoft.common.base.BaseService;
import com.ptsoft.common.base.EntityDao;
import com.ptsoft.pts.basic.dao.MessageDao;
import com.ptsoft.pts.basic.model.vo.Message;

@Service
public class MessageService extends BaseService<Message, Integer>{

	@Autowired
	private MessageDao msgDao;
	
	@SuppressWarnings("rawtypes")
	@Override
	protected EntityDao getEntityDao() {
		return this.msgDao;
	}

	@Override
	public void save(Message entity) {
		msgDao.insert(entity);
	}

	public Message findByMobile(String mobile){
		return msgDao.getByMobile(mobile);
	}
}
