package com.spring.mpvue.springbootmpvue.mybatis.mapper;

import com.spring.mpvue.springbootmpvue.mybatis.po.SysApiConfig;
import com.spring.mpvue.springbootmpvue.mybatis.po.SysApiConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysApiConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    int countByExample(SysApiConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    int deleteByExample(SysApiConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    int insert(SysApiConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    int insertSelective(SysApiConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    List<SysApiConfig> selectByExample(SysApiConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    SysApiConfig selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    int updateByExampleSelective(@Param("record") SysApiConfig record, @Param("example") SysApiConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    int updateByExample(@Param("record") SysApiConfig record, @Param("example") SysApiConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    int updateByPrimaryKeySelective(SysApiConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sys_api_config
     *
     * @mbggenerated Tue Jun 02 15:01:16 CST 2020
     */
    int updateByPrimaryKey(SysApiConfig record);
}