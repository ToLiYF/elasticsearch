package com.xdh.es.service.impl;

import com.xdh.es.model.BookModel;
import com.xdh.es.model.domin.BookRequestVO;
import com.xdh.es.service.BookService;
import com.xdh.es.utils.Page;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private static final String INDEX_NAME = "book";
    private static final String INDEX_TYPE = "_doc";

    @Autowired
    private RestHighLevelClient client;

    @Override
    public Page<BookModel> list(BookRequestVO bookRequestVO) {
        return null;
    }

    @Override
    public BookModel detail(Integer id) {
        return null;
    }

    @Override
    public void save(BookModel bookModel) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", bookModel.getId());
        jsonMap.put("name", bookModel.getName());
        jsonMap.put("author", bookModel.getAuthor());
        jsonMap.put("category", bookModel.getCategory());
        jsonMap.put("price", bookModel.getPrice());
        jsonMap.put("sellTime", bookModel.getSellTime());
        jsonMap.put("sellReason", bookModel.getSellReason());
        jsonMap.put("status", bookModel.getStatus());


        IndexRequest indexRequest = new IndexRequest(INDEX_NAME,INDEX_NAME,String.valueOf(bookModel.getId()));
        indexRequest.source(jsonMap);
        client.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                String index = indexResponse.getIndex();
                String type = indexResponse.getType();
                String id = indexResponse.getId();
                long version = indexResponse.getVersion();

                log.info("Index: {}, Type: {}, Id: {}, Version: {}", index, type, id, version);

                if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                    log.info("写入文档");
                } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    log.info("修改文档");
                }
                ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
                if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                    log.warn("部分分片写入成功");
                }
                if (shardInfo.getFailed() > 0) {
                    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                        String reason = failure.reason();
                        log.warn("失败原因: {}", reason);
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

    @Override
    public void update(BookModel bookModel) {

    }

    @Override
    public void delete(Integer id) {

    }
}
