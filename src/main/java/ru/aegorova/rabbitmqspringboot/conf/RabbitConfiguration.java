package ru.aegorova.rabbitmqspringboot.conf;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    // for connection with RabbitMQ
    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    // to control queues
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    // to send messages (producer)
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    // queue to create pdf doc in fanout exchange
    @Bean
    public Queue fanoutReport() {
        return new Queue("report_queue");
    }

    // queue to download jpg file in fanout exchange
    @Bean
    public Queue fanoutJpgDownload() {
        return new Queue("fanout_jpg_download_queue");
    }

    // queue to write statistic in excel file in fanout exchange
    @Bean
    public Queue fanoutStatistic() {
        return new Queue("statistic_queue");
    }

    // declare fanout exchange
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("ex_fan");
    }

    // bind fanoutReport queue to fanout exchange
    @Bean
    public Binding bindingFanoutReport(){
        return BindingBuilder.bind(fanoutReport()).to(fanoutExchange());
    }

    // bind jpgDownloader queue to fanout exchange
    @Bean
    public Binding bindingJpgDownloader(){
        return BindingBuilder.bind(fanoutJpgDownload()).to(fanoutExchange());
    }

    // bind statistic queue to fanout exchange
    @Bean
    public Binding bindingStatistic(){
        return BindingBuilder.bind(fanoutStatistic()).to(fanoutExchange());
    }

    // declare direct exchange
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("ex_dir");
    }

    // queue to send email in direct exchange
    @Bean
    public Queue directSendEmail() { return new Queue("email_queue"); }

    // bind sendEmail queue to direct exchange
    @Bean
    public Binding bindingEmailSender(){
        return BindingBuilder.bind(directSendEmail()).to(directExchange()).with("confirm");
    }

    // declare topic exchange
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("ex_topic");
    }

    // queue to create doc for confirmed users in topic exchange
    @Bean
    public Queue topicConfirmed() { return new Queue("confirmed_queue"); }

    // queue to create doc for not confirmed users in topic exchange
    @Bean
    public Queue topicNotConfirmed() { return new Queue("not_confirmed_queue"); }

    // bind topicConfirmed queue to direct exchange
    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(topicConfirmed()).to(topicExchange()).with("*.confirmed");
    }

    // bind topicNotConfirmed queue to direct exchange
    @Bean
    public Binding binding2(){
        return BindingBuilder.bind(topicNotConfirmed()).to(topicExchange()).with("*.not_confirmed");
    }

}
