//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//class APITalkerTest {
//
//    @Test
//    void shouldTestToStringForDataType() {
//        Tuple<String> tup = new Tuple<>("first", "second");
//        assertEquals("(first, second)", tup.toString());
//    }
//
//    @Test
//    void shouldTestToStringForDifferentDataType() {
//        BigInteger n1 = Mockito.mock(BigInteger.class);
//        BigDecimal d1 = Mockito.mock(BigDecimal.class);
//        Tuple<Number> tup = new Tuple<>(n1, d1);
//        Mockito.when(n1.toString()).thenReturn("5");
//        Mockito.when(d1.toString()).thenReturn("16.2");
//        assertEquals("(5, 16.2)", tup.toString());
//    }
//
//    @Test
//    void shouldTestGetFirst() {
//        Date d1 = Mockito.mock(Date.class);
//        Date d2 = Mockito.mock(Date.class);
//        Tuple<Date> dates = new Tuple<>(d1, d2);
//        assertEquals(d1, dates.getFirst());
//    }
//
//    @Test
//    void shouldTestGetSecond() {
//        Date d1 = Mockito.mock(Date.class);
//        Date d2 = Mockito.mock(Date.class);
//        Tuple<Date> dates = new Tuple<>(d1, d2);
//        assertEquals(d2, dates.getSecond());
//    }
//}