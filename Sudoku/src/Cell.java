import java.util.List;


public class Cell {

	private int value = 0;
	private List possible;
	public Cell(){
		
	}
	
	public int getValue(){
		return value;
	}
	
	public void setvalue(int v){
		value=v;
	}
	
	public void setPossible(List l){
		possible = l;
	}
	
	public void removePossible(int i){
		possible.remove(new Integer(i));
	}
	
	public List getPossible(){
		return possible;
	}
	
	public int listSize(){
		return possible.size();
	}

	public void makePossible(int val) {
		possible.add(val);
	}
}
