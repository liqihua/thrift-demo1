package com.example.thrift.server;

import com.example.thrift.api.OrderService;
import com.example.thrift.api.impl.OrderServiceImpl;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportFactory;

/**
 * @author yyadmin
 * @date 2021/5/10
 *
 */
public class Server {

    public static void main(String[] args) {
        OrderService.Processor processor = new OrderService.Processor<OrderService.Iface>(new OrderServiceImpl());
        try {
            TServerTransport transport = new TServerSocket(2345);
            TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(transport);
            tArgs.processor(processor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            tArgs.transportFactory(new TTransportFactory());
            tArgs.minWorkerThreads(10);
            tArgs.maxWorkerThreads(20);
            TServer server = new TThreadPoolServer(tArgs);
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

