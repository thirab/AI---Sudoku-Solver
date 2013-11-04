import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class bigGrid {

	SmallGrid[][] game = new SmallGrid[3][3];
	public bigGrid(){
		for(int i = 0; i<3; i++){
			for(int j=0; j<3; j++){
				game[i][j]= new SmallGrid();
			}
		}
	}
	
	public SmallGrid getSmallGrid(int x, int y){
		return game[x][y];
	}
	

	/**
	 * validUpDown checks to see if a column can have the value added to it.
	 * @param x
	 * @param val
	 * @return
	 */
	public boolean validUpDown(int x, int val){
		boolean valid = true;
		List row = new ArrayList();
		row.add(val);
		for(int i = 0; i < 9; i++){
			int cell = getCellValue(x,i);
			if(cell != 0){
				if(row.contains(cell)){
					valid = false;
				}
			}
		}
		return valid;
	}
	public int getCellValue(int x, int y){
		if(x==0 && y ==0){
			return game[(x)][y].getValue(x, y);
		}else if( x==0){
			return game[(x)][(int)Math.floor(y/3)].getValue(x, y%3);
		}else if( y==0){
			return game[(int)Math.floor(x/3)][(y)].getValue(x%3, y);
		}else{
			return game[(int)Math.floor(x/3)][(int)Math.floor(y/3)].getValue(x%3, y%3);
		}
	}
	public void setCellValue(int x, int y, int val){
		if(x==0 && y ==0){
			 game[(x)][y].setValue(x, y, val);
		}else if( x==0){
			 game[(x)][(int)Math.floor(y/3)].setValue(x, y%3, val);
		}else if( y==0){
			 game[(int)Math.floor(x/3)][(y)].setValue(x%3, y, val);
		}else{
			 game[(int)Math.floor(x/3)][(int)Math.floor(y/3)].setValue(x%3, y%3, val);
		}
	}
	public SmallGrid getGrid(int x, int y){
		if(x==0 && y ==0){
			return game[(x)][y];
		}else if( x==0){
			return game[(x)][(int)Math.floor(y/3)];
		}else if( y==0){
			return game[(int)Math.floor(x/3)][(y)];
		}else{
			return game[(int)Math.floor(x/3)][(int)Math.floor(y/3)];
		}
	}
	public List validUpDownList(int x){
		List row = newList();
		for(int i = 0; i < 9; i++){
			int cell = getCellValue(x,i);
			if(cell != 0){
				row.remove(new Integer(cell));
			}
		}
		return row;
	}
	
	public List newList(){
		List l = new ArrayList();
		for(int i=1; i<10; i++){
			l.add(i);
		}
		return l;
	}
	public List validAcrossList(int y){
		List col = newList();
		for(int i = 0; i < 9; i++){
			int cell = getCellValue(i, y);
			if(cell != 0){
				col.remove(new Integer(cell));
				}
			}
		return col;
	}
	
	/**
	 * Get all valid options for each cell in a list
	 * @param x
	 * @param y
	 * @return
	 */
	public List<Integer> validOptions(int x, int y){
		List<Integer> l = new ArrayList();
		List<Integer> up = validUpDownList(x);
		List<Integer> across = validAcrossList(y);
		List<Integer> smallG = getGrid(x,y).possibleNums();
		for(int i=1; i<10; i++){
			if(up.contains(i) && across.contains(i) && smallG.contains(i)){
				l.add(new Integer(i));
			}
		}
		return l;
	}
	/**
	 * validAcross returns wether a not it is valid for a specific a row can have a val added to it. 
	 * @param y
	 * @param val
	 * @return
	 */
	public boolean validAcross(int y, int val){
		boolean valid = true;
		List row = new ArrayList();
		row.add(val);
		for(int i = 0; i < 9; i++){
			int cell = getCellValue(i,y);
			if(cell != 0){
				if(row.contains(cell)){
					valid = false;
				}
			}
		}
		return valid;
	}
	
	public boolean validSmallGrid(int x, int y, int val){
		if(getGrid(x,y).possibleNums().contains(val)){
			return true;
		}
		return false;
	}

	public boolean validLines() {
		boolean valid = true;
		
		for(int i=0; i<9; i++){
			List row = new ArrayList();
			List column = new ArrayList();
			for(int j=0; j<9; j++){
				int rValue = getCellValue(i,j);
				int cValue = getCellValue(j,i);
				if(row.contains(rValue) || rValue == 0 || column.contains(cValue) || cValue == 0){
					valid = false;
					return valid;
				}
				row.add(rValue);
				column.add(cValue);

			}
		}
		
		return valid;
	}
}
