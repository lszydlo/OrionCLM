package eu.skillcraft.orionclm.preparation;

import eu.skillcraft.orionclm.preparation.ValidatorFactory.ContractValidator;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;

public class PreparationService {

  private NumberFactory generatorFactory;
  private ContractRepo repo;
  private ValidatorFactory validatorFactory;

  void create(String type) {

    ContractNumberB numberB = generatorFactory.create().generate(type);

    Contract contract  = new Contract(numberB);
    repo.save(contract);
  }

  void update(UUID contractId, String content) {

    Contract contract  = repo.load(contractId);
    ValidatorFactory.ContractValidator val = validatorFactory.create();
    contract.update(content, val);
    repo.save(contract);
  }

  void sendToApproval(UUID contractId) {

    Contract contract  = repo.load(contractId);
    //contract.update(content);
    repo.save(contract);
  }


  static class Contract {

    private final ContractNumberB number;
    private List<Section> sections;

    public Contract(@NonNull ContractNumberB number) {
      this.number = number;
    }

    public void update(String content, ContractValidator val) {
        ValidatorFactory.CValidationErrors errors = val.validate(sections);
    }
  }

  static class Section {
    String type;
    String content;
  }
}
