package com.elasticsearch.Util;

import co.elastic.clients.elasticsearch._types.query_dsl.FuzzyQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.function.Supplier;

public class ElasticeSearchUtil {

    public static Supplier<Query> supplier(){
       Supplier<Query> supplier =  ()->Query.of(q-> q.matchAll(matchAllQuery()));
       return supplier;
    }

    public static MatchAllQuery matchAllQuery(){
        MatchAllQuery.Builder matAllQuery = new MatchAllQuery.Builder();
        return matAllQuery.build();
    }

    public static Supplier<Query> matchQueryWithFieldSuplier(String field, String value){
        Supplier<Query> supplier = ()-> Query.of(q-> q.match(matchFieldQuery(field, value)));
        return supplier;
    }

    public static Supplier<Query> fuzzySupplier(String aproximateProName){
        Supplier<Query> supplier = ()-> Query.of(q->q.fuzzy(getFuzzyQuery(aproximateProName)));
        return supplier;
    }
    public static FuzzyQuery getFuzzyQuery(String aproximateProName){
        FuzzyQuery.Builder builder = new FuzzyQuery.Builder();
        return builder.field("name").value(aproximateProName).build();
    }


    private static MatchQuery matchFieldQuery(String field, String value) {
        MatchQuery.Builder matQuery = new MatchQuery.Builder();
        return matQuery.field(field).query(value).build();
    }

}
