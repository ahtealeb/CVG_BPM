package cmd;

public class RAMDto {

		private String taskName;
		private String roleName;
		private int col;
		private int row;
		private String operation;
		private String newCellvalue;
		private String oldCellvalue;
		private String cellValue;
		
		
		public String getTaskName() {
			return taskName;
		}
		public void setTaskName(String taskName) {
			this.taskName = taskName;
		}
		public String getRoleName() {
			return roleName;
		}
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}
		public int getCol() {
			return col;
		}
		public void setCol(int col) {
			this.col = col;
		}
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		 
		 
		public String getNewCellvalue() {
			return newCellvalue;
		}
		public void setNewCellvalue(String newCellvalue) {
			this.newCellvalue = newCellvalue;
		}
		public String getOldCellvalue() {
			return oldCellvalue;
		}
		public void setOldCellvalue(String oldCellvalue) {
			this.oldCellvalue = oldCellvalue;
		}
		public String getCellValue() {
			return cellValue;
		}
		public void setCellValue(String cellValue) {
			this.cellValue = cellValue;
		}
		public String getOperation() {
			return operation;
		}
		public void setOperation(String operation) {
			this.operation = operation;
		}
		
		
}
