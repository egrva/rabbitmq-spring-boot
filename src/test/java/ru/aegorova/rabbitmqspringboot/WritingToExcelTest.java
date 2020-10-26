package ru.aegorova.rabbitmqspringboot;


import org.junit.jupiter.api.Test;
import ru.aegorova.rabbitmqspringboot.models.User;
import ru.aegorova.rabbitmqspringboot.services.StatisticServiceXls;

public class WritingToExcelTest {

    @Test
    public void writingToExcel() {
        User user = User.builder()
                .name("Anastasiia")
                .surname("Egorova")
                .passport("1234 123456")
                .age("20")
                .passport_date("16.01.2020")
                .build();
        StatisticServiceXls statisticServiceXls = new StatisticServiceXls();
        String path = "statistic/Statistic.xls";
        statisticServiceXls.writeIntoFile(path, user);
    }
}
