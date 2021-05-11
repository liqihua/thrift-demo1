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
	list<OrderVO> listOrder(1: ListOrderParam param);
}