package com.ptsoft.pts.basic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ptsoft.common.base.BaseMybatisDao;
import com.ptsoft.pts.basic.model.vo.Message;

@Repository
public class MessageDao extends BaseMybatisDao<Message, Integer>{

	@Override
	protected String getMybatisMapperPrefix() {
		return "Message";
	}

	@Override
	public List<Message> findAll() {
		return null;
	}
	
	public Message getByMobile(String mobile){
		return (Message)this.getSqlSession().selectOne("Message_getByMobile", mobile);
	}

}
