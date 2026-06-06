package service;

import data.Reader;

import java.sql.SQLException;
import java.util.List;

public interface ReaderService {
    List<Reader> select();
}
