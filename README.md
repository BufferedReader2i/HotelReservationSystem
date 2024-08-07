# 酒店管理系统

## 1.功能
### 订单信息界面（OrderView.java）
>显示用户的订单信息，并允许用户进行管理

### 用户评价界面（ReviewView.java）
> 允许用户对入住过的酒店进行评价

### 统计报表界面（StatisticsView.java）

>汇总统计：（1）按城市、区域、酒店、价格等因素统计一段时间内 客房的预定情况，以便指定后续的营销策略 
2）按照入住人信息（年龄、性别、职业、受教育程度、收入状况等）
统计客房预定情况，给出不同客户的喜好推荐，为后续营销制定策略提供指导。
给出界面，系统的所有后台信息（如客房情况）需要维护。

## 2. 确定实体和属性
   基于需求，我们可以确定以下实体及其属性：

用户（User）
UserID (主键, INT, 不可空, 自动增长)
Username (VARCHAR, 不可空, 长度50)
Password (VARCHAR, 不可空, 长度255)
Points (INT, 可空, 默认0)
UserLevel (VARCHAR, 可空, 长度50)


酒店管理员（HotelAdmin）
AminID (主键, INT, 不可空, 自动增长)
Password(VARCHAR, 不可空, 长度255)
HotelID ( 外键, INT, 不可空)

酒店（Hotel）
HotelID (主键, INT, 不可空, 自动增长)
Name (VARCHAR, 不可空, 长度255)
Address (VARCHAR, 不可空, 长度255)
City (VARCHAR, 不可空, 长度100)
District (VARCHAR, 可空, 长度100)

房型（RoomType）
RoomTypeID (主键, INT, 不可空, 自动增长)
Name (VARCHAR, 不可空, 长度100)
Description (TEXT, 可空)
HotelID (外键, INT, 不可空)

订单（Orders）
OrderID (主键, INT, 不可空, 自动增长)
UserID (外键, INT, 不可空)
RoomID（外键，INT，不可空）
UserName
CheckInDate (DATE, 不可空)
CheckOutDate (DATE, 不可空)
TotalPrice (DECIMAL, 不可空, 长度10,2)
Status (VARCHAR, 可空, 长度50)
Comment (TEXT, 不可空)
Date (DATETIME, 不可空, 默认当前时间)

客房（Room）
RoomID（主键，INT，不可空）
RoomTypeID（外键）
HotelID(外键)
Price (DECIMAL, 不可空, 长度10,2)

入住人（Guest）
GuestID (主键, INT, 不可空, 自动增长)
OrderID (外键, INT, 不可空)
Name (VARCHAR, 不可空, 长度100)
IDNumber (VARCHAR, 不可空, 长度18)
Age (INT, 可空)
Gender (VARCHAR, 可空, 长度10)
Occupation (VARCHAR, 可空, 长度100)
EducationLevel (VARCHAR, 可空, 长度100)
IncomeStatus (VARCHAR, 可空, 长度100)





