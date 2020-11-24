package com.michaljk.micra.models;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Builder
@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String name;
    private List<Balance> balances;
}
