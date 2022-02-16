package ru.otus.etl.batch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.etl.batch.dvo.BookDvo;
import ru.otus.etl.database.mongo.model.BookDocument;
import ru.otus.etl.service.DvoService;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class JobConfig {
    @Bean
    public Job importUserJob(JobBuilderFactory jobBuilderFactory, Step migrateStep) {
        return jobBuilderFactory.get("NoSqlToSql")
                .incrementer(new RunIdIncrementer())
                .start(migrateStep)
                .build();
    }

    @Bean
    public Step migrateStep(StepBuilderFactory stepBuilderFactory, ItemReader<BookDocument> readerMongo,
                            ItemProcessor<BookDocument, BookDvo> processorAuthor,
                            ItemWriter<BookDvo> sqlWriter) {
        return stepBuilderFactory.get("step1")
                .<BookDocument, BookDvo>chunk(10)
                .reader(readerMongo)
                .processor(processorAuthor)
                .writer(sqlWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<BookDocument> readerMongo(MongoTemplate template) {
        MongoItemReader<BookDocument> reader = new MongoItemReader<>();
        reader.setCollection("books");
        reader.setTemplate(template);
        reader.setQuery("{}");
        reader.setTargetType(BookDocument.class);
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        reader.setSort(sorts);
        return reader;
    }

    @Bean
    @StepScope
    public ItemProcessor<BookDocument, BookDvo> processorAuthor(DvoService service) {
        return service::createBookDvo;
    }

}
