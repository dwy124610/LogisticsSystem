package com.dwy.logistics.model.entities;

public class Code extends CodeKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column code.name
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column code.citycode
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private String citycode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column code.name
     *
     * @return the value of code.name
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column code.name
     *
     * @param name the value for code.name
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column code.citycode
     *
     * @return the value of code.citycode
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public String getCitycode() {
        return citycode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column code.citycode
     *
     * @param citycode the value for code.citycode
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setCitycode(String citycode) {
        this.citycode = citycode == null ? null : citycode.trim();
    }
}