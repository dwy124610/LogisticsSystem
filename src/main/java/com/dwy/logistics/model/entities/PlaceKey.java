package com.dwy.logistics.model.entities;

public class PlaceKey {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column place.uid
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    private String uid;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column place.uid
     *
     * @return the value of place.uid
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public String getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column place.uid
     *
     * @param uid the value for place.uid
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }
}