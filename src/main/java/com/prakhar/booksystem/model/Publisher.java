package com.prakhar.booksystem.model;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Data
public class Publisher {

    public String publisherName;

}
