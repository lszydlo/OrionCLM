package eu.skillcraft.orionclm.preparation;

import eu.skillcraft.orionclm.preparation.PreparationService.Section;
import java.util.ArrayList;
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


  class PriceListChecker implements Checker {

    private final ValidationConfig config;

    public PriceListChecker(ValidationConfig config) {
      this.config = config;
    }

    @Override
    public void check(List<Section> sections, ValidationConfig config, CValidationErrors errors) {

      if (config.contains("PRICE")) {
        if (isNotValid(sections)) {
          errors.append("price list missing");
        }
      }
    }

    private boolean isNotValid(List<Section> sections) {
      ///
      return true;
    }
  }

  class TermsChecker implements Checker {

    @Override
    public void check(List<Section> sections, ValidationConfig config, CValidationErrors errors) {
      if (config.contains("TERMS")) {
        if (isNotValid(sections)) {
          errors.append("terms are missing");
        }
      }
    }

    private boolean isNotValid(List<Section> sections) {
      ///
      return true;
    }
  }

  class ContractorChecker implements Checker {

    @Override
    public void check(List<Section> sections, ValidationConfig config, CValidationErrors errors) {
      if (config.contains("CONTRACTOR")) {
        if (isNotValid(sections)) {
          errors.append("contractor missing");
        }
      }
    }

    private boolean isNotValid(List<Section> sections) {
      ///
      return true;
    }
  }


  public static class CValidationErrors {

    List<String> errors = new ArrayList<>();

    public void append(String error) {
      errors.add(error);
    }
  }
}
