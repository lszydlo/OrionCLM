package eu.skillcraft.orionclm.preparation;

import lombok.NonNull;

public class PreparationService {

  private ContractNumberFactory factory;
  private NumberGeneratorFactory generatorFactory;
  private ContractRepo repo;

  void create(String type) {

    ContractNumber number = factory.next(type);
    ContractNumberB numberB = generatorFactory.create().generate(type);

    Contract contract  = new Contract(number);
    repo.save(contract);
  }

  static class Contract {

    private final ContractNumber number;

    public Contract(@NonNull ContractNumber number) {
      this.number = number;
    }
  }
}
