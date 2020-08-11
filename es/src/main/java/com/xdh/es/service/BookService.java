package com.xdh.es.service;

import com.xdh.es.model.BookModel;
import com.xdh.es.model.domin.BookRequestVO;
import com.xdh.es.utils.Page;

public interface BookService {
    Page<BookModel> list(BookRequestVO bookRequestVO);

    BookModel detail(Integer id);

    void save(BookModel bookModel);

    void update(BookModel bookModel);

    void delete(Integer id);
}
