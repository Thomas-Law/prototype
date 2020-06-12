package com.example.prototype.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.prototype.model.OrderLineItem;

@Repository
@Mapper
public interface PrototypeMapper {
	
	public String getTest();
	public void updateOrderLineItem(@Param("orderLineItemId") String orderLineItemId, @Param("orderLineItem") OrderLineItem orderLineItem);
	public OrderLineItem getOrderLineItem(@Param("orderLineItemId") String orderLineItemId);
	
}
