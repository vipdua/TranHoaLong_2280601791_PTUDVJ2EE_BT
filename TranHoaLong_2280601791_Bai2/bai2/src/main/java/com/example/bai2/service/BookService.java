package com.example.bai2.service;
import com.example.bai2.model.Book;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();

    // Constructor
    public BookService() {
        books.add(new Book(1, "Dế Mèn Phiêu Lưu Ký", "Tô Hoài"));
        books.add(new Book(2, "Tắt Đèn", "Ngô Tất Tố"));
        books.add(new Book(3, "Lão Hạc", "Nam Cao"));
    }

    // Lấy tất cả sách
    public List<Book> getAllBooks() {
        return books;
    }

    // Lấy sách theo ID
    public Book getBookById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Thêm sách mới
    public void addBook(Book book) {
        books.add(book);
    }

    // Cập nhật thông tin sách theo ID
    public void updateBook(int id, Book updatedBook) {
        books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(updatedBook.getTitle());
                    book.setAuthor(updatedBook.getAuthor());
                });
    }

    // Xóa sách theo ID
    public void deleteBook(int id) {
        books.removeIf(book -> book.getId() == id);
    }
}