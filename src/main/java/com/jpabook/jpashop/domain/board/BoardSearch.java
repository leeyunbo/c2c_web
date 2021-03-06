package com.jpabook.jpashop.domain.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardSearch {

    private String memberName;
    private String subject;
    private BoardCategory boardCategory;
    private Integer page;
}
