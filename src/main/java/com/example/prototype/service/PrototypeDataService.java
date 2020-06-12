package com.example.prototype.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.prototype.mapper.PrototypeMapper;
import com.example.prototype.model.OrderLineItem;

@Service
public class PrototypeDataService {
	
	private Logger logger = LogManager.getLogger();
	
	@Autowired
	private PrototypeMapper prototypeMapper;
	
	@Transactional(value="PrototypeTxnMgr", readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public String getTest() throws Exception {
		return prototypeMapper.getTest();
	}
	
	@Transactional(value="PrototypeTxnMgr", readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void updateOrderLineItem(String orderLineItemId, OrderLineItem orderLineItem) throws Exception {
		prototypeMapper.updateOrderLineItem(orderLineItemId, orderLineItem);
	}
	
	@Transactional(value="PrototypeTxnMgr", readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public OrderLineItem getOrderLineItem(String orderLineItemId) throws Exception {
		return prototypeMapper.getOrderLineItem(orderLineItemId);
	}
	
}
