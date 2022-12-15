package eu.skillcraft.orionclm.preparation;

import eu.skillcraft.orionclm.preparation.ValidatorFactory.ContractValidator;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.jms.JmsProperties.Template;

public class PreparationService {

  private NumberFactory generatorFactory;
  private ContractRepo repo;
  private ValidatorFactory validatorFactory;
  private TemplateRepo templateRepo;

  void create(String type) {

    ContractNumberB numberB = generatorFactory.create().generate(type);

    Contract contract  = new Contract(numberB);
    repo.save(contract);
  }

  void createFromTemplate(UUID templateId) {

    ContractTemplate template = templateRepo.load(templateId);
    UUID serviceId  = template.serviceId();

    ContractNumberB numberB = generatorFactory.create().generate(template.getType());

    Contract contract  = new Contract(numberB);
    Section section = null;//sectionProvider.get("TERMS", serviceId);
    contract.addSection(section);


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

    public void addSection(Section section) {


    }
  }

  static class Section {
    String type;
    String content;
  }

  static class ContractTemplate {

    public UUID serviceId() {
      return null;
    }

    public String getType() {
      return null;
    }
  }
}
