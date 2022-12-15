package eu.skillcraft.orionclm.preparation.ext;

import java.util.List;
import java.util.UUID;

public class SapClient {
  SapTerms getTerms(UUID serviceId) {
    return new SapTerms(serviceId, "", List.of("sdfsdf", "sdfsdfsdf"));
  }

  public static class SapTerms {
    UUID serviceId;
    String title;
    List<String> items;

    public  SapTerms(UUID serviceId, String title, List<String> items) {
      this.serviceId = serviceId;
      this.title = title;
      this.items = items;
    }
  }
}
