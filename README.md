# thrift-demo1

## 安装thrift

### 下载，https://thrift.apache.org/

### 配置环境变量

把下载的 thrift-xxx.exe 重命名为 thrift.exe，修改环境变量 path 把 thrift.exe 目录路径添加进去，不需要 thrift.exe，比如 D:\software\thrift\thrift.exe，path 环境变量只添加 D:\software\thrift

## 创建 OrderService.thrift 并编写接口定义、参数、返回类型

```
namespace java com.example.thrift.api

struct ListOrderParam 
{
	1: required i32 page = 1;	//页码
	2: required i32 pageSize;	//每页数量
}

struct OrderVO
{
	1: string orderCode;	//订单号
	2: string userName;	//用户名
	3: i64 total;	//订单金额，分
}

service OrderService 
{
	list<OrderVO> listOrder(1: ListOrderParam param);   //查询订单列表
}
```

## 运行命令生成 java 代码

thrift -r -gen java user.thrift  
我把生成的 ListOrderParam、OrderVO、OrderService 拷贝到 demo 模块 thrift-api 中，thrift-client、thrift-server 的 pom.xml 需要引用，因为下面用到

## 服务端编写

### 在 thrift-server 中创建 OrderServiceImpl 并实现 OrderService.Iface 接口

```
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
```
### 在 thrift-server 中编写启动类
```
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
```
### 运行 Server.main() 启动服务端

## 调用方编写

### 在 thrift-client 中编写 Client 类

```
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
```

### 运行 Client.main() 执行调用