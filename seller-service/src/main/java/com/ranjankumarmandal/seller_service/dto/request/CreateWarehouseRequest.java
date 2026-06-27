@Data
public class CreateWarehouseRequest {

    @NotBlank
    private String warehouseName;

    private String city;

    private String state;

    private String country;

    private String zipCode;
}