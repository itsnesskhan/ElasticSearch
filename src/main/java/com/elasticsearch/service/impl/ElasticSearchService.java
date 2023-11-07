package com.elasticsearch.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.elasticsearch.Util.ElasticeSearchUtil;
import com.elasticsearch.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ElasticSearchService {

    private static final String INDEX_NAME = "products";

    @Autowired
    private final ElasticsearchClient elasticsearchClient;

    public List<Product> matchAllService() throws IOException {
        Supplier<Query> supplier = ElasticeSearchUtil.supplier();
        SearchResponse<Product> productSearchResponse = elasticsearchClient.
                                    search(s -> s.query(supplier.get()), Product.class);
        List<Hit<Product>> hits = productSearchResponse.hits().hits();
        ArrayList<Product> listOfProducts = new ArrayList<>();
        for (Hit<Product> hit :hits) {
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }

    public List<Product> matchFieldService(String field, String value) throws IOException {
        Supplier<Query> supplier = ElasticeSearchUtil.matchQueryWithFieldSuplier(field, value);
        SearchResponse<Product> productSearchResponse = elasticsearchClient.
                            search(s -> s.index(INDEX_NAME).query(supplier.get()), Product.class);
        List<Hit<Product>> hits = productSearchResponse.hits().hits();
        ArrayList<Product> listOfProducts = new ArrayList<>();
        for (Hit<Product> hit :hits) {
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }


    public List<Product> fuzzayFiedMatchQuery(String name, String sortBy) throws IOException {
        Supplier<Query> supplier = ElasticeSearchUtil.fuzzySupplier(name);

        SearchResponse<Product> productSearchResponse = elasticsearchClient
                                .search(s ->
                                         s.index(
                                                 INDEX_NAME)
                                        .query(supplier.get())
                                                 , Product.class);
        List<Hit<Product>> hits = productSearchResponse.hits().hits();
        ArrayList<Product> listOfProducts = new ArrayList<>();
        for (Hit<Product> hit :hits) {
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }
}
