/**
 * 
 */
package com.ptsoft.pts.system.model.vo;

import com.ptsoft.common.base.BaseEntity;
import com.ptsoft.pts.PisConstants;

/**
 * 系统日志
 * 
 * @author fuyiyong
 *
 */
public class SysLog extends BaseEntity
{
	private static final long serialVersionUID = 3114554672938403197L;
	/**直增长ID*/
	private int id;
	/**创建时间*/
	private String create_time;
	/**操作人*/
	private String operator;
	/**操作人ID*/
	private int operator_id;
	/**客户端IP*/
	private String ip;
	/**浏览器名称*/
	private String browser;
	/**执行的动作*/
	private String action_id;
	/**执行的方法*/
	private String function_id;
	/**操作类型*/
	private String action_type;
	/**日志类型 1-正常 2-异常 3-企业业务*/
	private int log_type;
	/**操作内容*/
	private String content;
	/**操作状态 0-失败 1-成功'*/
	private int status;
	/**操作结果*/
	private String result;
	
	/**状态名称*/
	public String getLogActionType()
	{
		PisConstants.LogActionType logActionType = PisConstants.LogActionType.FromKey(Integer.parseInt(this.action_type));
		if (logActionType != null)
		{
			return logActionType.getText();
		}
		else
		{
			return "-";
		}
	}
	
	public SysLog()
	{
	}
	
	public SysLog(String operator, int operator_id, String function_id, String content, int status, String action_type, int log_type) 
	{
		this.operator = operator;
		this.operator_id = operator_id;
		this.function_id = function_id;
		this.action_type = action_type;
		this.log_type = log_type;
		this.content = content;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public int getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(int operator_id) {
		this.operator_id = operator_id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getAction_id() {
		return action_id;
	}
	public void setAction_id(String action_id) {
		this.action_id = action_id;
	}
	public String getFunction_id() {
		return function_id;
	}
	public void setFunction_id(String function_id) {
		this.function_id = function_id;
	}
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public int getLog_type() {
		return log_type;
	}
	public void setLog_type(int log_type) {
		this.log_type = log_type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
