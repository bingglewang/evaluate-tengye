package com.spring.mpvue.springbootmpvue.mybatis.po;

public class SysLogWithBLOBs extends SysLog {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_log.params
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    private String params;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_log.time
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    private String time;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_log.exception
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    private String exception;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_log.params
     *
     * @return the value of sys_log.params
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    public String getParams() {
        return params;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_log.params
     *
     * @param params the value for sys_log.params
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_log.time
     *
     * @return the value of sys_log.time
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    public String getTime() {
        return time;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_log.time
     *
     * @param time the value for sys_log.time
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_log.exception
     *
     * @return the value of sys_log.exception
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    public String getException() {
        return exception;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_log.exception
     *
     * @param exception the value for sys_log.exception
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    public void setException(String exception) {
        this.exception = exception == null ? null : exception.trim();
    }
}