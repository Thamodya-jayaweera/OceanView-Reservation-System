package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BookingNoGenerator {

        public Long genarate() {
            Random random = new Random();
            int number = 100000 + random.nextInt(900000); // 6-digit number
            return (long) number;
        }
    }
