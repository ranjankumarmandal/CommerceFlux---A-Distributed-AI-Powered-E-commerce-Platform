@Data
public class CreateStoreRequest {

    @NotBlank
    private String storeName;

    private String description;

    private String logoUrl;
}