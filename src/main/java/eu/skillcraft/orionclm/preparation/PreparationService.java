package eu.skillcraft.orionclm.preparation;

import java.util.List;
import lombok.NonNull;

public class PreparationService {

  private NumberFactory factory;
  private NumberGeneratorFactory generatorFactory;
  private ContractRepo repo;

  void create(String type) {

    NumberFactory.ContractNumber number = factory.next(type);
    ContractNumberB numberB = generatorFactory.create().generate(type);

    Contract contract  = new Contract(number);
    repo.save(contract);
  }

  static class Contract {

    private final NumberFactory.ContractNumber number;
    private List<Section> sections;

    public Contract(@NonNull NumberFactory.ContractNumber number) {
      this.number = number;
    }
  }

  static class Section {
    String type;
    String content;
  }
}
