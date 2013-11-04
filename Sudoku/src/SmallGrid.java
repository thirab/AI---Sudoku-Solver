import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SmallGrid {

	private int[][] gridArray= {{0,0,0},{0,0,0},{0,0,0}};
	
	public SmallGrid(){
		
	}

	public void setValue(int x, int y, int change){
		
		gridArray[x][y]=change;
		
	}
	public int getValue(int x, int y){
		return gridArray[x][y];
	}
	public boolean valid(){
		boolean valid = true;
		List values = new ArrayList();
		for(int i = 0; i<3;i++){
			for(int j = 0; j<3; j++){
				if(values.contains(gridArray[i][j]) || gridArray[i][j]==0){
					valid=false;
				}else{
					values.add(gridArray[i][j]);
				}
			}
		}
		
		return valid;
	}
	public List notPossibleNums(){
		List notPossible = new ArrayList();
		for(int i = 0; i<3;i++){
			for(int j = 0; j<3; j++){
				if(gridArray[i][j] != 0){
					notPossible.add(gridArray[i][j]);
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
}
