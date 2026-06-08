package service;

import data.Reader;

import java.sql.SQLException;
import java.util.List;

public interface ReaderService {
    List<Reader> select();
    int insert(Reader reader);
    int update(Reader reader);
    int deleteReaders(Long[] ids);
    Reader selectByUsername(String username);
}
