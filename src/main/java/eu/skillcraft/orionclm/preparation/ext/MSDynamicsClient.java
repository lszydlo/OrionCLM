package eu.skillcraft.orionclm.preparation.ext;

import java.util.Map;
import java.util.UUID;
import lombok.Getter;

public class MSDynamicsClient {

  MSProductDetails getProductDetails(UUID productId) {
    return new MSProductDetails(productId, Map.of("trems", UUID.randomUUID()), "product A");
  }

  MSDocument getDocument(UUID docID) {
    return new MSDocument(docID, "content", "title");
  }

  @Getter
  public static class MSProductDetails {

    private final UUID productId;
    private final Map<String, UUID> stringStringHashMap;
    private final String product_a;

    public MSProductDetails(UUID productId, Map<String, UUID> stringStringHashMap,
        String product_a) {
      this.productId = productId;
      this.stringStringHashMap = stringStringHashMap;
      this.product_a = product_a;
    }
  }

  @Getter
  public static class MSDocument {

    private final UUID docID;
    private final String content;
    private final String title;

    public MSDocument(UUID docID, String content, String title) {

      this.docID = docID;
      this.content = content;
      this.title = title;
    }
  }
}
