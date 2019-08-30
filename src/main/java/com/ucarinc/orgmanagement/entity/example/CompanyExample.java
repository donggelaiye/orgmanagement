package com.ucarinc.orgmanagement.entity.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CompanyExample extends Example{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CompanyExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCoNameIsNull() {
            addCriterion("co_name is null");
            return (Criteria) this;
        }

        public Criteria andCoNameIsNotNull() {
            addCriterion("co_name is not null");
            return (Criteria) this;
        }

        public Criteria andCoNameEqualTo(String value) {
            addCriterion("co_name =", value, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameNotEqualTo(String value) {
            addCriterion("co_name <>", value, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameGreaterThan(String value) {
            addCriterion("co_name >", value, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameGreaterThanOrEqualTo(String value) {
            addCriterion("co_name >=", value, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameLessThan(String value) {
            addCriterion("co_name <", value, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameLessThanOrEqualTo(String value) {
            addCriterion("co_name <=", value, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameLike(String value) {
            addCriterion("co_name like", value, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameNotLike(String value) {
            addCriterion("co_name not like", value, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameIn(List<String> values) {
            addCriterion("co_name in", values, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameNotIn(List<String> values) {
            addCriterion("co_name not in", values, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameBetween(String value1, String value2) {
            addCriterion("co_name between", value1, value2, "coName");
            return (Criteria) this;
        }

        public Criteria andCoNameNotBetween(String value1, String value2) {
            addCriterion("co_name not between", value1, value2, "coName");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeIsNull() {
            addCriterion("co_credit_code is null");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeIsNotNull() {
            addCriterion("co_credit_code is not null");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeEqualTo(String value) {
            addCriterion("co_credit_code =", value, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeNotEqualTo(String value) {
            addCriterion("co_credit_code <>", value, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeGreaterThan(String value) {
            addCriterion("co_credit_code >", value, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeGreaterThanOrEqualTo(String value) {
            addCriterion("co_credit_code >=", value, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeLessThan(String value) {
            addCriterion("co_credit_code <", value, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeLessThanOrEqualTo(String value) {
            addCriterion("co_credit_code <=", value, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeLike(String value) {
            addCriterion("co_credit_code like", value, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeNotLike(String value) {
            addCriterion("co_credit_code not like", value, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeIn(List<String> values) {
            addCriterion("co_credit_code in", values, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeNotIn(List<String> values) {
            addCriterion("co_credit_code not in", values, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeBetween(String value1, String value2) {
            addCriterion("co_credit_code between", value1, value2, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoCreditCodeNotBetween(String value1, String value2) {
            addCriterion("co_credit_code not between", value1, value2, "coCreditCode");
            return (Criteria) this;
        }

        public Criteria andCoTypeIsNull() {
            addCriterion("co_type is null");
            return (Criteria) this;
        }

        public Criteria andCoTypeIsNotNull() {
            addCriterion("co_type is not null");
            return (Criteria) this;
        }

        public Criteria andCoTypeEqualTo(Byte value) {
            addCriterion("co_type =", value, "coType");
            return (Criteria) this;
        }

        public Criteria andCoTypeNotEqualTo(Byte value) {
            addCriterion("co_type <>", value, "coType");
            return (Criteria) this;
        }

        public Criteria andCoTypeGreaterThan(Byte value) {
            addCriterion("co_type >", value, "coType");
            return (Criteria) this;
        }

        public Criteria andCoTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("co_type >=", value, "coType");
            return (Criteria) this;
        }

        public Criteria andCoTypeLessThan(Byte value) {
            addCriterion("co_type <", value, "coType");
            return (Criteria) this;
        }

        public Criteria andCoTypeLessThanOrEqualTo(Byte value) {
            addCriterion("co_type <=", value, "coType");
            return (Criteria) this;
        }

        public Criteria andCoTypeIn(List<Byte> values) {
            addCriterion("co_type in", values, "coType");
            return (Criteria) this;
        }

        public Criteria andCoTypeNotIn(List<Byte> values) {
            addCriterion("co_type not in", values, "coType");
            return (Criteria) this;
        }

        public Criteria andCoTypeBetween(Byte value1, Byte value2) {
            addCriterion("co_type between", value1, value2, "coType");
            return (Criteria) this;
        }

        public Criteria andCoTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("co_type not between", value1, value2, "coType");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeIsNull() {
            addCriterion("business_time is null");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeIsNotNull() {
            addCriterion("business_time is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeEqualTo(Date value) {
            addCriterionForJDBCDate("business_time =", value, "businessTime");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("business_time <>", value, "businessTime");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeGreaterThan(Date value) {
            addCriterionForJDBCDate("business_time >", value, "businessTime");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("business_time >=", value, "businessTime");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeLessThan(Date value) {
            addCriterionForJDBCDate("business_time <", value, "businessTime");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("business_time <=", value, "businessTime");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeIn(List<Date> values) {
            addCriterionForJDBCDate("business_time in", values, "businessTime");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("business_time not in", values, "businessTime");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("business_time between", value1, value2, "businessTime");
            return (Criteria) this;
        }

        public Criteria andBusinessTimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("business_time not between", value1, value2, "businessTime");
            return (Criteria) this;
        }

        public Criteria andCoAddressIsNull() {
            addCriterion("co_address is null");
            return (Criteria) this;
        }

        public Criteria andCoAddressIsNotNull() {
            addCriterion("co_address is not null");
            return (Criteria) this;
        }

        public Criteria andCoAddressEqualTo(String value) {
            addCriterion("co_address =", value, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressNotEqualTo(String value) {
            addCriterion("co_address <>", value, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressGreaterThan(String value) {
            addCriterion("co_address >", value, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressGreaterThanOrEqualTo(String value) {
            addCriterion("co_address >=", value, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressLessThan(String value) {
            addCriterion("co_address <", value, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressLessThanOrEqualTo(String value) {
            addCriterion("co_address <=", value, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressLike(String value) {
            addCriterion("co_address like", value, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressNotLike(String value) {
            addCriterion("co_address not like", value, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressIn(List<String> values) {
            addCriterion("co_address in", values, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressNotIn(List<String> values) {
            addCriterion("co_address not in", values, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressBetween(String value1, String value2) {
            addCriterion("co_address between", value1, value2, "coAddress");
            return (Criteria) this;
        }

        public Criteria andCoAddressNotBetween(String value1, String value2) {
            addCriterion("co_address not between", value1, value2, "coAddress");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeIsNull() {
            addCriterion("business_scope is null");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeIsNotNull() {
            addCriterion("business_scope is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeEqualTo(String value) {
            addCriterion("business_scope =", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotEqualTo(String value) {
            addCriterion("business_scope <>", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeGreaterThan(String value) {
            addCriterion("business_scope >", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeGreaterThanOrEqualTo(String value) {
            addCriterion("business_scope >=", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeLessThan(String value) {
            addCriterion("business_scope <", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeLessThanOrEqualTo(String value) {
            addCriterion("business_scope <=", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeLike(String value) {
            addCriterion("business_scope like", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotLike(String value) {
            addCriterion("business_scope not like", value, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeIn(List<String> values) {
            addCriterion("business_scope in", values, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotIn(List<String> values) {
            addCriterion("business_scope not in", values, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeBetween(String value1, String value2) {
            addCriterion("business_scope between", value1, value2, "businessScope");
            return (Criteria) this;
        }

        public Criteria andBusinessScopeNotBetween(String value1, String value2) {
            addCriterion("business_scope not between", value1, value2, "businessScope");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeIsNull() {
            addCriterion("legal_representative is null");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeIsNotNull() {
            addCriterion("legal_representative is not null");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeEqualTo(String value) {
            addCriterion("legal_representative =", value, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeNotEqualTo(String value) {
            addCriterion("legal_representative <>", value, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeGreaterThan(String value) {
            addCriterion("legal_representative >", value, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeGreaterThanOrEqualTo(String value) {
            addCriterion("legal_representative >=", value, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeLessThan(String value) {
            addCriterion("legal_representative <", value, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeLessThanOrEqualTo(String value) {
            addCriterion("legal_representative <=", value, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeLike(String value) {
            addCriterion("legal_representative like", value, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeNotLike(String value) {
            addCriterion("legal_representative not like", value, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeIn(List<String> values) {
            addCriterion("legal_representative in", values, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeNotIn(List<String> values) {
            addCriterion("legal_representative not in", values, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeBetween(String value1, String value2) {
            addCriterion("legal_representative between", value1, value2, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andLegalRepresentativeNotBetween(String value1, String value2) {
            addCriterion("legal_representative not between", value1, value2, "legalRepresentative");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalIsNull() {
            addCriterion("registered_capital is null");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalIsNotNull() {
            addCriterion("registered_capital is not null");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalEqualTo(String value) {
            addCriterion("registered_capital =", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalNotEqualTo(String value) {
            addCriterion("registered_capital <>", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalGreaterThan(String value) {
            addCriterion("registered_capital >", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalGreaterThanOrEqualTo(String value) {
            addCriterion("registered_capital >=", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalLessThan(String value) {
            addCriterion("registered_capital <", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalLessThanOrEqualTo(String value) {
            addCriterion("registered_capital <=", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalLike(String value) {
            addCriterion("registered_capital like", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalNotLike(String value) {
            addCriterion("registered_capital not like", value, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalIn(List<String> values) {
            addCriterion("registered_capital in", values, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalNotIn(List<String> values) {
            addCriterion("registered_capital not in", values, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalBetween(String value1, String value2) {
            addCriterion("registered_capital between", value1, value2, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andRegisteredCapitalNotBetween(String value1, String value2) {
            addCriterion("registered_capital not between", value1, value2, "registeredCapital");
            return (Criteria) this;
        }

        public Criteria andSetDateIsNull() {
            addCriterion("set_date is null");
            return (Criteria) this;
        }

        public Criteria andSetDateIsNotNull() {
            addCriterion("set_date is not null");
            return (Criteria) this;
        }

        public Criteria andSetDateEqualTo(Date value) {
            addCriterionForJDBCDate("set_date =", value, "setDate");
            return (Criteria) this;
        }

        public Criteria andSetDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("set_date <>", value, "setDate");
            return (Criteria) this;
        }

        public Criteria andSetDateGreaterThan(Date value) {
            addCriterionForJDBCDate("set_date >", value, "setDate");
            return (Criteria) this;
        }

        public Criteria andSetDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("set_date >=", value, "setDate");
            return (Criteria) this;
        }

        public Criteria andSetDateLessThan(Date value) {
            addCriterionForJDBCDate("set_date <", value, "setDate");
            return (Criteria) this;
        }

        public Criteria andSetDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("set_date <=", value, "setDate");
            return (Criteria) this;
        }

        public Criteria andSetDateIn(List<Date> values) {
            addCriterionForJDBCDate("set_date in", values, "setDate");
            return (Criteria) this;
        }

        public Criteria andSetDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("set_date not in", values, "setDate");
            return (Criteria) this;
        }

        public Criteria andSetDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("set_date between", value1, value2, "setDate");
            return (Criteria) this;
        }

        public Criteria andSetDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("set_date not between", value1, value2, "setDate");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityIsNull() {
            addCriterion("registered_authority is null");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityIsNotNull() {
            addCriterion("registered_authority is not null");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityEqualTo(String value) {
            addCriterion("registered_authority =", value, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityNotEqualTo(String value) {
            addCriterion("registered_authority <>", value, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityGreaterThan(String value) {
            addCriterion("registered_authority >", value, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityGreaterThanOrEqualTo(String value) {
            addCriterion("registered_authority >=", value, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityLessThan(String value) {
            addCriterion("registered_authority <", value, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityLessThanOrEqualTo(String value) {
            addCriterion("registered_authority <=", value, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityLike(String value) {
            addCriterion("registered_authority like", value, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityNotLike(String value) {
            addCriterion("registered_authority not like", value, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityIn(List<String> values) {
            addCriterion("registered_authority in", values, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityNotIn(List<String> values) {
            addCriterion("registered_authority not in", values, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityBetween(String value1, String value2) {
            addCriterion("registered_authority between", value1, value2, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andRegisteredAuthorityNotBetween(String value1, String value2) {
            addCriterion("registered_authority not between", value1, value2, "registeredAuthority");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIsNull() {
            addCriterion("approval_date is null");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIsNotNull() {
            addCriterion("approval_date is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalDateEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date =", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date <>", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateGreaterThan(Date value) {
            addCriterionForJDBCDate("approval_date >", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date >=", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateLessThan(Date value) {
            addCriterionForJDBCDate("approval_date <", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date <=", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIn(List<Date> values) {
            addCriterionForJDBCDate("approval_date in", values, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("approval_date not in", values, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("approval_date between", value1, value2, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("approval_date not between", value1, value2, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusIsNull() {
            addCriterion("registered_status is null");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusIsNotNull() {
            addCriterion("registered_status is not null");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusEqualTo(String value) {
            addCriterion("registered_status =", value, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusNotEqualTo(String value) {
            addCriterion("registered_status <>", value, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusGreaterThan(String value) {
            addCriterion("registered_status >", value, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusGreaterThanOrEqualTo(String value) {
            addCriterion("registered_status >=", value, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusLessThan(String value) {
            addCriterion("registered_status <", value, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusLessThanOrEqualTo(String value) {
            addCriterion("registered_status <=", value, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusLike(String value) {
            addCriterion("registered_status like", value, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusNotLike(String value) {
            addCriterion("registered_status not like", value, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusIn(List<String> values) {
            addCriterion("registered_status in", values, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusNotIn(List<String> values) {
            addCriterion("registered_status not in", values, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusBetween(String value1, String value2) {
            addCriterion("registered_status between", value1, value2, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andRegisteredStatusNotBetween(String value1, String value2) {
            addCriterion("registered_status not between", value1, value2, "registeredStatus");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseIsNull() {
            addCriterion("business_license is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseIsNotNull() {
            addCriterion("business_license is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseEqualTo(String value) {
            addCriterion("business_license =", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNotEqualTo(String value) {
            addCriterion("business_license <>", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseGreaterThan(String value) {
            addCriterion("business_license >", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseGreaterThanOrEqualTo(String value) {
            addCriterion("business_license >=", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseLessThan(String value) {
            addCriterion("business_license <", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseLessThanOrEqualTo(String value) {
            addCriterion("business_license <=", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseLike(String value) {
            addCriterion("business_license like", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNotLike(String value) {
            addCriterion("business_license not like", value, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseIn(List<String> values) {
            addCriterion("business_license in", values, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNotIn(List<String> values) {
            addCriterion("business_license not in", values, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseBetween(String value1, String value2) {
            addCriterion("business_license between", value1, value2, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andBusinessLicenseNotBetween(String value1, String value2) {
            addCriterion("business_license not between", value1, value2, "businessLicense");
            return (Criteria) this;
        }

        public Criteria andCoNatureIsNull() {
            addCriterion("co_nature is null");
            return (Criteria) this;
        }

        public Criteria andCoNatureIsNotNull() {
            addCriterion("co_nature is not null");
            return (Criteria) this;
        }

        public Criteria andCoNatureEqualTo(Byte value) {
            addCriterion("co_nature =", value, "coNature");
            return (Criteria) this;
        }

        public Criteria andCoNatureNotEqualTo(Byte value) {
            addCriterion("co_nature <>", value, "coNature");
            return (Criteria) this;
        }

        public Criteria andCoNatureGreaterThan(Byte value) {
            addCriterion("co_nature >", value, "coNature");
            return (Criteria) this;
        }

        public Criteria andCoNatureGreaterThanOrEqualTo(Byte value) {
            addCriterion("co_nature >=", value, "coNature");
            return (Criteria) this;
        }

        public Criteria andCoNatureLessThan(Byte value) {
            addCriterion("co_nature <", value, "coNature");
            return (Criteria) this;
        }

        public Criteria andCoNatureLessThanOrEqualTo(Byte value) {
            addCriterion("co_nature <=", value, "coNature");
            return (Criteria) this;
        }

        public Criteria andCoNatureIn(List<Byte> values) {
            addCriterion("co_nature in", values, "coNature");
            return (Criteria) this;
        }

        public Criteria andCoNatureNotIn(List<Byte> values) {
            addCriterion("co_nature not in", values, "coNature");
            return (Criteria) this;
        }

        public Criteria andCoNatureBetween(Byte value1, Byte value2) {
            addCriterion("co_nature between", value1, value2, "coNature");
            return (Criteria) this;
        }

        public Criteria andCoNatureNotBetween(Byte value1, Byte value2) {
            addCriterion("co_nature not between", value1, value2, "coNature");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressIsNull() {
            addCriterion("registered_address is null");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressIsNotNull() {
            addCriterion("registered_address is not null");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressEqualTo(String value) {
            addCriterion("registered_address =", value, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressNotEqualTo(String value) {
            addCriterion("registered_address <>", value, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressGreaterThan(String value) {
            addCriterion("registered_address >", value, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressGreaterThanOrEqualTo(String value) {
            addCriterion("registered_address >=", value, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressLessThan(String value) {
            addCriterion("registered_address <", value, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressLessThanOrEqualTo(String value) {
            addCriterion("registered_address <=", value, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressLike(String value) {
            addCriterion("registered_address like", value, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressNotLike(String value) {
            addCriterion("registered_address not like", value, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressIn(List<String> values) {
            addCriterion("registered_address in", values, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressNotIn(List<String> values) {
            addCriterion("registered_address not in", values, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressBetween(String value1, String value2) {
            addCriterion("registered_address between", value1, value2, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andRegisteredAddressNotBetween(String value1, String value2) {
            addCriterion("registered_address not between", value1, value2, "registeredAddress");
            return (Criteria) this;
        }

        public Criteria andOpenBankIsNull() {
            addCriterion("open_bank is null");
            return (Criteria) this;
        }

        public Criteria andOpenBankIsNotNull() {
            addCriterion("open_bank is not null");
            return (Criteria) this;
        }

        public Criteria andOpenBankEqualTo(String value) {
            addCriterion("open_bank =", value, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankNotEqualTo(String value) {
            addCriterion("open_bank <>", value, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankGreaterThan(String value) {
            addCriterion("open_bank >", value, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankGreaterThanOrEqualTo(String value) {
            addCriterion("open_bank >=", value, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankLessThan(String value) {
            addCriterion("open_bank <", value, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankLessThanOrEqualTo(String value) {
            addCriterion("open_bank <=", value, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankLike(String value) {
            addCriterion("open_bank like", value, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankNotLike(String value) {
            addCriterion("open_bank not like", value, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankIn(List<String> values) {
            addCriterion("open_bank in", values, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankNotIn(List<String> values) {
            addCriterion("open_bank not in", values, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankBetween(String value1, String value2) {
            addCriterion("open_bank between", value1, value2, "openBank");
            return (Criteria) this;
        }

        public Criteria andOpenBankNotBetween(String value1, String value2) {
            addCriterion("open_bank not between", value1, value2, "openBank");
            return (Criteria) this;
        }

        public Criteria andCoAccountIsNull() {
            addCriterion("co_account is null");
            return (Criteria) this;
        }

        public Criteria andCoAccountIsNotNull() {
            addCriterion("co_account is not null");
            return (Criteria) this;
        }

        public Criteria andCoAccountEqualTo(String value) {
            addCriterion("co_account =", value, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountNotEqualTo(String value) {
            addCriterion("co_account <>", value, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountGreaterThan(String value) {
            addCriterion("co_account >", value, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountGreaterThanOrEqualTo(String value) {
            addCriterion("co_account >=", value, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountLessThan(String value) {
            addCriterion("co_account <", value, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountLessThanOrEqualTo(String value) {
            addCriterion("co_account <=", value, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountLike(String value) {
            addCriterion("co_account like", value, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountNotLike(String value) {
            addCriterion("co_account not like", value, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountIn(List<String> values) {
            addCriterion("co_account in", values, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountNotIn(List<String> values) {
            addCriterion("co_account not in", values, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountBetween(String value1, String value2) {
            addCriterion("co_account between", value1, value2, "coAccount");
            return (Criteria) this;
        }

        public Criteria andCoAccountNotBetween(String value1, String value2) {
            addCriterion("co_account not between", value1, value2, "coAccount");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneIsNull() {
            addCriterion("registered_phone is null");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneIsNotNull() {
            addCriterion("registered_phone is not null");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneEqualTo(Long value) {
            addCriterion("registered_phone =", value, "registeredPhone");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneNotEqualTo(Long value) {
            addCriterion("registered_phone <>", value, "registeredPhone");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneGreaterThan(Long value) {
            addCriterion("registered_phone >", value, "registeredPhone");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneGreaterThanOrEqualTo(Long value) {
            addCriterion("registered_phone >=", value, "registeredPhone");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneLessThan(Long value) {
            addCriterion("registered_phone <", value, "registeredPhone");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneLessThanOrEqualTo(Long value) {
            addCriterion("registered_phone <=", value, "registeredPhone");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneIn(List<Long> values) {
            addCriterion("registered_phone in", values, "registeredPhone");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneNotIn(List<Long> values) {
            addCriterion("registered_phone not in", values, "registeredPhone");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneBetween(Long value1, Long value2) {
            addCriterion("registered_phone between", value1, value2, "registeredPhone");
            return (Criteria) this;
        }

        public Criteria andRegisteredPhoneNotBetween(Long value1, Long value2) {
            addCriterion("registered_phone not between", value1, value2, "registeredPhone");
            return (Criteria) this;
        }

        public Criteria andCoMarkIsNull() {
            addCriterion("co_mark is null");
            return (Criteria) this;
        }

        public Criteria andCoMarkIsNotNull() {
            addCriterion("co_mark is not null");
            return (Criteria) this;
        }

        public Criteria andCoMarkEqualTo(Byte value) {
            addCriterion("co_mark =", value, "coMark");
            return (Criteria) this;
        }

        public Criteria andCoMarkNotEqualTo(Byte value) {
            addCriterion("co_mark <>", value, "coMark");
            return (Criteria) this;
        }

        public Criteria andCoMarkGreaterThan(Byte value) {
            addCriterion("co_mark >", value, "coMark");
            return (Criteria) this;
        }

        public Criteria andCoMarkGreaterThanOrEqualTo(Byte value) {
            addCriterion("co_mark >=", value, "coMark");
            return (Criteria) this;
        }

        public Criteria andCoMarkLessThan(Byte value) {
            addCriterion("co_mark <", value, "coMark");
            return (Criteria) this;
        }

        public Criteria andCoMarkLessThanOrEqualTo(Byte value) {
            addCriterion("co_mark <=", value, "coMark");
            return (Criteria) this;
        }

        public Criteria andCoMarkIn(List<Byte> values) {
            addCriterion("co_mark in", values, "coMark");
            return (Criteria) this;
        }

        public Criteria andCoMarkNotIn(List<Byte> values) {
            addCriterion("co_mark not in", values, "coMark");
            return (Criteria) this;
        }

        public Criteria andCoMarkBetween(Byte value1, Byte value2) {
            addCriterion("co_mark between", value1, value2, "coMark");
            return (Criteria) this;
        }

        public Criteria andCoMarkNotBetween(Byte value1, Byte value2) {
            addCriterion("co_mark not between", value1, value2, "coMark");
            return (Criteria) this;
        }

        public Criteria andModifyEmpIsNull() {
            addCriterion("modify_emp is null");
            return (Criteria) this;
        }

        public Criteria andModifyEmpIsNotNull() {
            addCriterion("modify_emp is not null");
            return (Criteria) this;
        }

        public Criteria andModifyEmpEqualTo(Long value) {
            addCriterion("modify_emp =", value, "modifyEmp");
            return (Criteria) this;
        }

        public Criteria andModifyEmpNotEqualTo(Long value) {
            addCriterion("modify_emp <>", value, "modifyEmp");
            return (Criteria) this;
        }

        public Criteria andModifyEmpGreaterThan(Long value) {
            addCriterion("modify_emp >", value, "modifyEmp");
            return (Criteria) this;
        }

        public Criteria andModifyEmpGreaterThanOrEqualTo(Long value) {
            addCriterion("modify_emp >=", value, "modifyEmp");
            return (Criteria) this;
        }

        public Criteria andModifyEmpLessThan(Long value) {
            addCriterion("modify_emp <", value, "modifyEmp");
            return (Criteria) this;
        }

        public Criteria andModifyEmpLessThanOrEqualTo(Long value) {
            addCriterion("modify_emp <=", value, "modifyEmp");
            return (Criteria) this;
        }

        public Criteria andModifyEmpIn(List<Long> values) {
            addCriterion("modify_emp in", values, "modifyEmp");
            return (Criteria) this;
        }

        public Criteria andModifyEmpNotIn(List<Long> values) {
            addCriterion("modify_emp not in", values, "modifyEmp");
            return (Criteria) this;
        }

        public Criteria andModifyEmpBetween(Long value1, Long value2) {
            addCriterion("modify_emp between", value1, value2, "modifyEmp");
            return (Criteria) this;
        }

        public Criteria andModifyEmpNotBetween(Long value1, Long value2) {
            addCriterion("modify_emp not between", value1, value2, "modifyEmp");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Byte value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Byte value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Byte value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Byte value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Byte value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Byte> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Byte> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Byte value1, Byte value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Byte value1, Byte value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andCreateEmpIsNull() {
            addCriterion("create_emp is null");
            return (Criteria) this;
        }

        public Criteria andCreateEmpIsNotNull() {
            addCriterion("create_emp is not null");
            return (Criteria) this;
        }

        public Criteria andCreateEmpEqualTo(Long value) {
            addCriterion("create_emp =", value, "createEmp");
            return (Criteria) this;
        }

        public Criteria andCreateEmpNotEqualTo(Long value) {
            addCriterion("create_emp <>", value, "createEmp");
            return (Criteria) this;
        }

        public Criteria andCreateEmpGreaterThan(Long value) {
            addCriterion("create_emp >", value, "createEmp");
            return (Criteria) this;
        }

        public Criteria andCreateEmpGreaterThanOrEqualTo(Long value) {
            addCriterion("create_emp >=", value, "createEmp");
            return (Criteria) this;
        }

        public Criteria andCreateEmpLessThan(Long value) {
            addCriterion("create_emp <", value, "createEmp");
            return (Criteria) this;
        }

        public Criteria andCreateEmpLessThanOrEqualTo(Long value) {
            addCriterion("create_emp <=", value, "createEmp");
            return (Criteria) this;
        }

        public Criteria andCreateEmpIn(List<Long> values) {
            addCriterion("create_emp in", values, "createEmp");
            return (Criteria) this;
        }

        public Criteria andCreateEmpNotIn(List<Long> values) {
            addCriterion("create_emp not in", values, "createEmp");
            return (Criteria) this;
        }

        public Criteria andCreateEmpBetween(Long value1, Long value2) {
            addCriterion("create_emp between", value1, value2, "createEmp");
            return (Criteria) this;
        }

        public Criteria andCreateEmpNotBetween(Long value1, Long value2) {
            addCriterion("create_emp not between", value1, value2, "createEmp");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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