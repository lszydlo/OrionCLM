package eu.skillcraft.orionclm.preparation;

import java.util.List;
import java.util.UUID;
import lombok.NonNull;

public class PreparationService {

  private NumberFactory factory;
  private NumberGeneratorFactory generatorFactory;
  private ContractRepo repo;

  void create(String type) {

    NumberFactory.ContractNumber number = factory.next(type);
    //ContractNumberB numberB = generatorFactory.create().generate(type);

    Contract contract  = new Contract(number);
    repo.save(contract);
  }

  void update(UUID contractId, String content) {


    Contract contract  = repo.load(contractId);
    //CVFalidator cval = validatorFactory.create();
    contract.update(content);
    repo.save(contract);
  }

  void sendToApproval(UUID contractId) {

    Contract contract  = repo.load(contractId);
    //contract.update(content);
    repo.save(contract);
  }


  static class Contract {

    private final NumberFactory.ContractNumber number;
    private List<Section> sections;

    public Contract(@NonNull NumberFactory.ContractNumber number) {
      this.number = number;
    }

    public void update(String content) {
//        validationErrors cval.validate(sections);
    }
  }

  static class Section {
    String type;
    String content;
  }
}
