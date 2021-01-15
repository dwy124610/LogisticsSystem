package com.dwy.logistics.model.entities;

import java.util.ArrayList;
import java.util.List;

public class CarExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public CarExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCarIDIsNull() {
            addCriterion("carID is null");
            return (Criteria) this;
        }

        public Criteria andCarIDIsNotNull() {
            addCriterion("carID is not null");
            return (Criteria) this;
        }

        public Criteria andCarIDEqualTo(Integer value) {
            addCriterion("carID =", value, "carID");
            return (Criteria) this;
        }

        public Criteria andCarIDNotEqualTo(Integer value) {
            addCriterion("carID <>", value, "carID");
            return (Criteria) this;
        }

        public Criteria andCarIDGreaterThan(Integer value) {
            addCriterion("carID >", value, "carID");
            return (Criteria) this;
        }

        public Criteria andCarIDGreaterThanOrEqualTo(Integer value) {
            addCriterion("carID >=", value, "carID");
            return (Criteria) this;
        }

        public Criteria andCarIDLessThan(Integer value) {
            addCriterion("carID <", value, "carID");
            return (Criteria) this;
        }

        public Criteria andCarIDLessThanOrEqualTo(Integer value) {
            addCriterion("carID <=", value, "carID");
            return (Criteria) this;
        }

        public Criteria andCarIDIn(List<Integer> values) {
            addCriterion("carID in", values, "carID");
            return (Criteria) this;
        }

        public Criteria andCarIDNotIn(List<Integer> values) {
            addCriterion("carID not in", values, "carID");
            return (Criteria) this;
        }

        public Criteria andCarIDBetween(Integer value1, Integer value2) {
            addCriterion("carID between", value1, value2, "carID");
            return (Criteria) this;
        }

        public Criteria andCarIDNotBetween(Integer value1, Integer value2) {
            addCriterion("carID not between", value1, value2, "carID");
            return (Criteria) this;
        }

        public Criteria andCarTypeIsNull() {
            addCriterion("carType is null");
            return (Criteria) this;
        }

        public Criteria andCarTypeIsNotNull() {
            addCriterion("carType is not null");
            return (Criteria) this;
        }

        public Criteria andCarTypeEqualTo(String value) {
            addCriterion("carType =", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotEqualTo(String value) {
            addCriterion("carType <>", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeGreaterThan(String value) {
            addCriterion("carType >", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeGreaterThanOrEqualTo(String value) {
            addCriterion("carType >=", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeLessThan(String value) {
            addCriterion("carType <", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeLessThanOrEqualTo(String value) {
            addCriterion("carType <=", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeLike(String value) {
            addCriterion("carType like", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotLike(String value) {
            addCriterion("carType not like", value, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeIn(List<String> values) {
            addCriterion("carType in", values, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotIn(List<String> values) {
            addCriterion("carType not in", values, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeBetween(String value1, String value2) {
            addCriterion("carType between", value1, value2, "carType");
            return (Criteria) this;
        }

        public Criteria andCarTypeNotBetween(String value1, String value2) {
            addCriterion("carType not between", value1, value2, "carType");
            return (Criteria) this;
        }

        public Criteria andVolumeIsNull() {
            addCriterion("volume is null");
            return (Criteria) this;
        }

        public Criteria andVolumeIsNotNull() {
            addCriterion("volume is not null");
            return (Criteria) this;
        }

        public Criteria andVolumeEqualTo(Double value) {
            addCriterion("volume =", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotEqualTo(Double value) {
            addCriterion("volume <>", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeGreaterThan(Double value) {
            addCriterion("volume >", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeGreaterThanOrEqualTo(Double value) {
            addCriterion("volume >=", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLessThan(Double value) {
            addCriterion("volume <", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeLessThanOrEqualTo(Double value) {
            addCriterion("volume <=", value, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeIn(List<Double> values) {
            addCriterion("volume in", values, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotIn(List<Double> values) {
            addCriterion("volume not in", values, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeBetween(Double value1, Double value2) {
            addCriterion("volume between", value1, value2, "volume");
            return (Criteria) this;
        }

        public Criteria andVolumeNotBetween(Double value1, Double value2) {
            addCriterion("volume not between", value1, value2, "volume");
            return (Criteria) this;
        }

        public Criteria andMinVolumeIsNull() {
            addCriterion("minVolume is null");
            return (Criteria) this;
        }

        public Criteria andMinVolumeIsNotNull() {
            addCriterion("minVolume is not null");
            return (Criteria) this;
        }

        public Criteria andMinVolumeEqualTo(Double value) {
            addCriterion("minVolume =", value, "minVolume");
            return (Criteria) this;
        }

        public Criteria andMinVolumeNotEqualTo(Double value) {
            addCriterion("minVolume <>", value, "minVolume");
            return (Criteria) this;
        }

        public Criteria andMinVolumeGreaterThan(Double value) {
            addCriterion("minVolume >", value, "minVolume");
            return (Criteria) this;
        }

        public Criteria andMinVolumeGreaterThanOrEqualTo(Double value) {
            addCriterion("minVolume >=", value, "minVolume");
            return (Criteria) this;
        }

        public Criteria andMinVolumeLessThan(Double value) {
            addCriterion("minVolume <", value, "minVolume");
            return (Criteria) this;
        }

        public Criteria andMinVolumeLessThanOrEqualTo(Double value) {
            addCriterion("minVolume <=", value, "minVolume");
            return (Criteria) this;
        }

        public Criteria andMinVolumeIn(List<Double> values) {
            addCriterion("minVolume in", values, "minVolume");
            return (Criteria) this;
        }

        public Criteria andMinVolumeNotIn(List<Double> values) {
            addCriterion("minVolume not in", values, "minVolume");
            return (Criteria) this;
        }

        public Criteria andMinVolumeBetween(Double value1, Double value2) {
            addCriterion("minVolume between", value1, value2, "minVolume");
            return (Criteria) this;
        }

        public Criteria andMinVolumeNotBetween(Double value1, Double value2) {
            addCriterion("minVolume not between", value1, value2, "minVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeIsNull() {
            addCriterion("maxVolume is null");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeIsNotNull() {
            addCriterion("maxVolume is not null");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeEqualTo(Double value) {
            addCriterion("maxVolume =", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeNotEqualTo(Double value) {
            addCriterion("maxVolume <>", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeGreaterThan(Double value) {
            addCriterion("maxVolume >", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeGreaterThanOrEqualTo(Double value) {
            addCriterion("maxVolume >=", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeLessThan(Double value) {
            addCriterion("maxVolume <", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeLessThanOrEqualTo(Double value) {
            addCriterion("maxVolume <=", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeIn(List<Double> values) {
            addCriterion("maxVolume in", values, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeNotIn(List<Double> values) {
            addCriterion("maxVolume not in", values, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeBetween(Double value1, Double value2) {
            addCriterion("maxVolume between", value1, value2, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeNotBetween(Double value1, Double value2) {
            addCriterion("maxVolume not between", value1, value2, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andHeightIsNull() {
            addCriterion("height is null");
            return (Criteria) this;
        }

        public Criteria andHeightIsNotNull() {
            addCriterion("height is not null");
            return (Criteria) this;
        }

        public Criteria andHeightEqualTo(Double value) {
            addCriterion("height =", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotEqualTo(Double value) {
            addCriterion("height <>", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThan(Double value) {
            addCriterion("height >", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThanOrEqualTo(Double value) {
            addCriterion("height >=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThan(Double value) {
            addCriterion("height <", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThanOrEqualTo(Double value) {
            addCriterion("height <=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightIn(List<Double> values) {
            addCriterion("height in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotIn(List<Double> values) {
            addCriterion("height not in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightBetween(Double value1, Double value2) {
            addCriterion("height between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotBetween(Double value1, Double value2) {
            addCriterion("height not between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andWidthIsNull() {
            addCriterion("width is null");
            return (Criteria) this;
        }

        public Criteria andWidthIsNotNull() {
            addCriterion("width is not null");
            return (Criteria) this;
        }

        public Criteria andWidthEqualTo(Double value) {
            addCriterion("width =", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotEqualTo(Double value) {
            addCriterion("width <>", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthGreaterThan(Double value) {
            addCriterion("width >", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthGreaterThanOrEqualTo(Double value) {
            addCriterion("width >=", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthLessThan(Double value) {
            addCriterion("width <", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthLessThanOrEqualTo(Double value) {
            addCriterion("width <=", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthIn(List<Double> values) {
            addCriterion("width in", values, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotIn(List<Double> values) {
            addCriterion("width not in", values, "width");
            return (Criteria) this;
        }

        public Criteria andWidthBetween(Double value1, Double value2) {
            addCriterion("width between", value1, value2, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotBetween(Double value1, Double value2) {
            addCriterion("width not between", value1, value2, "width");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table car
     *
     * @mbg.generated do_not_delete_during_merge Fri Jan 08 16:16:33 CST 2021
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table car
     *
     * @mbg.generated Fri Jan 08 16:16:33 CST 2021
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}