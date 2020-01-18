package com.roncoo.paymentProvider.dao;


import com.roncoo.entity.PayOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author weining
 * @date 2020/1/16 11:57
 */
@Repository
public interface PayOrderDao {

    /**
     * 新增
     */
    @Insert("insert into t_payorder(oid,title,money,preurl,flag,type,stime,modtime) values(#{oid},#{title},#{money},#{preurl},1,#{type},now(),now())")
    int insert(PayOrder order);

    /**
     * 查询
     */
    @Select("select id,oid,title,money,preurl,flag,type,stime,modtime from t_payorder order by id desc")
    List<PayOrder> selectAll();

    /**
     * 修改订单的支付状态
     */
    @Update("update t_payorder set flag=#{flag},modtime=now() where oid=#{oid}")
    int update(@Param("oid") String oid, @Param("flag") int flag);

    /**
     * 查询指定的订单
     */
    @Select("select id,oid,title,money,preurl,flag,type,stime,modtime from t_payorder where oid=#{oid}")
    PayOrder selectByOid(String oid);
}
