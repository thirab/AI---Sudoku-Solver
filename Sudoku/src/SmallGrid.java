import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SmallGrid {

	private Cell[][] gridArray= new Cell[3][3];
	
	public SmallGrid(){
		for(int i =0; i<3; i++){
			for(int j=0; j<3; j++){
				gridArray[i][j]=new Cell();
			}
		}
	}

	public void setValue(int x, int y, int change){
		
		gridArray[x][y].setvalue(change);
		
	}
	public int getValue(int x, int y){
		return gridArray[x][y].getValue();
	}
	public boolean valid(){
		boolean valid = true;
		List values = new ArrayList();
		for(int i = 0; i<3;i++){
			for(int j = 0; j<3; j++){
				if(values.contains(gridArray[i][j].getValue()) || gridArray[i][j].getValue()==0){
					valid=false;
				}else{
					values.add(gridArray[i][j].getValue());
				}
			}
		}
		
		return valid;
	}
	public List notPossibleNums(){
		List notPossible = new ArrayList();
		for(int i = 0; i<3;i++){
			for(int j = 0; j<3; j++){
				if(gridArray[i][j].getValue() != 0){
					notPossible.add(gridArray[i][j].getValue());
				}
			}
		}
		return notPossible;
	}
	
	public List possibleNums(){
		List possible = new ArrayList(Arrays.asList(1,2,3,4,5,6,7,8,9));
		List notPossible = notPossibleNums();
		for(int i =0; i < notPossible.size();i++){
			possible.remove(new Integer((Integer) notPossible.get(i)));
		}
		return possible;
	
	}
	public Cell getCell(int x, int y){
		return gridArray[x][y];
	}
	public void setPossible(int var){
		for(int i=0; i<3;i++){
			for(int j=0; j<3; j++){
				gridArray[i][j].makePossible(var);
			}
		}
		
	}
	public void removePossible(int var){
		for(int i=0; i<3;i++){
			for(int j=0; j<3; j++){
				gridArray[i][j].removePossible(var);
			}
		}
		
	}
}
