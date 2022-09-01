package com.encore.smartcity.search;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@NoArgsConstructor
public class PGSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long level;

    private String name;

    private String flowId;

    private String uri;

//    private String parentName;

    private String parentId;


}
