package com.dwy.logistics.model.entities;

public class CodeKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column code.adcode
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private String adcode;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column code.adcode
     *
     * @return the value of code.adcode
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public String getAdcode() {
        return adcode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column code.adcode
     *
     * @param adcode the value for code.adcode
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setAdcode(String adcode) {
        this.adcode = adcode == null ? null : adcode.trim();
    }
}