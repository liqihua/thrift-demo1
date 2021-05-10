package com.example.thrift.api.impl;

import com.example.thrift.api.ListOrderParam;
import com.example.thrift.api.OrderService;
import com.example.thrift.api.OrderVO;
import org.apache.thrift.TException;

import java.util.LinkedList;
import java.util.List;

/**
 * @author yyadmin
 * @date 2021/5/10
 *
 */
public class OrderServiceImpl implements OrderService.Iface{

    @Override
    public List<OrderVO> listOrder(ListOrderParam param) throws TException {
        List<OrderVO> voList = new LinkedList<OrderVO>();
        for(int i = 0; i < param.page * param.pageSize; i++) {
            OrderVO vo = new OrderVO();
            vo.setOrderCode("orderCode" + i);
            vo.setUserName("userName" + i);
            vo.setTotal(i * 1000);
            voList.add(vo);
        }
        return voList;
    }

}

