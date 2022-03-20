package application;

public abstract class Person {
	private String name;

	protected Person(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return "Name: " + name;
	}

}
