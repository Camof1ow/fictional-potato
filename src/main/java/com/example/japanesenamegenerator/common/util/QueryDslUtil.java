package com.example.japanesenamegenerator.common.util;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimplePath;
import org.springframework.data.domain.Sort;

public class QueryDslUtil
{
    public static OrderSpecifier<?>[] getSortedColumn(Sort sorts, EntityPathBase entityPathBase){
        return sorts.toList().stream().map(x ->{
            Order order = x.getDirection().name().equals("ASC") ? Order.ASC : Order.DESC;
            SimplePath<Object> filedPath = Expressions.path(Object.class, entityPathBase, x.getProperty());

            return new OrderSpecifier(order, filedPath).nullsLast();
        }).toArray(OrderSpecifier[]::new);
    }
}