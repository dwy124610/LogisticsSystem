package com.dwy.logistics.model.entities;

import java.util.Date;

public class Transport {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transport.startPlaceID
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private String startPlaceID;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transport.endPlaceID
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private String endPlaceID;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transport.goodsID
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private Integer goodsID;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transport.goodsNumber
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private Integer goodsNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transport.carNumber
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private Integer carNumber;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transport.carVolume
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private Double carVolume;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column transport.time
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private Date time;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transport.startPlaceID
     *
     * @return the value of transport.startPlaceID
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public String getStartPlaceID() {
        return startPlaceID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transport.startPlaceID
     *
     * @param startPlaceID the value for transport.startPlaceID
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setStartPlaceID(String startPlaceID) {
        this.startPlaceID = startPlaceID == null ? null : startPlaceID.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transport.endPlaceID
     *
     * @return the value of transport.endPlaceID
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public String getEndPlaceID() {
        return endPlaceID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transport.endPlaceID
     *
     * @param endPlaceID the value for transport.endPlaceID
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setEndPlaceID(String endPlaceID) {
        this.endPlaceID = endPlaceID == null ? null : endPlaceID.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transport.goodsID
     *
     * @return the value of transport.goodsID
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public Integer getGoodsID() {
        return goodsID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transport.goodsID
     *
     * @param goodsID the value for transport.goodsID
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setGoodsID(Integer goodsID) {
        this.goodsID = goodsID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transport.goodsNumber
     *
     * @return the value of transport.goodsNumber
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transport.goodsNumber
     *
     * @param goodsNumber the value for transport.goodsNumber
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transport.carNumber
     *
     * @return the value of transport.carNumber
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public Integer getCarNumber() {
        return carNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transport.carNumber
     *
     * @param carNumber the value for transport.carNumber
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setCarNumber(Integer carNumber) {
        this.carNumber = carNumber;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transport.carVolume
     *
     * @return the value of transport.carVolume
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public Double getCarVolume() {
        return carVolume;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transport.carVolume
     *
     * @param carVolume the value for transport.carVolume
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setCarVolume(Double carVolume) {
        this.carVolume = carVolume;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column transport.time
     *
     * @return the value of transport.time
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public Date getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column transport.time
     *
     * @param time the value for transport.time
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setTime(Date time) {
        this.time = time;
    }
}