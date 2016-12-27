package model;

public class AbstractFactory {

    public enum types {
       Clowns , Plates;
    }
	private static AbstractFactory factoryOfFactory;
	private AbstractFactory(){

	}
	
	/*public FactoryIF getFactoryOf(types t){
		if(t == types.Clowns){
			return ClownsFactory.getInstance();
		}else if(t == types.Plates){
			return PlatesFactory.getInstance();
		}
	}*/
	
	public static AbstractFactory getInstance(){
		if(factoryOfFactory == null){
			factoryOfFactory = new AbstractFactory();
		}
		return factoryOfFactory;
	}
	
	
	
}