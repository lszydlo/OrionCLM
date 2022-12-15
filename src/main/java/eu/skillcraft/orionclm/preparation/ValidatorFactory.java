package eu.skillcraft.orionclm.preparation;

import eu.skillcraft.orionclm.preparation.PreparationService.Section;
import java.util.List;

public class ValidatorFactory {

  ConfigPort configPort;

  public ContractValidator create() {
    return new ChainContractValidator();
  }

  public interface ContractValidator {
    CValidationErrors validate(List<Section> sections);
  }

  class ChainContractValidator implements ContractValidator {


    @Override
    public CValidationErrors validate(List<Section> sections) {
      return null;
    }
  }

  interface Checker {
    void check(List<Section> sections, ValidationConfig config, CValidationErrors errors);

  }


  public static class CValidationErrors {

  }
}
