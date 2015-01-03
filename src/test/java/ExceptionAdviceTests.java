import com.kaiinui.appenginetest.error.DocUrl;
import com.kaiinui.appenginetest.error.ExceptionAdvice;
import com.kaiinui.appenginetest.error.ErrorResponse;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.junit.Assert.*;

/**
 * Created by kaiinui on 2015/01/01.
 */
public class ExceptionAdviceTests {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    class BadRequestException extends Exception {
        public BadRequestException(String message) {
            super(message);
        }
    }

    @Test
    public void testItReturnsAsJsonErrorPack() {
        ErrorResponse resp = new ExceptionAdvice().handleException(new Exception("message")).getBody();

        assertEquals(resp.message, "message");
        assertEquals(resp.code, 500);
        assertEquals(resp.url, "");
    }

    @Test
    public void testItReturnsAppropriateStatusCode() {
        ResponseEntity<ErrorResponse> res = new ExceptionAdvice().handleException(new BadRequestException("message"));

        assertEquals(res.getStatusCode().value(), HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testItReturnsResponseStatusWithResponseStatusAnnotationValue() {
        ErrorResponse resp = new ExceptionAdvice().handleException(new BadRequestException("message")).getBody();

        assertEquals(resp.message, "message");
        assertEquals(resp.code, HttpStatus.BAD_REQUEST.value());
        assertEquals(resp.url, "");
    }

    @Test
    public void testItReturnsDocUrlWithDocUrlAnnotationValue() {
        @DocUrl("http://example.com/")
        class SomeDocumentedException extends Exception {
            public SomeDocumentedException(String message) {
                super(message);
            }
        }

        ErrorResponse resp = new ExceptionAdvice().handleException(new SomeDocumentedException("message")).getBody();

        assertEquals(resp.message, "message");
        assertEquals(resp.code, 500);
        assertEquals(resp.url, "http://example.com/");
    }
}
