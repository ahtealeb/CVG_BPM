package cmd;

public class Role {
	
	String roleName;
	String roleFile;
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleFile() {
		return roleFile;
	}
	public void setRoleFile(String roleFile) {
		this.roleFile = roleFile;
	}
	
	@Override
	public boolean equals(Object obj) {
		Role r = (Role) obj;
		if(r.getRoleName().equals(roleName)){
			return true;
		}else{
			return false;
		}
	}

}
