package kakao.springboot.demo.handler;

import kakao.springboot.demo.model.Book;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class BookHandler {

    public Mono<ServerResponse> allBooks(ServerRequest request) {
        Book book1 = new Book();
        book1.setIsbn("1234");
        book1.setTitle("boooooook");

        Book book2 = new Book();
        book2.setIsbn("4321");
        book2.setTitle("books");

        Flux<Book> books = Flux.just(book1,book2);

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(books, Book.class);
    }
}
