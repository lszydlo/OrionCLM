package eu.skillcraft.orionclm.patterns.decorator;

public class Cat implements Animal {

  public String doSomething() {
    return "Kot";
  }
}

class AnimalDescriptionPrinter {


  void print () {
    AnimalFactory factory = new AnimalFactory();
    Animal animal = factory.create();
    System.out.println(animal.doSomething());
  }


  private class AnimalFactory {

    public Animal create() {
      return new WildDecorator(new Cat());
    }
  }

  class WildDecorator implements Animal {

    private final Animal decorated;

    public WildDecorator(Animal decorated) {
      this.decorated = decorated;
    }

    public String doSomething() {
      return "Dziki " + decorated.doSomething();
    }
  }

}



