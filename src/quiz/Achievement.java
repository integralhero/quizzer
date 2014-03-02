package quiz;

public class Achievement {
	private String image;
	private String name;
	private String description;
	
	public Achievement(String image, String name, String description){
		this.image = image;
		this.name = name;
		this.description = description;
	}
	
	public String getImage(){
		return this.image;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
}
