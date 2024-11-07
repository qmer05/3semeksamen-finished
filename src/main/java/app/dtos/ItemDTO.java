package app.dtos;

import app.enums.Category;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ItemDTO {
    private String name;
    private int weightInGrams;
    private int quantity;
    private String description;

    private String category;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private ZonedDateTime updatedAt;

    private List<BuyingOption> buyingOptions;

    @Getter
    @Setter
    public static class BuyingOption {
        private String shopName;
        private String shopUrl;
        private int price;

        @Override
        public String toString() {
            return "BuyingOption{" +
                    "shopName='" + shopName + '\'' +
                    ", shopUrl='" + shopUrl + '\'' +
                    ", price=" + price +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "name='" + name + '\'' +
                ", weightInGrams=" + weightInGrams +
                ", quantity=" + quantity +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", buyingOptions=" + buyingOptions +
                '}';
    }
}
