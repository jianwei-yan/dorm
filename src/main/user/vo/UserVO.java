package main.user.vo;

public class UserVO {
	private String id;
	private String name;
	private String pwd;
	private String no;
	private String sex;
	private String phone;
	private String role_id;
	private String isValid;


	private String dorm_no;
	private String build_id;
	private String creator;
	private String build_name;
	private String dorm_name;
	private String admin_user_id;

	public String getCreator() {
		return creator;
	}

	public String getBuild_name() {
		return build_name;
	}

	public String getDorm_name() {
		return dorm_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
	   this.no = no;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String roleId) {
		role_id = roleId;
	}

   public String getIsValid()
   {
      return isValid;
   }

   public void setIsValid(String isValid)
   {
      this.isValid = isValid;
   }

	public void setDorm_no(String dorm_no) {
	}

	public void setBuild_id(String build_id) {
   		this.build_id = build_id;
	}

	public void setCreator(String creator) {
   		this.creator = creator;
	}

	public void setBuild_name(String build_name) {
		this.build_name= build_name;
   }

	public String getDorm_no() {
   		return dorm_no;
	}

	public String getBuild_id() {
   		return build_id;
	}

	public String getAdmin_user_id() {
   		return admin_user_id;
	}

	public void setDorm_name(String dorm_name) {
   		this.dorm_name = dorm_name;
	}

	public void setAdmin_user_id(String admin_user_id) {
   		this.admin_user_id = admin_user_id;
	}
}
