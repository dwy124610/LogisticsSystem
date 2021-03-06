package com.dwy.logistics.mapper;

import com.dwy.logistics.model.entities.Orders;
import com.dwy.logistics.model.entities.OrdersExample;
import com.dwy.logistics.model.entities.OrdersKey;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface OrdersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @SelectProvider(type=OrdersSqlProvider.class, method="countByExample")
    long countByExample(OrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @DeleteProvider(type=OrdersSqlProvider.class, method="deleteByExample")
    int deleteByExample(OrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @Delete({
        "delete from orders",
        "where orderID = #{orderID,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(OrdersKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @Insert({
        "insert into orders (orderID, startPlaceID, ",
        "endPlaceID, goodsID, ",
        "goodsNumber, time)",
        "values (#{orderID,jdbcType=INTEGER}, #{startPlaceID,jdbcType=VARCHAR}, ",
        "#{endPlaceID,jdbcType=VARCHAR}, #{goodsID,jdbcType=INTEGER}, ",
        "#{goodsNumber,jdbcType=INTEGER}, #{time,jdbcType=DATE})"
    })
    int insert(Orders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @InsertProvider(type=OrdersSqlProvider.class, method="insertSelective")
    int insertSelective(Orders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @SelectProvider(type=OrdersSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="orderID", property="orderID", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="startPlaceID", property="startPlaceID", jdbcType=JdbcType.VARCHAR),
        @Result(column="endPlaceID", property="endPlaceID", jdbcType=JdbcType.VARCHAR),
        @Result(column="goodsID", property="goodsID", jdbcType=JdbcType.INTEGER),
        @Result(column="goodsNumber", property="goodsNumber", jdbcType=JdbcType.INTEGER),
        @Result(column="time", property="time", jdbcType=JdbcType.DATE)
    })
    List<Orders> selectByExample(OrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @Select({
        "select",
        "orderID, startPlaceID, endPlaceID, goodsID, goodsNumber, time",
        "from orders",
        "where orderID = #{orderID,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="orderID", property="orderID", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="startPlaceID", property="startPlaceID", jdbcType=JdbcType.VARCHAR),
        @Result(column="endPlaceID", property="endPlaceID", jdbcType=JdbcType.VARCHAR),
        @Result(column="goodsID", property="goodsID", jdbcType=JdbcType.INTEGER),
        @Result(column="goodsNumber", property="goodsNumber", jdbcType=JdbcType.INTEGER),
        @Result(column="time", property="time", jdbcType=JdbcType.DATE)
    })
    Orders selectByPrimaryKey(OrdersKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @UpdateProvider(type=OrdersSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Orders record, @Param("example") OrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @UpdateProvider(type=OrdersSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Orders record, @Param("example") OrdersExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @UpdateProvider(type=OrdersSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Orders record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table orders
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @Update({
        "update orders",
        "set startPlaceID = #{startPlaceID,jdbcType=VARCHAR},",
          "endPlaceID = #{endPlaceID,jdbcType=VARCHAR},",
          "goodsID = #{goodsID,jdbcType=INTEGER},",
          "goodsNumber = #{goodsNumber,jdbcType=INTEGER},",
          "time = #{time,jdbcType=DATE}",
        "where orderID = #{orderID,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Orders record);
}