package eu.skillcraft.orionclm.patterns.decorator;

class AnimalDescriptionPrinter {

  void print() {
    AnimalFactory factory = new AnimalFactory();
    Animal animal = factory.create();
    System.out.println(animal.getDescription());
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

    public String getDescription() {
      return "Dziki " + decorated.getDescription();
    }
  }

}
