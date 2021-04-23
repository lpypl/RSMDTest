# ResultSetMetaData接口测试

## 表和视图定义

```sql
create table table0(t0c0 int, t0c1 int, t0c2 int);
create view view0(v0c0, v0c1, v0c2) as select t0c0, t0c1, t0c2 from table0;
```

## 查询语句
```sql
/* without alias*/
select * from view0;
/* with alias*/
select v0c0 as alias_v0c0, v0c1 as alias_v0c1, v0c2 as alias_v0c2 from view0;
```

## 在MySQL上的结果
`ResultSetMetaData.getColumnName`返回某一列在视图中定义的名字，而`ResultSetMetaData.getColumnLabel`返回查询时赋予的别名。
```text
8.0.23-0ubuntu0.20.10.1
SELECT WITHOUT ALIAS
Index     	ColName   	ColLabel  
1         	v0c0      	v0c0      
2         	v0c1      	v0c1      
3         	v0c2      	v0c2      
SELECT WITH ALIAS
Index     	ColName   	ColLabel  
1         	v0c0      	alias_v0c0
2         	v0c1      	alias_v0c1
3         	v0c2      	alias_v0c2
```

## 在TiDB上的结果
`ResultSetMetaData.getColumnName`返回某一列在表中而非视图中定义的名字，而`ResultSetMetaData.getColumnLabel`返回查询时赋予的别名。  
然而，考虑到用户查询视图时是将其当做一张抽象的表来看待的，并不关心具体是表还是视图，也许返回属性列在视图定义中的名字更为合理，MySQL的行为就是这样的。  
而且，如果用户在查询视图时使用as关键字起了别名，似乎无法从ResultSetMetaData中获取视图中定义的属性名。
```text
5.7.25-TiDB-v5.0.0
SELECT WITHOUT ALIAS
Index     	ColName   	ColLabel  
1         	t0c0      	v0c0      
2         	t0c1      	v0c1      
3         	t0c2      	v0c2      
SELECT WITH ALIAS
Index     	ColName   	ColLabel  
1         	t0c0      	alias_v0c0
2         	t0c1      	alias_v0c1
3         	t0c2      	alias_v0c2
```