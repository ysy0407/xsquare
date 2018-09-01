package com.xsquare.modules.xsquare.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * 
 * 
 * @author yusongyuan
 * @email ysy_0407@foxmail.com
 * @date 2018-02-22 16:13:09
 */
public class ClasstableEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//课程表ID
	private Integer id;
	//课程ID
	private Integer courseId;
	//课程内容
	private CourseEntity courseEntity;
	//老师ID，用“|”分隔
	private String teacherId;
	//老师内容
	private TeacherEntity teacherEntity;
	//教室ID
	private Integer classroomId;
	//教室内容
	private ClassroomEntity classroomEntity;
	//上课开始的小时数
	private String startHour;
	//上课开始的分钟数
	private String startMinute;
	//上课结束的小时数
	private String endHour;
	//上课结束的分钟数
	private String endMinute;
	//签到次数
	private int signNum;
	//上课内容
	private String describe;
	//上课日期，年月日
	private String date;
	//时间段，1：上午，2：中午，3：晚上
	private Integer timeSlot;
	//课程表状态，1：正常，0：删除
	private Integer status;

	public ClasstableEntity(){}

	public ClasstableEntity(String describe) {
		this.describe = describe;
	}

	public ClasstableEntity(String date, Integer timeSlot, String describe) {
		this.date = date;
		this.timeSlot = timeSlot;
		this.describe = describe;
	}

	@Override
	public String toString() {
		return "ClasstableEntity{" +
				"id=" + id +
				", courseId=" + courseId +
				", teacherId='" + teacherId + '\'' +
				", classroomId=" + classroomId +
				", startHour=" + startHour +
				", startMinute=" + startMinute +
				", endHour=" + endHour +
				", endMinute=" + endMinute +
				", describe='" + describe + '\'' +
				", date='" + date + '\'' +
				", timeSlot=" + timeSlot +
				", status=" + status +
				'}';
	}

	/**
	 * 设置：课程表ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：课程表ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：课程ID
	 */
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	/**
	 * 获取：课程ID
	 */
	public Integer getCourseId() {
		return courseId;
	}
	/**
	 * 设置：老师ID，用“|”分隔
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	/**
	 * 获取：老师ID，用“|”分隔
	 */
	public String getTeacherId() {
		return teacherId;
	}
	/**
	 * 设置：教室ID
	 */
	public void setClassroomId(Integer classroomId) {
		this.classroomId = classroomId;
	}
	/**
	 * 获取：教室ID
	 */
	public Integer getClassroomId() {
		return classroomId;
	}
	/**
	 * 设置：上课开始的小时数
	 */
	public void setStartHour(String startHour) {
		this.startHour = startHour;
	}
	/**
	 * 获取：上课开始的小时数
	 */
	public String getStartHour() {
		return startHour;
	}
	/**
	 * 设置：上课开始的分钟数
	 */
	public void setStartMinute(String startMinute) {
		this.startMinute = startMinute;
	}
	/**
	 * 获取：上课开始的分钟数
	 */
	public String getStartMinute() {
		return startMinute;
	}
	/**
	 * 设置：上课结束的小时数
	 */
	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}
	/**
	 * 获取：上课结束的小时数
	 */
	public String getEndHour() {
		return endHour;
	}
	/**
	 * 设置：上课结束的分钟数
	 */
	public void setEndMinute(String endMinute) {
		this.endMinute = endMinute;
	}
	/**
	 * 获取：上课结束的分钟数
	 */
	public String getEndMinute() {
		return endMinute;
	}

	public int getSignNum() {
		return signNum;
	}

	public void setSignNum(int signNum) {
		this.signNum = signNum;
	}

	/**
	 * 设置：上课内容
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	/**
	 * 获取：上课内容
	 */
	public String getDescribe() {
		return describe;
	}
	/**
	 * 设置：上课日期，年月日
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * 获取：上课日期，年月日
	 */
	public String getDate() {
		return date;
	}
	/**
	 * 设置：时间段，1：上午，2：中午，3：晚上
	 */
	public void setTimeSlot(Integer timeSlot) {
		this.timeSlot = timeSlot;
	}
	/**
	 * 获取：时间段，1：上午，2：中午，3：晚上
	 */
	public Integer getTimeSlot() {
		return timeSlot;
	}
	/**
	 * 设置：课程表状态，1：正常，0：删除
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：课程表状态，1：正常，0：删除
	 */
	public Integer getStatus() {
		return status;
	}

	public CourseEntity getCourseEntity() {
		return courseEntity;
	}

	public void setCourseEntity(CourseEntity courseEntity) {
		this.courseEntity = courseEntity;
	}

	public TeacherEntity getTeacherEntity() {
		return teacherEntity;
	}

	public void setTeacherEntity(TeacherEntity teacherEntity) {
		this.teacherEntity = teacherEntity;
	}

	public ClassroomEntity getClassroomEntity() {
		return classroomEntity;
	}

	public void setClassroomEntity(ClassroomEntity classroomEntity) {
		this.classroomEntity = classroomEntity;
	}
}
