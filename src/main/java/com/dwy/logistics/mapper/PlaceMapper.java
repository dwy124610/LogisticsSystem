package com.dwy.logistics.mapper;

import com.dwy.logistics.model.entities.Place;
import com.dwy.logistics.model.entities.PlaceExample;
import com.dwy.logistics.model.entities.PlaceKey;
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

public interface PlaceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @SelectProvider(type=PlaceSqlProvider.class, method="countByExample")
    long countByExample(PlaceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @DeleteProvider(type=PlaceSqlProvider.class, method="deleteByExample")
    int deleteByExample(PlaceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @Delete({
        "delete from place",
        "where uid = #{uid,jdbcType=VARCHAR}"
    })
    int deleteByPrimaryKey(PlaceKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @Insert({
        "insert into place (uid, name, ",
        "lng, lat)",
        "values (#{uid,jdbcType=VARCHAR}, #{name,jdbcType=VARCHAR}, ",
        "#{lng,jdbcType=DOUBLE}, #{lat,jdbcType=DOUBLE})"
    })
    int insert(Place record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @InsertProvider(type=PlaceSqlProvider.class, method="insertSelective")
    int insertSelective(Place record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @SelectProvider(type=PlaceSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="uid", property="uid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="lng", property="lng", jdbcType=JdbcType.DOUBLE),
        @Result(column="lat", property="lat", jdbcType=JdbcType.DOUBLE)
    })
    List<Place> selectByExample(PlaceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @Select({
        "select",
        "uid, name, lng, lat",
        "from place",
        "where uid = #{uid,jdbcType=VARCHAR}"
    })
    @Results({
        @Result(column="uid", property="uid", jdbcType=JdbcType.VARCHAR, id=true),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="lng", property="lng", jdbcType=JdbcType.DOUBLE),
        @Result(column="lat", property="lat", jdbcType=JdbcType.DOUBLE)
    })
    Place selectByPrimaryKey(PlaceKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @UpdateProvider(type=PlaceSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Place record, @Param("example") PlaceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @UpdateProvider(type=PlaceSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Place record, @Param("example") PlaceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @UpdateProvider(type=PlaceSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Place record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table place
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    @Update({
        "update place",
        "set name = #{name,jdbcType=VARCHAR},",
          "lng = #{lng,jdbcType=DOUBLE},",
          "lat = #{lat,jdbcType=DOUBLE}",
        "where uid = #{uid,jdbcType=VARCHAR}"
    })
    int updateByPrimaryKey(Place record);
}