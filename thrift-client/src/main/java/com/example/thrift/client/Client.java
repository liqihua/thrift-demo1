package com.example.thrift.client;

import com.example.thrift.api.ListOrderParam;
import com.example.thrift.api.OrderService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @author yyadmin
 * @date 2021/5/10
 *
 */
public class Client {

    public static void main(String[] args) {
        TTransport transport = null;
        try {
            transport = new TSocket("localhost", 2345);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            OrderService.Client client = new OrderService.Client(protocol);
            transport.open();
            ListOrderParam param = new ListOrderParam();
            param.setPage(1);
            param.setPageSize(20);
            System.out.println(client.listOrder(param));
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

}

